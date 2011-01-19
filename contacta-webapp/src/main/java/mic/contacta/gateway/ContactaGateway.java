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

import java.util.List;

import mic.contacta.json.CoverageJson;
import mic.contacta.json.PhoneJson;
import mic.contacta.json.SipAccountJson;
import mic.contacta.model.AppointmentModel;
import mic.contacta.model.SipAccountModel;
import mic.contacta.model.CoverageModel.CoverageType;
import mic.contacta.server.api.ContactaException;
import mic.organic.core.Service;

/**
 *
 * @author mic
 * @created Jun 10, 2009
 */
public interface ContactaGateway extends Service
{
  String SERVICE_NAME = "contactaGateway";

  /*
   *
   */
  AppointmentModel book(long startTs, long endTs, String attendeeString, String subject, String description);

  /**
   * @param appointmentId
   * @return
   */
  AppointmentModel cancel(int appointmentId);

  /**
   * @param appointmentId
   * @return
   */
  AppointmentModel remail(int appointmentId);


  List<PhoneJson> phoneList();

  PhoneJson phonePersist(PhoneJson json) throws ContactaException;

  Boolean[] phoneDelete(int[] ids);

  /**
   * @param mac
   * @param login
   * @return
   */
  SipAccountModel phoneAddAccount(int phoneId, String login);

  List<SipAccountJson> accountList();

  SipAccountJson accountCreateUpdate(SipAccountJson json) throws ContactaException;

  Boolean[] accountDelete(int[] ids);

  String checkAsterisk();

  boolean restartAsterisk();

  /**
   * write asterisk extensions_profile.conf file and send reload to local asterisk
   */
  void updateExtensionProfile();

  /**
   * @param fromLogin
   * @return
   */
  boolean coverageRemove(String fromLogin);

  /*
   *
   */
  boolean coverageAdd(String fromLogin, String toLogin, CoverageType type, String options, int timeout);

  /**
   * @return
   */
  List<CoverageJson> coverageList();

  /**
   * @param exten
   * @return
   */
  boolean notifyCheckCfg(String exten);

}
