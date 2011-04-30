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
package mic.contacta.server.spi;

import java.util.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;
import org.asteriskjava.manager.action.OriginateAction;
import org.asteriskjava.manager.response.CommandResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mic.contacta.asterisk.spi.AsteriskService;
import mic.contacta.model.AppointmentModel;
import mic.contacta.model.ConferenceModel;
import mic.contacta.server.api.ConferenceLine;
import mic.contacta.server.api.ContactaException;
import mic.contacta.server.dao.AppointmentDao;
import mic.organic.mail.*;


/**
 *
 *
 * @author mic
 * @created Jun 13, 2009
 */
@Service(CalendarService.SERVICE_NAME)
public class CalendarServiceImpl implements CalendarService
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @PersistenceContext private EntityManager entityManager;

  @Autowired private ContactaSession contactaSession;
  @Autowired private AppointmentDao appointmentDao;
  @Autowired(required=false) private AsteriskService asteriskService;

  @Autowired private MailQueueWorker mailQueueWorker;
  @Autowired private MailService mailService;
  //@Autowired private MailDao mailDao;
  @Autowired private MailProducer freemarkerEmailProducer;

  private Locale it_IT = new Locale("it", "IT");
  //private Locale en_GB = new Locale("en", "GB");

  private String conferenceExten = "9998,9999";
  private List<ConferenceLine> conferenceList;

  private String mailFrom = "no_reply@nodomain.local";
  private String mailSubject = "appointment email";


  /**
   *
   */
  public CalendarServiceImpl()
  {
    super();
  }


  /**
   * @param conferenceExten the conferenceExten to set
   */
  public void setConferenceExten(String conferenceExten)
  {
    this.conferenceExten = conferenceExten;
  }


  /**
   * @return the conferenceList
   */
  @Override
  public List<ConferenceLine> getConferenceList()
  {
    return conferenceList;
  }


  /**
   * @param conferenceList the conferenceList to set
   */
  public void setConferenceList(List<ConferenceLine> conferenceList)
  {
    this.conferenceList = conferenceList;
  }


  /**
   * @param mailFrom the mailFrom to set
   */
  public void setMailFrom(String mailFrom)
  {
    this.mailFrom = mailFrom;
  }


  /**
   * @param mailSubject the mailSubject to set
   */
  public void setMailSubject(String mailSubject)
  {
    this.mailSubject = mailSubject;
  }


  /*
   *
   */
  @PostConstruct
  public void configure() throws Exception
  {
    if (conferenceList == null)
    {
      conferenceList = new ArrayList<ConferenceLine>();
      if (StringUtils.isNotBlank(conferenceExten))
      {
        for (String exten : conferenceExten.split(","))
        {
          conferenceList.add(new ConferenceLine(exten));
        }
      }
      else
      {
        String exten = "9999";
        conferenceList.add(new ConferenceLine(exten));
      }
    }

    log().info("ConferenceLine lines availables: {}", conferenceList.size());
    for (ConferenceLine conf : conferenceList)
    {
      log().info(" - line exten={}", conf.getExten());
    }
  }


  /**
   * @return
   */
  private String createPin()
  {
    String pin = String.valueOf((int)(Math.random()*9000)+1000);
    return pin;
  }


  /*
   * this test send an email!!!
   */
  private Mail createMail(AppointmentModel appointment)
  {
    String from = appointment.getMail();
    String attendees = appointment.getAttendees();
    String subject = appointment.getSubject();
    //attendees = "mic@openinnovation.it,roberto.grasso72@gmail.com";

    if (StringUtils.isNotBlank(attendees))
    {
      attendees = appointment.getMail()+","+attendees;
    }
    else
    {
      attendees = appointment.getMail();
    }

    Map<String,Object> params = new HashMap<String,Object>();
    params.put("a", appointment);

    Set<MailAddress> recipients = new HashSet<MailAddress>();
    for (String attendee : attendees.split(","))
    {
      recipients.add(new MailAddress(MailAddressType.To, attendee));
    }

    Mail mail = freemarkerEmailProducer.produce("sendpin", it_IT, MailFormat.plain, params);
    if (mail != null)
    {
      mail.setFrom(new MailAddress(MailAddressType.To, from));
      mail.setSubject(subject);
      //mail.setBody("This is a test email.");
      mail.setRecipients(recipients);

      mailService.enqueue(mail);
    }
    mailQueueWorker.run();
    return mail;
  }


  /*
   *
   */
  private String buildCodeDisabled(String line, Date startTime)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(startTime);
    int h = calendar.get(Calendar.HOUR_OF_DAY);
    int d = calendar.get(Calendar.DAY_OF_YEAR);
    String code = "_"+d+"_"+h+"#"+line;
    return code;
  }


  /*
   * @see mic.contacta.server.ptool.PtoolService#asteriskCommand(java.lang.String)
   */
  public Object asteriskCommand(String command)
  {
    Object result = null;
    try
    {
      result = asteriskService.sendCommand(command);

      // TODO piccola grande porcata temporanea.  per ora cast, poi faro' un oggetto result fatto bene
      CommandResponse response = (CommandResponse) (result);
      log().debug("response: {}", response);
      for (String line : response.getResult())
      {
        log().debug("{}", line);
      }
    }
    catch (ContactaException e)
    {
      log().warn("cannot send command to asterisk: {}", e.getMessage());
    }
    return result;
  }


  /*
   *
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public AppointmentModel book(Date startTime, Date endTime, /*List<Person> attendeeList*/ String attendees, String subject, String description)
  {
    String pin = createPin();
    ConferenceModel conference = new ConferenceModel(null, 6, pin, "9998");
    //conferenceDao.create(conference);

    String mail = contactaSession.getAccount().getEmail();
    mail = StringUtils.isNotBlank(mail) ? mail : mailFrom;
    subject = StringUtils.isNotBlank(subject) ? subject : mailSubject;
    endTime = new Date(endTime.getTime()-60*1000L);

    AppointmentModel appointment = new AppointmentModel(contactaSession.getAccount(), attendees);
    appointment.setSubject(subject);
    appointment.setDescription(description);
    appointment.setMail(mail);
    appointment.setStartTime(startTime);
    appointment.setEndTime(endTime);
    appointment.setConference(conference);

    List<AppointmentModel> overlapList = appointmentDao.findOverlapping(appointment);
    int n = overlapList.size();
    log().info("overlapList: {}", n);
    for (AppointmentModel app : overlapList)
    {
      log().info(" - appointmentModel={}", app.getMail());
    }

    if (n < conferenceList.size())
    {
      List<String> freeLines = new ArrayList<String>();
      for (ConferenceLine cl : conferenceList)
      {
        freeLines.add(cl.getExten());
      }
      for (AppointmentModel app : overlapList)
      {
        String line = app.getConference().getCode();
        if (line.indexOf('#') > -1)
        {
          line = line.substring(line.indexOf('#')+1);
        }
        freeLines.remove(line);
      }

      if (freeLines.size() > 0)
      {
        log().info("freeLines={}, {}", freeLines.size(), freeLines.get(0));
        String code = buildCodeDisabled(freeLines.get(0), startTime);
        conference.setCode(code);
        appointmentDao.create(appointment);
      }
      else
      {
        log().warn("smth goes wrong, no free lines!??!?");
      }
      try
      {
        createMail(appointment);
      }
      catch(Exception e)
      {
        log().warn("cannot create the mail: {}", e.getMessage());
      }
    }
    else
    {
      log().info("not enought conference available");
      appointment = null;
    }
    return appointment;
  }


  /*
   *
   */
  @Transactional(propagation=Propagation.MANDATORY,readOnly=true)
  @Override
  public List<AppointmentModel> listAppointments(String mail)
  {
    List<AppointmentModel> list = null;
    if (StringUtils.isNotBlank(mail))
    {
      log().info("mail={}", mail);
      list = appointmentDao.findByMail(mail);
    }
    return list;
  }


  /*
   * @see mic.contacta.gateway.CalendarService#weekCalendar(java.util.Date)
   */
  @Transactional(propagation=Propagation.MANDATORY,readOnly=true)
  @Override
  public List<AppointmentModel> weekCalendar(Date week)
  {
    Date start = week;
    Date end = new Date(week.getTime()+7*24*60*60*1000L);
    List<AppointmentModel> list = entityManager.createQuery("from AppointmentModel where startTime > ?1 and startTime < ?2")
                                               .setParameter(1, start, TemporalType.TIMESTAMP)
                                               .setParameter(2, end, TemporalType.TIMESTAMP)
                                               .getResultList();
    return list;
  }


  /*
   * @see mic.contacta.web.CalendarService#cancel(mic.contacta.model.AppointmentModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public void cancel(AppointmentModel appointment)
  {
    appointmentDao.delete(appointment);

    String code = appointment.getConference().getCode();
    asteriskCommand("MeetMe kick "+code+" all");
    modifyLine(false, appointment, code);
  }


  /*
   * @see mic.contacta.web.CalendarService#remail(mic.contacta.model.AppointmentModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public void remail(AppointmentModel appointment)
  {
    createMail(appointment);
  }


  /*
   * @see mic.contacta.web.CalendarService#activateConferences(mic.contacta.model.AppointmentModel)
   */
  @Transactional(propagation=Propagation.REQUIRED)
  @Override
  public void activateConferences(Map<String,String> properties)
  {
    //log().info("message={}", properties.get("message"));
    //log().info("jobDetailData={}", properties.get("jobDetailData"));
    //log().info("triggerData={}", properties.get("triggerData"));

    Date now = new Date(System.currentTimeMillis()+20*1000L);
    activate(now);
    deleteOverdue(now);
    warn5mins();
  }


  /**
   * @param app
   * @param code
   */
  @SuppressWarnings("deprecation")
  private void logAppDetail(AppointmentModel app, String code, String msg)
  {
    log().info("{} {}, {}, {}:{}-{}:{}", new Object[] { msg, code, app.getMail(), app.getStartTime().getHours(), app.getStartTime().getMinutes(), app.getEndTime().getHours(), app.getEndTime().getMinutes()  } );
  }


  /*
   * warn last 5 mins appointments
   */
  private void warn5mins()
  {
    Date now = new Date(System.currentTimeMillis()+5*60*1000L);
    List<AppointmentModel> list = entityManager.createQuery("from AppointmentModel where ?1 > endTime")
                                               .setParameter(1, now, TemporalType.TIMESTAMP)
                                               .getResultList();
    int n = list.size();
    log().info("list: {}", n);
    for (AppointmentModel app : list)
    {
      String code = app.getConference().getCode();

      logAppDetail(app, code, "last 5 mins for");

      OriginateAction originateAction = new OriginateAction();
      originateAction.setAsync(false);
      originateAction.setChannel("Local/203@interni");
      originateAction.setContext("interni");
      originateAction.setExten("201");
      originateAction.setPriority(1);
      //originateAction.setCallerId(app.getConference().getPin());
      originateAction.setVariable("indice",app.getConference().getPin());
      originateAction.setVariable("stanza",code);
      //log().info("originate: CallerID", app.getConference().getPin());
      try
      {
        asteriskService.sendCommand(originateAction);
      }
      catch (Exception e)
      {
        log().warn(e.getMessage(), e);
      }
    }
  }


  /*
   * delete overdue appointments
   */
  private void deleteOverdue(Date now)
  {
    List<AppointmentModel> list = entityManager.createQuery("from AppointmentModel where ?1 > endTime")
                                               .setParameter(1, now, TemporalType.TIMESTAMP)
                                               .getResultList();
    int n = list.size();
    log().info("list: {}", n);
    for (AppointmentModel app : list)
    {
      String code = app.getConference().getCode();
      logAppDetail(app, code, "cancel");

      cancel(app);
    }
  }


  /*
   * activate
   */
  private void activate(Date now)
  {
    List<AppointmentModel> list = entityManager.createQuery("from AppointmentModel where ?1 between startTime and endTime")
                                               .setParameter(1, now, TemporalType.TIMESTAMP)
                                               .getResultList();
    int n = list.size();
    log().info("list: {}", n);
    for (AppointmentModel app : list)
    {
      String code = app.getConference().getCode();
      if (code.startsWith("_"))
      {
        code = code.substring(code.indexOf('#')+1);
        app.getConference().setCode(code);
        appointmentDao.update(app);

        logAppDetail(app, code, "activating");

        modifyLine(true, app, code);
      }
      else
      {
        log().info("active on {}", code);
      }
    }
  }


  /**
   * @param app
   * @param code
   */
  private void modifyLine(boolean active, AppointmentModel app, String code)
  {
    for (ConferenceLine line : conferenceList)
    {
      if (StringUtils.equals(code, line.getExten()))
      {
        line.setActive(active);
        //line.setColor("red");
        line.setOwner(active ? app.getMail() : null);
      }
    }
  }

}
