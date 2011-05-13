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
package mic.contacta.server;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mic.contacta.domain.AppointmentModel;
import mic.contacta.domain.ConferenceLine;
import mic.organic.core.Service;


/**
 * @author michele.bianchi@gmail.com
 * @version $Revision: 616 $
 */
public interface CalendarService extends Service
{
  String SERVICE_NAME = "calendarService";

  /**
   * @param startTime
   * @param endTime
   * @param attendeeList
   * @param subject
   * @param description
   * @return
   */
  AppointmentModel book(Date startTime, Date endTime, /*List<Person> attendeeList*/ String attendees, String subject, String description);

  /**
   * @param mail
   * @return
   */
  List<AppointmentModel> listAppointments(String mail);

  /**
   * @param appointment
   */
  void cancel(AppointmentModel appointment);

  /**
   * @param appointment
   */
  void remail(AppointmentModel appointment);

  /**
   * @param properties
   */
  void activateConferences(Map<String, String> properties);

  /**
   * @param week
   * @return
   */
  List<AppointmentModel> weekCalendar(Date week);

  /**
   * @return
   */
  List<ConferenceLine> getConferenceList();


}
