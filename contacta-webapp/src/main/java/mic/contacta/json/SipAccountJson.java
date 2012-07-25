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
package mic.contacta.json;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import mic.contacta.domain.SipAccountModel.Presence;
import mic.organic.gateway.AbstractJson;


/**
 *
 *
 * @author mic
 * @created May 17, 2009
 */
public class SipAccountJson extends AbstractJson
{
  private String firstName;
  private String lastName;
  private String fullName;
  private String email;

  private String cocLogin;
  private String cocPin;

  private String login;
  private String password;
  private Presence presence;
  private String profileName;
  private String profileOptions;
  private String context;
  private String callgroup;
  private String pickupgroup;
  private boolean callwaiting;
  private int ringTimeout;

  private boolean vmEnabled;
  private String vmPin;
  private boolean vmSendEmail;

  private String phone;

  // from SipUser
  private String state;


  /**
   *
   */
  public SipAccountJson()
  {
    super();
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


  /**
   * @return the firstName
   */
  public String getFirstName()
  {
    return firstName;
  }


  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }


  /**
   * @return the lastName
   */
  public String getLastName()
  {
    return lastName;
  }


  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }


  /**
   * @return the email
   */
  public String getEmail()
  {
    return email;
  }


  /**
   * @param email the email to set
   */
  public void setEmail(String email)
  {
    this.email = email;
  }


  /**
   *
   */
  public String getLogin()
  {
    return this.login;
  }


  /**
   *
   * @param login
   */
  public void setLogin(String login)
  {
    this.login = login;
  }


  /**
   *
   * @return
   */
  public String getPassword()
  {
    return this.password;
  }


  /**
   *
   * @param password
   */
  public void setPassword(String password)
  {
    this.password = password;
  }


  /**
   * @return the presence
   */
  public Presence getPresence()
  {
    return presence;
  }


  /**
   * @param presence the presence to set
   */
  public void setPresence(Presence presence)
  {
    this.presence = presence;
  }


  /**
   * @return the ringTimeout
   */
  public int getRingTimeout()
  {
    return ringTimeout;
  }


  /**
   * @param ringTimeout the ringTimeout to set
   */
  public void setRingTimeout(int ringTimeout)
  {
    this.ringTimeout = ringTimeout;
  }


  /**
   * @return the profileName
   */
  public String getProfileName()
  {
    return profileName;
  }


  /**
   * @param profileName the profileName to set
   */
  public void setProfileName(String profile)
  {
    this.profileName = profile;
  }


  /**
   * @return the profileOptions
   */
  public String getProfileOptions()
  {
    return profileOptions;
  }


  /**
   * @param profileOptions the profileOptions to set
   */
  public void setProfileOptions(String profileOptions)
  {
    this.profileOptions = profileOptions;
  }


  public boolean getVmEnabled()
  {
    return vmEnabled;
  }


  public void setVmEnabled(boolean vmEnabled)
  {
    this.vmEnabled = vmEnabled;
  }


  public String getVmPin()
  {
    return vmPin;
  }


  public void setVmPin(String vmPin)
  {
    this.vmPin = vmPin;
  }


  /**
   * @return the vmSendEmail
   */
  public boolean isVmSendEmail()
  {
    return vmSendEmail;
  }


  /**
   * @param vmSendEmail the vmSendEmail to set
   */
  public void setVmSendEmail(boolean vmSendEmail)
  {
    this.vmSendEmail = vmSendEmail;
  }


  public String getCallgroup()
  {
    return callgroup;
  }


  public void setCallgroup(String callGroup)
  {
    this.callgroup = callGroup;
  }


  public String getPickupgroup()
  {
    return pickupgroup;
  }


  public void setPickupgroup(String pickupgroup)
  {
    this.pickupgroup = pickupgroup;
  }


  public boolean isCallwaiting()
  {
    return callwaiting;
  }


  public void setCallwaiting(boolean callwaiting)
  {
    this.callwaiting = callwaiting;
  }


  public String getContext()
  {
    return context;
  }


  public void setContext(String context)
  {
    this.context = context;
  }


  public String getFullName()
  {
    return fullName;
  }


  public void setFullName(String fullName)
  {
    this.fullName = fullName;
  }


  public String getCocLogin()
  {
    return cocLogin;
  }


  public void setCocLogin(String cocLogin)
  {
    this.cocLogin = cocLogin;
  }


  public String getCocPin()
  {
    return cocPin;
  }


  public void setCocPin(String cocPin)
  {
    this.cocPin = cocPin;
  }


  public void setPhone(String assignedPhoneMac)
  {
    this.phone = assignedPhoneMac;
  }


  public String getPhone()
  {
    return phone;
  }


  /**
   * Values: on, off, dnd
   * It is a guess on the ip:port and presence
   *
   * @return the state
   */
  public String getState()
  {
    return state;
  }


  /**
   * @param state the state to set
   */
  public void setState(String state)
  {
    this.state = state;
  }

}
