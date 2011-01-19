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
package mic.contacta.pmod.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import mic.contacta.model.ProductModel;
import mic.contacta.server.spi.ContactaConfiguration;
import mic.contacta.server.spi.TemplateService;
import mic.organic.vfs.OrganicVfs;


/**
 *
 * @author mic
 * @created May 30, 2009
 */
public abstract class AbstractConfigurer implements Configurer
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private ContactaConfiguration configuration;
  @Autowired protected OrganicVfs organicVfs;
  @Autowired protected TemplateService templateService;

  private String code;
  private String vendor;
  private String userAgent;
  private List<ProductModel> supportedProductList;
  private List<ProvisioningResource> cfgList;
  private Map<String,ProvisioningResource> cfgMap;
  protected List<String> resourceNameList;
  protected boolean logGeneratedConfiguration = true;
  protected FileObject logFo;
  protected FileObject confFo;
  protected FileObject binaryFo;


  /**
   *
   */
  public AbstractConfigurer(String code)
  {
    super();

    this.code = code;
    supportedProductList = new ArrayList<ProductModel>();
    resourceNameList = new ArrayList<String>();
    cfgList = new ArrayList<ProvisioningResource>();
    cfgMap = new HashMap<String,ProvisioningResource>();
  }


  /*
   * @see mic.contacta.server.ptool.Configurer#getName()
   */
  @Override
  public String getCode()
  {
    return code;
  }


  /**
   * @return the vendor
   */
  public String getVendor()
  {
    return vendor;
  }


  /**
   * @param vendor the vendor to set
   */
  protected void setVendor(String vendor)
  {
    this.vendor = vendor;
  }


  /**
   * @return the userAgent
   */
  public String getUserAgent()
  {
    return userAgent;
  }


  /**
   * @param userAgent the userAgent to set
   */
  public void setUserAgent(String userAgent)
  {
    this.userAgent = userAgent;
  }


  /*
   * @see mic.contacta.pmod.common.Configurer#getSupportedPhoneList()
   */
  @Override
  public List<ProductModel> getSupportedProductList()
  {
    return supportedProductList;
  }


  /*
   * @see mic.contacta.pmod.common.Configurer#getResourceNameList()
   */
  @Override
  public List<String> getResourceNameList()
  {
    return resourceNameList;
  }


  /*
   * @see mic.contacta.pmod.common.Configurer#createSession(mic.contacta.pmod.common.ProvisioningContext)
   */
  @Override
  public ProvisioningSession createSession(ProvisioningContext provisioningContext)
  {
    ProvisioningSession provisioningSession = new ProvisioningSession(getCode(), provisioningContext.getIpAddress());
    return provisioningSession;
  }


  /*
   *
   */
  protected void addSupportedProduct(ProductModel product)
  {
    supportedProductList.add(product);
  }


  /**
   * @param provisioningResource
   */
  protected void addCfg(ProvisioningResource provisioningResource)
  {
    cfgList.add(provisioningResource);
    cfgMap.put(provisioningResource.getName(), provisioningResource);
    resourceNameList.add(provisioningResource.getName()+" ["+provisioningResource.getClass().getName()+"]");
  }


  /**
   * lookup with the right order, the matches can be multiple, the 1st wins
   *
   * @param resourceName
   * @return
   */
  protected ProvisioningResource lookupCfg(String resourceName)
  {
    for (ProvisioningResource cfg : cfgList)
    {
      if (cfg.match(resourceName))
      {
        return cfg;
      }
    }
    return null;
  }


  /**
   * @param url
   */
  protected FileObject resolveUrl(String url)
  {
    FileObject fo = null;
    if (StringUtils.isNotBlank(url))
    {
      try
      {
        if (StringUtils.startsWith(url, "./") || !StringUtils.contains(url, ":"))
        {
          fo = organicVfs.resolve(SystemUtils.getUserDir());
          fo = fo.resolveFile(url);
        }
        else
        {
          fo = organicVfs.resolve(url);
        }
      }
      catch (FileSystemException e)
      {
        log().error("cannot lookup template configuration directory '{}': {}", configuration.getTemplateUrl(), e.getMessage());
      }
    }
    return fo;
  }


  /*
   *
   */
  protected void configurePath()
  {
    confFo = resolveUrl(configuration.getTemplateUrl());
    binaryFo = resolveUrl(configuration.getBinaryUrl());

    if (binaryFo == null)
    {
      binaryFo = confFo;
    }

    try
    {
      logFo = organicVfs.resolve(SystemUtils.getUserDir());
      logFo = logFo.resolveFile(LOGS_PATH);
    }
    catch (FileSystemException e)
    {
      log().warn("cannot logs path: {}", e.getMessage());
      log().warn("logs uploaded by the phone will be disabled!");
    }
  }

}
