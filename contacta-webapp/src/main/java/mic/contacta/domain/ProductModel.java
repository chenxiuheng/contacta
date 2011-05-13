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
package mic.contacta.domain;

import javax.persistence.*;
import org.hibernate.annotations.Index;


/**
 * Phone Vendor Model
 *
 * @author mic
 * @created Jan 1, 2009
 */
@Entity
@Table(name = "coprd")
@org.hibernate.annotations.Table(appliesTo = "coprd", indexes = { @Index(name="coprd_code_idx", columnNames = { "code" } ) } )
public class ProductModel extends AbstractJsonModel
{
  public static final String GENERIC = "generic";

  private String vendor;
  private String model;
  private String version;
  private String userAgent;
  private String[] resourceNameList;


  /*
   * let use it only by jpa
   */
  @Deprecated
  public ProductModel()
  {
    super(null);
  }


  /*
   *
   */
  public ProductModel(String code, String vendor, String model, String version, String userAgent)
  {
    super(code);
    this.vendor = vendor;
    this.model = model;
    this.version = version;
    this.userAgent = userAgent;
  }


  /**
   * @return the vendor
   */
  @Basic(optional=false)
  @Column(nullable=false, length=20)
  public String getVendor()
  {
    return vendor;
  }


  /**
   * @param vendor the vendor to set
   */
  public void setVendor(String vendor)
  {
    this.vendor = vendor;
  }


  /**
   * @return the model
   */
  @Basic(optional=false)
  @Column(nullable=false, length=20)
  public String getModel()
  {
    return model;
  }


  /**
   * @param model the model to set
   */
  public void setModel(String model)
  {
    this.model = model;
  }


  /**
   * @return the version
   */
  @Basic(optional=false)
  @Column(nullable=false, length=20)
  public String getVersion()
  {
    return version;
  }


  /**
   * @param version the version to set
   */
  public void setVersion(String version)
  {
    this.version = version;
  }


  /**
   * @return the userAgent
   */
  @Basic(optional=false)
  @Column(nullable=false, length=200)
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


  /**
   * @return the resourceNameList
   */
  @Basic(optional=true)
  @Column(nullable=true,length=10000)
  public String[] getResourceNameList()
  {
    return resourceNameList;
  }


  /**
   * @param resourceNameList the resourceNameList to set
   */
  public void setResourceNameList(String[] resourceNameList)
  {
    this.resourceNameList = resourceNameList;
  }




}
