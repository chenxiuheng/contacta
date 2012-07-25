/**
 * Contacta webapp, http://www.openinnovation.it - Michele Bianchi
 * Copyright(C) 1999-2012 info@openinnovation.it
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
import mic.contacta.domain.CdrModel;
import mic.organic.core.Dao;


/**
 *
 *
 * @author mic
 * @created Feb 3, 2009
 */
public interface CdrDao extends Dao<CdrModel>
{
  String UNIQUEID = "uniqueid";

  /**
   *
   * @param uniqueid
   * @return
   */
  CdrModel findByUniqueId(String uniqueid);


  /**
   * return the missed calls in the skype channel
   *
   * @param exten
   * @return
   */
  List<CdrModel> missedSkypeCalls(String exten);
}