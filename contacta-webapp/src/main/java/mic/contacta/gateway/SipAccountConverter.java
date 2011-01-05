/* $Id: SipAccountConverter.java 660 2010-07-17 23:14:21Z michele.bianchi $
 *
 * Copyright(C) 2010 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.contacta.gateway;

import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mic.contacta.json.PhoneJson;
import mic.contacta.json.SipAccountJson;
import mic.contacta.model.PbxContextModel;
import mic.contacta.model.PbxProfileModel;
import mic.contacta.model.SipAccountModel;
import mic.contacta.server.dao.PbxContextDao;
import mic.contacta.server.dao.PbxProfileDao;
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
  public SipAccountModel jsonToModel(SipAccountJson from, SipAccountModel to)
  {
    if (to == null)
    {
      to = new SipAccountModel();
    }

    super.jsonToModel(from, to);
    to.setCode(from.getLogin()); // alias: login

    String email = from.getEmail();
    email = StringUtils.isNotBlank(email) ? email : from.getLogin()+"@interni.local";
    to.setLabel(from.getFullName());
    to.setEmail(email);
    to.setPassword(from.getPassword());
    to.setCallgroup(from.getCallgroup());
    to.setPickupgroup(from.getPickupgroup());
    to.setVmSendEmail(from.isVmSendEmail());
    to.setRingTimeout(from.getRingTimeout());

    PbxContextModel pbxContext = pbxContextDao.findByCode(from.getContext());
    if (pbxContext != null)
    {
      to.setContext(pbxContext);
      to.getSipUser().setContext(pbxContext.getCode());
    }
    PbxProfileModel pbxProfile = pbxProfileDao.findByCode(from.getProfileName());
    if(from.getProfileName().equals("none"))
    {
      to.setProfile(null);
    }
    else
    {
      to.setProfile(pbxProfile);
      to.setProfileOptions(from.getProfileOptions());
    }

    to.setVmEnabled(from.getVmEnabled());
    if (to.getVmBox() != null)
    {
      to.getVmBox().setPin(from.getVmPin());
      to.getVmBox().setEmail(to.getVmSendEmail() ? to.getPerson().getEmail() : "");
    }
    String mailbox = to.getVmEnabled() ? to.getLogin()+"@"+to.getVoicemailContext() : null;
    to.getSipUser().setMailbox(mailbox);

    return to;
  }


  /*
   *
   */
  @Override
  public SipAccountJson modelToJson(SipAccountModel from, SipAccountJson to)
  {
    if (to == null)
    {
      to = new SipAccountJson();
    }

    super.modelToJson(from, to);

    String context = from.getContext() != null ? from.getContext().getCode() : null;

    // TODO: non e' da mettere nell'AbstractConverter e cambiare l'interfaccia?
    to.setId(from.getId());

    //to.setCode(from.getCode());
    to.setId(from.getId());

    to.setEmail(from.getEmail());
    to.setFirstName(from.getLabel());
    to.setLastName(from.getLabel());
    to.setFullName(from.getLabel());

    to.setLogin(from.getLogin());
    to.setPassword(from.getPassword());
    to.setProfileName(from.getProfile() != null ? from.getProfile().getCode() : null);
    to.setProfileOptions(from.getProfileOptions());
    to.setContext(context);
    to.setCallgroup(from.getCallgroup());
    to.setPickupgroup(from.getPickupgroup());
    to.setCallwaiting(true);
    to.setRingTimeout(from.getRingTimeout());

    to.setVmSendEmail(from.getVmSendEmail());
    to.setVmEnabled(from.getVmEnabled());
    //if(from.getVmEnabled())
    {
      //to.setVmPin(from.getVmBox().getPin()); // URGENT c'era questo
    }
    //else
    {
      to.setVmPin("");
    }

    if (from.getCoc() != null)
    {
      to.setCocLogin(from.getCoc().getLogin());
      to.setCocPin(from.getCoc().getPin());
    }

    if(from.getPhone() != null)
    {
      to.setPhone(from.getPhone().getMacAddress());
    }

    return to;
  }


  /*
   * questa piccola porcata di metodo puo' essere invocato con vettori di 2
   * dimensioni: 17 o 22.  il primo e' un subarray di un'altra custom query
   * URGENT ci sono da rifare le 2 query delle table perche' sono incomprensibili e immodificabili!!!!!
   */
  public SipAccountJson modelToJsonBrief(Object[] from)
  {
    SipAccountJson to = new SipAccountJson();
    to.setId((Integer) from[0]);

    to.setLogin((String) from[1]);
    to.setPassword((String) from[2]);
    to.setProfileName((String) from[3]);
    to.setProfileOptions((String) from[4]);
    to.setContext((String) from[5]);
    to.setCallgroup((String) from[6]);
    to.setPickupgroup((String) from[7]);
    to.setCallwaiting(true);

    if (from[8] != null)
    {
      to.setVmEnabled((Boolean) from[8]);
    }
    //if(json.getVmEnabled())
    {
      to.setVmPin((String) from[9]);
    }
    /*else
    {
      json.setVmPin("");
    }*/

    to.setEmail((String) from[10]);
    to.setFirstName((String) from[11]);
    to.setLastName((String) from[12]);
    to.setFullName((String) from[13]);

    to.setCocLogin((String) from[14]);
    to.setCocPin((String) from[15]);

    to.setPhone((String) from[16]);

    if (from.length > 17)
    {
      to.setVmSendEmail((Boolean) from[21]);
      to.setRingTimeout((Integer) from[22]);
    }

    if (from.length > 23) // FIXME uhm, this can be very slow
    {
      //SipUserModel sip = model.getSipUser();
      Object o = from[23];
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
