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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs.FileObject;
import org.asteriskjava.manager.response.CommandResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import mic.contacta.asterisk.spi.AsteriskService;
import mic.contacta.model.PhoneModel;
import mic.contacta.model.SipAccountModel;
import mic.contacta.pmod.common.Configurer;
import mic.contacta.server.api.Call;
import mic.contacta.server.api.ContactaException;
import mic.contacta.server.api.Line;
import mic.contacta.server.api.LineComparator;
import mic.contacta.server.dao.PbxContextDao;
import mic.contacta.server.dao.PbxProfileDao;
import mic.contacta.server.dao.SipAccountDao;
import mic.contacta.util.ContactaUserDetailsService;
import mic.organic.aaa.model.PersonModel;
import mic.organic.aaa.spi.AddressbookService;
import mic.organic.core.OrganicException;
import mic.organic.util.Banner;
import mic.organic.vfs.OrganicVfs;


/**
 *
 *
 *
 *
 * @author         michele.bianchi@gmail.com
 * @version        $Revision: 672 $
 */
@Service(ContactaService.SERVICE_NAME)
public class ContactaServiceImpl implements ContactaService
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @PersistenceContext EntityManager entityManager;

  @Autowired protected OrganicVfs organicVfs;
  @Autowired private ContactaConfiguration configuration;
  @Autowired private ContactaUserDetailsService contactaUserDetailsService;

  @Autowired private ImportService importService;
  @Autowired private AddressbookService addressbookService;
  @Autowired private ProvisioningService provisioningService;
  @Autowired private InventoryService inventoryService;
  @Autowired private SipService sipService;
