/* $Id: CocSetAgi.java 642 2010-05-30 09:15:27Z michele.bianchi $
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
import mic.contacta.model.CocModel;
import mic.contacta.model.SipAccountModel;
import mic.contacta.server.dao.CocDao;
import mic.contacta.server.spi.ContactaService;
import mic.contacta.server.spi.SipService;
import mic.contacta.util.ContactaUtils;


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
@Service("cocSetAgi")
public class CocSetAgi extends AbstractContactaAgi
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private ContactaService contactaService;
  @Autowired private SipService sipService;
  @Autowired private CocDao cocDao;


  /*
   *
   */
  public CocSetAgi()
  {
    super("*162");
  }


  /**
   * this has with a lot of speech
   */
  protected void fullExperience(AgiChannel channel) throws AgiException
  {
    //printDebug(request, channel);
    channel.setVariable("CHANNEL(language)", "it");
    channel.exec("Wait", "1");
    channel.exec("Background", "agent-user");
    channel.exec("Read", "digito||3");
    String loginExten = channel.getVariable("digito");
    SipAccountModel loginSip = sipService.findAccountByLogin(loginExten);
    if (loginSip == null)
    {
      log().warn("{}: who are you?!?!?", loginExten);
      channel.exec("Playback", "tt-somethingwrong");
      return;
    }
    channel.exec("Playback", "beep");
    channel.sayNumber(loginExten);
    channel.exec("Background", "agent-pass");
    channel.exec("Read", "digito2||");
    String s = channel.getVariable("digito2");
    channel.sayDigits(s);
    String pin = ContactaUtils.passwordToPin(loginSip.getPassword());
    if (StringUtils.equals(pin, s))
    {
      loginSip.getSipUser().setContext("internazionali");
      sipService.updateAccount(loginSip);
      channel.exec("Playback", "beep");
      channel.exec("Playback", "beep");
      channel.exec("Playback", "beep");
      channel.sayAlpha("ok");

      contactaService.asteriskCommand(ContactaService.SIP_RELOAD);
    }
    else
    {
      channel.exec("Playback", "beeperr");
      channel.exec("Background", "vm-reenterpassword");
    }
  }


  /*
   * @see org.asteriskjava.fastagi.AgiScript#service(org.asteriskjava.fastagi.AgiRequest, org.asteriskjava.fastagi.AgiChannel)
   */
  @Transactional
  @Override
  public void service(AgiRequest request, AgiChannel channel) throws AgiException
  {
    String callerExten = request.getCallerIdNumber();
    SipAccountModel callerSip = sipService.findAccountByLogin(callerExten);
    if (callerSip == null)
    {
      log().warn("{}: who are you?!?!?", callerExten);
      channel.exec("Wait", "1");
      channel.exec("Playback", "beeperr");
      return;
    }
    String cocLogin = request.getExtension().substring(prefix.length());
    log().info("cocLogin", cocLogin);
    CocModel coc = cocDao.findByLogin(cocLogin);
    if (coc == null)
    {
      log().warn("{}: who are you?!?!?", cocLogin);
      channel.exec("Wait", "1");
      channel.exec("Playback", "beeperr");
      return;
    }
    if (StringUtils.isBlank(coc.getPin()))
    {
      channel.exec("Wait", "1");
      channel.exec("Playback", "beeperr");
    }

    channel.exec("Read", "digito||4");
    String secret = channel.getVariable("digito");
    String pin = coc.getPin();
    if (StringUtils.equals(pin, secret))
    {
      callerSip.getSipUser().setContext("internazionali");
      callerSip = sipService.updateAccount(callerSip);
      channel.exec("Wait", "1");
      channel.exec("Playback", "beep");
      channel.exec("Playback", "beep");
      channel.exec("Playback", "beep");

      contactaService.asteriskCommand(ContactaService.SIP_RELOAD);
    }
    else
    {
      channel.exec("Wait", "1");
      channel.exec("Playback", "beeperr");
    }
  }

}
