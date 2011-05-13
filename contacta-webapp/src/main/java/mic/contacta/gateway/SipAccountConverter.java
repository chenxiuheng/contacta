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
package mic.contacta.gateway;

import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mic.contacta.dao.PbxContextDao;
import mic.contacta.dao.PbxProfileDao;
import mic.contacta.domain.PbxContextModel;
import mic.contacta.domain.PbxProfileModel;
import mic.contacta.domain.SipAccountModel;
import mic.contacta.domain.VoicemailModel;
import mic.contacta.json.PhoneJson;
import mic.contacta.json.SipAccountJson;
import mic.organic.gateway.AbstractJsonConverter;

/**
 *
 * @author mic
 * @created May 23, 2010
 */
@Service
public class SipAccountConverter extends AbstractJsonConverter<SipAccountModel, SipAccountJson>
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired PbxContextDao pbxContextDao;
  @Autowired PbxProfileDao pbxProfileDao;


  /*
   *
   */
  @Override
  public SipAccountModel jsonToModel(SipAccountJson src, SipAccountModel dst)
  {
    if (dst == null)
    {
      dst = new SipAccountModel();
    }
    super.jsonToModel(src, dst);
    dst.setCode(src.getLogin()); // alias: login

    String email = src.getEmail();
    email = StringUtils.isNotBlank(email) ? email : src.getLogin()+"@interni.local";
    dst.setLabel(src.getFullName());
    dst.setEmail(email);
    dst.setPassword(src.getPassword());
    dst.setCallgroup(src.getCallgroup());
    dst.setPickupgroup(src.getPickupgroup());
    dst.setVmSendEmail(src.isVmSendEmail());
    dst.setRingTimeout(src.getRingTimeout());

    PbxContextModel pbxContext = pbxContextDao.findByCode(src.getContext());
    if (pbxContext != null)
    {
      dst.setContext(pbxContext);
      dst.getSipUser().setContext(pbxContext.getCode());
    }
    PbxProfileModel pbxProfile = pbxProfileDao.findByCode(src.getProfileName());
    if(src.getProfileName().equals("none"))
    {
      dst.setProfile(null);
    }
    else
    {
      dst.setProfile(pbxProfile);
      dst.setProfileOptions(src.getProfileOptions());
    }

    dst.setVmEnabled(src.getVmEnabled());
    if (src.getVmEnabled())
    {
      String vmEmail = dst.getVmSendEmail() ? dst.getPerson().getEmail() : "";
      if (dst.getVmBox() != null)
      {
        dst.getVmBox().setPin(src.getVmPin());
        dst.getVmBox().setEmail(vmEmail);
      }
      else
      {
        VoicemailModel vmBox = SipAccountModel.buildVmBox(dst.getLogin(), src.getVmPin(), dst.getLabel(), vmEmail);
        dst.setVmBox(vmBox);
      }
    }
    else
    {
      dst.setVmBox(null);
    }
    String mailbox = dst.getVmEnabled() ? dst.getLogin()+"@"+dst.getVoicemailContext() : null;
    dst.getSipUser().setMailbox(mailbox);

    return dst;
  }


  /*
   *
   */
  @Override
  public SipAccountJson modelToJson(SipAccountModel src, SipAccountJson dst)
  {
    if (dst == null)
    {
      dst = new SipAccountJson();
    }
    super.modelToJson(src, dst);
    //dst.setCode(src.getCode());

    String context = src.getContext() != null ? src.getContext().getCode() : null;

    dst.setEmail(src.getEmail());
    dst.setFirstName(src.getLabel());
    dst.setLastName(src.getLabel());
    dst.setFullName(src.getLabel());

    dst.setLogin(src.getLogin());
    dst.setPassword(src.getPassword());
    dst.setProfileName(src.getProfile() != null ? src.getProfile().getCode() : null);
    dst.setProfileOptions(src.getProfileOptions());
    dst.setContext(context);
    dst.setCallgroup(src.getCallgroup());
    dst.setPickupgroup(src.getPickupgroup());
    dst.setCallwaiting(true);
    dst.setRingTimeout(src.getRingTimeout());

    dst.setVmSendEmail(src.getVmSendEmail());
    dst.setVmEnabled(src.getVmEnabled());
    //if(from.getVmEnabled())
    {
      //to.setVmPin(from.getVmBox().getPin()); // URGENT c'era questo
    }
    //else
    {
      dst.setVmPin("");
    }

    if (src.getCoc() != null)
    {
      dst.setCocLogin(src.getCoc().getLogin());
      dst.setCocPin(src.getCoc().getPin());
    }

    if(src.getPhone() != null)
    {
      dst.setPhone(src.getPhone().getMacAddress());
    }

    return dst;
  }


  /*
   * questa piccola porcata di metodo puo' essere invocato con vettori di 2
   * dimensioni: 17 o 22.  il primo e' un subarray di un'altra custom query
   * URGENT ci sono da rifare le 2 query delle table perche' sono incomprensibili e immodificabili!!!!!
   */
  public SipAccountJson modelToJsonBrief(Object[] src)
  {
    SipAccountJson to = new SipAccountJson();
    to.setId((Integer) src[0]);

    to.setLogin((String) src[1]);
    to.setPassword((String) src[2]);
    to.setProfileName((String) src[3]);
    to.setProfileOptions((String) src[4]);
    to.setContext((String) src[5]);
    to.setCallgroup((String) src[6]);
    to.setPickupgroup((String) src[7]);
    to.setCallwaiting(true);

    if (src[8] != null)
    {
      to.setVmEnabled((Boolean) src[8]);
    }
    //if(json.getVmEnabled())
    {
      to.setVmPin((String) src[9]);
    }
    /*else
    {
      json.setVmPin("");
    }*/

    to.setEmail((String) src[10]);
    to.setFirstName((String) src[11]);
    to.setLastName((String) src[12]);
    to.setFullName((String) src[13]);

    to.setCocLogin((String) src[14]);
    to.setCocPin((String) src[15]);

    to.setPhone((String) src[16]);

    if (src.length > 17)
    {
      to.setVmSendEmail((Boolean) src[21]);
      to.setRingTimeout((Integer) src[22]);
    }

    if (src.length > 23) // FIXME uhm, this can be very slow
    {
      //SipUserModel sip = model.getSipUser();
      Object o = src[23];
      boolean r = false;
      if (o instanceof String)
      {
        r = !StringUtils.equals(o.toString(), "0");
      }
      else if (o instanceof Short)
      {
        r = (Short)o != 0;
      }
      if (r/*sip.getPort()*/)
      {
        to.setState("on");
      }
      else
      {
        to.setState("off");
      }
    }
    return to;
  }


  /*
   *
   */
  public void addAccountToPhoneJson(Object[] model, List<PhoneJson> jsonList)
  {
    for(PhoneJson phoneJson : jsonList)
    {
      if(phoneJson.getId() == ((Integer) model[17]))
      {
        Object[] accountObj = ArrayUtils.subarray(model, 0, 17);
        SipAccountJson accountJson = modelToJsonBrief(accountObj);
        // URGENT phoneJson.getAccounts().add(accountJson);
        return;
      }
    }
  }


}
