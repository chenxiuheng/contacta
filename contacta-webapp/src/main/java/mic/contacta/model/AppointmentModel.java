/* $Id: AppointmentModel.java 634 2010-05-08 11:14:28Z michele.bianchi $
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
package mic.contacta.model;

import java.util.Date;

import javax.persistence.*;

import org.apache.commons.lang.StringUtils;

import mic.organic.core.AbstractModel;


/**
 *
 * @author mic
 * @created Feb 3, 2009
 */
@Entity
@Table(name="coapo")
public class AppointmentModel extends AbstractModel
{
  public static final String [] appointmentExcludes = { "account" };

  public enum Repeat { None };
  public enum Reminder { Never };

  private String code;
  private SipAccountModel account;
  private String mail;
  private String attendees;
  private String subject;
  private String location;
  private String description;
  private Date startTime;
  private Date endTime;
  private boolean allDay;
  private Repeat repeat;
  private Reminder reminder;

  private ConferenceModel conference;
  private String room;


  /*
   *
   */
  @Deprecated
  public AppointmentModel()
  {
    setExcludes(appointmentExcludes);
  }


  /*
   *
   */
  public AppointmentModel(SipAccountModel account, String attendees)
  {
    this();
    this.account = account;
    //this.code = code;
    this.attendees = StringUtils.isNotBlank(attendees) ? attendees : "";
  }


  /**
   * @return the code
   */
  @Basic//(optional=false)
  //@Column(unique=true,nullable=false)
  public String getCode()
  {
    return code;
  }


  /**
   * @param code the code to set
   */
  public void setCode(String code)
  {
    this.code = code;
  }


  /**
   * @return the mail
   */
  @Basic(optional=false)
  @Column(nullable=false,length=80)
  public String getMail()
  {
    return mail;
  }


  /**
   * @param mail the mail to set
   */
  public void setMail(String mail)
  {
    this.mail = mail;
  }


  /**
   * @return the account
   */
  @ManyToOne(optional=true)
  @JoinColumn(nullable=true)
  public SipAccountModel getAccount()
  {
    return account;
  }


  /**
   * @param account the account to set
   */
  public void setAccount(SipAccountModel fromSip)
  {
    this.account = fromSip;
  }


  /**
   * @return the attendees
   */
  @Basic
  @Column(length=500)
  public String getAttendees()
  {
    return attendees;
  }


  /**
   * @param attendees the attendees to set
   */
  public void setAttendees(String options)
  {
    this.attendees = options;
  }


  /**
   * @return the subject
   */
  @Basic(optional=false)
  @Column(nullable=false,length=200)
  public String getSubject()
  {
    return subject;
  }


  /**
   * @param subject the subject to set
   */
  public void setSubject(String subject)
  {
    this.subject = subject;
  }


  /**
   * @return the location
   */
  @Basic
  @Column(length=200)
  public String getLocation()
  {
    return location;
  }


  /**
   * @param location the location to set
   */
  public void setLocation(String location)
  {
    this.location = location;
  }


  /**
   * @return the description
   */
  @Basic
  @Column(length=4000)
  public String getDescription()
  {
    return description;
  }


  /**
   * @param description the description to set
   */
  public void setDescription(String description)
  {
    this.description = description;
  }


  /**
   * @return the startTime
   */
  @Basic(optional=false)
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable=false,length=200)
  public Date getStartTime()
  {
    return startTime;
  }


  /**
   * @param startTime the startTime to set
   */
  public void setStartTime(Date start)
  {
    this.startTime = start;
  }


  /**
   * @return the endTime
   */
  @Basic(optional=false)
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable=false,length=200)
  public Date getEndTime()
  {
    return endTime;
  }


  /**
   * @param endTime the endTime to set
   */
  public void setEndTime(Date end)
  {
    this.endTime = end;
  }


  /**
   * @return the allDay
   */
  @Basic
  public boolean getAllDay()
  {
    return allDay;
  }


  /**
   * @param allDay the allDay to set
   */
  public void setAllDay(boolean allDay)
  {
    this.allDay = allDay;
  }


  /**
   * @return the repeat
   */
  @Basic
  public Repeat getRepeat()
  {
    return repeat;
  }


  /**
   * @param repeat the repeat to set
   */
  public void setRepeat(Repeat repeat)
  {
    this.repeat = repeat;
  }


  /**
   * @return the reminder
   */
  @Basic
  public Reminder getReminder()
  {
    return reminder;
  }


  /**
   * @param reminder the reminder to set
   */
  public void setReminder(Reminder reminder)
  {
    this.reminder = reminder;
  }


  /**
   * @return the conference
   */
  @OneToOne(cascade=CascadeType.ALL)
  public ConferenceModel getConference()
  {
    return conference;
  }


  /**
   * @param conference the conference to set
   */
  public void setConference(ConferenceModel conference)
  {
    this.conference = conference;
  }


  /**
   * @return the room
   */
  @Basic
  @Column(length=80)
  public String getRoom()
  {
    return room;
  }


  /**
   * @param room the room to set
   */
  public void setRoom(String room)
  {
    this.room = room;
  }

}
