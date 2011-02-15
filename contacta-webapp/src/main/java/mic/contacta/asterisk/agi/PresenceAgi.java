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

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mic.contacta.asterisk.agi.AbstractContactaAgi;
import mic.contacta.model.CoverageModel;
import mic.contacta.model.SipAccountModel;
import mic.contacta.model.CoverageModel.CoverageType;
import mic.contacta.model.SipAccountModel.Presence;
import mic.contacta.server.api.ContactaException;
import mic.contacta.server.spi.ContactaService;
import mic.contacta.server.spi.SipService;


/**
 * this agi set the presence for the caller
 *
 * @author mic
 * @created May 17, 2009
 */
@Service("presenceAgi")
public class PresenceAgi extends AbstractContactaAgi
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private SipService sipService;
  @Autowired private ContactaService contactaService;


  /*
   *
   */
  public PresenceAgi()
  {
    super("");
  }


  /*
   * @see org.asteriskjava.fastagi.AgiScript#service(org.asteriskjava.fastagi.AgiRequest, org.asteriskjava.fastagi.AgiChannel)
   */
  @Transactional
  @Override
  public void service(AgiRequest request, AgiChannel channel) throws AgiException
  {
    String callerExten = request.getCallerIdNumber();
    log().info("callerExten={}", callerExten);

    SipAccountModel callerSip = sipService.sipByLogin(callerExten);
    if (callerSip == null)
    {
      log().warn("{}: who are you?!?!?", callerExten);
      channel.exec("Wait", "1");
      channel.exec("Playback", "beeperr");
      channel.exec("Playback", "beeperr");
      channel.exec("Playback", "beeperr");
      channel.exec("Hangup");
    }
    else
    {
      boolean go = false;
      for (CoverageModel cvg : callerSip.getCoverageList())
      {
        go |= (cvg.getType() == CoverageType.Assistant);
      }

      Presence presence = callerSip.getPresence();
      if (go || presence == Presence.Dnd)
      {
        presence = (presence == Presence.Online) ? Presence.Dnd : Presence.Online;
        log().info("callerExten={}, setting presence={}", callerExten, presence.toString());
        callerSip.setPresence(presence);
        sipService.sipUpdate(callerSip);
        channel.exec("Wait", "1");
        switch (presence)
         {
           case Dnd:
                channel.exec("Playback", "dnd_attivo");
                break;
           case Online:
                channel.exec("Playback", "dnd_disattivo");
                break;
           default:
                log().warn("Presence status not recognized ");
                break;
          }
        channel.exec("Hangup");

        try
        {
          sipService.writeExtenHint();
          contactaService.asteriskCommand(ContactaService.EXTENSIONS_RELOAD);
        }
        catch (ContactaException e)
        {
          log().warn("cannot write extensions_hint.conf: {}", e.getMessage(), e);
        }
      }
      else
      {
        log().warn("{}: cannot use presence", callerExten);
        channel.exec("Wait", "1");
        channel.exec("Playback", "beeperr");
        channel.exec("Hangup");
      }
    }
  }

}
