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

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;
import java.io.IOException;
import javax.annotation.Resource;
import org.apache.commons.vfs.FileObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import mic.contacta.pmod.common.Configurer;
import mic.contacta.pmod.common.ProvisioningContext;
import mic.contacta.server.api.ContactaConstants;
import mic.contacta.util.ContactaUtils;


/**
 *
 *
 * @author mic
 * @created Dec 1, 2008
 */
@ContextConfiguration(locations = { ContactaConstants.TEST_PROVISIONING_SPRING })
public class ProvisioningServiceTest extends AbstractProvisioningTests
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private ProvisioningService provisioningService;
  @Autowired(required=false) private ImportService importService;
  @Resource(name="thomson2030Configurer") private Configurer configurer;

  /*
   *
   */
  @Test(enabled=false)
  public void testGenerateConfiguration() throws Exception
  {
    assertNotNull(provisioningService);

    addSampleProducts();

    FileObject fo = organicVfs.resolve("res:data.csv");
    //importService.importFromCsv(fo);
    //provisioningService.generate();
    assertTrue(true);
  }


  /*
   *
   */
  private void processResource(String resourceName, int i) throws IOException
  {
    MockHttpServletResponse response = new MockHttpServletResponse();
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setMethod("GET");
    request.setPathInfo(resourceName);
    request.addHeader("user-agent", "THOMSON ST2030 hw5 fw2.67 00-04-f2-1f-98-bd");

    ProvisioningContext provisioningContext = new ProvisioningContext(request, response);
    boolean r = provisioningService.doProvisioning(provisioningContext);

    assertTrue(r);
  }


  /*
   *
   */
  @Test
  public void testGenerateThomson() throws Exception
  {
    //addSampleProducts();

    createAccountAndPhone("buuu");
    provisioningService.registerPmod(configurer);

    processResource("thomson/st2030s.inf", 732);
    //doSmth("thomson/v2030SG.R11.1.090305.2.67.2.zz", 1663792);
    //doSmth("thomson/v2030_dsp_v310.zz", 245225);
    processResource("thomson/Tone-Melodies.txt", 2329);
    processResource("thomson/Tone-RG.txt", 447);
    processResource("thomson/Tone-CW.txt", 151);
    //doSmth("thomson/ToneTbl_2.67.2.zz", 8862);
    //doSmth("thomson/LangTbl_2.67.2.zz", 30091);
    processResource("thomson/TelConf2030SG.R11.1.SED.091020.2.69.2.txt", 32671);
    processResource("thomson/ComConf2030SG.R11.1.SED.091020.2.69.2.txt", 16685);
    //processResource("thomson/ST2030S_"+ContactaUtils.macAddressTextToHex(macAddress).toUpperCase()+".ser", 0);
    processResource("thomson/ST2030S_"+ContactaUtils.macAddressTextToHex(macAddress).toUpperCase()+".txt", 14348);


  }

}
