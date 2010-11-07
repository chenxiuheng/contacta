/* $Id: ContactDao.java 25 2008-12-16 18:30:51Z michele.bianchi@gmail.com $
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
package mic.contacta.server.dao;

import java.util.List;

import mic.contacta.model.PhoneModel;
import mic.organic.aaa.model.PersonModel;
import mic.organic.core.CodeDao;


/**
 *
 * @author mic
 */
public interface PhoneDao extends CodeDao<PhoneModel>
{

  /**
   * Find a phone by its mac address (aka, the code)
   *
   * @param macAddress
   * @return
   */
  PhoneModel findByMacAddress(String macAddress);


  /**
   *
   * @return
   */
  List<String> findMacAddressList();


  /**
   * @param person
   * @return
   */
  List<PhoneModel> findByPerson(PersonModel person);

}
