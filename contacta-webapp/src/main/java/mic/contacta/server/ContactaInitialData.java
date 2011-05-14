/**
 * Synapse - Copyright(c) 1999-2011 Michele Bianchi <info@openinnovation.it>
 * Openinnovation srl, http://www.openinnovation.it
 * All Rights Reserved
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
  protected void localPolicies(Map<String, PolicyModel> policyMap, Map<String, RoleModel> roleMap, Map<String, OperationModel> operationMap, Map<String, AccountModel> accountMap)
  {
    // TODO Auto-generated method stub

  }


  /*
   *
   */
  protected AccountModel findOrCreateSipAccount(Map<String, SipAccountModel> accountMap, String login, String password, String displayName, GroupModel group, RoleModel... roles)
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
      account = contactaBuilder.buildSipAccount(login, password, displayName);
      accountService.accountPersist(account);
    }
    accountMap.put(login, account);
    return account;
  }


  /*
   *
   */
  private Map<String, SipAccountModel> initialSipAccounts(Map<String, SipAccountModel> accountMap, Map<String, OperationModel> operationMap, Map<String, RoleModel> roleMap, Map<String, GroupModel> groupMap)
  {
    if (accountMap == null)
    {
      accountMap = new HashMap<String, SipAccountModel>();
    }
    Long count = (Long)(entityManager.createQuery("select count(*) from AccountModel").getSingleResult());
    if (count == 0)
    {
      findOrCreateSipAccount(accountMap, "41", "41", "Administrator (root)", null, roleMap.get(ROLE_USER), roleMap.get(ROLE_ADMIN));
      findOrCreateSipAccount(accountMap, "42", "42", "Administrator (root)", null, roleMap.get(ROLE_USER), roleMap.get(ROLE_ADMIN));

      //localAccounts(operationMap, roleMap, groupMap, accountMap);
    }

    SipAccountModel root = (SipAccountModel)(accountService.accountByLogin(contactaConfiguration.getAdminLogin()));
    if (root == null)
    {
      String login = contactaConfiguration.getAdminLogin();
      String password = contactaConfiguration.getAdminPassword();
      root = contactaBuilder.buildSipAccount(login, password, "Contacta administrator");
      accountService.accountPersist(root);
    }

    return accountMap;
  }


  /*
   *
   */
  private void initialPbx()
  {
    Long count = (Long)(entityManager.createQuery("select count(*) from PbxContextModel").getSingleResult());
    if (count == 0)
    {
      pbxContextDao.create(contactaBuilder.buildPbxContext("sipphones", "sipphones"));
      pbxContextDao.create(contactaBuilder.buildPbxContext("international", "international"));
      pbxContextDao.create(contactaBuilder.buildPbxContext("national-cell", "national-cell"));
      pbxContextDao.create(contactaBuilder.buildPbxContext("national", "national"));
      pbxContextDao.create(contactaBuilder.buildPbxContext("numspecial", "numspecial"));
      pbxContextDao.create(contactaBuilder.buildPbxContext("fromremote", "fromremote"));
      pbxContextDao.create(contactaBuilder.buildPbxContext("co_inbound", "Inbound"));
      pbxContextDao.create(contactaBuilder.buildPbxContext("co_outbound", "Outbound"));
    }

    count = (Long)(entityManager.createQuery("select count(*) from PbxProfileModel").getSingleResult());
    if (count == 0)
    {
      pbxProfileDao.create(contactaBuilder.buildPbxProfile(organicVfs, "dial", "Dial", "macro(co_stdexten,${EXTEN},SIP/,30)", "res:conf/asterisk/macro/stdexten_nos.conf"));
      pbxProfileDao.create(contactaBuilder.buildPbxProfile(organicVfs, "dial-vm", "Dial + segreteria", "macro(co_stdexten,${EXTEN},SIP/,30)", "res:conf/asterisk/macro/stdexten.conf"));
      pbxProfileDao.create(contactaBuilder.buildPbxProfile(organicVfs, "dial-hunt", "Dial a cascata", "macro(co_stdexten,${EXTEN},SIP/,30)", "res:conf/asterisk/macro/stdextentwo.conf"));
      pbxProfileDao.create(contactaBuilder.buildPbxProfile(organicVfs, "dial-hunt-vm", "Dial a cascata + segreteria", "macro(co_stdexten,${EXTEN},SIP/,30)", "res:conf/asterisk/macro/outisbusy.conf"));
      pbxProfileDao.create(contactaBuilder.buildPbxProfile(organicVfs, "coverage", "Coverage", "macro(boss_assistant,${EXTEN},SIP/,${RINGTIMEOUT})", "res:conf/asterisk/macro/boss_assistant.conf"));
    }
  }


  /**
   *
   */
  @Transactional
  public void initialData()
  {
    Map<String, OperationModel> operationMap = initialOperations();
    Map<String, RoleModel> roleMap = initialRoles();
    Map<String, GroupModel> groupMap = initialGroups(roleMap);
    Map<String, AccountModel> accountMap = new HashMap<String, AccountModel>(); //initialAccounts(operationMap, roleMap, groupMap);
    initialSipAccounts(null, operationMap, roleMap, groupMap);
    Map<String, PolicyModel> policyMap = initialPolicies(roleMap, operationMap, accountMap);
    Map<String, PersonModel> personMap = initialPersons();
    Map<String, OrganizationModel> organizationMap = initialOrganizations();


    for (Configurer configurer : configurerList)
    {
      provisioningService.registerPmod(configurer);
    }

    initialPbx();
  }

}
