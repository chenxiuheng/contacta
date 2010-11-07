/* $Id: ConferenceAction.java 660 2010-07-17 23:14:21Z michele.bianchi $
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
package mic.contacta.webapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mic.contacta.model.AppointmentModel;
import mic.contacta.server.spi.CalendarService;
import mic.organic.aaa.ldap.PersonDao;


/**
 *
 * @author mic
 * @created Apr 16, 2008
 */
@Service("conferenceAction")
@Scope("prototype")
public class ConferenceAction extends AbstractContactaAction
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  private static SimpleDateFormat dayLabelFormat = new SimpleDateFormat("EEEE dd/MM");

  @Autowired protected PersonDao personDao;
  @Autowired protected CalendarService calendarService;

  private List<AppointmentModel> appointmentList;
  private Calendar weekStart;
  private String weekN;
  private int [][] week;
  private String [] labelDay;


  /**
   * @return the appointmentList
   */
  public List<AppointmentModel> getAppointmentList()
  {
    return appointmentList;
  }


  /**
   * @return the week
   */
  public int[][] getWeek()
  {
    return week;
  }


  /**
   * @return the weekStart
   */
  public Date getWeekStart()
  {
    return weekStart.getTime();
  }


  /**
   * @return the weekN
   */
  public String getWeekN()
  {
    return weekN;
  }


  /**
   * @param weekN the weekN to set
   */
  public void setWeekN(String weekN)
  {
    this.weekN = weekN;
  }


  /**
   * @return the labelDay
   */
  public String[] getLabelDay()
  {
    return labelDay;
  }


  /*
   *
   */
  public String my()
  {
    String mail = contactaSession.getAccount().getEmail();
    appointmentList = calendarService.listAppointments(mail);
    return SUCCESS;
  }


  /*
   *
   */
  public String calendar()
  {
    /*String[] ids = TimeZone.getAvailableIDs();
    for (String id : ids)
    {
      log().info("tz={}", id);
    }

    labelHour = new String[24];
    for (int i = 0; i < 24; i++)
    {
      labelHour[i] = ""+i;
    }
    */
    Locale locale = Locale.getDefault();//ITALY;//new Locale(Locale.ITALIAN, Locale.ITALY);
    TimeZone tz = TimeZone.getDefault(); //getTimeZone("Europe/Rome");
    //log().info("locale={}", locale);
    //log().info("tz={}", tz);

    // day start at 8 o'clock
    int hStart = 8;

    Calendar dayStart = Calendar.getInstance();
    //c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    //dayStart.add(Calendar.HOUR, 300);  // just try like tomorrow
    dayStart.set(Calendar.HOUR_OF_DAY, 0);
    dayStart.set(Calendar.MINUTE, 0);
    dayStart.set(Calendar.SECOND, 0);
    //log().info("dayStart: {}", SimpleDateFormat.getDateTimeInstance().format(dayStart.getTime()));

    weekStart = Calendar.getInstance();
    weekStart.setTime(dayStart.getTime());
    weekStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    //log().info("weekStart: {}", SimpleDateFormat.getDateTimeInstance().format(weekStart.getTime()));

    moveToWeek();

    createDayLabel(locale, weekStart);

    createWeekSchedule(locale, tz, hStart, 0);

    return SUCCESS;
  }


  /**
   * @param locale
   * @param tz
   * @param hStart
   * @param firstDayOfWeek
   */
  private void createWeekSchedule(Locale locale, TimeZone tz, int hStart, Integer firstDayOfWeek)
  {
    appointmentList = calendarService.weekCalendar(weekStart.getTime());

    week = new int[(24-hStart)*2][7];
    for (AppointmentModel app : appointmentList)
    {
      Calendar calendar = Calendar.getInstance(); // tz, locale
      //calendar.setFirstDayOfWeek(Calendar.MONDAY);
      calendar.setTime(app.getStartTime());
      int d = (calendar.get(Calendar.DAY_OF_WEEK)-Calendar.MONDAY);
      int h = (calendar.get(Calendar.HOUR_OF_DAY)-hStart)*2+calendar.get(Calendar.MINUTE)/30;
      int l = 1+(int)(app.getEndTime().getTime()-app.getStartTime().getTime())/(30*60*1000);

      d = d == -1 ? 6 : d; // TODO questo fa schifo, ma adesso funzia con it_IT

      //log().info("adding {}x{} {}", new Object[] {d, h, l});
      //log().info("calendar: {}", SimpleDateFormat.getDateTimeInstance().format(calendar.getTime()));

      for (int i = 0; i < l; i++)
      {
        if (h+i >= 0) // we can have hStart, so skip
        {
          week[h+i][d]++;
        }
      }
    }

    float r = 100F/calendarService.getConferenceList().size();
    for (int i = 0; i < 7; i++)
    {
      for (int j = 0; j < (24-hStart)*2; j++)
      {
        week[j][i] = Math.round(week[j][i]*r);
      }
    }
  }


  /**
   * @param locale
   * @param wStart
   * @param day
   * @return
   */
  private Integer createDayLabel(Locale locale, Calendar wStart)
  {
    Map<String, Integer> dayList = wStart.getDisplayNames(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);
    labelDay = new String[7];
    for (String id : dayList.keySet())
    {
      int i = dayList.get(id);
      //log().info("key={}, value={}", id, i);
      if (i >= 0 && i < 7)
      {
        labelDay[i] = id;
      }
    }

    Calendar day = Calendar.getInstance();
    day.setTime(wStart.getTime());
    for (int i = 0; i < 7; i++)
    {
      labelDay[i] = dayLabelFormat.format(day.getTime());
      labelDay[i] = labelDay[i].replaceAll(" ", "<br/>");
      day.add(Calendar.HOUR, 24);
    }
    return 0;
  }


  /**
   *
   */
  private void moveToWeek()
  {
    if (contactaSession.getWeekN() == 0)
    {
      contactaSession.setWeekN(weekStart.get(Calendar.WEEK_OF_YEAR));
    }
    if (StringUtils.isNotBlank(weekN))
    {
      try
      {
        Integer i = Integer.parseInt(weekN);
        int wn = (contactaSession.getWeekN()+i) % 52;
        contactaSession.setWeekN(wn);
        weekStart.set(Calendar.WEEK_OF_YEAR, wn);
      }
      catch(NumberFormatException e)
      {
      }
    }
    weekN = String.valueOf(contactaSession.getWeekN());
  }

}
