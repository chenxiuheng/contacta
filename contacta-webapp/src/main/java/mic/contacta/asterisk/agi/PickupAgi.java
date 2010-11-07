/* $Id: PickupAgi.java 616 2010-04-03 21:07:58Z michele.bianchi $
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
import mic.contacta.model.SipAccountModel;
import mic.contacta.server.spi.SipService;


/**
 *
 * @author mic
 * @created Jan 20, 2009
 */
@Service("pickupAgi")
public class PickupAgi extends AbstractContactaAgi
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  public static final String VARNAME_PICKUP = "PICKUP";

  @Autowired private SipService sipService;


  /*
   *
   */
  public PickupAgi()
  {
    super("#79");
  }


  /*
   * do smth when decided what to do, now just print some logs...
   */
  protected void postDecision(Boolean b)
  {
    if (b)
    {
      log().debug("pickup!");
      //channel.setVariable("GLOBAL(PICKUPMARK)", calleeExten);
      //int r = channel.exec("DPickup", calleeExten);//+"@PICKUPMARK");
    }
    else
    {
      log().debug("permission denied");
      //channel.sayPhonetic("ko");
    }
  }


  /*
   * @see org.asteriskjava.fastagi.AgiScript#service(org.asteriskjava.fastagi.AgiRequest, org.asteriskjava.fastagi.AgiChannel)
   */
  @Transactional(readOnly=true)
  @Override
  public void service(AgiRequest request, AgiChannel channel) throws AgiException
  {
    String pickerExten = request.getCallerIdNumber();
    SipAccountModel pickerSip = sipService.findAccountByLogin(pickerExten);
    if (pickerSip == null)
    {
      log().warn("{}: who are you?!?!?", pickerExten);
      channel.setVariable(VARNAME_PICKUP, Boolean.FALSE.toString());
      return;
    }
    /* recognize the callee in the sip db */
    String calleeExten = request.getExtension().substring(prefix.length());
    SipAccountModel calleeSip = sipService.findAccountByLogin(calleeExten);
    if (calleeSip == null)
    {
      log().warn("{}: who is she?!?!?", calleeExten);
      channel.setVariable(VARNAME_PICKUP, Boolean.FALSE.toString());
      return;
    }

    String callgroup = calleeSip.getCallgroup();
    if (StringUtils.isNotBlank(callgroup))
    {
      String[] pickupgroups = pickerSip.getPickupgroup().split(",");
      for (String s : pickupgroups)
      {
        if (StringUtils.equals(callgroup, s))
        {
          log().debug("pickupGroup: {}, on callGroup: {}", pickerSip.getPickupgroup(), callgroup);
          log().info("pickup!");
          channel.setVariable(VARNAME_PICKUP, Boolean.TRUE.toString());
          return;
        }
      }
    }
    channel.setVariable(VARNAME_PICKUP, Boolean.FALSE.toString());
    log().info("permission denied");
    //Boolean b = StringUtils.equals(calleeSip.getPickupgroup(), pickerSip.getPickupgroup());
    //postDecision(b);
  }

}
