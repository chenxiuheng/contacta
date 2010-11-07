/* $Id: AbstractContactaAgi.java 616 2010-04-03 21:07:58Z michele.bianchi $
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
package mic.contacta.asterisk.agi;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.AgiScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author mic
 * @created Jan 20, 2009
 */
public abstract class AbstractContactaAgi implements AgiScript
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  protected String prefix;


  /*
   *
   */
  public AbstractContactaAgi(String prefix)
  {
    super();
    this.prefix = prefix;
  }


  /**
   * @return the prefix
   */
  public String getPrefix()
  {
    return prefix;
  }


  /**
   * @param prefix the prefix to set
   */
  public void setPrefix(String prefix)
  {
    this.prefix = prefix;
  }


  /**
   * @param callerIdName
   * @param callerId
   * @return
   */
  public String mkCallerId(String callerIdName, String callerId)
  {
    callerId = callerIdName + " <" + callerId + ">";
    return callerId;
  }


  /*
   *
   */
  protected void printDebug(AgiRequest request, AgiChannel channel)
  {
    //log().info("request: {}, channel: {}", request, channel);
    for (Object key : request.getParameterMap().keySet())
    {
      String value = request.getParameter((String)key);
      log().info("parameter: {}, {}", key, value);
    }
  }
}
