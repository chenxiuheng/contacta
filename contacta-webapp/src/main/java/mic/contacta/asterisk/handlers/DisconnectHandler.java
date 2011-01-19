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
package mic.contacta.asterisk.handlers;

import org.asteriskjava.manager.event.DisconnectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import mic.contacta.asterisk.spi.AbstractHandler;


/**
 *
 * @author mic
 * @created Jan 19, 2009
 */
@Service("disconnectHandler")
public class DisconnectHandler extends AbstractHandler<DisconnectEvent>
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  /*
   *
   */
  public DisconnectHandler()
  {
  }


  /*
   * @see org.springframework.beans.factory.FactoryBean#getObject()
   */
  @Override
  public Object getObject() throws Exception
  {
    DisconnectHandler handler = new DisconnectHandler();
    afterFactory(handler);
    return handler;
  }


  /*
   * @see org.springframework.beans.factory.FactoryBean#getObjectType()
   */
  @Override
  public Class<?> getObjectType()
  {
    return DisconnectHandler.class;
  }


  /*
   * @see mic.contacta.server.spi.Handler#handleEvent()
   */
  @Override
  public void handleEvent(DisconnectEvent event)
  {
    log().debug("event={}", event);
    //asteriskService.disconnectAsterisk();
  }

}
