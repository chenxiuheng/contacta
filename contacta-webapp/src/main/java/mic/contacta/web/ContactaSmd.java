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
package mic.contacta.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.json.annotations.SMDMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.Action;

import mic.contacta.domain.AppointmentModel;
import mic.contacta.domain.SipAccountModel;
import mic.contacta.domain.CoverageModel.CoverageType;
import mic.contacta.gateway.PbxGateway;
import mic.contacta.gateway.SipAccountConverter;
import mic.contacta.json.PhoneJson;
import mic.contacta.json.SipAccountJson;
import mic.contacta.json.WizardJson;
import mic.contacta.server.ContactaException;
import mic.contacta.server.PhonebarService;
import mic.organic.gateway.DatastoreJson;
import mic.organic.gateway.GatewayException;
import mic.organic.gateway.Json;


/**
 *
 * @author mic
 * @created Apr 16, 2008
 */
@Service("contactaAction")
@Scope("request")
public class ContactaSmd extends AbstractContactaAction
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private PhonebarService phonebarService;
  @Autowired private PbxGateway pbxGateway;
  @Autowired private SipAccountConverter sipAccountConverter;


  /**
   * just the default method, doing nothing
   */
  public String bind()
  {
    log().debug("");
    return SUCCESS;
  }


  /**
   * just the default method, doing nothing
   */
  @Override
  public String execute()
  {
    log().warn("dont call execute, i'm a smd");
    return Action.SUCCESS;
  }


  /**
   *
   */
  @SMDMethod
  public DatastoreJson<? extends Json> findDatastore(String key)
  {
    log().debug("key={}", key);
    DatastoreJson<? extends Json> store = null;
    /*if (StringUtils.equalsIgnoreCase(key, "person"))
    {
      List<PersonJson> jsonList = pbxGateway.personLoad();
      //List<ContactModel> modelList = contactaConverter.sampleContactList();
      //List<ContactJson> jsonList = contactaConverter.contactModelToJson(modelList);
      store = new mic.organic.dojo.DefaultDatastoreJson<PersonJson>(DEFAULT_DATASTORE_ID, /*DEFAULT_DATASTORE_TITLE* /"lastName", jsonList);
    }
    else*/ if (StringUtils.equalsIgnoreCase(key, "account"))
    {
      List<SipAccountJson> jsonList = pbxGateway.sipList();
      store = new mic.organic.gateway.DefaultDatastoreJson<SipAccountJson>(DatastoreJson.IDENTIFIER, "login", jsonList);
    }
    else if (StringUtils.equalsIgnoreCase(key, "phone"))
    {
      List<PhoneJson> jsonList = pbxGateway.phoneList();
      store = new mic.organic.gateway.DefaultDatastoreJson<PhoneJson>(DatastoreJson.IDENTIFIER, "macAddress", jsonList);
    }
    //log().info("store={}", store);
    return store;
  }


  /**
   * ExtenJson person, SipAccountJson account, PhoneJson phone
   * @throws ContactaException
   * @throws GatewayException
   */
  @SMDMethod
  public String wizardPhonePerson(WizardJson json) throws GatewayException
  {
    SipAccountJson account = pbxGateway.sipPersist(json.getAccount());
    PhoneJson phone = pbxGateway.phonePersist(json.getPhone());

    pbxGateway.phoneAddAccount(phone.getId(), account.getLogin());

    return SUCCESS;
  }


  ////////////////////////////////////////////
  // PHONE                                  //
  ////////////////////////////////////////////
  /**
   *
   */
  @SMDMethod
  public List<PhoneJson> phoneLoad()
  {
    return pbxGateway.phoneList();
  }


  /**
   * @throws ContactaException
   *
   */
  @SMDMethod
  public PhoneJson phoneCreateUpdate(PhoneJson json) throws GatewayException
  {
    return pbxGateway.phonePersist(json);
  }


  /**
   * @throws ContactaException
   *
   */
  @SMDMethod
  public void phoneDelete(int[] ids) throws ContactaException
  {
    try
    {
      pbxGateway.phoneRemove(ids);
    }
    catch(Exception e)
    {
      log().warn("Non e' stato possibile cancellare l'oggetto: {}", e.getMessage());
      throw new ContactaException("Non e' stato possibile cancellare l'oggetto: "+e.getMessage());
    }
  }


  /**
   * @throws ContactaException
   *
   */
  @SMDMethod
  public SipAccountJson phoneAddAccount(int phoneId, String login) throws ContactaException
  {
    SipAccountModel sip = pbxGateway.phoneAddAccount(phoneId, login);
    if (sip != null)
    {
      return sipAccountConverter.modelToJson(sip, null);
    }
    else
    {
      return null;
    }
  }

  ////////////////////////////////////////////
  // ACCOUNT                                //
  ////////////////////////////////////////////
  /**
   *
   */
  @SMDMethod
  public List<SipAccountJson> accountLoad()
  {
    return pbxGateway.sipList();
  }


  /**
   * @throws ContactaException
   *
   */
  @SMDMethod
  public SipAccountJson accountCreateUpdate(SipAccountJson json) throws GatewayException
  {
    return pbxGateway.sipPersist(json);
  }


  /**
   * @throws ContactaException
   *
   */
  @SMDMethod
  public void accountDelete(int[] ids) throws ContactaException
  {
    try
    {
      pbxGateway.sipRemove(ids);
    }
    catch(Exception e)
    {
      log().warn("Non e' stato possibile cancellare l'oggetto: {}", e.getMessage());
      throw new ContactaException("Non e' stato possibile cancellare l'oggetto: "+e.getMessage());
    }
  }


  ////////////////////////////////////////////
  // STATUS                                 //
  ////////////////////////////////////////////
  /**
   *
   */
  @SMDMethod
  public String checkAsterisk()
  {
    return pbxGateway.asteriskCheck();
  }


  /**
   *
   */
  @SMDMethod
  public boolean restartAsterisk()
  {
    return pbxGateway.asteriskRestart();
  }


  /**
   *
   */
  @SMDMethod
  public boolean updateExtensionProfile()
  {
    pbxGateway.extenProfileUpdate();
    return true;
  }


  /**
   *
   */
  @SMDMethod
  public boolean addCoverage(String fromLogin, String toLogin, CoverageType type, String options, int timeout)
  {
    boolean r = pbxGateway.coverageAdd(fromLogin, toLogin, type, options, timeout);
    if (r)
    {
      pbxGateway.extenProfileUpdate();
    }
    return r;
  }


  /**
   *
   */
  @SMDMethod
  public boolean removeCoverage(String fromLogin)
  {
    boolean r = pbxGateway.coverageRemove(fromLogin);
    if (r)
    {
      pbxGateway.extenProfileUpdate();
    }
    return r;
  }


  /**
   *  Action: Originate
   *  Channel: SIP/8500
   *  Context: nazionali
   *  Exten: 8000
   *  Priority: 1
   *  Callerid: "Michele Bianchi <8500>"
   */
  @SMDMethod
  public String dial(String exten)
  {
    return phonebarService.dial(contactaSession.getAccount(), exten);
  }


  /**
   * @param toContext
   *
   */
  @SMDMethod
  public String transfer(String exten, String toContext)
  {
    String login = contactaSession.getAccount().getLogin();
    return phonebarService.transfer(login, exten, toContext);
  }


  /**
   *
   */
  @SMDMethod
  public String hold()
  {
    String login = contactaSession.getAccount().getLogin();
    return phonebarService.hold(login);
  }


  /**
   *
   */
  @SMDMethod
  public String unhold()
  {
    String login = contactaSession.getAccount().getLogin();
    return phonebarService.unhold(login);
  }


  /**
   *
   */
  @SMDMethod
  public String answer()
  {
    String login = contactaSession.getAccount().getLogin();
    return phonebarService.answer(login);
  }


  /**
   *
   */
  @SMDMethod
  public String book(long startTs, long endTs, String attendeeString, String subject, String description)
  {
    AppointmentModel appointment = pbxGateway.book(startTs, endTs, attendeeString, subject, description);
    return appointment != null ? "OK" : "KO";
  }


  /**
   *
   */
  @SMDMethod
  public String cancel(int appointmentId)
  {
    AppointmentModel appointment = pbxGateway.cancel(appointmentId);
    return appointment != null ? "OK" : "KO";
  }


  /**
   *
   */
  @SMDMethod
  public String remail(int appointmentId)
  {
    AppointmentModel appointment = pbxGateway.remail(appointmentId);
    return appointment != null ? "OK" : "KO";
  }

  /**
   * just the default method, doing nothing
   */
  @SMDMethod
  public void notifyCheckCfg(String exten)
  {
    pbxGateway.asteriskSipNotifyCheckCfg(exten);
  }

}
