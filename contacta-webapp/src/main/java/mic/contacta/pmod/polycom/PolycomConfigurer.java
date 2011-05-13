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
package mic.contacta.pmod.polycom;

import static mic.organic.core.OrganicConstants.MIMETYPE_BINARY;
import static mic.organic.core.OrganicConstants.MIMETYPE_XML;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mic.contacta.domain.PhoneModel;
import mic.contacta.domain.ProductModel;
import mic.contacta.pmod.common.Contacta404Exception;
import mic.contacta.pmod.common.ProvisioningResource;
import mic.contacta.pmod.common.AbstractConfigurer;
import mic.contacta.pmod.common.ProvisioningSession;
import mic.contacta.pmod.common.ProvisioningContext;
import mic.contacta.server.InventoryService;
import mic.contacta.server.ProvisioningService;
import mic.contacta.server.ProvisioningServiceImpl;
import mic.contacta.server.TemplateService;
import mic.contacta.util.ContactaHelper;
import mic.organic.core.OrganicException;


/**
 *
 * @author mic
 * @created Jan 25, 2009
 */
@Service("polycomConfigurer")
public class PolycomConfigurer extends AbstractConfigurer
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  private static final String POLYCOM_CONF_DIR = "polycom";

  private static final String POLYCOM = "Polycom";
  private static final String POLYCOM_UA = "POLYCOM USER-AGENT";

  private static final String IMAGES_PATH = "images/spip450";

  private static final String BOOTROM_SUFFIX = "bootrom.ld";
  private static final String SIP_SUFFIX = "sip.ld";
  private static final String CFG_SUFFIX = ".cfg";
  private static final String IMAGE_SUFFIX = ".jpg";
  private static final String PHONE_CFG_SUFFIX = "-phone.cfg";
  private static final String DIRECTORY_SUFFIX = "-directory.xml";
  private static final String LOCALIZATION_SUFFIX = "-dictionary.xml";
  private static final String APPFLASH_LOG_SUFFIX = "-appFlash.log";
  private static final String APP_LOG_SUFFIX = "-app.log";
  private static final String BOOT_LOG_SUFFIX = "-boot.log";

  private static final String DEFAULT_CFG = "000000000000.cfg";
  private static final String DEFAULT_LICENSE_CFG = "000000000000-license.cfg";
  private static final String LICENSE_SUFFIX = "-license.cfg";
  private static final String SIP_CFG = "sip.cfg";
  private static final String PHONE1_CFG = "phone1.cfg";
  private static final String LOCAL_SETTINGS_CFG = "local-settings.cfg";
  private static final String WELCOME_WAV = "SoundPointIPWelcome.wav";
  private static final String SOFTKEY_CFG = "softkey.cfg";

  private static final String CUSTOM_CFG_SUFFIX = "-custom.cfg";

  @Autowired private ProvisioningService provisioningService;
  @Autowired protected TemplateService templateService;
  @Autowired private InventoryService inventoryService;

  @Autowired private PolycomDirectory polycomDirectory;
  @Autowired private PolycomPutLog polycomPutLog;

  private String romsPath = "./roms";
  private String bootromPath = "";//"bootrom412";
  private String sipromPath = "";//"sip311revb";
  private String licensePath = "./license";

  private FileObject bootromFo;
  private FileObject sipromFo;
  private FileObject localizationFo;
  private FileObject licenseFo;

  private FileObject defaultCfgFo;    //("/000000000000.cfg"))
  private FileObject defaultLicenseFo;
  private FileObject localSettingsFo;
  private FileObject macCfgFo;
  private FileObject macDirectoryFo;
  private FileObject macPhoneFo;
  private FileObject phone1CfgFo;
  private FileObject sipCfgFo;
  //private FileObject globalDirectory;
  private FileObject globalWelcome;

  boolean enableSiteLicense = false;


  /*
   *
   */
  public PolycomConfigurer()
  {
    super("polycom");
  }


  /**
   * @param romsPath the romsPath to set
   */
  public void setRomsPath(String romsPath)
  {
    this.romsPath = romsPath;
  }


  /**
   * @param bootromPath the bootromPath to set
   */
  public void setBootromPath(String bootromPath)
  {
    this.bootromPath = bootromPath;
  }


  /**
   * @param sipromPath the sipromPath to set
   */
  public void setSipromPath(String sipromPath)
  {
    this.sipromPath = sipromPath;
  }


  /**
   * @param licensePath
   */
  public void setLicensePath(String licensePath)
  {
    this.licensePath = licensePath;
  }


  /**
   * @param enableSiteLicense the enableSiteLicense to set
   */
  public void setEnableSiteLicense(boolean enableSiteLicense)
  {
    this.enableSiteLicense = enableSiteLicense;
  }


  /**
   * @param res
   */
  protected FileObject loadResource(String res)
  {
    FileObject fo = null;
    try
    {
      fo = confFo.resolveFile(res);//, MIMETYPE_XML, null);
    }
    catch (FileSystemException e)
    {
      log().error("cannot load {}", res);
    }
    return fo;
  }


  /*
   * @see mic.contacta.pmod.polycom.AbstractConfigurer#configure()
   */
  @PostConstruct
  public void configure()
  {
    configurePath();

    try
    {
      confFo = confFo.resolveFile(POLYCOM_CONF_DIR);
    }
    catch (FileSystemException e)
    {
      log().error("cannot lookup directory 'polycom' in the templateUrl");
    }

    try
    {
      FileObject romFo = null;
      if (romsPath.startsWith("./"))
      {
        romFo = organicVfs.resolve(SystemUtils.getUserDir());
        romFo = romFo.resolveFile(romsPath);
      }
      else
      {
        romFo = organicVfs.resolve(romsPath);
      }
      bootromFo = romFo.resolveFile(bootromPath);
      sipromFo =  romFo.resolveFile(sipromPath);
      localizationFo = sipromFo;
    }
    catch (FileSystemException e)
    {
      log().warn("cannot locate rom location: {}", e.getMessage());
      log().warn("Firmware upgrading will be disabled!");
      //throw new OrganicException("cannot locate rom location", e);
    }

    polycomPutLog.setLogFo(logFo);

    try
    {
      licenseFo = organicVfs.resolve(SystemUtils.getUserDir());
      licenseFo = licenseFo.resolveFile(licensePath);
    }
    catch (FileSystemException e)
    {
      log().warn("cannot locate license location: {}", e.getMessage());
      log().warn("ldap registration will be disabled!");
      //throw new OrganicException("cannot locate rom location", e);
    }

    defaultCfgFo = loadResource("default.cfg");
    defaultLicenseFo = loadResource("ldap-license.cfg");
    localSettingsFo = loadResource("local-settings.xml.ftl");
    macCfgFo = loadResource("mac.cfg.ftl");
    macDirectoryFo = loadResource("mac-directory.xml.ftl");
    macPhoneFo = loadResource("mac-phone.cfg.ftl");
    phone1CfgFo = loadResource("phone1.cfg");
    sipCfgFo = loadResource("sip.cfg");
    globalWelcome = loadResource("welcome.wav");// "/SoundPointIPWelcome.wav", MIMETYPE_WAV, null);
    //globalDirectory = loadResource("mac-directory.xml.ftl");

    addSupportedProduct(new ProductModel("SPIP_330", POLYCOM, "SPIP_330", "2.x", POLYCOM_UA));
    addSupportedProduct(new ProductModel("SPIP_450", POLYCOM, "SPIP_450", "2.x", POLYCOM_UA));
    addSupportedProduct(new ProductModel("SPIP_670", POLYCOM, "SPIP_670", "2.x", POLYCOM_UA));

    provisioningService.registerPmod(this);
  }


  /*
   *
   */
  protected FileObject loadGlobalResource(String resourceName) throws FileSystemException, Contacta404Exception
  {
    FileObject fo = null;
    if (resourceName.endsWith(BOOTROM_SUFFIX))
    {
      fo = bootromFo.resolveFile(resourceName);
    }
    else if (resourceName.endsWith(SIP_SUFFIX))
    {
      fo = sipromFo.resolveFile(resourceName);
    }
    else if (resourceName.endsWith(LOCALIZATION_SUFFIX))
    {
      fo = localizationFo.resolveFile(resourceName);
    }
    else if (resourceName.endsWith(IMAGE_SUFFIX))
    {
      fo = sipromFo.resolveFile(IMAGES_PATH).resolveFile(resourceName);
    }
    else if (resourceName.equals(PHONE1_CFG))
    {
      fo = phone1CfgFo;
    }
    else if (resourceName.equals(SIP_CFG))
    {
      fo = sipCfgFo;
    }
    else if (resourceName.equals(DEFAULT_LICENSE_CFG))
    {
      if (enableSiteLicense)
      {
        fo = defaultLicenseFo;
      }
      else
      {
        throw new Contacta404Exception();
      }
    }
    else if (resourceName.equals(DEFAULT_CFG))
    {
      fo = defaultCfgFo;
    }
    else if (resourceName.equals(WELCOME_WAV))
    {
      fo = globalWelcome;
    }
    else if (resourceName.endsWith(CUSTOM_CFG_SUFFIX))
    {
      fo = loadResource(resourceName);
    }
    return fo;
  }


  /*
   * @see mic.contacta.server.ptool.Configurer#match()
   */
  @Override
  public boolean match(ProvisioningContext provisioningContext)
  {
    return StringUtils.containsIgnoreCase(provisioningContext.getUserAgent(), "Polycom");
  }


  /*
   * @see mic.contacta.server.ptool.Configurer#loadPhoneConfiguration(java.util.Map, java.lang.String)
   *
   *
  @Transactional(readOnly=true)
  @Override
  public ProvisioningSession loadPhoneConfiguration(Map<String, Object> params, String macAddress) throws OrganicException
  {
    ProvisioningSession provisioningSession = super.loadPhoneConfiguration(macAddress);
    if (provisioningSession != null)
    {
      provisioningSession.setDefaultCfg(defaultCfgFo);
      provisioningSession.setDefaultPhoneCfg(phone1CfgFo);
      //phoneConfiguration.setMacCfg(confFo.resolveFile(mac+".cfg", MIMETYPE_XML, null));
      provisioningSession.setSipCfg(sipCfgFo);

      PhoneModel phone = inventoryService.findPhoneByMacAddress(macAddress);
      provisioningSession.setPhone(phone);
      provisioningSession.setPhoneCfg(createString(params, phone, macPhoneFo, true));
      provisioningSession.setLocalSettings(createString(params, phone, localSettingsFo, true));
      provisioningSession.setDirectoryXml(createString(params, phone, macDirectoryFo, true)/*TODO globalDirectory* /);//("/"+mac+"-directory.xml"))
      provisioningSession.setWelcomeWav(globalWelcome);
    }
    else
    {
      log().warn("no phone associated to MAC {}", macAddress);
    }
    return provisioningSession;
  }*/


  /*
   *
   */
  protected String transform(Map<String, Object> params, FileObject fo) throws OrganicException
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
  protected String createString(Map<String, Object> params, PhoneModel phone, FileObject template, boolean fromFile) throws OrganicException
  {
    params.put(ProvisioningServiceImpl.PHONE, phone);
    params.put(ProvisioningServiceImpl.ACCOUNT_LIST, phone.getSipAccountList());
    if (phone.getSipAccountList().size() > 0)
    {
      params.put(ProvisioningServiceImpl.PRIMARY_ACCOUNT, phone.getSipAccountList().get(0));
    }
    if (phone.getSipAccountList().size() > 1)
    {
      params.put(ProvisioningServiceImpl.SECONDARY_ACCOUNT, phone.getSipAccountList().get(1));
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


  /*
   * FIXME remove provisioningService callback
   *
   * @see mic.contacta.server.ptool.Configurer#provisionGet(java.lang.String, javax.servlet.ServletResponse, mic.contacta.server.ptool.PtoolService)
   */
  private boolean provisionGet(ProvisioningContext provisioningContext) throws FileSystemException, IOException, OrganicException
  {
    ServletResponse response = provisioningContext.getResponse();
    String resourceName = provisioningContext.getResourceName();

    FileObject fo;
    try
    {
      fo = loadGlobalResource(resourceName);
    }
    catch (Contacta404Exception e)
    {
      return false;
    }
    if (fo != null)
    {
      writeGlobalResource(fo, resourceName, response);
      return true;  // FIXME questo return fa proprio schifo
    }

    PhoneModel phone = null;
    String body = null;
    Map<String, Object> params = provisioningContext.getParams();//PtoolService().createParams();

    if (resourceName.endsWith(CFG_SUFFIX) && resourceName.length() == 12+4) // FIXME isMacAddress!!!! no la lunghezza, almeno non di DEFAULT_CFG, giusto!??!?!!!
    {
      body = transform(params, macCfgFo);
    }
    else if (resourceName.equals(LOCAL_SETTINGS_CFG))
    {
      body = transform(params, localSettingsFo);
    }
    else if (resourceName.endsWith(LICENSE_SUFFIX) && !resourceName.equals(DEFAULT_LICENSE_CFG))
    {
      try
      {
        fo = licenseFo.resolveFile(resourceName);
        writeGlobalResource(fo, resourceName, response);
        return true;
      }
      catch (FileSystemException e)
      {
        log().info("license {} not found, returning an empty file", resourceName);
        body = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?><licenses/>";
      }
    }
    else
    {
      String macAddress = extractMacAddressFromResourceName(resourceName);
      /*PhoneModel*/ phone = inventoryService.phoneByMacAddress(macAddress);
      if (resourceName.endsWith(DIRECTORY_SUFFIX))
      {
        params.put("phone", phone);

        body = createString(params, phone, macDirectoryFo, true);
      }
      else if (resourceName.endsWith(PHONE_CFG_SUFFIX))
      {
        String config = phone.getConfig();
        params.put("config", config);
        params.put("phone", phone);
        body = createString(params, phone, macPhoneFo, true);
      }
      /*else if (resourceName.endsWith(CFG_SUFFIX))
      {
        log().info("try loading local geneci resource: {}", resourceName);
        loadGlobalResource(resourceName);
      }*/
      else
      {
        log().warn("unsupported resource: {}", resourceName);
      }
    }

    if (body != null)
    {
      writeLocalResource(phone, resourceName, body, response);
      return true;
    }
    else
    {
      return false;
    }
  }


  /*
   * @see mic.contacta.server.ptool.Configurer#provisionPut(javax.servlet.ServletRequest, java.lang.String, java.lang.String)
   */
  private boolean provisionPut(ProvisioningContext provisioningContext, String macNumeric) throws IOException
  {
    ServletRequest request = provisioningContext.getRequest();
    String resourceName = provisioningContext.getResourceName();


    boolean tmp = false;
    //log().info("{} put {}",  macNumeric, resourceName);
    if (resourceName.equals(macNumeric+BOOT_LOG_SUFFIX))
    {
      tmp = polycomPutLog.handleBootlogPut(request, resourceName);
    }
    else if (resourceName.equals(macNumeric+DIRECTORY_SUFFIX))
    {
      String body = polycomPutLog.getPutContent(request, resourceName);
      if (body != null)
      {
        tmp = true;
        polycomDirectory.handleDirectoryPut(request, resourceName, body);
      }
    }
    else if (resourceName.equals(macNumeric+APP_LOG_SUFFIX))
    {
      tmp = polycomPutLog.handleApplogPut(request, resourceName);
    }
    else if (resourceName.equals(macNumeric+APPFLASH_LOG_SUFFIX))
    {
      tmp = polycomPutLog.handleAppflashPut(request, resourceName);
    }
    else if (resourceName.equals(macNumeric+PHONE_CFG_SUFFIX))
    {
      tmp = polycomPutLog.handlePhoneConfigPut(request, resourceName);
    }
    else
    {
      tmp = polycomPutLog.handlePut(request, resourceName);
    }

    if (!tmp)
    {
      log().warn("PUT body is blank...");
    }
    return tmp;
  }


  /*
   * TODO io qui metterei prima il find del phone/session e poi invocherei il configurer, al ritorno
   * fare l'update su jpa e sul fs se necessario.
   */
  @Transactional
  @Override
  public ProvisioningResource doProvisioning(ProvisioningContext provisioningContext, ProvisioningSession provisioningSession) throws IOException
  {
    boolean tmp = false;
    ProvisioningResource cfg = null;
    try
    {
      if (provisioningContext.getMethod().equalsIgnoreCase("GET"))
      {
        //Map<String, Object> params = provisioningService.createParams();  // TODO i'm time consuming, and often they dont need me :(  back to phoneConfiguration with cache/session is preferrable
        tmp = provisionGet(provisioningContext);
      }
      else if (provisioningContext.getMethod().equalsIgnoreCase("PUT"))
      {
//      String macAddress = pathInfo.substring(1, pathInfo.indexOf('/', 1)).toLowerCase();
//      String macNumeric = ContactaUtils.numericMacAddress(macAddress);//"0004f21fe67a";
//      String resourceName = pathInfo.substring(pathInfo.indexOf('/', 1)+1);
//      log().info("method={}, resourceName={}, mac={}", new Object[] { method, resourceName, macNumeric });
        String macNumeric = provisioningContext.getResourceName().substring(0, 12);//"0004f21fe67a"
        try
        {
          tmp = provisionPut(provisioningContext, macNumeric);
        }
        catch (Exception e)
        {
          if (e instanceof IOException)
          {
            throw (IOException)e;
          }
          else
          {
            log().warn(e.getMessage(), e);
            tmp = false;
          }
        }
      }
      else
      {
        ContactaHelper.loggerProvisioning().warn("unsupported method: {}", provisioningContext.getMethod());
        log().warn("unsupported method: {}", provisioningContext.getMethod());
      }
    }
    catch (OrganicException e)
    {
      log().warn(e.getMessage());
      throw new IOException(e);
    }
    catch (IOException e) // org.mortbay.jetty.EofException, caused by IOException "Broken pipe"
    {
      ContactaHelper.loggerProvisioning().debug("transmission terminated (probably by the client): {}", e.getMessage());
      //throw new ServletException(e);
      tmp = true;
    }
    return cfg;
  }


  /*
   * @see mic.contacta.server.ptool.Configurer#loadPhoneConfiguration(java.util.Map, java.lang.String)
   *
  @Transactional(readOnly=true)
  public ProvisioningSession loadPhoneConfiguration(String macAddress) throws OrganicException
  {
    ProvisioningSession provisioningSession = null;
    PhoneModel phone = inventoryService.findPhoneByMacAddress(macAddress);
    if (phone != null)
    {
      provisioningSession = new ProvisioningSession(this, phone);
      provisioningSession.setBootrom(bootromFo);
      provisioningSession.setSiprom(sipromFo);
      provisioningSession.setLocalization(localizationFo);
    }
    else
    {
      log().warn("no phone associated to MAC {}", macAddress);
    }
    return provisioningSession;
  }*/


  /*
   *
   */
  protected String extractMacAddressFromResourceName(String resourceName)
  {
    String macAddress = resourceName.substring(0, 12);
    return macAddress;
  }


  /**
   * write the resource (file, xml, whatever) to the outputstream directed to the phone
   * the resource is named local, as it is per-phone and vs the global that is equals
   * for any phone
   */
  protected void writeLocalResource(PhoneModel phone, String resourceName, String body, ServletResponse response) throws IOException
  {
    if (logGeneratedConfiguration  && phone != null)
    {
      try
      {
        FileObject fo = logFo.resolveFile(CONFIGURATIONS);//.resolveFile(phone.getMacAddress());
        fo.createFolder();
        fo = fo.resolveFile(resourceName);
        fo.getContent().getOutputStream().write(body.getBytes());
        fo.close();
      }
      catch (IOException e)
      {
        log().warn("cannot write a copy of {}: {}", resourceName, e.getMessage());
      }
    }
    response.setContentType(MIMETYPE_XML);
    response.setContentLength(body.length());
    response.getOutputStream().write(body.getBytes());
  }


  /**
   * write the resource (file, xml, whatever) to the outputstream directed to the phone
   * global means equals for any phone
   */
  protected void writeGlobalResource(FileObject resourceFo, String resourceName, ServletResponse response) throws FileSystemException, IOException
  {
    int size = (int)(resourceFo.getContent().getSize());
    String contentType = resourceFo.getContent().getContentInfo().getContentType();
    log().debug("downloading: res={} [{}] {}", new Object[] { resourceName, contentType, resourceFo.getName().getPath() });
    //log().debug("size/buffer={}/{} for {}", new Object[] { fo.getContent().getSize(), response.getBufferSize(), fo.getName().getPath() });
    if (StringUtils.isBlank(contentType))
    {
      log().info("############################################ MIMETYPE_BINARY for {}", resourceName);
      contentType = MIMETYPE_BINARY;
    }
    try
    {
      FileObject fo = logFo.resolveFile(CONFIGURATIONS);//.resolveFile(phone.getMacAddress());
      fo.createFolder();
      fo = fo.resolveFile(resourceName);
      FileUtil.copyContent(resourceFo, fo);
      fo.close();
    }
    catch (IOException e)
    {
      log().warn("cannot write a copy of {}: {}", resourceName, e.getMessage());
    }
    response.setContentType(contentType);
    response.setContentLength(size);
    FileUtil.writeContent(resourceFo, response.getOutputStream());
  }


  /*
   * @see mic.contacta.pmod.common.Configurer#getResourceNameList()
   */
  @Override
  public List<String> getResourceNameList()
  {
    // TODO Auto-generated method stub
    return null;
  }


}
