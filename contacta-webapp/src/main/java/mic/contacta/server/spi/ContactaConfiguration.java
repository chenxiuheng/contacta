/* $Id: ContactaConfiguration.java 673 2010-09-17 20:58:29Z michele.bianchi $
 *
 * Contacta, http://openinnovation.it - roberto grasso, michele bianchi
 * Copyright(C) 1998-2009 [michele.bianchi@gmail.com]
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import mic.contacta.util.DhcpdConfiguration;
import mic.organic.core.OrganicConfiguration;
import mic.organic.dojo.Combo;


/**
 * A javabean representing the configuration properties
 *
 * @author mic
 * @created Jan 1, 2009
 */
@Service(ContactaConfiguration.SERVICE_NAME)
public class ContactaConfiguration extends OrganicConfiguration
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  public static final String SERVICE_NAME = "contactaConfiguration";

  private String templateUrl = "res:conf";
  private String binaryUrl;
  private String outputUrl = "./target/conf";

  private String csvFilename = "./data.csv";
  private boolean csvImport;
  private boolean dropBeforeImport;
  private String sipHost = "10.57.98.130";
  private String sipPort = "5060";
  private String sipHost2 = "10.57.98.130";
  private String sipPort2 = "5060";
  private String defaultLocale = "Italian_Italy";
  private String tftpServerName = "http://10.57.98.130:8088/d";
  private DhcpdConfiguration dhcpd = new DhcpdConfiguration();
  private boolean useDrop612 = false;
  private String contactaReplicaUrl;

  @Deprecated
  private Combo coverageCombo = new Combo (new String[] { "Assistant", "Assistant2nd" });



  /*
   *
   */
  public ContactaConfiguration()
  {
  }


  /**
   * @return the csvFilename
   */
  public String getCsvFilename()
  {
    return csvFilename;
  }


  /**
   * @param csvFilename the csvFilename to set
   */
  public void setCsvFilename(String csvFilename)
  {
    this.csvFilename = csvFilename;
  }


  /**
   * @return the csvImport
   */
  public boolean getCsvImport()
  {
    return csvImport;
  }


  /**
   * @param csvImport the csvImport to set
   */
  public void setCsvImport(boolean csvImport)
  {
    this.csvImport = csvImport;
  }


  /**
   * @return the dropBeforeImport
   */
  public boolean getDropBeforeImport()
  {
    return dropBeforeImport;
  }


  /**
   * @param dropBeforeImport the dropBeforeImport to set
   */
  public void setDropBeforeImport(boolean dropBeforeImport)
  {
    this.dropBeforeImport = dropBeforeImport;
  }


  public String getSipHost()
  {
    return sipHost;
  }


  public void setSipHost(String sipHost)
  {
    this.sipHost = sipHost;
  }


  public String getSipPort()
  {
    return sipPort;
  }


  public void setSipPort(String sipPort)
  {
    this.sipPort = sipPort;
  }


  /**
   * @return the sipHost2
   */
  public String getSipHost2()
  {
    return sipHost2;
  }


  /**
   * @param sipHost2 the sipHost2 to set
   */
  public void setSipHost2(String sipHost2)
  {
    this.sipHost2 = sipHost2;
  }


  /**
   * @return the sipPort2
   */
  public String getSipPort2()
  {
    return sipPort2;
  }


  /**
   * @param sipPort2 the sipPort2 to set
   */
  public void setSipPort2(String sipPort2)
  {
    this.sipPort2 = sipPort2;
  }


  public String getDefaultLocale()
  {
    return defaultLocale;
  }


  public void setDefaultLocale(String defaultLocale)
  {
    this.defaultLocale = defaultLocale;
  }


  public String getTftpServerName()
  {
    return tftpServerName;
  }


  public void setTftpServerName(String tftpServerName)
  {
    this.tftpServerName = tftpServerName;
  }


  /**
   * @return the dhcpd
   */
  public DhcpdConfiguration getDhcpd()
  {
    return dhcpd;
  }


  /**
   * @param dhcpd the dhcpd to set
   */
  public void setDhcpd(DhcpdConfiguration dhcpd)
  {
    this.dhcpd = dhcpd;
  }


  /**
   * @param useDrop612
   */
  public void setUseDrop612(boolean useDrop612)
  {
    this.useDrop612 = useDrop612;
  }

  /**
   * @return
   */
  public boolean getUseDrop612()
  {
    return useDrop612;
  }


  /**
   * @return the contactaReplicaUrl
   */
  public String getContactaReplicaUrl()
  {
    return contactaReplicaUrl;
  }


  /**
   * This is the URL to invoke when extensions_provile.conf must be regenerated
   * @param contactaReplicaUrl the contactaReplicaUrl to set
   */
  public void setContactaReplicaUrl(String contactaReplicaUrl)
  {
    this.contactaReplicaUrl = contactaReplicaUrl;
  }


  /**
   * @return the coverageCombo
   */
  public Combo getCoverageCombo()
  {
    return coverageCombo;
  }


  /**
   * @return the templateUrl
   */
  public String getTemplateUrl()
  {
    return templateUrl;
  }


  /**
   * @param templateUrl the templateUrl to set
   */
  public void setTemplateUrl(String templateUrl)
  {
    this.templateUrl = templateUrl;
  }


  /**
   * @return the binaryUrl
   */
  public String getBinaryUrl()
  {
    return binaryUrl;
  }


  /**
   * @param binaryUrl the binaryUrl to set
   */
  public void setBinaryUrl(String binaryUrl)
  {
    this.binaryUrl = binaryUrl;
  }


  /**
   * @return the outputUrl
   */
  public String getOutputUrl()
  {
    return outputUrl;
  }


  /**
   * @param outputUrl the outputUrl to set
   */
  public void setOutputUrl(String outputUrl)
  {
    this.outputUrl = outputUrl;
  }


}
