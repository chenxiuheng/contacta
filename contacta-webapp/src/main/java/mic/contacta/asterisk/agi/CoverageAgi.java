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
package mic.contacta.asterisk.agi;

import org.apache.commons.lang3.StringUtils;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mic.contacta.asterisk.agi.AbstractContactaAgi;
import mic.contacta.domain.CoverageModel;
import mic.contacta.domain.SipAccountModel;
import mic.contacta.domain.CoverageModel.CoverageType;
import mic.contacta.domain.SipAccountModel.Presence;
import mic.contacta.server.PbxService;


/**
 * this agi set the variable needed by boss-assistant macro
 *
 * presence se settata a false il direttore non ha abilitato il DND
 * assistantParallel se messa a true il direttore ha una doppia segretaria in parallelo
 * assistantCascade se messa a true il direttore ha due segretarie in cascata
 * ringTimeout pari al tempo in secondi in cui squilla il telefono del direttore
 * assistantRing0 ari al tempo in secondi in cui squilla il telefono della o delle segretarie
 * assistantLogin0 che e' l'interno della prima segretaria
 * assistantLogin1 che e' l'interno della seconda segretaria
 * vmEnabled a true o false per indicare se il direttore ha la voicemail attiva o no
 *
 * @author mic
 * @created May 17, 2009
 */
@Service("coverageAgi")
public class CoverageAgi extends AbstractContactaAgi
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private PbxService pbxService;


  /*
   *
   */
  public CoverageAgi()
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
    String calleeExten = channel.getVariable("ARG1");
    log().debug("calleeExten={}", calleeExten);
    if (StringUtils.isBlank(calleeExten))
    {
      return;
    }

    SipAccountModel callee = pbxService.sipByLogin(calleeExten);
    if (callee == null)
    {
      log().warn("{}: who are you?!?!?", calleeExten);
      //channel.exec("Wait", "1");
      //channel.exec("Playback", "beeperr");
    }

    String presence = callee.getPresence().toString();
    String vmEnabled = String.valueOf(callee.getVmEnabled());
    String ringTimeout = String.valueOf(callee.getRingTimeout());

    String assistantLogin0 = "";
    String assistantRing0 = "15";
    String assistantLogin1 = "";
    String assistantRing1 = "15";
    String assistantParallel = Boolean.FALSE.toString();
    String assistantCascade = Boolean.FALSE.toString();

    if (callee.getHasCoverage())
    {
      CoverageModel cvg0 = null;
      CoverageModel cvg1 = null;
      int n = callee.getCoverageList().size();
      if (n == 1 || n == 2)
      {
        cvg0 =  callee.getCoverageList().get(0);
        assistantLogin0 = String.valueOf(cvg0.getToSip().getLogin());
        assistantRing0 = String.valueOf(cvg0.getRingTimeout());
      }
      if (n == 2)
      {
        cvg1 =  callee.getCoverageList().get(1);
        assistantParallel = String.valueOf(cvg1.getType() == CoverageType.Assistant);
        assistantCascade = String.valueOf(cvg1.getType() == CoverageType.Assistant2nd);

        assistantLogin1 = String.valueOf(cvg1.getToSip().getLogin());
        assistantRing1 = String.valueOf(cvg1.getRingTimeout());
      }
      else if (n > 2)
      {
        log().warn("too many lines on coverage path for sip.login={}, dont know what to do yet", callee.getLogin());
      }

      // if the boss has no vm, the assistant rings forever (almost:)
      if (callee.getVmEnabled() == false)
      {
        if (cvg1 == null || cvg1.getType() == CoverageType.Assistant)
        {
          assistantRing0 = "300";
        }
        else
        {
          assistantRing1 = "300";
        }
      }

      // assistants can call the boss always
      if (callee.getPresence() == Presence.Dnd)
      {
        String callerExten = request.getCallerIdNumber();
        if (cvg0 != null && StringUtils.equals(cvg0.getToSip().getLogin(), callerExten) ||
            cvg1 != null && StringUtils.equals(cvg1.getToSip().getLogin(), callerExten))
        {
          presence = Presence.Online.toString();
        }
      }
    }
    else
    {
      log().warn("this sip.login={} has no coverage", callee.getLogin());
    }
    log().debug("presence={}", presence);
    log().debug("ringTimeout={}", ringTimeout);
    log().debug("vmEnabled={}", vmEnabled);
    log().debug("assistantParallel={}", assistantParallel);
    log().debug("assistantCascade={}", assistantCascade);
    log().debug("assistantLogin0={}", assistantLogin0);
    log().debug("assistantRing0={}", assistantRing0);
    log().debug("assistantLogin1={}", assistantLogin1);
    log().debug("assistantRing1={}", assistantRing1);

    channel.setVariable("presence", presence);
    channel.setVariable("ringTimeout", ringTimeout);
    channel.setVariable("vmEnabled", vmEnabled);
    channel.setVariable("assistantParallel", assistantParallel);
    channel.setVariable("assistantCascade", assistantCascade);
    channel.setVariable("assistantLogin0", assistantLogin0);
    channel.setVariable("assistantRing0", assistantRing0);
    channel.setVariable("assistantLogin1", assistantLogin1);
    channel.setVariable("assistantRing1", assistantRing1);
  }

}