//  @Autowired private CocService cocService;
//  @Autowired private CalendarService calendarService;
//  @Autowired private PhonebarService phonebarService;
  @Autowired private SipAccountDao sipAccountDao;
  @Autowired(required=false) private AsteriskService asteriskService;
  @Autowired private ContactaBuilder sampleBuilder;
  @Autowired private PbxContextDao pbxContextDao;
  @Autowired private PbxProfileDao pbxProfileDao;

  private String automaticUpgrade;
  private List<Configurer> configurerList;


  /*
   *
   */
  public ContactaServiceImpl()
  {
    super();

    configurerList = new ArrayList<Configurer>();
  }


  /**
   * @param automaticUpgrade the automaticUpgrade to set
   */
  public void setAutomaticUpgrade(String automaticUpgrade)
  {
    this.automaticUpgrade = automaticUpgrade;
  }


  /*
   *
   */
  @PostConstruct
  @Override
  public void startup()
  {
    Banner.setApplicationName("Contacta");
    Banner.printBanner(log());

    SipAccountModel root = sipAccountDao.findByCode(contactaUserDetailsService.getAdminLogin());
    if (root == null)
    {
      root = sampleBuilder.buildSipAccount(contactaUserDetailsService.getAdminLogin(), contactaUserDetailsService.getAdminPassword());
      sipAccountDao.create(root);
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


  /*
   *
   */
  @PreDestroy
  @Override
  public void shutdown()
  {
    logger.warn("=====================================================================");
    logger.warn("");
    logger.warn("   shutting down...");
    logger.warn("");
    logger.warn("=====================================================================");
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
  @SuppressWarnings("unused")
  private void automaticUpgrade()
  {
    if (StringUtils.isNotBlank(automaticUpgrade))
    {
      log().warn("======= attention: executing automaticUpgrade:{} =======", automaticUpgrade);
      List<SipAccountModel> accountList = sipService.findAccountList();
      for (SipAccountModel account : accountList)
      {
        account.getVmBox().setEmail(account.getVmSendEmail() ? account.getEmail() : "");
        sipService.updateAccount(account);
      }

      sipService.upgradeCheck();
    }
  }


  /**
   *
   */
  private void dropAll()
  {
    log().info("dropping everything before import...");

    for (SipAccountModel sipAccount : sipService.findAccountList())
    {
      sipService.deleteAccount(sipAccount);
    }
    for (PhoneModel phone : inventoryService.findPhoneList())
    {
      inventoryService.deletePhone(phone);
    }
    for (PersonModel person : addressbookService.personList())
    {
      addressbookService.personDelete(person);
    }
  }


  /**
   *
   */
  @Transactional(propagation=Propagation.REQUIRED)
  @Override
  public void importFromCsv()
  {
    boolean reload = false;

    if (configuration.getCsvImport())
    {
      if (configuration.getDropBeforeImport())
      {
        dropAll();
      }
      if (inventoryService.findPhoneList().size() > 0)
      {
        log().info("phone present, import will not run.");
      }
      else
      {
        log().info("no phone found, starting import...");
        String bootFilename = configuration.getCsvFilename();
        try
        {
          importService.importFromCsv(bootFilename);
          reload = true;
        }
        catch (OrganicException e)
        {
          log().warn("continuing even if: {}", e.getMessage());
          //log().info(e.getMessage(), e);
        }
        log().info("import done.");
      }
    }

    if (reload)
    {
      try
      {
        sipService.writeExtenProfile(configuration.getUseDrop612());
        asteriskCommand(ContactaService.RELOAD);// was "extensions reload"
      }
      catch (ContactaException e)
      {
        log().warn("continuing even if: {}", e.getMessage());
        //log().info(e.getMessage(), e);
      }
    }
  }


  /**
   * @throws OrganicException
   *
   */
  @Transactional(propagation=Propagation.REQUIRES_NEW)
  @Override
  public void importFromWebappCsv(FileObject csvFile, boolean drop) throws OrganicException
  {
    if (drop)
    {
      log().info("Dropping data, command executed from Admin GUI");
      dropAll();

      entityManager.flush();
    }

    log().info("Importing data from Admin GUI uploaded file");
    try
    {
      importService.importFromCsv(csvFile);
      entityManager.flush();

      sipService.writeExtenProfile(configuration.getUseDrop612());
      asteriskCommand(ContactaService.RELOAD);// was "extensions reload");
    }
    catch (Exception e)
    {
      log().warn("continuing even if: {}", e.getMessage());
      throw new OrganicException("Got errors while importing data. Rollbacking to previous state");
    }

    log().info("import done.");
  }


  /*
   * @see mic.contacta.server.ptool.PtoolService#asteriskCommand(java.lang.String)
   */
  @Override
  public Object asteriskCommand(String command)
  {
    Object result = null;
    try
    {
      result = asteriskService.sendCommand(command);

      // TODO piccola grande porcata temporanea.  per ora cast, poi faro' un oggetto result fatto bene
      CommandResponse response = (CommandResponse) (result);
      log().debug("response: {}", response);
      for (String line : response.getResult())
      {
        log().debug("{}", line);
      }
    }
    catch (ContactaException e)
    {
      log().warn("cannot send command to asterisk: {}", e.getMessage());
    }
    return result;
  }


  /*
   *
   */
  private void trySetCall(Map<String, Line> lineMap, Call call)
  {
    Line line = lineMap.get(call.getFrom().getCallerId());
    if (line == null && call.getTo() != null)
    {
      line = lineMap.get(call.getTo().getCallerId());
    }
    if (line != null)
    {
      line.setCall(call);
    }
  }


  /*
   *
   */
  private void trySetCall2(Map<String, Line> lineMap, Call call)
  {
    Line line = lineMap.get(call.getTo().getCallerId());
    if (line == null && call.getFrom() != null)
    {
      line = lineMap.get(call.getFrom().getCallerId());
    }
    if (line != null)
    {
      line.setCall(call);
    }
  }


  /*
   * @see mic.contacta.server.spi.ContactaService#lineStatus()
   */
  @Override
  public List<Line> lineStatus() throws ContactaException
  {
    List<Call> list = asteriskService.getActiveCalls();
    //callList = list;

    List<SipAccountModel> sipList = sipAccountDao.findAll();

    Map<String,Line> lineMap = new HashMap<String,Line>();
    for (SipAccountModel sip : sipList)
    {
      String exten = sip.getLogin();
      Line line = new Line(exten, sip.getCallerId());
      lineMap.put(exten, line);
    }
    for (Call call : list)
    {
      trySetCall(lineMap, call);
      trySetCall2(lineMap, call);
    }
    List<Line> lineList = new ArrayList<Line>(lineMap.values());
    LineComparator comparator = new LineComparator();
    Collections.sort(lineList, comparator);
    return lineList;
  }
}
