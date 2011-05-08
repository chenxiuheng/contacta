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

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mic.contacta.model.SipAccountModel;
import mic.contacta.server.spi.SipService;
import mic.organic.aaa.model.PersonModel;
import mic.organic.aaa.spi.AccountService;
import mic.organic.aaa.spi.AddressbookService;


/**
 *
 *
 * @author mic
 * @created May 1, 2011
 */
@Service("skypeInAgi")
public class SkypeInAgi extends AbstractContactaAgi
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private SipService sipService;
  @Autowired private AddressbookService addressbookService;
  @Autowired private AccountService accountService;


  /*
   *
   */
  public SkypeInAgi()
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
    String[] excludes = new String[] { "logger" };
    ReflectionToStringBuilder builder = new ReflectionToStringBuilder(request, ToStringStyle.MULTI_LINE_STYLE).setExcludeFieldNames(excludes);
    log().info("request:\n{}", builder.toString());

    String callerIdNumber = request.getCallerIdNumber();
    //SipAccountModel pickerSip = sipService.sipByLogin(callerIdNumber);
    String calleeIdNumber = request.getExtension();  // callee
    PersonModel person = addressbookService.personByUri(calleeIdNumber);
    String skype2exten = null;
    if (person == null)
    {
      log().warn("{}: who are you?!?!?", callerIdNumber);
    }
    else
    {
      skype2exten = person.getExtension();
      log().info("skype2exten={}", skype2exten);
    }
//    SipAccountModel calleeSip = sipService.sipByLogin(calleeIdNumber);
//    if (calleeSip == null)
//    {
//      log().warn("{}: who is she?!?!?", calleeIdNumber);
//    }

    channel.setVariable("SKYPE_2_EXTEN", skype2exten);
    //channel.setContext("sipphones");
    //log().info("permission denied");
    //Boolean b = StringUtils.equals(calleeSip.getPickupgroup(), pickerSip.getPickupgroup());
    //postDecision(b);
  }

}
