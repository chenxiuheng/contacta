/* $Id: Handler.java 616 2010-04-03 21:07:58Z michele.bianchi $
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
package mic.contacta.asterisk.spi;

import java.util.concurrent.Callable;

import org.asteriskjava.manager.event.ManagerEvent;
import org.springframework.beans.factory.FactoryBean;

/**
 *
 * @author mic
 * @created Jan 19, 2009
 */
public interface Handler<T extends ManagerEvent> extends Callable<Void>, FactoryBean
{

  void handleEvent(T managerEvent);

  /**
   * @param managerEvent
   */
  void setEvent(ManagerEvent managerEvent);
}
