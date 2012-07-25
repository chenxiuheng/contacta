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

import mic.contacta.dao.PhoneDao;
import mic.contacta.dao.ProductDao;
import mic.contacta.domain.PhoneModel;
import mic.contacta.domain.ProductModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Description of the Class
 *
 * @author mic
 * @version $Revision: 645 $
 */
@Service(InventoryService.SERVICE_NAME)
public class InventoryServiceImpl implements InventoryService
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private ProductDao productDao;
  @Autowired private PhoneDao phoneDao;


  /**
   *
   */
  public InventoryServiceImpl()
  {
    super();
  }


  /*
   *
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public List<String> macAddressList()
  {
    return phoneDao.findMacAddressList();
  }


  /*
   * @see mic.contacta.server.ptool.InventoryService#createPhone(mic.contacta.ptool.dao.PhoneModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public PhoneModel phonePersist(PhoneModel phone, ProductModel product)
  {
    if (phone.getId() == 0)
    {
      phone.setProduct(product);
      return phoneDao.create(phone);
    }
    else
    {
      return phoneDao.update(phone);
    }
  }


  /*
   * @see mic.contacta.server.ptool.InventoryService#updatePhone(mic.contacta.ptool.dao.PhoneModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public PhoneModel phonePersist(PhoneModel model)
  {
    return phonePersist(model, null);
  }


  /*
   * @see mic.contacta.server.ptool.InventoryService#deletePhone(mic.contacta.ptool.dao.PhoneModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public boolean phoneDelete(PhoneModel model)
  {
    return phoneDao.delete(model);
  }


  /*
   * @see mic.contacta.server.ptool.InventoryService#findAllPhones()
   */
  @Transactional(propagation=Propagation.MANDATORY, readOnly=true)
  @Override
  public List<PhoneModel> phoneList()
  {
    return phoneDao.findAll();
  }


  /*
   * @see mic.contacta.server.ptool.InventoryService#findAllPhones()
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public PhoneModel phoneFind(int id)
  {
    return phoneDao.find(id);
  }


  /*
   * @see mic.contacta.server.ptool.InventoryService#findPhoneByMacAddress(java.lang.String)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public PhoneModel phoneByMacAddress(String macAddress)
  {
    return phoneDao.findByMacAddress(macAddress);
  }


  /*
   * @see mic.contacta.server.InventoryService#createProduct(mic.contacta.domain.ProductModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public ProductModel productPersist(ProductModel model)
  {
    return productDao.create(model);
  }


  /*
   * @see mic.contacta.server.InventoryService#deleteProduct(mic.contacta.domain.ProductModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public boolean productDelete(ProductModel model)
  {
    return productDao.delete(model);
  }


  /*
   * @see mic.contacta.server.InventoryService#findProduct(int)
   */
  @Transactional(propagation=Propagation.MANDATORY, readOnly=true)
  @Override
  public ProductModel productFind(int id)
  {
    return productDao.find(id);
  }


  /*
   * @see mic.contacta.server.InventoryService#updateProduct(mic.contacta.domain.ProductModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public ProductModel productUpdate(ProductModel model)
  {
    return productDao.update(model);
  }


  /*
   * @see mic.contacta.server.ptool.InventoryService#findAllPhones()
   */
  @Transactional(propagation=Propagation.MANDATORY, readOnly=true)
  @Override
  public List<ProductModel> productList()
  {
    return productDao.findAll();
  }


  /*
   * @see mic.contacta.server.InventoryService#findProductByCode(java.lang.String)
   */
  @Transactional(propagation=Propagation.MANDATORY, readOnly=true)
  @Override
  public ProductModel productByCode(String code)
  {
    ProductModel product = productDao.findByCode(code);
    return product;
  }

}
