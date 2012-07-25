/**
 * Contacta webapp, http://www.openinnovation.it - Michele Bianchi
 * Copyright(C) 1999-2012 info@openinnovation.it
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
package mic.contacta.asterisk;

import java.util.List;

import mic.contacta.asterisk.spi.AsteriskService;
import mic.contacta.asterisk.spi.SipStatus;
import mic.contacta.domain.ChannelStatusLine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import static org.testng.Assert.*;
import org.testng.annotations.Test;


/**
 * Class providing unit testing for the Mpbx.
 *
 * @author    mic
 * @created   April 21, 2005
 * @version   $Revision: 616 $
 */
@ContextConfiguration(locations={ "/test-contacta.spring.xml" })
public class AsteriskServiceItest extends AbstractTestNGSpringContextTests
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private AsteriskService asteriskService;


  /*
   * register 400, 401 and 402
   * call from 402 the 401, then run the test.
   * 400 should start ringing and 401 stop.
   * if you answer to 400 you should talk with 402.
   */
  @Test
  public void testPickup() throws Exception
  {
    String r = null;// TODO asteriskService.getActiveChannels();
    log().info("activeChannels\n---------------\n{}", asteriskService.getActiveChannels());

    //asteriskService.dial("402", "401");
    String callerExten = "402";
    String calleeExten = "401";
    String pickerExten = "400";

    ChannelStatusLine csl = asteriskService.findChannelByExtension(calleeExten);
    log().info("channelStatusLine: {}", csl);

    if (csl != null)
    {
      asteriskService.redirect(csl.getChannel(), pickerExten, csl.getContext(), 1);
      log().info("activeChannels\n---------------\n{}", asteriskService.getActiveChannels());
    }
    else
    {
      log().info("no active channel for caller extension {}", calleeExten);
    }
  }


  /*
   *
   */
  @Test
  public void testSipStatus() throws Exception
  {
    //AccountService accountService = OrganicHelper.getAccountService();

    //HashMap<String,Presence> map = new HashMap<String,Presence>();
    //PeerEntryEvent unknownPeer = new PeerEntryEvent(null);
    //unknownPeer.setObjectName("UNKNOWN");
    /*for (AccountModel createdBy : accountService.findAllAccounts())
    {
      map.put(createdBy.getLogin(), new Presence(createdBy, null));//unknownPeer));
    }*/

    List<SipStatus> sipStatusList = asteriskService.getSipStatusList();
    assertNotNull(sipStatusList, "returned null list");
    for (SipStatus sip : sipStatusList)
    {
      log().info("sip {} is connected={}", sip.getPeer().getObjectName(), sip.isConnected());
    }
    //TODO !!!!!!!!!!!!!!!!! assertEquals(sipStatusList.size(), 1, "connect only 1 phone");
    //TODO !!!!!!!!!!!!!!!!! assertTrue(sipStatusList.get(0).isConnected());
  }


  /*
   *
   */
  //@Test
  public void testDial() throws Exception
  {
    asteriskService.dial("302", "302");
  }


  //@Test
  public void testRegister() throws Exception
  {
    asteriskService.recordStart("302");
  }


  //@Test
  public void stopRegister() throws Exception
  {
    asteriskService.recordStop("302");
  }


}

