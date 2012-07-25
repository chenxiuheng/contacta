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
package mic.contacta.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import mic.contacta.server.ContactaSession;
import mic.contacta.server.InventoryService;
import mic.contacta.server.PbxService;
import mic.contacta.server.ProvisioningService;



/**
 *
 * @author mic
 * @created Nov 15, 2008
 */
public abstract class AbstractContactaAction implements Action
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired protected ProvisioningService provisioningService;
  @Autowired protected ContactaSession contactaSession;

  @Autowired protected InventoryService inventoryService;
  @Autowired protected PbxService pbxService;


  /**
   * just the default method, doing nothing
   */
  @Override
  public String execute()
  {
    return Action.SUCCESS;
  }

}
