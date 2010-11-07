/* $Id: ContactaMbeanImpl.java 642 2010-05-30 09:15:27Z michele.bianchi $
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
package mic.contacta.util;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mic.contacta.asterisk.spi.AsteriskService;
import mic.contacta.server.spi.ProvisioningService;


/**
 * @author mic
 * @created Jan 14, 2009
 */
@Service(ContactaMbeanImpl.SERVICE_NAME)
public class ContactaMbeanImpl implements ContactaMbean
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private AsteriskService asteriskService;


  /*
   *
   */
  public ContactaMbeanImpl()
  {
  }


  /**
   * @return the configuration
   */
  @Override
  public String getConfiguration()
  {
    ReflectionToStringBuilder builder = new ReflectionToStringBuilder(ProvisioningService.SERVICE_NAME, ToStringStyle.MULTI_LINE_STYLE);
    return builder.toString();
  }


  /*
   * @see mic.contacta.server.jmx.ContactaMbean#getAsteriskCconnected()
   */
  @Override
  public int getAsteriskConnections()
  {
    int r = asteriskService.getConnections();
    return r;
  }

}
