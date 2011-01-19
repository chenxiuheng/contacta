/**
 * Contacta, http://www.openinnovation.it - Michele Bianchi, Roberto Grasso
 * Copyright(C) 1999-2011 info@openinnovation.it
 *
 * This program is free software; you can redistribute it and/or modify it under the terms
 * of the GNU General Public License v2 as published by the Free Software Foundation
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. http://gnu.org/licenses/gpl-2.0.txt
 *
 * You should have received a copy of the GNU General Public License along with this program;
 * if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1301, USA.
 */
package mic.contacta.server.spi;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs.FileObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import mic.contacta.model.PhoneModel;
import mic.contacta.model.ProductModel;
import mic.contacta.model.SipAccountModel;
import mic.contacta.pmod.common.ProvisioningResource;
import mic.contacta.pmod.common.Configurer;
import mic.contacta.pmod.common.ProvisioningContext;
import mic.contacta.pmod.common.ProvisioningSession;
import mic.contacta.server.api.ContactaException;
import mic.organic.aaa.spi.AddressbookService;
import mic.organic.core.OrganicException;


/**
 *
 *
 * @author mic
 * @created Nov 30, 2008
 */
@Service(ProvisioningService.SERVICE_NAME)
public class ProvisioningServiceImpl implements ProvisioningService
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private ContactaConfiguration configuration;
  @Autowired protected TemplateService templateService;
  @Autowired private AddressbookService addressbookService;
  @Autowired private SipService sipService;
  @Autowired private InventoryService inventoryService;

  @Autowired(required=false) private AutoCreator autoCreator;
  @Resource(name="provisioningSessionCache") private Cache provisioningSessionCache;

  private Map<String,Configurer> configurerMap;


  /*
   *
   */
  public ProvisioningServiceImpl()
  {
    super();

    configuration = new ContactaConfiguration();
    configurerMap = new HashMap<String,Configurer>();
  }


  /*
   *
   */
  @PostConstruct
  public void configure() throws OrganicException
  {
  }


  /**
   * @return the configurerList
   */
  public List<Configurer> getConfigurerList()
  {
    return new ArrayList<Configurer>(configurerMap.values());
  }


  /**
   * @param configurerCode
   * @return
   */
  private Configurer getConfigurer(String configurerCode)
  {
    return configurerMap.get(configurerCode);
  }


  /**
   * @param phoneConfiguration TODO
   * @return
   */
  private Configurer matchConfigurer(ProvisioningContext provisioningContext)
  {
    for (Configurer configurer : configurerMap.values())
    {
      boolean match = configurer.match(provisioningContext);
      if (match == true)
      {
        log().info("match configurer={} for res={} ua={}", new Object[] { configurer.getCode(), provisioningContext.getResourceName(), provisioningContext.getUserAgent() });
        return configurer;
      }
    }
    return null;
  }


  /*
   *
   */
  private PhoneModel findPhoneByMacAddress(String macAddress) throws ContactaException
  {
    PhoneModel phone = inventoryService.findPhoneByMacAddress(macAddress);
    if (phone == null)
    {
      log().warn("Phone macaddress {} had not been registered by administrator. It will not be provisioned", macAddress);
      throw new ContactaException("phone not found");
    }
    return phone;
  }


  /**
   * @param provisioningContext
   * @return
   */
  private ProvisioningSession createPhoneSession(ProvisioningContext provisioningContext)
  {
    ProvisioningSession provisioningSession = null;
    Element element = provisioningSessionCache.get(provisioningContext.getIpAddress());
    if (element != null)
    {
      provisioningSession = (ProvisioningSession)(element.getObjectValue());
      log().info("provisioningSession: "+provisioningSession.getCode());
    }
    else
    {
      Configurer configurer = matchConfigurer(provisioningContext);
      if (configurer != null)
      {
        provisioningSession = configurer.createSession(provisioningContext);
        if (provisioningSession != null)
        {
          element = new Element(provisioningSession.getCode(), provisioningSession);
          provisioningSessionCache.put(element);
        }
      }
      else
      {
        log().info("cannot match phoneContext: {}", provisioningContext);
      }
    }
    return provisioningSession;
  }


  /**
   * @param macText
   * @param productCode
   * @return the autoCreator
   */
  private PhoneModel autoCreatePhone(String macText, String productCode)
  {
    PhoneModel phone = null;
    if (autoCreator != null)
    {
      phone = autoCreator.autocreator(macText, productCode);
    }
    return phone;
  }


  /*
   * Generate the configuration file for dhcpd, asterisk and eventually phones
   */
  //@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  //@Override
  private void generate()
  {
    log().info("The configuration was disabled, dhcp and asterisk files were not generated!");
    /*
    Map<String, Object> params = createParams();
    try
    {
      //templateService.generate("polycom/mac.cfg.ftl", "000000000000.cfg", params);

      templateService.generate("dhcp/dhcpd.conf.ftl", "dhcpd.conf", params);
      templateService.generate("asterisk/macro_extensions.conf.ftl", "asterisk/macro_extensions.conf", params, true);
      templateService.generate("asterisk/extensions.conf.ftl", "asterisk/extensions.conf", params, true);
      templateService.generate("asterisk/sip.conf.ftl", "asterisk/sip.conf", params);
      templateService.generate("asterisk/users.conf.ftl", "asterisk/users.conf", params);
      templateService.generate("asterisk/voicemail.conf.ftl", "asterisk/voicemail.conf", params);
    }
    catch (Exception e)
    {
      log().warn("The configuration was interrupted, not all files were generated!", e);
    }
    */
  }


  /*
   *
   */
  private String transform(Map<String, Object> params, FileObject fo) throws OrganicException
  {
    String path = fo.getName().getPath();
    String body = null;
    try
    {
      // = templateService.getTemplateBaseFo().resolveFile(path);
      body = templateService.generate(fo, params);
      log().debug("generated body from: {}, length={}", path, body.length());
    }
    catch (Exception e)
    {
      log().warn("cannot transform: {}", path);
      throw new OrganicException("cannot transform: "+path, e);
    }
    return body;
  }


  /*
   *
   */
  private String createString(Map<String, Object> params, PhoneModel phone, FileObject template, boolean fromFile) throws OrganicException
  {
    params.put(PHONE, phone);
    params.put(ACCOUNT_LIST, phone.getSipAccountList());
    if (phone.getSipAccountList().size() > 0)
    {
      params.put(PRIMARY_ACCOUNT, phone.getSipAccountList().get(0));
    }
    if (phone.getSipAccountList().size() > 1)
    {
      params.put(SECONDARY_ACCOUNT, phone.getSipAccountList().get(1));
    }
    String body = null;
    try
    {
      body = templateService.generate(template, params);
      log().debug("generated body from: {}, length={}", fromFile ? template : "[string]", body.length());
    }
    catch (Exception e)
    {
      log().warn("cannot transform: {}", template);
      throw new OrganicException("cannot transform: "+template, e);
    }
    return body;
  }


  /**
   * @return
   */
  //@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  //@Override
  private Map<String, Object> createParams()
  {
    Map<String,Object> params = new HashMap<String,Object>();
    params.put("sipAccountList", sipService.findAccountList());
    params.put("phoneList", inventoryService.findPhoneList());
    params.put("personList", addressbookService.personList());
    params.put("organizationList", addressbookService.organizationList());

    params.put("speedDialList", null);

    return params;
  }


  /*
   * @see mic.contacta.server.ptool.PtoolService#registerPmod(mic.contacta.server.ptool.Configurer)
   */
  @Transactional(propagation=Propagation.REQUIRED)
  @Override
  public void registerPmod(Configurer configurer)
  {
    for (ProductModel supportedProduct : configurer.getSupportedProductList())
    {
      ProductModel product = inventoryService.findProductByCode(supportedProduct.getCode());
      if (product != null)
      {
        log().info("product {} / {} is already registered", configurer.getCode(), product.getCode());
      }
      else
      {
        log().info("adding supported product {} / {}", configurer.getCode(), supportedProduct.getCode());
        product = inventoryService.createProduct(supportedProduct);
      }
      if (configurer.getResourceNameList() != null)
      {
        product.setResourceNameList(configurer.getResourceNameList().toArray(new String[0]));
        inventoryService.updateProduct(product);
      }
    }
    //configurerList.add(configurer);
    configurerMap.put(configurer.getCode(), configurer);
  }


  /**
   * @param request
   * @param response
   * @return
   */
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  @Override
  public ProvisioningContext createProvisioningContext(HttpServletRequest request, HttpServletResponse response)
  {
    ProvisioningContext provisioningContext = new ProvisioningContext(request, response);
    return provisioningContext;
  }


  /*
   * @see mic.contacta.server.ptool.PtoolService#doProvisioning(mic.contacta.server.ptool.PhoneRequest)
   */
  @Transactional(propagation=Propagation.REQUIRED)
  @Override
  public boolean doProvisioning(ProvisioningContext provisioningContext) throws IOException
  {
    ProvisioningSession provisioningSession = createPhoneSession(provisioningContext);

    boolean r = false;
    if (provisioningSession == null)
    {
      log().warn("cannot provision phoneContext: {}", provisioningContext);
    }
    else
    {
      Configurer configurer = getConfigurer(provisioningSession.getConfigurerCode());
      ProvisioningResource cfg = configurer.doProvisioning(provisioningContext, provisioningSession);

      String macAddress = provisioningSession.getMacAddress();
      PhoneModel phone = null;
      if (StringUtils.isNotBlank(macAddress))
      {
        try
        {
          phone = findPhoneByMacAddress(macAddress);
        }
        catch (ContactaException e)
        {
          log().info("{}", e.getMessage());

          phone = autoCreatePhone(macAddress, provisioningSession.getProductCode());
          provisioningContext.setPhone(phone);
        }
      }

      Map<String, Object> params = provisioningContext.getParams();
      params.put("contacta", configuration);
      params.put("baseUrl", "http://"+configuration.getServerHost()+":"+configuration.getServerPort()); // URGENT 8088
      long ts = 0;
      if (phone != null /*&& cfg.needParams()*/)
      {

        phone.setIpAddress(provisioningContext.getIpAddress());
        phone.setLastBoot(new Date());
        inventoryService.updatePhone(phone);

        params.put(PHONE, phone);
        params.put(ACCOUNT_LIST, phone.getSipAccountList());
        for (SipAccountModel sip : phone.getSipAccountList())
        {
          ts = Math.max(sip.getVersion().getTime(), ts);
        }
      }
      if (phone == null && cfg != null && cfg.needParams())
      {
        log().error("cannot provision this phone, it's not managed by the app, pliz add me in the gui!");
      }
      else if (cfg != null)
      {
        HttpServletResponse response = provisioningContext.getResponse();
        cfg.write(provisioningContext.getResourceName(), ts, response, params);

        //      String body = cfg.getBody();
        //      response.setContentType(MIMETYPE_XML);
        //      response.setContentLength(body.length());
        //      response.getOutputStream().write(body.getBytes());
        r = true;
      }
    }
    return r;
  }


}
