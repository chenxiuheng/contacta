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
package mic.contacta.server.api;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;



/**
 *
 * @author mic
 * @created May 25, 2009
 */
public class Line
{
  public static final String [] lineExcludes = { "call" };

  private String exten;
  private String callerId;
  private Call call;

  /*
   *
   */
  @Override
  public String toString()
  {
    ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames(lineExcludes);
    return builder.toString();
  }


  /**
   * @param string
   */
  public Line(String exten, String callerId)
  {
    this.exten = exten;
    this.callerId = callerId;
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
   * @return the callerId
   */
  public String getCallerId()
  {
    return callerId;
  }

  /**
   * @param callerId the callerId to set
   */
  public void setCallerId(String callerId)
  {
    this.callerId = callerId;
  }

  /**
   * @return the call
   */
  public Call getCall()
  {
    return call;
  }

  /**
   * @param call the call to set
   */
  public void setCall(Call call)
  {
    this.call = call;
  }
}
