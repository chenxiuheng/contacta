/* $Id: ThomsonTest.java 673 2010-09-17 20:58:29Z michele.bianchi $
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
package mic.contacta.pmod.thomson;

import static org.testng.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import mic.contacta.model.PhoneModel;
import mic.contacta.model.SipAccountModel;
import mic.contacta.pmod.common.AbstractPmodTests;
import mic.contacta.pmod.common.ProvisioningResource;
import mic.contacta.pmod.common.Configurer;
import mic.contacta.pmod.common.ProvisioningContext;
import mic.contacta.pmod.common.ProvisioningSession;
import mic.contacta.server.spi.ContactaConfiguration;
import mic.contacta.server.spi.SampleBuilder;
import mic.contacta.util.ContactaUtils;


/**
 *
 *
 * @author mic
 * @created Dec 1, 2008
 */
@ContextConfiguration(locations = { "/mic/contacta/test-pmod.spring.xml" })
public class ThomsonTest extends AbstractPmodTests
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Resource(name="thomson2030Configurer") private Configurer configurer;
  private ContactaConfiguration configuration = new ContactaConfiguration();

  private List<SipAccountModel> list;


  /**
   * @return
   */
  private Map<String, Object> createParams()
  {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("contacta", configuration);
    params.put("baseUrl", "http://serverHost:80/p"); // URGENT 8088
    params.put("accountList", list);
    return params;
  }


  /*
   *
   */
  private void processResource(String resourceName, int responseLength) throws IOException, UnsupportedEncodingException
  {
    MockHttpServletResponse response = new MockHttpServletResponse();
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setMethod("GET");
    request.setPathInfo(resourceName);

    ProvisioningContext provisioningContext = new ProvisioningContext(request, response);
    ProvisioningSession provisioningSession = createPhoneSession(configurer);
    ProvisioningResource cfg = configurer.doProvisioning(provisioningContext, provisioningSession);
    PhoneModel phone = SampleBuilder.buildPhone(macAddress);
    provisioningContext.setPhone(phone);
    Map<String, Object> params = createParams();

    if (cfg != null)
    {
      cfg.write(provisioningContext.getResourceName(), 0, response, params);
    }

    String body = response.getContentAsString();
    //body = body.replaceFirst("<!-- \\$Id:.*\\$ -->", "<!-- \\$Id\\$ -->");
    //body = body.replaceFirst("http://.+:8088/o/.+\\.action", "http://localhost:8088/o/.+\\.action");
    body = body.replaceAll("http://.+:8088/p/thomson", "http://localhost:8088/p/thomson");
    log().info("resourceName={}\n{}", resourceName, body);
    log().debug("ASSERT resourceName={}", resourceName);
    assertEquals(body.length(), responseLength);
  }


  /*
   * 10.0.2.7 "GET http://10.0.2.1/thomson/st2030s.inf HTTP/1.1" 200 297
   * 10.0.2.7 "GET http://10.0.0.1/thomson/v2030SG.R11.1.090305.2.67.2.zz HTTP/1.1" 200 1663792
   * 10.0.2.7 "GET http://10.0.0.1/thomson/v2030_dsp_v310.zz HTTP/1.1" 200 245225
   *
   * --> reboot
   *
   * 10.0.2.7 "GET /thomson/st2030s.inf HTTP/1.1" 200 627
   * 10.0.2.7 "GET /thomson/Tone-Melodies.txt HTTP/1.1" 200 2329
   * 10.0.2.7 "GET /thomson/Tone-RG.txt HTTP/1.1" 200 447
   * 10.0.2.7 "GET /thomson/Tone-CW.txt HTTP/1.1" 200 151
   * 10.0.2.7 "GET /thomson/ToneTbl_2.67.2.zz HTTP/1.1" 200 8862
   * 10.0.2.7 "GET /thomson/LangTbl_2.67.2.zz HTTP/1.1" 200 30091
   * 10.0.2.7 "GET /thomson/TelConf2030SG.R11.1.2.67.2.txt HTTP/1.1" 200 32671
   * 10.0.2.7 "GET /thomson/ComConf2030SG.R11.1.2.67.2.txt HTTP/1.1" 200 16078
   * 10.0.2.7 "GET /thomson/ST2030S_001F9F16E7F8.ser HTTP/1.1" 404 357
   * 10.0.2.7 "GET /thomson/ST2030S_001F9F16E7F8.txt HTTP/1.1" 200 16163
   *
   * --> reboot
   *
   * 10.0.2.7 "GET /thomson/st2030s.inf HTTP/1.1" 200 627
   * 10.0.2.7 "GET /thomson/ST2030S_001F9F16E7F8.ser HTTP/1.1" 404 357
   * 10.0.2.7 "GET /thomson/ST2030S_001F9F16E7F8.txt HTTP/1.1" 200 16163
   *
   * --> ready - standby
   */
  @Test(enabled=true)
  public void testConfigV2692() throws Exception
  {
    //createAccountAndPhone("");
    sipAccount = SampleBuilder.buildContactaAccount("boh");

    list = new ArrayList<SipAccountModel>();
    list.add(sipAccount);

    processResource("thomson/st2030s.inf", 743);  // dont use final / in the config!!!!!!!!!!!!!!!!!!!!!
    //doSmth("thomson/v2030SG.R11.1.090305.2.67.2.zz", 1663792);
    //doSmth("thomson/v2030_dsp_v310.zz", 245225);
    processResource("thomson/Tone-Melodies.txt", 2329);
    processResource("thomson/Tone-RG.txt", 447);
    processResource("thomson/Tone-CW.txt", 151);
    //doSmth("thomson/ToneTbl_2.67.2.zz", 8862);
    //doSmth("thomson/LangTbl_2.67.2.zz", 30091);
    processResource("thomson/TelConf2030SG.R11.1.SED.091020.2.69.2.txt", 32671);
    processResource("thomson/ComConf2030SG.R11.1.SED.091020.2.69.2.txt", 16685);
    processResource("thomson/ST2030S_"+ContactaUtils.macAddressTextToHex(macAddress).toUpperCase()+".ser", 0);
    processResource("thomson/ST2030S_"+ContactaUtils.macAddressTextToHex(macAddress).toUpperCase()+".txt", 14450);
  }


  /*
   *
   */
  @Test(enabled=false)
  public void testRoms() throws Exception
  {
    processResource("thomson/v2030SG.R11.1.090305.2.67.2.zz", 1663792);
    processResource("thomson/v2030_dsp_v310.zz", 245225);
    processResource("thomson/ToneTbl_2.67.2.zz", 8862);
    processResource("thomson/LangTbl_2.67.2.zz", 30091);
  }
}
