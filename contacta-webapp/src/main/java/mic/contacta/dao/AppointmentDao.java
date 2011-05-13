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
package mic.contacta.dao;

import java.util.List;

import mic.contacta.domain.AppointmentModel;
import mic.organic.core.Dao;


/**
 *
 *
 * @author mic
 * @created Feb 3, 2009
 */
public interface AppointmentDao extends Dao<AppointmentModel>
{

  /**
   * Find a coverage by its code
   *
   * @param login
   * @return
   */
  AppointmentModel findByCode(String code);

  /**
   * @param mail
   * @return
   */
  List<AppointmentModel> findByMail(String mail);

  /**
   * @param appointment
   * @return
   */
  List<AppointmentModel> findOverlapping(AppointmentModel appointment);

}