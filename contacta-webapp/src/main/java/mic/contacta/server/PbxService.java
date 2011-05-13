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

import java.util.List;
import mic.contacta.domain.CdrModel;
import mic.contacta.domain.CoverageModel;
import mic.contacta.domain.PhoneModel;
import mic.contacta.domain.SipAccountModel;
import mic.contacta.domain.CoverageModel.CoverageType;
import mic.organic.core.Service;


/**
 * Manage everything about SIP: accounts, ... nothing else by now:)
 *
 * @author mic
 * @created Jan 1, 2009
 */
public interface PbxService extends Service
{
  String SERVICE_NAME = "pbxService";


  /**
   * Create a Sip account
   *
   * @param model
   * @return
   */
  SipAccountModel sipCreate(SipAccountModel account);


  /**
   * Update the Sip account
   *
   * @param model
   * @return
   */
  SipAccountModel sipUpdate(SipAccountModel account);


  /**
   * Delete Sip account completely
   *
   * @param id
   * @return
   */
  boolean sipDelete(SipAccountModel account);


  /**
   * Find a Sip account by the internel id (PK)
   *
   * @param id
   * @return
   */
  SipAccountModel sipFind(int id);


  /**
   * Find a Sip account by the login (unique key)
   *
   * @param login
   * @return
   */
  SipAccountModel sipByLogin(String login);


  /**
   * Find all Sip accounts in the system
   *
   * @return
   */
  List<SipAccountModel> sipList();


  /**
   * @return
   */
  List<Object[]> sipBriefList();


  /**
   * Assign a phone to a SIP account
   *
   * @param phone
   * @param account
   */
  void assignPhoneToAccount(PhoneModel phone, SipAccountModel account);


  /**
   * @param fromSip
   * @param toSip
   * @param type
   * @param options
   * @param timeout TODO
   * @return
   */
  CoverageModel coverageAdd(SipAccountModel fromSip, SipAccountModel toSip, CoverageType type, String options, int timeout);


  /**
   * @param fromSip
   */
  void coverageRemove(SipAccountModel fromSip);


  /**
   *
   */
  void upgradeCheck();


  /**
   * Write the extension macro profile to file, see CC-55
   *
   * @throws ContactaException
   */
  void writeExtenProfile(boolean dropExtension) throws ContactaException;


  /**
   * Write the extension hint to file and reload extensions in asterisk
   *
   * @throws ContactaException
   */
  void writeExtenHint() throws ContactaException;


  /**
   *
   * @param exten
   * @return
   */
  List<CdrModel> missedSkypeCalls(String exten);
}
