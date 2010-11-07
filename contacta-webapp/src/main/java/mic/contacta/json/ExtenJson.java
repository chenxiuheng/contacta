/* $Id: ExtenJson.java 27 2008-12-17 21:39:10Z michele.bianchi@gmail.com $
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

/**
 *
 * @author mic
 * @created Dec 17, 2008
 */
public class ExtenJson  extends AbstractJson
{
  private String exten;
  private String mail;


  /*
   *
   */
  public ExtenJson()
  {
  }


  /*
   *
   */
  @Override
  public String toString()
  {
    ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);//.setExcludeFieldNames(excludes);
    return builder.toString();
  }


  /**
   * @return the exten
   */
  public String getExten()
  {
    return exten;
  }


  /**
   * @param exten the exten to set
   */
  public void setExten(String exten)
  {
    this.exten = exten;
  }


  /**
   * @return the mail
   */
  public String getMail()
  {
    return mail;
  }


  /**
   * @param mail the mail to set
   */
  public void setMail(String mail)
  {
    this.mail = mail;
  }


}
