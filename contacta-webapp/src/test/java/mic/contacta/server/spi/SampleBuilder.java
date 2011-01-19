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
    account.setLabel("name_" + suffix);
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
