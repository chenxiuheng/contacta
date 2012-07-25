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
package mic.contacta.server;

import java.util.List;
import mic.contacta.domain.PhoneModel;
import mic.contacta.domain.ProductModel;
import mic.organic.core.Service;


/**
 * Manage the phone inventory TODO can this be the hardware inventory????
 *
 * @author mic
 * @created Dec 29, 2007
 */
public interface InventoryService extends Service
{
  public final static String SERVICE_NAME = "inventoryService";


  /**
   * Create a new phoneInfo and persist it
   *
   * @param model
   * @return
   */
  ProductModel productPersist(ProductModel model);


  /**
   * Update the phoneInfo in the system db
   *
   * @param model
   * @return
   */
  ProductModel productUpdate(ProductModel model);


  /**
   * Delete a phoneInfo completely
   *
   * @param model
   * @return
   */
  boolean productDelete(ProductModel model);


  /**
   * Find a phoneInfo by its internal id (PK)
   *
   * @param id
   * @return
   */
  ProductModel productFind(int id);


  /**
   * @param product
   * @return
   */
  ProductModel productByCode(String code);


  /**
   * @return
   */
  List<ProductModel> productList();


  /**
   * Persist the phone
   *
   * @param model
   * @param product
   * @return
   */
  PhoneModel phonePersist(PhoneModel model, ProductModel product);


  /**
   * Persist the phone
   *
   * @param model
   * @return
   */
  PhoneModel phonePersist(PhoneModel model);


  /**
   * Delete a phone completely
   *
   * @param model
   * @return
   */
  boolean phoneDelete(PhoneModel model);


  /**
   * Find a phone by its internal id (PK)
   *
   * @param id
   * @return
   */
  PhoneModel phoneFind(int id);


  /**
   * Find a phone by the MAC address (unique key)
   *
   * @param macAddress
   * @return
   */
  PhoneModel phoneByMacAddress(String macAddress);


  /**
   * Find all phones in the system
   *
   * @return
   */
  List<PhoneModel> phoneList();


  /**
   * @return
   */
  List<String> macAddressList();

}
