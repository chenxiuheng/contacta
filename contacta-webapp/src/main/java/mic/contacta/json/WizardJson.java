/* $Id: WizardJson.java 649 2010-06-13 17:18:01Z michele.bianchi $
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
package mic.contacta.json;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import mic.organic.gateway.AbstractJson;
import mic.organic.json.PersonJson;


/**
 * AccountJson
 */
public class WizardJson extends AbstractJson
{
  private PersonJson person;
  private SipAccountJson account;
  private PhoneJson phone;


  /*
   *
   */
  @Override
  public String toString()
  {
    ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
    return builder.toString();
  }


  /*
   * @see mic.organic.web.dojo.Json#getId()
   */
  @Override
  public int getId()
  {
    return 0;
  }


  /**
   * @return the person
   */
  public PersonJson getPerson()
  {
    return person;
  }


  /**
   * @param person the person to set
   */
  public void setPerson(PersonJson person)
  {
    this.person = person;
  }


  /**
   * @return the account
   */
  public SipAccountJson getAccount()
  {
    return account;
  }


  /**
   * @param account the account to set
   */
  public void setAccount(SipAccountJson account)
  {
    this.account = account;
  }


  /**
   * @return the phone
   */
  public PhoneJson getPhone()
  {
    return phone;
  }


  /**
   * @param phone the phone to set
   */
  public void setPhone(PhoneJson phone)
  {
    this.phone = phone;
  }
}
