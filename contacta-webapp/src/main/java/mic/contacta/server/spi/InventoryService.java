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

import java.util.List;

import mic.contacta.model.ProductModel;
import mic.contacta.model.PhoneModel;
import mic.organic.core.Service;


/**
 * Manage the phone inventory
 *
 * TODO can this be the hardware inventory????
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
  ProductModel createProduct(ProductModel model);

  /**
   * Update the phoneInfo in the system db
   *
   * @param model
   * @return
   */
  ProductModel updateProduct(ProductModel model);

  /**
   * Delete a phoneInfo completely
   *
   * @param model
   * @return
   */
  boolean deleteProduct(ProductModel model);

  /**
   * Find a phoneInfo by its internal id (PK)
   *
   * @param id
   * @return
   */
  ProductModel findProduct(int id);


  /**
   * @param product
   * @return
   */
  ProductModel findProductByCode(String code);


  /**
   * @return
   */
  List<ProductModel> findProductList();


  /**
   * Create a new phone and persist it
   *
   * @param model
   * @param product
   * @return
   */
  PhoneModel createPhone(PhoneModel model, ProductModel product);

  /**
   * Update the phone in the system db
   *
   * @param model
   * @return
   */
  PhoneModel updatePhone(PhoneModel model);

  /**
   * Delete a phone completely
   *
   * @param model
   * @return
   */
  boolean deletePhone(PhoneModel model);

  /**
   * Find a phone by its internal id (PK)
   *
   * @param id
   * @return
   */
  PhoneModel findPhone(int id);

  /**
   * Find a phone by the MAC address (unique key)
   *
   * @param macAddress
   * @return
   */
  PhoneModel findPhoneByMacAddress(String macAddress);


  /**
   * Find all phones in the system
   *
   * @return
   */
  List<PhoneModel> findPhoneList();

  /**
   *
   * @return
   */
  List<String> findMacAddressList();

}
