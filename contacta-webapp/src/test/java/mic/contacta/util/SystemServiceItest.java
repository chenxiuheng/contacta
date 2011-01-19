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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import mic.contacta.server.api.ContactaConstants;


/**
 *
 *
 * @author mic
 * @created Dec 1, 2008
 */
@ContextConfiguration(locations = { ContactaConstants.SPRING_TEST_ORM, ContactaConstants.TEST_PROVISIONING_SPRING })
public class SystemServiceItest extends AbstractTransactionalTestNGSpringContextTests
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private SystemService systemService;


  /*
   *
   */
  @Test
  public void testCheckAsterisk()
  {
    String asteriskStatus = systemService.checkAsterisk();

    boolean status = asteriskStatus.equals("Asterisk is up and running") ||
    asteriskStatus.equals("Asterisk is down. Plese restart") ||
    asteriskStatus.equals("Cannot reach asterisk");

    Assert.assertTrue(status);
  }

}
