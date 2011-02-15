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
package mic.contacta.server.spi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mic.contacta.model.*;
import mic.contacta.server.dao.PbxContextDao;


/**
 *
 * @author mic
 * @created May 29, 2010
 */
@Service
public class AutoCreator
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired protected InventoryService inventoryService;
  @Autowired private PbxContextDao pbxContextDao;
  @Autowired private SipService sipService;


  /**
   * @param macAddress
   * @param macText
   * @return
   */
  public PhoneModel autocreator(String macText, String productCode)
  {
    log().warn("Phone macaddress {} had not been registered by administrator. Creating a new one", macText);

    ProductModel product = inventoryService.findProductByCode(productCode); // TODO extract product model
    PhoneModel phone = new PhoneModel(macText);
    phone = inventoryService.createPhone(phone, product);

    PbxContextModel pbxContext = pbxContextDao.findByCode("sipphones");
    SipAccountModel account = new SipAccountModel("NEW@"+macText, macText+"@new", "999_"+macText, "999", pbxContext);
    account.setCallgroup("");
    account.setPickupgroup("");
    account.setProfile(null); // URGENT use the ''new unregistered phone profile''
    account.setProfileOptions("");
    account = sipService.sipCreate(account);
    sipService.sipCreate(account);

    sipService.assignPhoneToAccount(phone, account);

    return phone;
  }
}
