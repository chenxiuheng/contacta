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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.asteriskjava.manager.response.CommandResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import mic.contacta.dao.AppointmentDao;
import mic.contacta.dao.CoverageDao;
import mic.contacta.dao.PbxContextDao;
import mic.contacta.dao.PbxProfileDao;
import mic.contacta.domain.*;
import mic.contacta.domain.CoverageModel.CoverageType;
import mic.contacta.json.CallsJson;
import mic.contacta.json.CoverageJson;
import mic.contacta.json.PhoneJson;
import mic.contacta.json.SipAccountJson;
import mic.contacta.server.*;
import mic.organic.aaa.ldap.Person;
import mic.organic.aaa.ldap.PersonDao;
import mic.organic.aaa.model.PersonModel;
import mic.organic.aaa.spi.AddressbookService;
import mic.organic.gateway.GatewayException;


/**
 *
 * @author mic
 * @created Jun 10, 2009
 */
@Service(PbxGateway.SERVICE_NAME)
public class PbxGatewayImpl implements PbxGateway
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @PersistenceContext EntityManager entityManager;

  @Autowired private PersonDao personDao;
  @Autowired private AppointmentDao appointmentDao;
  @Autowired private CalendarService calendarService;

  @Autowired private AddressbookService addressbookService;
  @Autowired private InventoryService inventoryService;
  @Autowired private PbxService pbxService;
  @Autowired private CocService cocService;
  @Autowired private CoverageDao coverageDao;

  @Autowired private ContactaService contactaService;
  @Autowired private ContactaConfiguration contactaConfiguration;

  @Autowired private PhoneConverter phoneConverter;
  @Autowired private SipAccountConverter sipAccountConverter;
  @Autowired private CoverageConverter coverageConverter;

  @Autowired private CallsConverter callsConverter;


  /*
   *
   */
  public PbxGatewayImpl()
  {
    super();
  }


  /**
   * @param attendee
   */
  @SuppressWarnings("unused")
  private void checkMailAddresses(String attendee)
  {
    List<Person> list = personDao.findByField("mail", attendee);
    if (list.size() == 0)
    {
      log().info("attendee.mail={} is not registered", attendee);
    }
    else
    {
      for (Person person : list)
      {
        log().info("person.mail={}", person.getMail());
      }
    }
  }


  /**
   *
   */
  @Transactional(propagation=Propagation.REQUIRED)
  @Override
  public AppointmentModel book(long startTs, long endTs, String attendeeString, String subject, String description)
  {
    String[] attendees = attendeeString.split(",");
    Date startTime = new Date(startTs);
    Date endTime = new Date(endTs);

    List<String> mailList = new ArrayList<String>();
    for (String attendee : attendees)
    {
      //checkMailAddresses(attendee);
      mailList.add(attendee);
    }
    AppointmentModel appointment = calendarService.book(startTime, endTime, attendeeString, subject, description);
    return appointment;
  }


  /**
   *
   */
  @Transactional(propagation=Propagation.REQUIRED)
  @Override
  public AppointmentModel cancel(int appointmentId)
  {
    AppointmentModel appointment = appointmentDao.find(appointmentId);
    calendarService.cancel(appointment);
    return appointment;
  }


  /**
   *
   */
  @Transactional(propagation=Propagation.REQUIRED)
  @Override
  public AppointmentModel remail(int appointmentId)
  {
    AppointmentModel appointment = appointmentDao.find(appointmentId);
    calendarService.remail(appointment);
    return appointment;
  }


  /**
   * this is a simple replica command, it just invokes the remote url.
   * contacta provides the /replica servlet path to do its job
   * TODO improve me
   */
  private void replica()
  {
    String url = contactaConfiguration.getContactaReplicaUrl();
    if (StringUtils.isNotBlank(url))
    {
      HttpClient httpClient = new HttpClient();
      GetMethod method = new GetMethod(url);
      try
      {
        int statusCode = httpClient.executeMethod(method);

        if (statusCode != HttpStatus.SC_OK)
        {
          log().warn("Method failed: {}", method.getStatusLine());
        }
      }
      catch (IOException e)
      {
        log().warn("Fatal transport error: {}", e.getMessage());
      }
      finally
      {
        // Release the connection.
        method.releaseConnection();
      }
    }
    else
    {
      log().debug("replica is not set");
    }
  }


  /**
   *
   */
  @Transactional(readOnly=true)
  @Override
  public void extenProfileUpdate()
  {
    try
    {
      pbxService.writeExtenProfile(contactaConfiguration.getUseDrop612());
      pbxService.writeExtenHint();
      contactaService.asteriskCommand(ContactaService.RELOAD); // was EXTENSIONS_RELOAD
    }
    catch (ContactaException e)
    {
    }
  }


  /*
   * @see mic.contacta.gateway.PbxGateway#phoneFind(int)
   */
  @Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
  @Override
  public PhoneJson phoneFind(int id)
  {
    PhoneModel model = inventoryService.phoneFind(id);
    PhoneJson json = phoneConverter.modelToJson(model);
    return json;
  }


  /*
   * @see mic.contacta.gateway.PbxGateway#phoneList()
   */
  @Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
  @Override
  public List<PhoneJson> phoneList()
  {
    List<PhoneModel> modelList = inventoryService.phoneList();
    Map<Integer,PhoneJson> jsonMap = new HashMap<Integer,PhoneJson>();

    for(PhoneModel model : modelList)
    {
      PhoneJson json = phoneConverter.modelToJson(model, null);
      json.setIpAddress("0.0.0.0");
      jsonMap.put(json.getId(), json);
    }

    List<Object[]> objectList = entityManager.createNativeQuery("select a.id,a.phone_id,a.callerId,u.ipaddr from acacc a,stsip u where a.sipuser_id=u.id").getResultList();
    for (Object[] v : objectList)
    {
      Integer phoneId = (Integer)(v[1]);
      PhoneJson phone = jsonMap.get(phoneId);
      if (phone != null)
      {
        String callerId = (String)(v[2]);
        String ipAddress = (String)(v[3]);
        phone.getAccounts().add(callerId);
        phone.setIpAddress(ipAddress);
      }
    }

    List<PhoneJson> jsonList = new ArrayList<PhoneJson>(jsonMap.values());
    for(PhoneJson phone : jsonList)
    {
      if (phone.getAccounts().size() == 0)
      {
        phone.getAccounts().add("N/A");
      }
    }

    return jsonList;
  }


  /*
   * @see mic.contacta.ptool.webapp.PtoolWebappGateway#phoneCreateUpdate(mic.contacta.ptool.webapp.json.PhoneJson)
   */
  @Transactional(propagation=Propagation.REQUIRES_NEW)
  @Override
  public PhoneJson phonePersist(PhoneJson json) throws GatewayException
  {
    PhoneModel model = null;
    if (json.getId() == 0)
    {
      model = phoneConverter.jsonToModel(json, null);
      ProductModel product = inventoryService.productByCode(json.getProduct());
      inventoryService.phonePersist(model, product);
    }
    else
    {
      model = inventoryService.phoneFind(json.getId());
      if (model == null)
      {
        throw new GatewayException("cannot find phone.id="+json.getId());
      }
      phoneConverter.jsonToModel(json, model);

      ProductModel product = inventoryService.productByCode(json.getProduct());
      if (product != null)
      {
        model.setProduct(product);
      }
      inventoryService.phonePersist(model);
    }
    json = phoneConverter.modelToJson(model, null);
    return json;
  }


  /**
   *
   */
  @Transactional(propagation=Propagation.REQUIRES_NEW)
  @Override
  public Boolean[] phoneRemove(int[] ids)
  {
    Boolean[] rs = new Boolean[ids.length];
    for (int i = 0; i < ids.length; i++)
    {
      try
      {
        PhoneModel phone = inventoryService.phoneFind(ids[i]);
        for (SipAccountModel account : phone.getSipAccountList())
        {
          account.setPhone(null);
          pbxService.sipPersist(account);
        }
        rs[i] = inventoryService.phoneDelete(phone);
        if (rs[i] == false)
        {
          log().info("the object does not exist");  // FIXME throw an exception?
        }
      }
      catch(Exception e)
      {
        log().warn(e.getMessage());
      }
    }
    extenProfileUpdate();
    replica();
    return rs;
  }


  /**
   *
   */
  @Transactional
  @Override
  public SipAccountModel phoneAddAccount(int phoneId, String login)
  {
    PhoneModel phone = inventoryService.phoneFind(phoneId);
    SipAccountModel sip = pbxService.sipByLogin(login);
    if (phone != null && sip != null)
    {
      pbxService.assignPhoneToAccount(phone, sip);
    }
    else
    {
      if (phone == null)
      {
        log().info("cannot find phone.id={} ", phoneId);
      }
      else
      {
        log().info("cannot find sip.login={}", login);
      }
    }
    return sip;
  }


  /**
   *
   */
  @Transactional(readOnly=true)
  @Override
  public List<SipAccountJson> sipList()
  {
    List<SipAccountJson> jsonList = new ArrayList<SipAccountJson>();
    /*
    List<Object[]> oList = pbxService.findAccountBriefList();
    for(Object[] model : oList)
    {
      SipAccountJson json = sipAccountConverter.modelToJsonBrief(model);
      jsonList.add(json);
    }
    */
    List<SipAccountModel> oList = pbxService.sipList();
    for(SipAccountModel model : oList)
    {
      SipAccountJson json = sipAccountConverter.modelToJson(model, null);
      jsonList.add(json);
    }
    return jsonList;
  }


  /*
   *
   */
  private void addCoc(SipAccountJson json, SipAccountModel model)
  {
    if (StringUtils.isNotBlank(json.getCocLogin()) /*&& StringUtils.isNotBlank(json.getCocPassword())*/)
    {
      if (model.getCoc() == null)
      {
        CocModel coc = new CocModel(json.getCocLogin(), json.getCocPin());
        model.setCoc(coc);
      }
      else
      {
        model.getCoc().setLogin(json.getCocLogin());
        model.getCoc().setPin(json.getCocPin());
      }
    }
  }


  @Autowired PbxContextDao pbxContextDao;
  @Autowired PbxProfileDao pbxProfileDao;


  /**
   *
   */
  @Transactional(propagation=Propagation.REQUIRES_NEW)
  @Override
  public SipAccountJson sipPersist(SipAccountJson json) throws GatewayException
  {
    SipAccountModel model = null;
    if (json.getId() != 0)
    {
      model = pbxService.sipFind(json.getId());
      if (model == null)
      {
        throw new GatewayException("cannot update model, doesnt exist anymore the id="+json.getId());
      }
    }
    model = sipAccountConverter.jsonToModel(json, model);

    PbxContextModel pbxContext = pbxContextDao.findByCode(json.getContext());
    if (pbxContext != null)
    {
      model.setContext(pbxContext);
      model.getSipUser().setContext(pbxContext.getCode());
    }
    PbxProfileModel pbxProfile = pbxProfileDao.findByCode(json.getProfileName());
    if(json.getProfileName().equals("none"))
    {
      model.setProfile(null);
    }
    else
    {
      model.setProfile(pbxProfile);
      model.setProfileOptions(json.getProfileOptions());
    }

    if (json.getId() == 0)
    {
      pbxService.sipPersist(model);
      addCoc(json, model);
    }
    else
    {
      if(StringUtils.isBlank(json.getCocLogin()))
      {
        CocModel coc = model.getCoc();
        if(coc != null)
        {
          model.setCoc(null);
          cocService.deleteCoc(coc.getId());
        }
      }
      else
      {
        addCoc(json, model);
      }
      pbxService.sipPersist(model);
    }

    extenProfileUpdate();
    replica();

    return sipAccountConverter.modelToJson(model);
  }



  /*
   *
   */
  @Transactional(propagation=Propagation.REQUIRES_NEW)
  @Override
  public Boolean[] sipRemove(int[] ids)
  {
    Boolean[] rs = new Boolean[ids.length];
    for (int i = 0; i < ids.length; i++)
    {
      try
      {
        SipAccountModel account = pbxService.sipFind(ids[i]);
        PhoneModel phone = account.getPhone();
        if (phone != null)
        {
          phone.getSipAccountList().clear();
          inventoryService.phonePersist(phone);
        }
        rs[i] = pbxService.sipDelete(account);
        if (rs[i] == false)
        {
          log().info("the object does not exist");  // FIXME throw an exception?
        }
        else
        {
          //log().info("deleting person: {}", account.getPerson());
          PersonModel person = account.getPerson();
          addressbookService.personDelete(person);
        }
      }
      catch(Exception e)
      {
        log().warn(e.getMessage(), e);
      }
    }
    extenProfileUpdate();
    replica();
    return rs;
  }


  /*
   * @see mic.contacta.ptool.webapp.PtoolWebappGateway#checkAsterisk()
   */
  @Override
  public String asteriskCheck()
  {
    Object result = contactaService.asteriskCommand("core show uptime");
    if (result != null)
    {
      CommandResponse response = (CommandResponse) (result);
      StringBuilder sb = new StringBuilder();
      for (String line : response.getResult())
      {
        sb.append(line);
      }
      return sb.toString();
    }
    else
    {
      log().warn("asterisk is not reachable");
      return "Asterisk non raggiungibile";
    }
  }


  /*
   * @see mic.contacta.ptool.webapp.PtoolWebappGateway#restartAsterisk()
   */
  @Override
  public boolean asteriskRestart()
  {
    Object result = contactaService.asteriskCommand(ContactaService.RELOAD);  // was SIP_RELOAD
    return result != null;
  }


  /*
   * @see mic.contacta.ptool.webapp.PtoolWebappGateway#restartAsterisk()
   */
  @Override
  public boolean asteriskSipNotifyCheckCfg(String exten)
  {
    Object result = contactaService.asteriskCommand("sip notify thomson-check-cfg "+exten);
    return result != null;
  }


  /*
   * @see mic.contacta.ptool.webapp.PtoolWebappGateway#findCoverageList()
   */
  @Override
  public List<CoverageJson> coverageList()
  {
    List<CoverageModel> modelList = coverageDao.findAll();
    List<CoverageJson> jsonList = new ArrayList<CoverageJson>();
    for (CoverageModel model : modelList)
    {
      jsonList.add(coverageConverter.modelToJson(model));
    }
    return jsonList;
  }


  /**
   *
   */
  @Transactional(propagation=Propagation.REQUIRES_NEW)
  @Override
  public boolean coverageAdd(String fromLogin, String toLogin, CoverageType type, String options, int timeout)
  {
    SipAccountModel fromSip = pbxService.sipByLogin(fromLogin);
    SipAccountModel toSip = pbxService.sipByLogin(toLogin);
    if (fromSip != null && toSip != null)
    {
      CoverageModel coverageRev = pbxService.coverageAdd(toSip, fromSip, CoverageType.Presence, null, 0);
      CoverageModel coverage = pbxService.coverageAdd(fromSip, toSip, type, options, timeout);
      return coverage != null;
    }
    else
    {
      log().info("cannot find sipAccount.login={}", fromLogin);
      log().info("cannot find sipAccount.login={}", toLogin);
      return false;
    }
  }


  /**
   *
   */
  @Transactional(propagation=Propagation.REQUIRES_NEW)
  @Override
  public boolean coverageRemove(String fromLogin)
  {
    SipAccountModel fromSip = pbxService.sipByLogin(fromLogin);
    if (fromSip != null)
    {
      pbxService.coverageRemove(fromSip);
      return true;
    }
    else
    {
      log().info("cannot find sipAccount.login={}", fromLogin);
      return false;
    }
  }


  /*
   * @see mic.contacta.gateway.PbxGateway#missedSkypeCalls(java.lang.String)
   */
  @Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=true)
  @Override
  public List<CallsJson> missedSkypeCalls(String exten)
  {
    List<CallsJson> jsonList = new ArrayList<CallsJson>();
    PersonModel person = addressbookService.personByExtension(exten);
    if (person != null && StringUtils.isNotBlank(person.getUri()))
    {
      List<CdrModel> modelList = pbxService.missedSkypeCalls(person.getUri());
      for (CdrModel model : modelList)
      {
        CallsJson json = callsConverter.modelToJson(model);
        PersonModel caller = addressbookService.personByUri(model.getSrc());
        if (caller != null)
        {
          json.setExten(caller.getExtension());
        }
        jsonList.add(json);
      }
    }
    else
    {
      log().warn("cannot find a person for exten:{}");  // FIXME connect sipaccount to person
    }
    return jsonList;
  }

}
