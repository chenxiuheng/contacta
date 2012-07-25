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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.vfs2.FileObject;
import org.asteriskjava.manager.response.CommandResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import mic.contacta.asterisk.spi.AsteriskService;
import mic.contacta.dao.SipAccountDao;
import mic.contacta.domain.Call;
import mic.contacta.domain.Line;
import mic.contacta.domain.LineComparator;
import mic.contacta.domain.PhoneModel;
import mic.contacta.domain.SipAccountModel;
import mic.contacta.pmod.common.Configurer;
import mic.organic.aaa.model.PersonModel;
import mic.organic.aaa.spi.AddressbookService;
import mic.organic.core.OrganicException;
import mic.organic.util.Banner;


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

  @Autowired private ContactaConfiguration configuration;
  @Autowired private ContactaInitialData initialData;

  @Autowired private ImportService importService;
  @Autowired private AddressbookService addressbookService;
  @Autowired private InventoryService inventoryService;
  @Autowired private PbxService pbxService;
//  @Autowired private CocService cocService;
//  @Autowired private CalendarService calendarService;
//  @Autowired private PhonebarService phonebarService;
  @Autowired private SipAccountDao sipAccountDao;
  @Autowired(required=false) private AsteriskService asteriskService;

  private String automaticUpgrade;


  /*
   *
   */
  public ContactaServiceImpl()
  {
    super();
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

    initialData.initialData();
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
    initialData.setConfigurerList(configurerList);
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
      List<SipAccountModel> accountList = pbxService.sipList();
      for (SipAccountModel account : accountList)
      {
        account.getVmBox().setEmail(account.getVmSendEmail() ? account.getEmail() : "");
        pbxService.sipPersist(account);
      }

      pbxService.upgradeCheck();
    }
  }


  /**
   *
   */
  private void dropAll()
  {
    log().info("dropping everything before import...");

    for (SipAccountModel sipAccount : pbxService.sipList())
    {
      pbxService.sipDelete(sipAccount);
    }
    for (PhoneModel phone : inventoryService.phoneList())
    {
      inventoryService.phoneDelete(phone);
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
      if (inventoryService.phoneList().size() > 0)
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
        pbxService.writeExtenProfile(configuration.getUseDrop612());
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

      pbxService.writeExtenProfile(configuration.getUseDrop612());
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
   * @see mic.contacta.server.ContactaService#lineStatus()
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
