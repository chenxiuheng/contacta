/* $Id: SampleBuilder.java 660 2010-07-17 23:14:21Z michele.bianchi $
 *
 * Copyright(C) 2010 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.contacta.server.spi;

import mic.contacta.model.PhoneModel;
import mic.contacta.model.ProductModel;
import mic.contacta.model.SipAccountModel;
import mic.organic.aaa.model.PersonModel;
import mic.organic.core.OrganicException;

/**
 *
 * @author mic
 * @created May 29, 2010
 */
public class SampleBuilder
{

  /**
   * @param suffix
   * @return
   */
  public static PersonModel buildPerson(String suffix) throws OrganicException
  {
    PersonModel person = new PersonModel();
    person.setDisplayName("fullName" + suffix);
    person.setFirstName("firstName" + suffix);
    person.setLastName("lastName" + suffix);
    person.setEmail("email" + suffix);
    //person = addressbookService.createPerson(person);
    return person;
  }


  /**
   * @param suffix
   * @return
   */
  public static SipAccountModel buildContactaAccount(String suffix)
  {
    SipAccountModel account = new SipAccountModel();
    account.setDisplayName("name_" + suffix);
    account.setEmail("email_" + suffix);
    account.setCode("login" + suffix);
    account.setPassword("password" + suffix);
    account.getSipUser().setCallgroup("callGroup" + suffix);
    account.getSipUser().setPickupgroup("pickup" + suffix);
    //account.setCallWaiting(callWaiting);
    account.getSipUser().setContext("context" + suffix);
    account.getSipUser().setName("login" + suffix);
    account.getSipUser().setUsername("login" + suffix);
    account.getSipUser().setCallerid("login" + suffix);
    account.getSipUser().setSecret("password" + suffix);
    return account;
  }


  /**
   * @param productCode
   *
   */
  public static ProductModel buildProduct(String productCode)
  {
    ProductModel product = new ProductModel(productCode, "vendor", productCode, "version", "UA "+productCode);
    return product;
  }


  /**
   *
   * @param macAddress
   * @return
   */
  public static PhoneModel buildPhone(String macAddress)
  {
    PhoneModel phone = new PhoneModel(/*PhoneModel.Vendor.POLYCOM, PhoneModel.Model.SPIP_450,*/ macAddress);
    phone.setProduct(buildProduct("STxxxx"));
    return phone;
  }

}
