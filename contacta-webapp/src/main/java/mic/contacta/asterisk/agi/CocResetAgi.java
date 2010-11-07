/* $Id: CocResetAgi.java 645 2010-05-30 14:50:47Z michele.bianchi $
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
package mic.contacta.asterisk.agi;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mic.contacta.asterisk.agi.AbstractContactaAgi;
import mic.contacta.model.SipAccountModel;
import mic.contacta.server.spi.ContactaService;
import mic.contacta.server.spi.SipService;


/**
 *
 * I configured the dialplan as follow:
 * exten => 777,1,Answer()
 * exten => 777,n,AGI(agi://10.0.0.1/cocSet)
 * exten => 777,n,Hangup()
 * exten => 778,1,Answer()
 * exten => 778,n,AGI(agi://10.0.0.1/cocReset)
 * exten => 778,n,Hangup()
 *
 * @author mic
 * @created Jan 20, 2009
 */
@Service("cocResetAgi")
public class CocResetAgi extends AbstractContactaAgi
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private SipService sipService;
  @Autowired private ContactaService contactaService;

  /*
   *
   */
  public CocResetAgi()
  {
    super("*163");
  }


  /*
   * @see org.asteriskjava.fastagi.AgiScript#service(org.asteriskjava.fastagi.AgiRequest, org.asteriskjava.fastagi.AgiChannel)
   */
  @Transactional
  @Override
  public void service(AgiRequest request, AgiChannel channel) throws AgiException
  {
    //printDebug(request, channel);
    String callerExten = request.getCallerIdNumber();
    SipAccountModel callerSip = sipService.findAccountByLogin(callerExten);
    if (callerSip == null)
    {
      log().warn("{}: who are you?!?!?", callerExten);
      channel.exec("Wait", "1");
      channel.exec("Playback", "beeperr");
      return;
    }
    callerSip.getSipUser().setContext(callerSip.getContext().getCode());
    sipService.updateAccount(callerSip);
    channel.exec("Wait", "1");
    channel.exec("Playback", "beep");

    contactaService.asteriskCommand(ContactaService.SIP_RELOAD);
  }

}
