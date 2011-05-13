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
package mic.contacta.pmod.polycom;

import static org.testng.Assert.*;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import mic.contacta.domain.PhoneModel;
import mic.contacta.pmod.common.Configurer;
import mic.contacta.pmod.common.ProvisioningContext;
import mic.contacta.pmod.common.ProvisioningSession;
import mic.contacta.server.AbstractProvisioningTests;
import mic.contacta.server.ContactaConstants;
import mic.organic.core.OrganicException;


/**
 *
 *
 * @author mic
 * @created Dec 1, 2008
 */
@ContextConfiguration(locations = { ContactaConstants.TEST_PROVISIONING_SPRING })
public class PolycomConfigurerTest extends AbstractProvisioningTests
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Resource(name="polycomConfigurer")  private Configurer polycomConfigurer;

  @Test
  public void pliz()
  {
    log().warn("pliz, activate me again!");
  }


  /*
   *
   */
  private ServletResponse doGet(String resourceName, int responseLength) throws IOException, OrganicException, ServletException
  {
    MockHttpServletResponse response = new MockHttpServletResponse();
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setMethod("GET");
    request.setPathInfo("/"+resourceName);
    ProvisioningContext provisioningContext = new ProvisioningContext(request, response);
    PhoneModel phone = null;
    ProvisioningSession provisioningSession = new ProvisioningSession(polycomConfigurer.getCode(), phone.getMacAddress());

    polycomConfigurer.doProvisioning(provisioningContext, provisioningSession);
    String body = response.getContentAsString();
    body = body.replaceFirst("<!-- \\$Id:.*\\$ -->", "<!-- \\$Id\\$ -->");
    body = body.replaceFirst("http://.+:8088/o/.+\\.action", "http://localhost:8088/o/.+\\.action");
    //log().info("resourceName={}\n{}", resourceName, body);
    assertEquals(body.length(), responseLength);  // FIXME qui...
    return response;
  }


  /*
   * this is the boot sequence at sunday 2009/01/25
   * see also http://wiki.chrome.homelinux.com/confluence/display/cc/phones
   */
  @Test(enabled=false)
  public void testBootSpip450() throws IOException, OrganicException, ServletException
  {
    createAccountAndPhone("");

    ServletResponse response = null;
    //doGet("2345-12450-001.bootrom.ld", 609364);
    response = doGet("0004f21f98bd.cfg", 440);
    //response = doGet("2345-12450-001.sip.ld", 3587333);
    //response = doPut("0004f21f98bd-boot.log");
    //response = doGet("2345-12450-001.bootrom.ld", 609364);
    response = doGet("0004f21f98bd.cfg", 440);
    //response = doGet("2345-12450-001.sip.ld", 3587333);
    response = doGet("local-settings.cfg", 4449);
    response = doGet("sip.cfg", 188522);
    response = doGet("phone1.cfg", 12776);
    response = doGet("0004f21f98bd-directory.xml", 186);
    response = doGet("0004f21f98bd-phone.cfg", 2139);
    response = doGet("0004f21f98bd-directory.xml", 186);
    //response = doPut("0004f21f98bd-app.log");
    //response = doGet("Leaf256x116.jpg", 7942);
    //response = doGet("Sailboat256x116.jpg", 9036);
    //response = doGet("Beach256x116.jpg", 4726);
    //response = doGet("Palm256x116.jpg", 14091);
    //response = doGet("Jellyfish256x116.jpg", 5040);
    //response = doGet("Mountain256x116.jpg", 9075);
    //response = doGet("SoundPointIPLocalization/Italian_Italy/SoundPointIP-dictionary.xml", 33753);
    assertNotNull(response);
  }


  /*
   *
   */
  //@Test
  public void testLoadPhoneConfiguration() throws OrganicException
  {
    createAccountAndPhone("");

    Map<String, Object> params = null;// FIXME provisioningService.createParams();
    ProvisioningSession provisioningSession = null;//configurer.loadPhoneConfiguration(params, macAddress);
    assertNotNull(provisioningSession);
    assertNotNull(provisioningSession.getMacAddress());
    assertEquals(provisioningSession.getMacAddress(), macAddress);
    assertNotNull(provisioningSession.getBootrom());
    assertNotNull(provisioningSession.getSiprom());
    assertNotNull(provisioningSession.getSipCfg());
    assertNotNull(provisioningSession.getDefaultCfg());
    assertNotNull(provisioningSession.getDirectoryXml());
    assertNull(provisioningSession.getMacCfg());
    assertNotNull(provisioningSession.getPhoneCfg());
    assertNotNull(provisioningSession.getWelcomeWav());

    log().debug("phoneConfiguration.phoneCfg:\n{}", provisioningSession.getPhoneCfg());
  }


}
