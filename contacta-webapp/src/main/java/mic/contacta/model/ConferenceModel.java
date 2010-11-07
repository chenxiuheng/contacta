/* $Id: ConferenceModel.java 634 2010-05-08 11:14:28Z michele.bianchi $
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
package mic.contacta.model;

import javax.persistence.*;

import mic.organic.core.AbstractModel;


/**
 *
 *
 * @author mic
 * @created May 18, 2009
 */
@Entity
@Table(name="stmem")
public class ConferenceModel extends AbstractModel
{
  public static final String [] conferenceExcludes = { };

  private String code;
  private String pin;
  private String pinAdmin;
  private int members;


  /*
   *
   */
  @Deprecated
  public ConferenceModel()
  {
    setExcludes(conferenceExcludes);
  }


  /*
   *
   */
  public ConferenceModel(String code, int members, String pin, String pinAdmin)
  {
    this();
    this.code = code;
    this.pin = pin;
    this.pinAdmin = pinAdmin;
    this.members = members;
  }


  /**
   * @return the code
   */
  @Basic(optional=false)
  @Column(name="confno",nullable=false,length=80,unique=true)
  @org.hibernate.annotations.Index(name="stmem_idx_confno")
  public String getCode()
  {
    return code;
  }


  /**
   * @param code the code to set
   */
  public void setCode(String code)
  {
    this.code = code;
  }


  /**
   * @return the pin
   */
  @Basic
  @Column(name="pin",length=80)
  public String getPin()
  {
    return pin;
  }


  /**
   * @param pin the pin to set
   */
  public void setPin(String pin)
  {
    this.pin = pin;
  }


  /**
   * @return the pinAdmin
   */
  @Basic
  @Column(name="adminpin",length=80)
  public String getPinAdmin()
  {
    return pinAdmin;
  }


  /**
   * @param pinAdmin the pinAdmin to set
   */
  public void setPinAdmin(String pinAdmin)
  {
    this.pinAdmin = pinAdmin;
  }


  /**
   * @return the members
   */
  @Basic(optional=false)
  @Column(name="members",nullable=false)
  public int getMembers()
  {
    return members;
  }


  /**
   * @param members the members to set
   */
  public void setMembers(int members)
  {
    this.members = members;
  }

}
