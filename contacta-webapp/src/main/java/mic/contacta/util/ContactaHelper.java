/* $Id: ContactaHelper.java 1468 2008-05-25 14:03:10Z michele.bianchi@gmail.com $
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mic.contacta.server.spi.ContactaService;
import mic.contacta.server.spi.ProvisioningService;
import mic.organic.core.*;



/**
 *
 *
 * @author    mic
 * @created   November 22, 2004
 */
public class  ContactaHelper
{
  static private Logger logger;
  static protected Logger log()  { if (ContactaHelper.logger == null) ContactaHelper.logger = LoggerFactory.getLogger(ContactaHelper.class);  return ContactaHelper.logger; }

  static private Logger loggerProvisioning;
  static public Logger loggerProvisioning()  { if (ContactaHelper.loggerProvisioning == null) ContactaHelper.loggerProvisioning = LoggerFactory.getLogger(LOG_PROVISIONING); return ContactaHelper.loggerProvisioning; }

  static public final String LOG_PROVISIONING = "provisioning";


  /*
   *
   */
  public static ContactaService getContactaService()
  {
    ContactaService contactaService = (ContactaService)(OrganicSpringHelper.getService(ContactaService.SERVICE_NAME));
    return contactaService;
  }


  /**
   * @return
   */
  public static ProvisioningService getPtoolService()
  {
    ProvisioningService provisioningService = (ProvisioningService) (OrganicSpringHelper.getService(ProvisioningService.SERVICE_NAME));
    return provisioningService;
  }

}

