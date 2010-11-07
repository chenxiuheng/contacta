/* $Id: ImportServiceTest.java 244 2009-02-13 15:53:51Z alessandro.panza $
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
package mic.contacta.webapp;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import mic.contacta.gateway.ContactaGateway;
import mic.contacta.gateway.SipAccountConverter;
import mic.contacta.json.SipAccountJson;
import mic.contacta.model.SipAccountModel;
import mic.contacta.server.api.ContactaConstants;
import mic.contacta.server.api.ContactaException;
import mic.contacta.server.spi.AbstractProvisioningTests;
import mic.contacta.server.spi.SipService;
import mic.organic.core.OrganicException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


/**
 *
 *
 * @author mic
 * @created Dec 1, 2008
 */
@ContextConfiguration(locations = { ContactaConstants.SPRING_TEST_ORM, ContactaConstants.TEST_PROVISIONING_SPRING })
public class ContactaGatewayTest extends AbstractProvisioningTests
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private ContactaGateway contactaGateway;
  @Autowired private SipService sipService;

  @Autowired private SipAccountConverter sipAccountConverter;


  /*
   *
   */
  //@Test
  //@Rollback(true)
  public void testDuplicateAccountOnCreate() throws OrganicException, ContactaException
  {
    createAccountAndPhone("");

    SipAccountModel accountModel = sipService.findAccountByLogin("login");

    // Create a different one
    SipAccountJson account = sipAccountConverter.modelToJson(accountModel, null);
    account.setId(0);
    account.setLogin("xxxlogin");
    account.setEmail("xxxemail");

    account = contactaGateway.accountCreateUpdate(account);

    assertTrue(true);

    // Create an existing account
    account = new SipAccountJson();
    account.setId(0);
    account.setLogin("login");
    account.setPassword("password");

    Exception thrownException = null;
    String message = "";
    try
    {
      account = contactaGateway.accountCreateUpdate(account);
    }
    catch (Exception e)
    {
      thrownException = e;
      message = e.getMessage();
    }

    assertTrue(thrownException instanceof ContactaException);
    assertEquals(message, "Duplication of a unique field: login");
  }


  /*
   *
   */
  //@Test
  //@Rollback(true)
  //@ExpectedException(ContactaException.class) TODO I want this annotation!
  public void testDuplicateAccountOnOneBadCreatiaon() throws OrganicException, ContactaException
  {
    createAccountAndPhone("");

    Exception thrownException = null;
    String message = "";

    // Create an existing account
    SipAccountJson account = new SipAccountJson();
    account.setId(0);
    account.setLogin("login");
    account.setPassword("password");

    try
    {
      account = contactaGateway.accountCreateUpdate(account);
    }
    catch (Exception e)
    {
      thrownException = e;
      message = e.getMessage();
    }

    assertTrue(thrownException instanceof ContactaException);
    assertEquals(message, "Duplication of a unique field: login");
  }



  /*
   *
   */
  //@Test
  //@Rollback(true)
  public void testDuplicateAccountOnUpdate() throws OrganicException, ContactaException
  {
    createAccountAndPhone("");

    SipAccountModel accountModel = sipService.findAccountByLogin("login");

    // Create a different one
    SipAccountJson account = sipAccountConverter.modelToJson(accountModel, null);
    account.setId(0);
    account.setLogin("zzzlogin");
    account.setEmail("zzzemail");

    account = contactaGateway.accountCreateUpdate(account);

    assertTrue(true);

    // TODO because of updateProfile
    // Update to a non-existing login
//    account.setLogin("yyylogin");
//    account = contactaGateway.accountCreateUpdate(account);

//    assertTrue(true);

    // Update to an existing login
    account.setLogin("login");

    Exception thrownException = null;
    String message = "";
    try
    {
      account = contactaGateway.accountCreateUpdate(account);
    }
    catch (Exception e)
    {
      thrownException = e;
      message = e.getMessage();
    }

    assertTrue(thrownException instanceof ContactaException);
    assertEquals(message, "Duplication of a unique field: login");
  }
}
