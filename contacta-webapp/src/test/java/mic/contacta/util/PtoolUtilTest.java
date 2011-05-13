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
package mic.contacta.util;

import static org.testng.Assert.assertEquals;

import mic.contacta.domain.SipAccountModel;
import mic.contacta.util.ContactaUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;


/**
 * Class providing unit testing for the Organic.
 *
 * @author    mic
 * @version   $Revision: 681 $
 */
public class PtoolUtilTest
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  /*
   *
   */
  @Test
  public void testPasswordToPin()
  {
    String pin = ContactaUtils.passwordToPin("XXXX1");
    assertEquals(pin, "99991");

    pin =  ContactaUtils.passwordToPin("zzzzzzzz");
    assertEquals(pin, "99999999");

    pin =  ContactaUtils.passwordToPin(".,.,.<<<<+\u00e8+\u00e8+\u00e8\u00e0\u00f9\u00f2\u00f9\u00f2\u00f9\u00e0\u00f9");
    assertEquals(pin, "00000000000000000000000");
  }


  /*
   *
   */
  @Test
  public void testGroupMoreThen63()
  {
    SipAccountModel account = new SipAccountModel();
    account.setCallgroup("10");
    assertEquals(account.getCallgroup(), "10");
    assertEquals(account.getSipUser().getCallgroup(), "10");

    account.setCallgroup("64");
    assertEquals(account.getCallgroup(), "64");
    assertEquals(account.getSipUser().getCallgroup(), "");

    account.setCallgroup("XXXX");
    assertEquals(account.getCallgroup(), "XXXX");
    assertEquals(account.getSipUser().getCallgroup(), "");

    account.setPickupgroup("10");
    assertEquals(account.getPickupgroup(), "10");
    assertEquals(account.getSipUser().getPickupgroup(), "10");

    account.setPickupgroup("64");
    assertEquals(account.getPickupgroup(), "64");
    assertEquals(account.getSipUser().getPickupgroup(), "");

    account.setPickupgroup("XXXX");
    assertEquals(account.getPickupgroup(), "XXXX");
    assertEquals(account.getSipUser().getPickupgroup(), "");

    account.setPickupgroup("10,64");
    assertEquals(account.getPickupgroup(), "10,64");
    assertEquals(account.getSipUser().getPickupgroup(), "10");

    account.setPickupgroup("10,64,8,XXXX");
    assertEquals(account.getPickupgroup(), "10,64,8,XXXX");
    assertEquals(account.getSipUser().getPickupgroup(), "10,8");
  }
}
