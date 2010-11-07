/* $Id: ContactaServiceItest.java 1468 2008-05-25 14:03:10Z michele.bianchi@gmail.com $
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

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import mic.contacta.server.api.ContactaException;


/**
 * Class providing unit testing ...
 *
 * @author    mic
 * @version   $Revision: 642 $
 */
@ContextConfiguration(locations={ "/test-contacta.spring.xml" })
public class ContactaServiceItest extends AbstractProvisioningTests
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  //@Autowired private ContactaService contactaService;


  /**
   *
   */
  //@Test
  public void testDial() throws ContactaException
  {
    //contactaService.dial("303", "303");

    assertTrue(true);
  }


  /**
   *
   */
  @Test
  public void testAccountOnSip() throws ContactaException
  {
    String sipList = null;//contactaService.getActiveSipList();
    log().info("siplist={}", sipList);
    assertNotNull(sipList, "returned null list");
  }
}

