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

import java.util.List;

import mic.contacta.model.CocModel;
import mic.organic.core.Service;


/**
 * Manage everything about SIP: cocs, ... nothing else by now:)
 *
 * @author mic
 * @created Jan 1, 2009
 */
public interface CocService extends Service
{
  public final static String SERVICE_NAME = "cocService";

  /**
   * Create a Sip coc
   *
   * @param model
   * @return
   */
  CocModel createCoc(CocModel model);

  /**
   * Update the Sip coc
   *
   * @param model
   * @return
   */
  CocModel updateCoc(CocModel model);

  /**
   * Delete Sip coc completely
   *
   * @param id
   * @return
   */
  Boolean[] deleteCoc(int id);

  /**
   * Find a Sip coc by the internel id (PK)
   *
   * @param id
   * @return
   */
  CocModel findCoc(int id);


  /**
   * Find a Sip coc by the login (unique key)
   *
   * @param login
   * @return
   */
  CocModel findCocByLogin(String login);

  /**
   * Find all Sip cocs in the system
   *
   * @return
   */
  List<CocModel> findCocList();

}
