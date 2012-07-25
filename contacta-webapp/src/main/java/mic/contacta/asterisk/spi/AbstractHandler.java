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
package mic.contacta.asterisk.spi;

import org.asteriskjava.manager.event.ManagerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 *
 * @author mic
 * @created Jan 19, 2009
 */
public abstract class AbstractHandler<T extends ManagerEvent> implements Handler<T>
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired protected AsteriskService asteriskService;
  private ManagerEvent managerEvent;


  /*
   *
   */
  public AbstractHandler()
  {
    super();
  }


  /**
   * @param peerEntryHandler
   */
  protected void afterFactory(AbstractHandler<T> abstractHandler)
  {
    abstractHandler.asteriskService = asteriskService;
  }


  /*
   * @see org.springframework.beans.factory.FactoryBean#isSingleton()
   */
  @Override
  public boolean isSingleton()
  {
    return false;
  }


  /*
   * @see mic.contacta.server.Handler#setEvent(org.asteriskjava.manager.event.ManagerEvent)
   */
  @Override
  public void setEvent(ManagerEvent managerEvent)
  {
    //log().info("managerEvent={}", managerEvent);
    this.managerEvent = managerEvent;
  }


  /*
   * @see java.util.concurrent.Callable#call()
   */
  @Override
  public Void call() throws Exception
  {
    handleEvent((T)(managerEvent));
    return null;
  }

}
