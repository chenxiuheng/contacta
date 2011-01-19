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
import org.apache.commons.vfs.FileObject;


/**
 * @author mic
 * @created Jan 1, 2009
 */
public class ProvisioningSession
{
  private String code;
  private String ipAddress;
  private String macAddress;
  private String productCode;
  private String configurerCode;

  private Map<String,ProvisioningResource> resourceMap;
  private List<ProvisioningResource> resourceList;
  private ProvisioningResource lastCfg;
  private ProvisioningResource globalCfg;
  private ProvisioningResource cfg;

  private FileObject bootrom;
  private FileObject siprom;
  private FileObject localization;
  private FileObject sipCfg;
  private FileObject macCfg;
  private FileObject defaultCfg;
  private String phoneCfg;
  private String localSettings;
  private String directoryXml;
  private FileObject welcomeWav;
  private FileObject defaultPhoneCfg;



  /**
   * @param thomsonConfigurer
   *
   */
  public ProvisioningSession(String configurerCode, String ipAddress)
  {
    super();

    this.configurerCode = configurerCode;
    this.code = ipAddress;
    this.ipAddress = ipAddress;

    resourceMap = new HashMap<String, ProvisioningResource>();
    resourceList = new ArrayList<ProvisioningResource>();
  }


  /**
   * @return
   */
  public String getCode()
  {
    return code;
  }


  /**
   * @return
   */
  public String getConfigurerCode()
  {
    return configurerCode;
  }


  /**
   * @return the macAddress
   */
  public String getMacAddress()
  {
    return macAddress;
  }


  /**
   * @param macAddress the macAddress to set
   */
  public void setMacAddress(String macAddress)
  {
    this.macAddress = macAddress;
  }


  /**
   * @return the productCode
   */
  public String getProductCode()
  {
    return productCode;
  }


  /**
   * @param productCode the productCode to set
   */
  public void setProductCode(String productCode)
  {
    this.productCode = productCode;
  }


  /**
   * @return the bootrom
   */
  public FileObject getBootrom()
  {
    return bootrom;
  }


  /**
   * @param bootrom the bootrom to set
   */
  public void setBootrom(FileObject bootrom)
  {
    this.bootrom = bootrom;
  }


  /**
   * @return the siprom
   */
  public FileObject getSiprom()
  {
    return siprom;
  }


  /**
   * @param siprom the siprom to set
   */
  public void setSiprom(FileObject siprom)
  {
    this.siprom = siprom;
  }


  /**
   * @return the localization
   */
  public FileObject getLocalization()
  {
    return localization;
  }


  /**
   * @param localization the localization to set
   */
  public void setLocalization(FileObject localization)
  {
    this.localization = localization;
  }


  /**
   * @return the sipCfg
   */
  public FileObject getSipCfg()
  {
    return sipCfg;
  }


  /**
   * @param sipCfg the sipCfg to set
   */
  public void setSipCfg(FileObject sipCfg)
  {
    this.sipCfg = sipCfg;
  }


  /**
   * @return the macCfg
   */
  public FileObject getMacCfg()
  {
    return macCfg;
  }


  /**
   * @param macCfg the macCfg to set
   */
  public void setMacCfg(FileObject macCfg)
  {
    this.macCfg = macCfg;
  }


  /**
   * @return the defaultCfg
   */
  public FileObject getDefaultCfg()
  {
    return defaultCfg;
  }


  /**
   * @param defaultCfg the defaultCfg to set
   */
  public void setDefaultCfg(FileObject defaultCfg)
  {
    this.defaultCfg = defaultCfg;
  }


  /**
   * @return the defaultPhoneCfg
   */
  public FileObject getDefaultPhoneCfg()
  {
    return defaultPhoneCfg;
  }


  /**
   * @param defaultPhoneCfg the defaultPhoneCfg to set
   */
  public void setDefaultPhoneCfg(FileObject defaultPhoneCfg)
  {
    this.defaultPhoneCfg = defaultPhoneCfg;
  }


  /**
   * @return the phoneCfg
   */
  public String getPhoneCfg()
  {
    return phoneCfg;
  }


  /**
   * @param phoneCfg the phoneCfg to set
   */
  public void setPhoneCfg(String phoneCfg)
  {
    this.phoneCfg = phoneCfg;
  }


  /**
   * @return the localSettings
   */
  public String getLocalSettings()
  {
    return localSettings;
  }


  /**
   * @param localSettings the localSettings to set
   */
  public void setLocalSettings(String localSettings)
  {
    this.localSettings = localSettings;
  }


  /**
   * @return the directoryXml
   */
  public String getDirectoryXml()
  {
    return directoryXml;
  }


  /**
   * @param directoryXml the directoryXml to set
   */
  public void setDirectoryXml(String directoryXml)
  {
    this.directoryXml = directoryXml;
  }


  /**
   * @return the welcomeWav
   */
  public FileObject getWelcomeWav()
  {
    return welcomeWav;
  }


  /**
   * @param welcomeWav the welcomeWav to set
   */
  public void setWelcomeWav(FileObject welcomeWav)
  {
    this.welcomeWav = welcomeWav;
  }


  /**
   *
   * @param cfg
   */
  public void addResource(ProvisioningResource cfg)
  {
    resourceList.add(cfg);
    resourceMap.put(cfg.getName(), cfg);
    lastCfg = cfg;
  }


  /**
   * @param cfg
   */
  public void setPhoneMac(ProvisioningResource cfg)
  {
    this.cfg = cfg;
    addResource(cfg);
  }


  /**
   * @return the globalCfg
   */
  public ProvisioningResource getGlobalCfg()
  {
    return globalCfg;
  }


  /**
   * @param globalCfg the globalCfg to set
   */
  public void setGlobalCfg(ProvisioningResource globalCfg)
  {
    this.globalCfg = globalCfg;
    addResource(cfg);
  }


  /**
   * @return the lastCfg
   */
  public ProvisioningResource getLastCfg()
  {
    return lastCfg;
  }

}
