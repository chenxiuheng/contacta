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
package mic.contacta.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.Index;

import org.apache.commons.lang3.StringUtils;

import mic.organic.aaa.model.PersonModel;
import mic.organic.core.AbstractCodeModel;


/**
 *
 *
 * @author mic
 * @created Jan 1, 2009
 */
@Entity
@Table(name = "copho")
@org.hibernate.annotations.Table(appliesTo = "copho", indexes = { @Index(name="copho_code_idx", columnNames = { "code" } ) } )
public class PhoneModel extends AbstractCodeModel
{
  private ProductModel product;
  private String serialNumber = "n/a";
  private PersonModel owner;
  private String location;
  private String info;
  private String ipAddress;
  private Date lastBoot;

  private String config;
  private String directory;
  private List<SipAccountModel> sipAccountList;
  private int lineCount;


  /*
   * let use it only by jpa
   */
  public PhoneModel()
  {
    super(null);

    sipAccountList = new ArrayList<SipAccountModel>();
  }


  /*
   *
   */
  public PhoneModel(/*ProductModel product,*/ String macAddress)
  {
    this();
    setMacAddress(macAddress);
  }


  /**
   * @return the product
   */
  @ManyToOne(optional=true)
  @JoinColumn(nullable=true)
  public ProductModel getProduct()
  {
    return product;
  }


  /**
   * @param product the product to set
   */
  public void setProduct(ProductModel product)
  {
    this.product = product;
  }


  /**
   * @return the owner
   */
  @ManyToOne(optional=true)
  @JoinColumn(nullable=true)
  public PersonModel getOwner()
  {
    return owner;
  }


  /**
   * @param owner the owner to set
   */
  public void setOwner(PersonModel owner)
  {
    this.owner = owner;
  }


  /*
   *
   */
  //@Basic(optional=false)
  //@Column(nullable=false, unique=true, length=20)
  //@org.hibernate.annotations.Index(name="copho_idx_mac")
  @Transient
  public String getMacAddress()
  {
    return getCode();
  }
  public void setMacAddress(String macAddress)
  {
    // TODO mettere a posto 'sta porcata
    if (StringUtils.isNotBlank(macAddress))
    {
      super.setCode(macAddress.toLowerCase());
    }
    else
    {
      //this.macAddress = null;
      throw new RuntimeException("macAddress cannot be null");
    }
  }


  /*
   *
   */
  @Basic(optional=false)
  @Column(nullable=false, length=20)
  public String getSerialNumber()
  {
    return serialNumber;
  }
  public void setSerialNumber(String serialId)
  {
    this.serialNumber = serialId;
  }


  /**
   * @return the extension
   */
  @OneToMany(mappedBy="phone", cascade={/*CascadeType.PERSIST, CascadeType.MERGE,*/ CascadeType.REFRESH}, fetch=FetchType.LAZY)
  public List<SipAccountModel> getSipAccountList()
  {
    return sipAccountList;
  }
  public void setSipAccountList(List<SipAccountModel> sipAccountList)
  {
    this.sipAccountList = sipAccountList;
  }


  /**
   * @return the lineCount
   */
  @Basic(optional=true)
  @Column(nullable=true)
  public int getLineCount()
  {
    return lineCount;
  }


  /**
   * @param lineCount the lineCount to set
   */
  public void setLineCount(int lineCount)
  {
    this.lineCount = lineCount;
  }


  /**
   * @return
   */
  @Basic
  @Column(length=2000)
  public String getConfig()
  {
    return config;
  }


  /**
   * @param config
   */
  public void setConfig(String config)
  {
    this.config = config;
  }


  /**
   * @return
   */
  @Transient
  public boolean getHasConfig()
  {
    return StringUtils.isNotBlank(config);
  }


  /**
   * @return the directory
   */
  @Basic
  @Column(length=32000)
  public String getDirectory()
  {
    return directory;
  }

  /**
   * @param directory the directory to set
   */
  public void setDirectory(String directory)
  {
    this.directory = directory;
  }


  /**
   * @return
   */
  @Transient
  public boolean getHasDirectory()
  {
    return StringUtils.isNotBlank(directory);
  }

  /**
   * @return the location
   */
  @Basic
  @Column(length=255)
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
  @Basic(optional=true)
  @Column(length=2000)
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
   * @return the ipAddress
   */
  @Basic(optional=true)
  @Column(nullable=true)
  public String getIpAddress()
  {
    return ipAddress;
  }


  /**
   * @param ipAddress the ipAddress to set
   */
  public void setIpAddress(String ipAddress)
  {
    this.ipAddress = ipAddress;
  }


  /**
   * @return the lastBoot
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Basic(optional=true)
  @Column(nullable=true)
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

}
