/* $Id: AbstractProvisioningTests.java 642 2010-05-30 09:15:27Z michele.bianchi $
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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

import mic.contacta.model.PhoneModel;
import mic.contacta.model.ProductModel;
import mic.contacta.model.SipAccountModel;
import mic.contacta.server.spi.InventoryService;
import mic.contacta.server.spi.SipService;
import mic.organic.aaa.model.PersonModel;
import mic.organic.aaa.spi.AddressbookService;
import mic.organic.core.OrganicException;
import mic.organic.vfs.OrganicVfs;


/**
 *
 * @author mic
 * @created Jan 31, 2009
 */
public abstract class AbstractProvisioningTests extends AbstractTransactionalTestNGSpringContextTests
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired protected OrganicVfs organicVfs;
  @Autowired protected AddressbookService addressbookService;
  @Autowired protected InventoryService inventoryService;
  @Autowired protected SipService sipService;

  protected String macAddress = "00:04:f2:1f:98:bd";


  /**
   *
   */
  public AbstractProvisioningTests()
  {
    super();
  }


  /**
   * @param suffix
   * @return
   */
  protected PersonModel buildPerson(String suffix) throws OrganicException
  {
    PersonModel person = SampleBuilder.buildPerson(suffix);
    person = addressbookService.personPersist(person);
    return person;
  }


  /**
   * @param suffix
   * @return
   */
  protected SipAccountModel buildContactaAccount(String suffix)
  {
    SipAccountModel account = SampleBuilder.buildContactaAccount(suffix);
    return account;
  }


  /**
   * @param productCode
   *
   */
  protected ProductModel addSampleProduct(String productCode)
  {
    ProductModel product = inventoryService.findProductByCode(productCode);
    if (product == null)
    {
      product = new ProductModel(productCode, "vendor", productCode, "version", "UA "+productCode);
      inventoryService.createProduct(product);
    }
    return product;
  }


  /**
   *
   */
  protected void addSampleProducts()
  {
    addSampleProduct(ProductModel.GENERIC);
    addSampleProduct("SPIP_330");
    addSampleProduct("SPIP_450");
    addSampleProduct("SPIP_670");
  }


  /**
   * create an account and a phone and associate them
   */
  protected PhoneModel createAccountAndPhone(String suffix, String macAddress) throws OrganicException
  {
    //PersonModel person = buildPerson(suffix);
    SipAccountModel account = buildContactaAccount(suffix);
    //account.setPerson(person);

    account = sipService.createAccount(account);
    //URGENT sipService.assignUpdateVoicemailToAccount(account, "pin" + suffix);

    addSampleProducts();
    //ProductModel product = new ProductModel(ProductModel.GENERIC, "vendor", "model", "version", "userAgent");
    //inventoryService.createProduct(product);

    PhoneModel phone = new PhoneModel(/*PhoneModel.Vendor.POLYCOM, PhoneModel.Model.SPIP_450,*/ macAddress);
    ProductModel product = inventoryService.findProductByCode(ProductModel.GENERIC);
    phone = inventoryService.createPhone(phone, product);

    sipService.assignPhoneToAccount(phone, account);
    return phone;
  }


  /**
   * create an account and a phone and associate them
   * use the default macAddress
   */
  protected PhoneModel createAccountAndPhone(String suffix) throws OrganicException
  {
    return createAccountAndPhone(suffix, macAddress);
  }

}
