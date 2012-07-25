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
package mic.contacta.gateway;

import java.util.List;
import mic.contacta.domain.AppointmentModel;
import mic.contacta.domain.SipAccountModel;
import mic.contacta.domain.CoverageModel.CoverageType;
import mic.contacta.json.CallsJson;
import mic.contacta.json.CoverageJson;
import mic.contacta.json.PhoneJson;
import mic.contacta.json.SipAccountJson;
import mic.organic.core.Service;
import mic.organic.gateway.GatewayException;


/**
 * @author mic
 * @created Jun 10, 2009
 */
public interface PbxGateway extends Service
{
  String SERVICE_NAME = "pbxGateway";


  /**
   * @param id
   * @return
   */
  PhoneJson phoneFind(int id);


  /**
   *
   * @return
   */
  List<PhoneJson> phoneList();


  /**
   *
   * @param json
   * @return
   * @throws GatewayException
   */
  PhoneJson phonePersist(PhoneJson json) throws GatewayException;


  /**
   *
   * @param ids
   * @return
   */
  Boolean[] phoneRemove(int[] ids);


  /**
   * @param mac
   * @param login
   * @return
   */
  SipAccountModel phoneAddAccount(int phoneId, String login);


  /**
   *
   * @return
   */
  List<SipAccountJson> sipList();


  /**
   *
   * @param json
   * @return
   * @throws GatewayException
   */
  SipAccountJson sipPersist(SipAccountJson json) throws GatewayException;


  /**
   *
   * @param ids
   * @return
   */
  Boolean[] sipRemove(int[] ids);


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
   *
   * @return
   */
  String asteriskCheck();


  /**
   *
   * @return
   */
  boolean asteriskRestart();


  /**
   * write asterisk extensions_profile.conf file and send reload to local asterisk
   */
  void extenProfileUpdate();


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


  /**
   * @param exten
   * @return
   */
  boolean asteriskSipNotifyCheckCfg(String exten);


  /**
   *
   * @param exten
   * @return
   */
  List<CallsJson> missedSkypeCalls(String exten);
}
