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
package mic.contacta.asterisk.agi;

import org.apache.commons.lang.StringUtils;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mic.contacta.asterisk.agi.AbstractContactaAgi;
import mic.contacta.domain.SipAccountModel;
import mic.contacta.server.PbxService;


/**
 * this agi do the pickup of the attendant (boss-assistant)
 *
 *
 * @author mic
 * @created May 17, 2009
 */
@Service("coveragePickupAgi")
public class CoveragePickupAgi extends AbstractContactaAgi
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private PbxService pbxService;


  /*
   *
   */
  public CoveragePickupAgi()
  {
    super("p");
  }


  /*
   * @see org.asteriskjava.fastagi.AgiScript#service(org.asteriskjava.fastagi.AgiRequest, org.asteriskjava.fastagi.AgiChannel)
   */
  @Transactional
  @Override
  public void service(AgiRequest request, AgiChannel channel) throws AgiException
  {
    String callerExten = request.getExtension();
    //String exten = channel.getVariable("ARG1");

    SipAccountModel sip = pbxService.sipByLogin(callerExten);
    if (sip == null)
    {
      log().warn("{}: who are you?!?!?", callerExten);
      //channel.exec("Wait", "1");
      //channel.exec("Playback", "beeperr");
    }

    String calleeExten = callerExten.substring(1);
    log().info("callerExten={}, calleeExten={}", callerExten, calleeExten);
    if (StringUtils.isNotBlank(calleeExten))
    {
      int r = channel.exec("DPickup", calleeExten);
    }
  }

}
