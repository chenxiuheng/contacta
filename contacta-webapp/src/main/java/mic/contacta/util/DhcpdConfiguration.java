/**
 * Contacta webapp, http://www.openinnovation.it - Michele Bianchi
 * Copyright(C) 1999-2012 info@openinnovation.it
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
package mic.contacta.util;


/**
 * the configuration for dhcpd3
 *
 * @author mic
 * @created Jan 11, 2009
 */
public class DhcpdConfiguration
{
  private String domainName = "nodomain.local";
  private String domainNameServers = "10.0.0.254";
  private String subnet = "10.0.2.0";
  private String netmask = "255.255.255.0";
  private String range = "10.0.2.10 10.0.2.19";
  private String ntpServers = "10.0.0.2";
  private String routers = "10.0.2.1";
  private String timeOffset = "3600";


  /*
   *
   */
  public DhcpdConfiguration()
  {
  }


  /**
   * @return the domainName
   */
  public String getDomainName()
  {
    return domainName;
  }


  /**
   * @param domainName the domainName to set
   */
  public void setDomainName(String domainName)
  {
    this.domainName = domainName;
  }


  /**
   * @return the domainNameServers
   */
  public String getDomainNameServers()
  {
    return domainNameServers;
  }


  /**
   * @param domainNameServers the domainNameServers to set
   */
  public void setDomainNameServers(String domainNameServers)
  {
    this.domainNameServers = domainNameServers;
  }


  /**
   * @return the subnet
   */
  public String getSubnet()
  {
    return subnet;
  }


  /**
   * @param subnet the subnet to set
   */
  public void setSubnet(String subnet)
  {
    this.subnet = subnet;
  }


  /**
   * @return the netmask
   */
  public String getNetmask()
  {
    return netmask;
  }


  /**
   * @param netmask the netmask to set
   */
  public void setNetmask(String netmask)
  {
    this.netmask = netmask;
  }


  /**
   * @return the range
   */
  public String getRange()
  {
    return range;
  }


  /**
   * @param range the range to set
   */
  public void setRange(String range)
  {
    this.range = range;
  }


  /**
   * @return the ntpServers
   */
  public String getNtpServers()
  {
    return ntpServers;
  }


  /**
   * @param ntpServers the ntpServers to set
   */
  public void setNtpServers(String ntpServers)
  {
    this.ntpServers = ntpServers;
  }


  /**
   * @return the routers
   */
  public String getRouters()
  {
    return routers;
  }


  /**
   * @param routers the routers to set
   */
  public void setRouters(String routers)
  {
    this.routers = routers;
  }


  /**
   * @return the timeOffset
   */
  public String getTimeOffset()
  {
    return timeOffset;
  }


  /**
   * @param timeOffset the timeOffset to set
   */
  public void setTimeOffset(String timeOffset)
  {
    this.timeOffset = timeOffset;
  }
}
