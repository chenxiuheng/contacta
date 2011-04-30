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
import mic.contacta.model.SipAccountModel;
import mic.contacta.server.spi.SipService;


/**
 *
 *
 * @author mic
 * @created May 1, 2011
 */
@Service("skypeAgi")
public class SkypeAgi extends AbstractContactaAgi
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private SipService sipService;


  /*
   *
   */
  public SkypeAgi()
  {
    super("#42");
  }


  /*
   * @see org.asteriskjava.fastagi.AgiScript#service(org.asteriskjava.fastagi.AgiRequest, org.asteriskjava.fastagi.AgiChannel)
   */
  @Transactional(readOnly=true)
  @Override
  public void service(AgiRequest request, AgiChannel channel) throws AgiException
  {
    String pickerExten = request.getCallerIdNumber();
    SipAccountModel pickerSip = sipService.sipByLogin(pickerExten);
    if (pickerSip == null)
    {
      log().warn("{}: who are you?!?!?", pickerExten);
      return;
    }
    /* recognize the callee in the sip db */
    String calleeExten = request.getExtension().substring(prefix.length());
    SipAccountModel calleeSip = sipService.sipByLogin(calleeExten);
    if (calleeSip == null)
    {
      log().warn("{}: who is she?!?!?", calleeExten);
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
          return;
        }
      }
    }
    //channel.setVariable(VARNAME_PICKUP, Boolean.FALSE.toString());
    log().info("permission denied");
    //Boolean b = StringUtils.equals(calleeSip.getPickupgroup(), pickerSip.getPickupgroup());
    //postDecision(b);
  }

}
