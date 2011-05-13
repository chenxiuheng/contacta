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
package mic.contacta.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

import mic.contacta.domain.PhoneModel;
import mic.contacta.domain.ProductModel;
import mic.contacta.domain.SipAccountModel;
import mic.contacta.server.InventoryService;
import mic.contacta.server.PbxService;
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
  @Autowired protected PbxService pbxService;

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
    ProductModel product = inventoryService.productByCode(productCode);
    if (product == null)
    {
      product = new ProductModel(productCode, "vendor", productCode, "version", "UA "+productCode);
      inventoryService.productPersist(product);
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

    account = pbxService.sipCreate(account);
    //URGENT pbxService.assignUpdateVoicemailToAccount(account, "pin" + suffix);

    addSampleProducts();
    //ProductModel product = new ProductModel(ProductModel.GENERIC, "vendor", "model", "version", "userAgent");
    //inventoryService.createProduct(product);

    PhoneModel phone = new PhoneModel(/*PhoneModel.Vendor.POLYCOM, PhoneModel.Model.SPIP_450,*/ macAddress);
    ProductModel product = inventoryService.productByCode(ProductModel.GENERIC);
    phone = inventoryService.phonePersist(phone, product);

    pbxService.assignPhoneToAccount(phone, account);
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
