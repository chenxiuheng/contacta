/**
 * Synapse - Copyright(c) 1999-2011 Michele Bianchi <info@openinnovation.it>
 * Openinnovation srl, http://www.openinnovation.it
 * All Rights Reserved
 */
package mic.contacta.server;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mic.contacta.dao.PbxContextDao;
import mic.contacta.dao.PbxProfileDao;
import mic.contacta.domain.SipAccountModel;
import mic.contacta.pmod.common.Configurer;
import mic.contacta.util.ContactaUserDetailsService;
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
  @Autowired private ContactaUserDetailsService contactaUserDetailsService;
  @Autowired private ContactaBuilder sampleBuilder;
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


  /**
   *
   */
  @Transactional
  public void initialData()
  {

    SipAccountModel root = (SipAccountModel)(accountService.accountByLogin(contactaUserDetailsService.getAdminLogin()));
    if (root == null)
    {
      root = sampleBuilder.buildSipAccount(contactaUserDetailsService.getAdminLogin(), contactaUserDetailsService.getAdminPassword());
      accountService.accountPersist(root);
    }

    for (Configurer configurer : configurerList)
    {
      provisioningService.registerPmod(configurer);
    }


    Long count = (Long)(entityManager.createQuery("select count(*) from PbxContextModel").getSingleResult());
    if (count == 0)
    {
      pbxContextDao.create(sampleBuilder.buildPbxContext("sipphones", "sipphones"));
      pbxContextDao.create(sampleBuilder.buildPbxContext("international", "international"));
      pbxContextDao.create(sampleBuilder.buildPbxContext("national-cell", "national-cell"));
      pbxContextDao.create(sampleBuilder.buildPbxContext("national", "national"));
      pbxContextDao.create(sampleBuilder.buildPbxContext("numspecial", "numspecial"));
      pbxContextDao.create(sampleBuilder.buildPbxContext("fromremote", "fromremote"));
      pbxContextDao.create(sampleBuilder.buildPbxContext("co_inbound", "Inbound"));
      pbxContextDao.create(sampleBuilder.buildPbxContext("co_outbound", "Outbound"));
    }

    count = (Long)(entityManager.createQuery("select count(*) from PbxProfileModel").getSingleResult());
    if (count == 0)
    {
      pbxProfileDao.create(sampleBuilder.buildPbxProfile(organicVfs, "dial", "Dial", "macro(co_stdexten,${EXTEN},SIP/,30)", "res:conf/asterisk/macro/stdexten_nos.conf"));
      pbxProfileDao.create(sampleBuilder.buildPbxProfile(organicVfs, "dial-vm", "Dial + segreteria", "macro(co_stdexten,${EXTEN},SIP/,30)", "res:conf/asterisk/macro/stdexten.conf"));
      pbxProfileDao.create(sampleBuilder.buildPbxProfile(organicVfs, "dial-hunt", "Dial a cascata", "macro(co_stdexten,${EXTEN},SIP/,30)", "res:conf/asterisk/macro/stdextentwo.conf"));
      pbxProfileDao.create(sampleBuilder.buildPbxProfile(organicVfs, "dial-hunt-vm", "Dial a cascata + segreteria", "macro(co_stdexten,${EXTEN},SIP/,30)", "res:conf/asterisk/macro/outisbusy.conf"));
      pbxProfileDao.create(sampleBuilder.buildPbxProfile(organicVfs, "coverage", "Coverage", "macro(boss_assistant,${EXTEN},SIP/,${RINGTIMEOUT})", "res:conf/asterisk/macro/boss_assistant.conf"));
    }
  }

}
