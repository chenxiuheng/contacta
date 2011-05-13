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

import java.util.List;

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
import mic.contacta.dao.SipAccountDao;
import mic.contacta.domain.SipAccountModel;
import mic.contacta.server.PbxService;


/**
 *
 * @author mic
 * @created Jan 20, 2009
 */
@Service("pickupAllAgi")
public class PickupAllAgi extends AbstractContactaAgi
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  public static final String VARNAME_PICKUP = "PICKUP";

  @Autowired private PbxService pbxService;
  @Autowired private SipAccountDao sipAccountDao;

  /*
   *
   */
  public PickupAllAgi()
  {
    super("#71");
  }


  /*
   * @see org.asteriskjava.fastagi.AgiScript#service(org.asteriskjava.fastagi.AgiRequest, org.asteriskjava.fastagi.AgiChannel)
   */
  @Transactional(readOnly=true)
  @Override
  public void service(AgiRequest request, AgiChannel channel) throws AgiException
  {
    String pickerExten = request.getCallerIdNumber();
    SipAccountModel pickerSip = pbxService.sipByLogin(pickerExten);
    if (pickerSip == null)
    {
      log().warn("{}: who are you?!?!?", pickerExten);
      channel.setVariable(VARNAME_PICKUP, Boolean.FALSE.toString());
      return;
    }

    String callgroup = pickerSip.getCallgroup();
    if (StringUtils.isNotBlank(callgroup))
    {
      log().info("pickup from {}, callgroup={}", pickerExten, callgroup);
      pickupAll(channel, pickerSip, callgroup);
    }
    else
    {
      log().info("pickup from {}, callgroup is blank ({})", pickerExten, callgroup);
    }
  }


  /**
   * @param callgroup
   * @throws AgiException
   *
   */
  private void pickupAll(AgiChannel channel, SipAccountModel pickerSip, String callgroup) throws AgiException
  {
    List<SipAccountModel> list = sipAccountDao.findAccountByCallgroup(callgroup);
    StringBuilder sb0 = new StringBuilder();
    StringBuilder sb1 = new StringBuilder();
    StringBuilder sb2 = new StringBuilder();
    StringBuilder sb3 = new StringBuilder();
    StringBuilder sb4 = new StringBuilder();

    for (SipAccountModel account : list)
    {
      if (StringUtils.equals(pickerSip.getLogin(), account.getLogin()) == false)
      {
        sb0.append(account.getLogin());
        sb0.append("@interni&");

        sb1.append(account.getLogin());
        sb1.append("@nazionali-cell&");

        sb2.append(account.getLogin());
        sb2.append("@nazionali&");

        sb3.append(account.getLogin());
        sb3.append("@internazionali&");

        sb4.append(account.getLogin());
        sb4.append("@daremoto&");
      }
    }
    String pickupString0 = sb0.length() > 0 ? sb0.toString().substring(0, sb0.length()-1) : null;
    String pickupString1 = sb1.length() > 0 ? sb1.toString().substring(0, sb1.length()-1) : null;
    String pickupString2 = sb2.length() > 0 ? sb2.toString().substring(0, sb2.length()-1) : null;
    String pickupString3 = sb3.length() > 0 ? sb3.toString().substring(0, sb3.length()-1) : null;
    String pickupString4 = sb4.length() > 0 ? sb4.toString().substring(0, sb4.length()-1) : null;

    log().debug("pickupString0={}", pickupString0);
    log().debug("pickupString1={}", pickupString1);
    log().debug("pickupString2={}", pickupString2);
    log().debug("pickupString3={}", pickupString3);
    log().debug("pickupString4={}", pickupString4);

    if (StringUtils.isNotBlank(pickupString0))
    {
      channel.setCallerId("Pickup gruppo "+callgroup);
      int r = channel.exec("DPickup", pickupString0);
      log().debug("pickupString0 result={}", r);

      r = channel.exec("DPickup", pickupString1);
      log().debug("pickupString1 result={}", r);

      r = channel.exec("DPickup", pickupString2);
      log().debug("pickupString2 result={}", r);

      r = channel.exec("DPickup", pickupString3);
      log().debug("pickupString3 result={}", r);

      r = channel.exec("DPickup", pickupString4);
      log().debug("pickupString4 result={}", r);
    }
  }

}
