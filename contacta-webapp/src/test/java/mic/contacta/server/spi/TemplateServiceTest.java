/* $Id: TemplateServiceTest.java 25 2008-12-16 18:30:51Z michele.bianchi@gmail.com $
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;

import static org.testng.Assert.*;

import mic.contacta.server.spi.TemplateService;


/**
 *
 * @author mic
 * @created Nov 23008
 */
@ContextConfiguration(locations = { "/mic/contacta/test-template.spring.xml" })
public class TemplateServiceTest extends AbstractTestNGSpringContextTests
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private TemplateService templateService;


  /*
   *
   */
  @Test
  public void simple() throws ScriptException, IOException
  {
    Map<String,Object> params = new HashMap<String,Object>();
    params.put("host", "10.0.0.1");
    params.put("port", "5060");
    params.put("calleridnum", "4444");

    templateService.generate("polycom/test.ftl", "test.cfg", params, true);
    assertTrue(true);
  }

}
