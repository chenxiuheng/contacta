/* $Id: PhoneAdapter.java 666 2010-07-20 21:52:48Z michele.bianchi $
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
package mic.contacta.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mic.contacta.json.PhoneJson;
import mic.contacta.model.PhoneModel;
import mic.contacta.server.api.ContactaException;
import mic.contacta.server.spi.InventoryService;

/**
 *
 * @author mic
 * @created May 27, 2009
 */
@Service
public class PhoneAdapter
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private InventoryService inventoryService;
  @Autowired private PhoneConverter phoneConverter;


  /**
   * @param json
   * @return
   * @throws ContactaException
   */
  public PhoneModel updatePhone(PhoneJson json) throws ContactaException
  {
    PhoneModel model = null;
    return model;
  }


}
