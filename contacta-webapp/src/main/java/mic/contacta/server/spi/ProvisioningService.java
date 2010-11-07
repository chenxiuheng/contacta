/* $Id$
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

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mic.contacta.pmod.common.Configurer;
import mic.contacta.pmod.common.ProvisioningContext;
import mic.organic.core.Service;


/**
 *
 *
 * @author mic
 * @created Nov 30, 2008
 */
public interface ProvisioningService extends Service
{
  String SERVICE_NAME = "provisioningService";

  String SECONDARY_ACCOUNT = "secondaryAccount";
  String PRIMARY_ACCOUNT = "primaryAccount";
  String ACCOUNT_LIST = "accountList";
  String PHONE = "phone";


  /**
   * @param configurer
   */
  void registerPmod(Configurer configurer);


  /**
   * @param request
   * @param response
   * @return
   */
  ProvisioningContext createProvisioningContext(HttpServletRequest request, HttpServletResponse response);


  /**
   * TODO uhm... non so se mi piace veramente questa signature...
   *
   * @param provisioningContext
   * @throws IOException
   */
  boolean doProvisioning(ProvisioningContext provisioningContext) throws IOException;


  /**
   * TODO specificare cosa fa, forse e' da muovere via, anche se dentro ci sono tutte le vars.  va in loop con i configurer
   */
  //Map<String, Object> createParams();

  /**
   * Hook all the configuration file for a phone defined by its MAC address
   *
   * From the ProvisioningSession is possible to reach all the files as FileObject
   *
   * TODO, move to inventory????
   *
   * @param macAddress
   * @return
   * @throws OrganicException
   */
  //ProvisioningSession loadPhoneConfiguration(String macAddress) throws OrganicException;

}
