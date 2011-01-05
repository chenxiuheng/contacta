/* $Id: PhoneDaoTest.java 2016 2008-11-04 15:49:09Z michele.bianchi@gmail.com $
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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import mic.contacta.model.PhoneModel;
import mic.contacta.model.ProductModel;
import mic.contacta.model.SipAccountModel;
import mic.contacta.server.api.ContactaConstants;
import mic.contacta.server.dao.PhoneDao;
import mic.contacta.server.dao.ProductDao;
import mic.contacta.server.dao.SipAccountDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * Class providing unit testing for the Organic.
 *
 * @author    mic
 * @version   $Revision: 661 $
 */
@ContextConfiguration(locations = { ContactaConstants.TEST_DAO_SPRING })
public class DaoTest extends AbstractTransactionalTestNGSpringContextTests
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @PersistenceContext EntityManager entityManager;
  @Autowired private SipAccountDao accountDao;
  @Autowired private PhoneDao phoneDao;
  @Autowired private ProductDao productDao;


  /*
   *
   */
  private PhoneModel buildPhone(String macAddress, String vendor, String model)
  {
    ProductModel product = productDao.findByCode(ProductModel.GENERIC);
    if (product == null)
    {
      product = new ProductModel(ProductModel.GENERIC, "vendor", "model", "version", "userAgent");
      productDao.create(product);
    }

    PhoneModel phone = new PhoneModel(/*PhoneModel.Vendor.POLYCOM, PhoneModel.Model.SPIP_450,*/ macAddress);
    phone.setProduct(product);

    return phone;
  }


  /*
   *
   */
  private SipAccountModel buildAccount(String login, String password, String numberExt)
  {
    SipAccountModel account = new SipAccountModel();
    account.setCode(login);
    account.setPassword(password);
    account.setLabel("displayName "+login);
    account.setCallerId("callerId "+login);
    return account;
  }


  /*
   *
   */
  @Test
  public void testCreateAccount()
  {
    SipAccountModel account = buildAccount("ale", "pwd", "300");

    try
    {
      accountDao.create(account);
      entityManager.flush();
    }
    catch (Exception e)
    {
      log().info("ciao", e);
    }
    account = accountDao.findByLogin("ale");

    assertNotNull(account);
    assertEquals(account.getPassword(), "pwd");
  }

  /*
   *
   */
  @Test
  public void testCreatePhone() //
  {
    PhoneModel phone = buildPhone("00:f2:45:8d", "POLYCOM", "SPIP_330");

    phoneDao.create(phone);

    phone = phoneDao.findByMacAddress("00:f2:45:8d");

    assertNotNull(phone);
    assertEquals(phone.getSerialNumber(), "n/a");
  }

  /*
   *
   */
  @Test
  public void testAddAccountToPhone()
  {
    PhoneModel phone = buildPhone("00:f2:45:8d", "POLYCOM", "SPIP_330");
    phoneDao.create(phone);

    SipAccountModel account = buildAccount("ale", "pwd", "300");
    accountDao.create(account);
    entityManager.flush();

    phone = phoneDao.findByMacAddress("00:f2:45:8d");
    account = accountDao.findByLogin("ale");

    phone.getSipAccountList().add(account);
    phoneDao.update(phone);

    account.setPhone(phone);
    accountDao.update(account);

    phone = phoneDao.findByMacAddress("00:f2:45:8d");

    assertNotNull(phone);
    assertEquals(phone.getSipAccountList().size(), 1);
    assertEquals(phone.getSipAccountList().get(0).getLogin(), "ale");

    account = accountDao.findByLogin("ale");

    assertNotNull(account);
    assertNotNull(account.getPhone());
    assertEquals(account.getPhone().getMacAddress(),"00:f2:45:8d");

  }

  /*
   *
   */
  @Test
  public void testChangePhoneAccount()
  {
    PhoneModel phone0 = buildPhone("00:f2:45:8d", "POLYCOM", "SPIP_330");
    phoneDao.create(phone0);

    SipAccountModel account = buildAccount("ale", "pwd", "300");
    accountDao.create(account);

    phone0 = phoneDao.findByMacAddress("00:f2:45:8d");
    account = accountDao.findByLogin("ale");

    phone0.getSipAccountList().add(account);
    phoneDao.update(phone0);

    account.setPhone(phone0);
    accountDao.update(account);

    phone0 = phoneDao.findByMacAddress("00:f2:45:8d");

    assertNotNull(phone0);
    assertEquals(phone0.getSipAccountList().size(), 1);
    assertEquals(phone0.getSipAccountList().get(0).getLogin(), "ale");

    account = accountDao.findByLogin("ale");

    assertNotNull(account);
    assertNotNull(account.getPhone());
    assertEquals(account.getPhone().getMacAddress(),"00:f2:45:8d");

    PhoneModel phone1 = buildPhone("00:ff:f5:fd", "POLYCOM", "SPIP_330");
    phoneDao.create(phone1);

    phone1 = phoneDao.findByMacAddress("00:ff:f5:fd");
    account = accountDao.findByLogin("ale");

    phone0 = phoneDao.findByMacAddress("00:f2:45:8d");
    phone0.getSipAccountList().remove(account);
    phoneDao.update(phone0);

    phone1.getSipAccountList().add(account);
    phoneDao.update(phone1);

    account.setPhone(phone1);
    accountDao.update(account);

    phone1 = phoneDao.findByMacAddress("00:ff:f5:fd");

    assertNotNull(phone1);
    assertEquals(phone1.getSipAccountList().size(), 1);
    assertEquals(phone1.getSipAccountList().get(0).getLogin(), "ale");

    account = accountDao.findByLogin("ale");

    assertNotNull(account);
    assertNotNull(account.getPhone());
    assertEquals(account.getPhone().getMacAddress(),"00:ff:f5:fd");

    phone0 = phoneDao.findByMacAddress("00:f2:45:8d");

    assertNotNull(phone0);
    assertEquals(phone0.getSipAccountList().size(), 0);

  }

}
