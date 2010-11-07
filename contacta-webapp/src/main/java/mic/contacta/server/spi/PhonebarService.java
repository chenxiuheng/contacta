/* $Id: PhonebarService.java 660 2010-07-17 23:14:21Z michele.bianchi $
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
package mic.contacta.server.spi;

import mic.contacta.model.SipAccountModel;
import mic.organic.core.Service;


/**
 * @author michele.bianchi@gmail.com
 * @version $Revision: 660 $
 */
public interface PhonebarService extends Service
{
  String SERVICE_NAME = "phonebarService";


  /**
   *
   * @param login
   * @param password TODO
   * @param exten
   * @return
   */
  @Deprecated
  String dial(String login, String password, String exten);


  /**
   * Transfer an active phone call to another extension
   *
   * @param fromExten
   * @param toExten
   * @param toContext TODO
   * @return
   */
  String transfer(String fromExten, String toExten, String toContext);


  /**
   * Put an exten (should be an active phone call) in hold, that is in its
   * pkidong exten.
   *
   * @param exten the extension
   * @return
   */
  String hold(String exten);


  /**
   *
   */
  String unhold(String exten);


  /**
   *
   */
  String answer(String exten);


  /**
   * @param person
   * @param exten
   * @return
   */
  String dial(SipAccountModel sipAccount, String exten);


}
