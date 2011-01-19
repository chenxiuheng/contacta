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
import mic.contacta.server.dao.PhoneDao;
import mic.contacta.server.dao.ProductDao;

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
   * @see mic.contacta.server.ptool.InventoryService#createPhone(mic.contacta.ptool.dao.PhoneModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public PhoneModel createPhone(PhoneModel phone, ProductModel product)
  {
    phone.setProduct(product);
    return phoneDao.create(phone);
  }


  /*
   * @see mic.contacta.server.ptool.InventoryService#deletePhone(mic.contacta.ptool.dao.PhoneModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public boolean deletePhone(PhoneModel model)
  {
    return phoneDao.delete(model);
  }


  /*
   * @see mic.contacta.server.ptool.InventoryService#findAllPhones()
   */
  @Transactional(propagation=Propagation.MANDATORY, readOnly=true)
  @Override
  public List<PhoneModel> findPhoneList()
  {
    return phoneDao.findAll();
  }


  /*
   * @see mic.contacta.server.ptool.InventoryService#findAllPhones()
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public PhoneModel findPhone(int id)
  {
    return phoneDao.find(id);
  }


  /*
   * @see mic.contacta.server.ptool.InventoryService#updatePhone(mic.contacta.ptool.dao.PhoneModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public PhoneModel updatePhone(PhoneModel model)
  {
    return phoneDao.update(model);
  }


  /*
   * @see mic.contacta.server.ptool.InventoryService#findPhoneByMacAddress(java.lang.String)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public PhoneModel findPhoneByMacAddress(String macAddress)
  {
    return phoneDao.findByMacAddress(macAddress);
  }


  /*
   *
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public List<String> findMacAddressList()
  {
    return phoneDao.findMacAddressList();
  }


  /*
   * @see mic.contacta.server.spi.InventoryService#createProduct(mic.contacta.model.ProductModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public ProductModel createProduct(ProductModel model)
  {
    return productDao.create(model);
  }


  /*
   * @see mic.contacta.server.spi.InventoryService#deleteProduct(mic.contacta.model.ProductModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public boolean deleteProduct(ProductModel model)
  {
    return productDao.delete(model);
  }


  /*
   * @see mic.contacta.server.spi.InventoryService#findProduct(int)
   */
  @Transactional(propagation=Propagation.MANDATORY, readOnly=true)
  @Override
  public ProductModel findProduct(int id)
  {
    return productDao.find(id);
  }


  /*
   * @see mic.contacta.server.spi.InventoryService#updateProduct(mic.contacta.model.ProductModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public ProductModel updateProduct(ProductModel model)
  {
    return productDao.update(model);
  }


  /*
   * @see mic.contacta.server.ptool.InventoryService#findAllPhones()
   */
  @Transactional(propagation=Propagation.MANDATORY, readOnly=true)
  @Override
  public List<ProductModel> findProductList()
  {
    return productDao.findAll();
  }


  /*
   * @see mic.contacta.server.spi.InventoryService#findProductByCode(java.lang.String)
   */
  @Transactional(propagation=Propagation.MANDATORY, readOnly=true)
  @Override
  public ProductModel findProductByCode(String code)
  {
    ProductModel product = productDao.findByCode(code);
    return product;
  }

}
