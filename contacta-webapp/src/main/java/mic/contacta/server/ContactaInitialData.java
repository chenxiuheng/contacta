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
package mic.contacta.server;

import static mic.organic.aaa.spi.AccountService.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mic.contacta.dao.PbxContextDao;
import mic.contacta.dao.PbxProfileDao;
import mic.contacta.domain.PbxContextModel;
import mic.contacta.domain.PbxProfileModel;
import mic.contacta.domain.SipAccountModel;
import mic.contacta.pmod.common.Configurer;
import mic.organic.aaa.model.*;
import mic.organic.core.AbstractInitialData;
import mic.organic.vfs.OrganicVfs;


/**
 *
 *
 * @author mic
 * @created Nov 24, 2010
 */
@Service
public class ContactaInitialData extends AbstractInitialData
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private OrganicVfs organicVfs;
  @Autowired private ContactaConfiguration contactaConfiguration;
  @Autowired private ContactaBuilder contactaBuilder;
  @Autowired private PbxContextDao pbxContextDao;
  @Autowired private PbxProfileDao pbxProfileDao;
  @Autowired private ProvisioningService provisioningService;

  private List<Configurer> configurerList;


  /*
   *
   */
  public ContactaInitialData()
  {
    super();

    configurerList = new ArrayList<Configurer>();
  }


  /**
   * @param configurerList the configurerList to set
   */
  public void setConfigurerList(List<Configurer> configurerList)
  {
    this.configurerList = configurerList;
  }


  /*
   * @see mic.organic.core.AbstractInitialData#localOperations(java.util.Map)
   */
  @Override
  protected void localOperations(Map<String, OperationModel> operationMap)
  {
  }


  /*
   * @see mic.organic.core.AbstractInitialData#localAccounts(java.util.Map, java.util.Map, java.util.Map, java.util.Map)
   */
  @Override
  protected void localAccounts(Map<String, OperationModel> operationMap, Map<String, RoleModel> roleMap, Map<String, GroupModel> groupMap, Map<String, AccountModel> accountMap)
  {
  }


  /*
   * @see mic.organic.core.AbstractInitialData#localRoles(java.util.Map)
   */
  @Override
  protected void localRoles(Map<String, RoleModel> roleMap)
  {
  }


  /*
   * @see mic.organic.core.AbstractInitialData#localGroups(java.util.Map, java.util.Map)
   */
  @Override
  protected void localGroups(Map<String, RoleModel> roleMap, Map<String, GroupModel> groupMap)
  {
  }


  /*
   * @see mic.organic.core.AbstractInitialData#localPersons(java.util.Map)
   */
  @Override
  protected void localPersons(Map<String, PersonModel> personMap)
  {
    findOrCreatePerson(personMap, "openinnovation.pbx", "PBX", "Openinnovation", null, "+3900030", "30", "pbx@localhost", "openinnovation.pbx");
    findOrCreatePerson(personMap, "openinnovation.demo", "Demo", "Openinnovation", null, "+3900033", "33", "demo@localhost", "openinnovation.demo");
    findOrCreatePerson(personMap, "linphone.mic", "Linphone35", "Chrome", null, "+3900035", "35", "linphone@localhost", null);
    findOrCreatePerson(personMap, "michele.bianchi", "Michele", "Bianchi", null, "+3900833", "833", "mic@localhost", "michele.bianchi");
    findOrCreatePerson(personMap, "roberto.grasso", "Roberto", "Grasso", null, "+3900834", "834", "rob@localhost", "roberto.grasso");
  }


  /*
   * @see mic.organic.core.AbstractInitialData#localOrganizations(java.util.Map)
   */
  @Override
  protected void localOrganizations(Map<String, OrganizationModel> organizationMap)
  {
  }


  /*
   * @see mic.organic.core.AbstractInitialData#localPolicies(java.util.Map, java.util.Map, java.util.Map, java.util.Map)
   */
  @Override
  protected void localPolicies(Map<String, PolicyModel> policyMap, Map<String, RoleModel> roleMap, Map<String, OperationModel> operationMap, Map<String, ? extends AccountModel> accountMap)
  {
    AccountModel root = accountService.accountByLogin("root");
    if (root != null)
    {
      authorizationService.addPolicy(root, roleMap.get(ROLE_SUPERVISOR), operationMap.get(OP_SUPERVISOR), null);
      authorizationService.addPolicy(root, roleMap.get(ROLE_ADMIN), operationMap.get(OP_ADMIN), null);
    }
    else
    {
      log().warn("cannot find sip.login: root");
      return;
    }

    String[] users = { "30", "33", "35", "41", "42" };
    for (String login : users)
    {
      AccountModel sip = accountService.accountByLogin(login);
      if (sip != null)
      {
      }
      else
      {
        log().warn("cannot find sip.login: {}", login);
      }
    }
  }


  /*
   *
   */
  protected AccountModel findOrCreateSipAccount(Map<String, SipAccountModel> accountMap, String login, String password, String displayName, PbxContextModel context, PbxProfileModel profile, GroupModel group, RoleModel... roles)
  {
    SipAccountModel account = (SipAccountModel)(accountService.accountByLogin(login));
    if (account == null)
    {
      if (password == null)
      {
        password = login;
      }
      if (displayName == null)
      {
        displayName = login+" account";
      }
      account = contactaBuilder.buildSipAccount(login, password, displayName, context != null ? context.getCode() : null);
      //log().info("context={}, profile={}", context, profile);
      account.setContext(context);
      account.setProfile(profile);
      accountService.accountPersist(account);
    }
    accountMap.put(login, account);
    return account;
  }


  /*
   *
   */
  private Map<String, SipAccountModel> initialSipAccounts(Map<String, SipAccountModel> accountMap, Map<String, OperationModel> operationMap, Map<String, RoleModel> roleMap, Map<String, GroupModel> groupMap, Map<String, PbxContextModel> contextMap, Map<String, PbxProfileModel> profileMap)
  {
    if (accountMap == null)
    {
      accountMap = new HashMap<String, SipAccountModel>();
    }
    Long count = (Long)(entityManager.createQuery("select count(*) from AccountModel").getSingleResult());
    //if (count == 0)
    {
      findOrCreateSipAccount(accountMap, "30", "3030", "OI-PBX", contextMap.get("sipphones"), profileMap.get("dial"), null, roleMap.get(ROLE_USER));
      findOrCreateSipAccount(accountMap, "33", "3333", "OI-Demo", contextMap.get("sipphones"), profileMap.get("dial"), null, roleMap.get(ROLE_USER));
      findOrCreateSipAccount(accountMap, "35", "35", "Linphone35", contextMap.get("sipphones"), profileMap.get("dial"), null, roleMap.get(ROLE_USER));
      //AccountModel sip41 = findOrCreateSipAccount(accountMap, "41", "4141", "Roberto Grasso", contextMap.get("sipphones"), profileMap.get("dial"), null, roleMap.get(ROLE_USER));
      //AccountModel sip42 = findOrCreateSipAccount(accountMap, "42", "4242", "Michele Bianchi", contextMap.get("sipphones"), profileMap.get("dial"), null, roleMap.get(ROLE_USER));

      //localAccounts(operationMap, roleMap, groupMap, accountMap);
    }

    SipAccountModel root = (SipAccountModel)(accountService.accountByLogin(contactaConfiguration.getAdminLogin()));
    if (root == null)
    {
      String login = contactaConfiguration.getAdminLogin();
      String password = contactaConfiguration.getAdminPassword();
      root = contactaBuilder.buildSipAccount(login, password, "Contacta administrator", null);
      accountService.accountPersist(root);
    }

    return accountMap;
  }


  /**
   *
   */
  private void createPbxContext(Map<String, PbxContextModel> contextMap, String code, String label)
  {
    PbxContextModel context = pbxContextDao.create(contactaBuilder.buildPbxContext(code, label));
    //log().info("put: {}={}", code, context);
    contextMap.put(code, context);
  }


  /**
   * @param macro
   * @param res
   *
   */
  private void createPbxProfile(Map<String, PbxProfileModel> profileMap, String code, String label, String macro, String res)
  {
    PbxProfileModel profile = pbxProfileDao.create(contactaBuilder.buildPbxProfile(organicVfs, code, label, macro, res));
    //log().info("put: {}={}", code, profile);
    profileMap.put(code, profile);
  }


  /*
   *
   */
  private void initialPbx(Map<String, PbxContextModel> contextMap, Map<String, PbxProfileModel> profileMap)
  {
    Long count = (Long)(entityManager.createQuery("select count(*) from PbxContextModel").getSingleResult());
    if (count == 0)
    {
      createPbxContext(contextMap, "sipphones", "sipphones");
      createPbxContext(contextMap, "international", "international");
      createPbxContext(contextMap, "national-cell", "national-cell");
      createPbxContext(contextMap, "national", "national");
      createPbxContext(contextMap, "numspecial", "numspecial");
      createPbxContext(contextMap, "fromremote", "fromremote");
      createPbxContext(contextMap, "co_inbound", "Inbound");
      createPbxContext(contextMap, "co_outbound", "Outbound");
    }

    count = (Long)(entityManager.createQuery("select count(*) from PbxProfileModel").getSingleResult());
    if (count == 0)
    {
      createPbxProfile(profileMap, "dial", "Dial", "macro(co_stdexten,${EXTEN},SIP/,30)", "res:conf/asterisk/macro/stdexten_nos.conf");
      createPbxProfile(profileMap, "dial-vm", "Dial + segreteria", "macro(co_stdexten,${EXTEN},SIP/,30)", "res:conf/asterisk/macro/stdexten.conf");
      createPbxProfile(profileMap, "dial-hunt", "Dial a cascata", "macro(co_stdexten,${EXTEN},SIP/,30)", "res:conf/asterisk/macro/stdextentwo.conf");
      createPbxProfile(profileMap, "dial-hunt-vm", "Dial a cascata + segreteria", "macro(co_stdexten,${EXTEN},SIP/,30)", "res:conf/asterisk/macro/outisbusy.conf");
      createPbxProfile(profileMap, "coverage", "Coverage", "macro(boss_assistant,${EXTEN},SIP/,${RINGTIMEOUT})", "res:conf/asterisk/macro/boss_assistant.conf");
    }
  }


  /**
   *
   */
  @Transactional
  public void initialData()
  {
    Map<String, PbxContextModel> contextMap = new HashMap<String, PbxContextModel>();
    Map<String, PbxProfileModel> profileMap = new HashMap<String, PbxProfileModel>();
    initialPbx(contextMap, profileMap);

    Map<String, OperationModel> operationMap = initialOperations();
    Map<String, RoleModel> roleMap = initialRoles();
    Map<String, GroupModel> groupMap = initialGroups(roleMap);
    Map<String, ? extends AccountModel> accountMap = null; //initialAccounts(operationMap, roleMap, groupMap);
    accountMap = initialSipAccounts(null, operationMap, roleMap, groupMap, contextMap, profileMap);
    Map<String, PolicyModel> policyMap = initialPolicies(roleMap, operationMap, accountMap);
    Map<String, PersonModel> personMap = initialPersons();
    Map<String, OrganizationModel> organizationMap = initialOrganizations();


    for (Configurer configurer : configurerList)
    {
      provisioningService.registerPmod(configurer);
    }
  }

}
