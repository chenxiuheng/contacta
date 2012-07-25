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
package mic.contacta.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mic.contacta.dao.SipUserDao;
import mic.contacta.domain.SipAccountModel;
import mic.contacta.domain.SipUserModel;
import mic.contacta.domain.VoicemailModel;
import mic.contacta.server.PbxService;

/**
 *
 * @author mic
 * @created Jun 28, 2009
 */
public class UpgradeTo052
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  private EntityManager entityManager;
  private SipUserDao sipUserDao;
  private PbxService pbxService;
  private String voicemailContext;


  /*
   *
   */
  public UpgradeTo052(PbxService pbxService, SipUserDao sipUserDao, EntityManager entityManager, String voicemailContext)
  {
    super();
    this.pbxService = pbxService;
    this.sipUserDao = sipUserDao;
    this.entityManager = entityManager;
    this.voicemailContext = voicemailContext;
  }


  /**
   * @param accountCount
   */
  public void fixVoicemail(long accountCount)
  {
    Long count = (Long)(entityManager.createQuery("select count(id) from VoicemailModel").getSingleResult());
    log().info("VoceimailModel={}", count);
    long delta = accountCount-count;
    if (delta == 0)
    {
      log().info("SipAccount and VoicemailModel seems fine, DB is correct");
    }
    else if (delta < 0)
    {
      log().error("some VoicemailModel is dangling, check DB manually");
    }
    else
    {
      log().warn("some VoicemailModel is missing, trying to fix it");

      List<SipAccountModel> accountList = pbxService.sipList();
      for (SipAccountModel account : accountList)
      {
        if (account.getVmBox() == null)
        {
          log().info("sipAccount.login={} .vmBox == null, generating one", account.getLogin());

          String login = account.getLogin();
          SipUserModel sipUser = account.getSipUser();

          VoicemailModel vmBox = new VoicemailModel();
          vmBox.setContext(voicemailContext);

          sipUser.setMailbox(login+"@"+voicemailContext);

          vmBox.setFullname(account.getLabel());
          vmBox.setMailbox(login);
          vmBox.setPin(account.getPassword());
          vmBox.setEmail(account.getVmSendEmail() ? account.getPerson().getEmail() : "");

          account.setVmBox(vmBox);
        }
      }
    }
  }

  /**
   * @param accountCount
   */
  public void fixSipUser(long accountCount)
  {
    Long count = (Long)(entityManager.createQuery("select count(id) from SipUserModel").getSingleResult());
    log().info("SipUserModel={}", count);
    long delta = accountCount-count;
    if (delta == 0)
    {
      log().info("SipAccount and SipUser seems fine, DB is correct");
    }
    else if (delta > 0)
    {
      log().error("some SipUser is missing, check DB manually");
    }
    else
    {
      log().warn("some SipUser is dangling, trying to fix it");

      List<SipAccountModel> accountList = pbxService.sipList();
      Map<String,SipAccountModel> accountMap = new HashMap<String,SipAccountModel>();
      for (SipAccountModel account : accountList)
      {
        accountMap.put(account.getLogin(), account);
      }

      List<SipUserModel> sipList = sipUserDao.findAll();
      log().info("sipUserList.size={}", sipList.size());
      for (SipUserModel sip : sipList)
      {
        SipAccountModel account = accountMap.get(sip.getName());
        if (account == null || account.getSipUser().getId() != sip.getId())
        {
          log().info("deleting dangling multiple sipUser.name={}", sip.getName());
          sipUserDao.delete(sip);
        }
      }
    }
  }

}
