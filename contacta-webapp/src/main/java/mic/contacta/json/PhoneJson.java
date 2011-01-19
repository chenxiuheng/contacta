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
package mic.contacta.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import mic.organic.gateway.AbstractJson;


/**
 * ExtenJson
 */
public class PhoneJson extends AbstractJson
{
  private String product;
  private String serialNumber = "n/a";
  private String owner;
  private String location;
  private String info;
  private String ipAddress;
  private Date lastBoot;

  private boolean hasConfig;
  //private String config;
  //private String directory;
  private List<String> accounts;

  /*
   *
   */
  public PhoneJson()
  {
    accounts = new ArrayList<String>();
  }


  /*
   *
   */
  @Override
  public String toString()
  {
    ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
    return builder.toString();
  }


  public String getMacAddress()
  {
    return getCode();
  }


  public void setMacAddress(String macAddress)
  {
    setCode(macAddress);
  }


  public List<String> getAccounts()
  {
    return accounts;
  }


  public void setAccounts(List<String> accounts)
  {
    this.accounts = accounts;
  }


  public void setIpAddress(String ipAddress)
  {
    this.ipAddress = ipAddress;
  }


  public String getIpAddress()
  {
    return ipAddress;
  }


  public void setHasConfig(boolean hasConfig)
  {
    this.hasConfig = hasConfig;
  }


  public boolean getHasConfig()
  {
    return hasConfig;
  }


  /**
   * @return the location
   */
  public String getLocation()
  {
    return location;
  }


  /**
   * @param location the location to set
   */
  public void setLocation(String location)
  {
    this.location = location;
  }


  /**
   * @return the info
   */
  public String getInfo()
  {
    return info;
  }


  /**
   * @param info the info to set
   */
  public void setInfo(String info)
  {
    this.info = info;
  }


  /**
   * @return the product
   */
  public String getProduct()
  {
    return product;
  }


  /**
   * @param product the product to set
   */
  public void setProduct(String product)
  {
    this.product = product;
  }


  /**
   * @return the owner
   */
  public String getOwner()
  {
    return owner;
  }


  /**
   * @param owner the owner to set
   */
  public void setOwner(String owner)
  {
    this.owner = owner;
  }


  /**
   * @return the lastBoot
   */
  public Date getLastBoot()
  {
    return lastBoot;
  }


  /**
   * @param lastBoot the lastBoot to set
   */
  public void setLastBoot(Date lastBoot)
  {
    this.lastBoot = lastBoot;
  }


  /**
   * @return the serialNumber
   */
  public String getSerialNumber()
  {
    return serialNumber;
  }


  /**
   * @param serialNumber the serialNumber to set
   */
  public void setSerialNumber(String serialNumber)
  {
    this.serialNumber = serialNumber;
  }

}