/* $Id: SipServiceImpl.java 1985 2008-10-27 17:23:55Z michele.bianchi@gmail.com $
 *
 * Contacta, http://openinnovation.it - roberto grasso, michele bianchi
 * Copyright(C) 1998-2009 [michele.bianchi@gmail.com]
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

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mic.contacta.asterisk.spi.ExtenHintWriter;
import mic.contacta.asterisk.spi.ExtenProfileWriter;
import mic.contacta.model.*;
import mic.contacta.model.CoverageModel.CoverageType;
import mic.contacta.server.api.ContactaException;
import mic.contacta.server.dao.*;
import mic.contacta.util.UpgradeTo052;
import mic.organic.vfs.OrganicVfs;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * pg_dump -U mic -h io --format=c contacta > a.psql && pg_restore --clean --dbname=contacta a.psql
 *
 * @author mic
 * @version $Revision: 660 $
 */
@Service(SipService.SERVICE_NAME)
public class SipServiceImpl implements SipService
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @PersistenceContext EntityManager entityManager;

  @Autowired private OrganicVfs organicVfs;
  @Autowired private SipAccountDao sipAccountDao;
  @Autowired private PhoneDao phoneDao;
  @Autowired private SipUserDao sipUserDao;
  @Autowired private CoverageDao coverageDao;
  @Autowired private PbxContextDao pbxContextDao;
  @Autowired private PbxProfileDao pbxProfileDao;
  @Autowired(required=false) private ExtenProfileWriter extenProfileWriter;
  @Autowired(required=false) private ExtenHintWriter extenHintWriter;

  private String extenHintUrl;
  private String profileExtensionUrl;

  private String voicemailContext = "utenti";

  private boolean generateAutoanswer = false;;


  /**
   *
   */
  public SipServiceImpl()
  {
    super();
  }


  /*
   *
   */
  @PostConstruct
  public void configure()
  {
//    for (PbxProfileModel profile : pbxProfileDao.findAll())
//    {
//      extenProfileWriter.addProfile(profile.getCode(), profile.getCommand());
//    }


    if(profileExtensionUrl != null)
    {
      if (profileExtensionUrl.startsWith("/") == false)
      {
        profileExtensionUrl = SystemUtils.getUserDir().getAbsolutePath()+SystemUtils.FILE_SEPARATOR+profileExtensionUrl;
      }
    }

    if(extenHintUrl != null)
    {
      if (extenHintUrl.startsWith("/") == false)
      {
        extenHintUrl = SystemUtils.getUserDir().getAbsolutePath()+SystemUtils.FILE_SEPARATOR+extenHintUrl;
      }
    }
  }


  /*
   * @see mic.organic.core.Component#startup()
   */
  @Transactional(propagation=Propagation.REQUIRES_NEW)
  @Override
  public void upgradeCheck()
  {
    //List<T> list = entityManager.createQuery(queryFindBySmth).setParameter("value", value).getResultList();

    Long accountCount = (Long)(entityManager.createQuery("select count(id) from SipAccountModel").getSingleResult());
    log().info("SipAccountModel={}", accountCount);

    UpgradeTo052 upgradeTo052 = new UpgradeTo052(this, sipUserDao, entityManager, voicemailContext);

    upgradeTo052.fixSipUser(accountCount);
    upgradeTo052.fixVoicemail(accountCount);
  }


  /**
   * @param agiBaseUrl the agiBaseUrl to set
   */
  public void setAgiBaseUrl(String agiBaseUrl)
  {
    extenProfileWriter.setAgiBaseUrl(agiBaseUrl);
  }


  /**
   * @param extenHintUrl the extenHintUrl to set
   */
  public void setExtenHintUrl(String extenHintUrl)
  {
    this.extenHintUrl = extenHintUrl;
  }


  /**
   * @param profileExtensionUrl the profileExtensionUrl to set
   */
  public void setProfileExtensionUrl(String profileExtensionUrl)
  {
    this.profileExtensionUrl = profileExtensionUrl;
  }


  /*
   * @see mic.contacta.server.ptool.SipService#createSipAccount(mic.contacta.ptool.dao.SipAccountModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public SipAccountModel createAccount(SipAccountModel account)
  {
    //validateProfile(account);
    return sipAccountDao.create(account);
  }


  /*
   * @see mic.contacta.server.ptool.SipService#updateSipAccount(mic.contacta.ptool.dao.SipAccountModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public SipAccountModel updateAccount(SipAccountModel model)
  {
    //validateProfile(model);
    String callerId = '"'+model.getLabel()+"\" <"+model.getLogin()+">";
    model.getSipUser().setCallerid(callerId);
    return sipAccountDao.update(model);
  }


  /*
   * @see mic.contacta.server.ptool.SipService#deleteSipAccount(mic.contacta.ptool.dao.SipAccountModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public boolean deleteAccount(SipAccountModel account)
  {
     if (account.getPhone() != null)
    {
      account.getPhone().getSipAccountList().remove(account);
      phoneDao.update(account.getPhone());
      account.setPhone(null);
    }
    return sipAccountDao.delete(account);
  }


  /*
   * @see mic.contacta.server.ptool.SipService#findAllSipAccounts()
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public List<SipAccountModel> findAccountList()
  {
    return sipAccountDao.findAll();
  }


  /*
   * @see mic.contacta.server.ptool.SipService#find(int)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public SipAccountModel findAccount(int id)
  {
    return sipAccountDao.find(id);
  }


  /*
   * @see mic.contacta.server.ptool.SipService#find(int)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public SipAccountModel findAccountByLogin(String login)
  {
    return sipAccountDao.findByLogin(login);
  }


  /*
   * @see mic.contacta.server.ptool.SipService#assignPhoneToAccount(mic.contacta.ptool.dao.PhoneModel, mic.contacta.ptool.dao.SipAccountModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public void assignPhoneToAccount(PhoneModel phone, SipAccountModel account)
  {
    List<SipAccountModel> accountList = phone.getSipAccountList();
    accountList.add(account);
    phoneDao.update(phone);

    account.setPhone(phone);
    sipAccountDao.update(account);
  }


  /*
   * @see mic.contacta.server.ptool.SipService#findCompleteAccountList()
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public List<Object[]> findAccountBriefList()
  {
    return sipAccountDao.findAccountBriefList();
  }


  /*
   * @see mic.contacta.server.ptool.SipService#addCoverage(mic.contacta.ptool.dao.SipAccountModel, mic.contacta.ptool.dao.SipAccountModel, java.lang.String, java.lang.String)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public CoverageModel addCoverage(SipAccountModel fromSip, SipAccountModel toSip, CoverageType type, String options, int timeout)
  {
    CoverageModel coverage = new CoverageModel(fromSip, toSip, type, options, timeout);
    coverage.setRank(fromSip.getCoverageList().size());
    coverageDao.update(coverage);
    fromSip.getCoverageList().add(coverage);
    fromSip.setHasCoverage(true);
    if (type != CoverageType.Presence)
    {
      fromSip.setProfile(pbxProfileDao.findByCode(PbxProfileModel.PROFILE_COVERAGE));
      fromSip.setProfileOptions(null);
    }
    sipAccountDao.update(fromSip);
    return coverage;
  }


  /*
   * @see mic.contacta.server.ptool.SipService#removeCoverage(mic.contacta.ptool.dao.SipAccountModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public void removeCoverage(SipAccountModel fromSip)
  {
    for (CoverageModel coverage : fromSip.getCoverageList())
    {
      coverageDao.delete(coverage);
    }
    fromSip.getCoverageList().clear();
    fromSip.setHasCoverage(false);
    if (StringUtils.equals(fromSip.getProfile().getCode(), PbxProfileModel.PROFILE_COVERAGE))  // TODO potential npe
    {
      fromSip.setProfile(pbxProfileDao.findByCode(PbxProfileModel.PROFILE_1));
      fromSip.setProfileOptions(null);
    }
    sipAccountDao.update(fromSip);
    coverageDao.deleteReverse(fromSip);
  }


  /**
   * TODO questa funzione e' lenta, punto e basta, adesso non ho proprio voglia di pensarci!
   * @throws ContactaException
   */
  @Transactional(readOnly=true)
  @Override
  public void writeExtenProfile(boolean drop612) throws ContactaException
  {
    log().info("writing profileExtensionUrl: {}", profileExtensionUrl);
    StringBuilder sb = extenProfileWriter.generate(findAccountList(), drop612);

    if (generateAutoanswer)
    {
      sb.append("\n[autoanswer]\n");
      sb.append("exten => s,1,Answer()\n");
      sb.append("exten => s,n,NoOp(Context: Autoanswer)\n");
      sb.append("exten => s,n,SIPAddHeader(Alert-Info: RANR)\n\n");
    }

    try
    {
      String tmp = extenHintUrl+"_"+System.currentTimeMillis();
      FileObject fo = organicVfs.resolve(tmp);
      fo.createFile();
      fo.getContent().getOutputStream().write(sb.toString().getBytes());
      fo.close();

      fo.moveTo(organicVfs.resolve(profileExtensionUrl));
    }
    catch (FileSystemException e)
    {
      log().warn("Cannot write {}, check location. ", profileExtensionUrl, e.getMessage());
      throw new ContactaException("Cannot write {}, check location. "+profileExtensionUrl, e);
    }
    catch (IOException e)
    {
      log().warn("Cannot write profile lines\n{}", sb.toString(), e);
      throw new ContactaException("Cannot write profile lines", e);
    }
  }


  /*
   * @see mic.contacta.server.ptool.SipService#writeExtenHint()
   */
  @Transactional(readOnly=true)
  @Override
  public void writeExtenHint() throws ContactaException
  {
    log().info("writing extenHintUrl: {}", extenHintUrl);
    StringBuilder sb = extenHintWriter.generate(findAccountList());

    try
    {
      String tmp = extenHintUrl+"_"+System.currentTimeMillis();
      FileObject fo = organicVfs.resolve(tmp);
      fo.createFile();
      fo.getContent().getOutputStream().write(sb.toString().getBytes());
      fo.close();

      fo.moveTo(organicVfs.resolve(extenHintUrl));
    }
    catch (FileSystemException e)
    {
      log().warn("Cannot write {}, check location. ", extenHintUrl, e.getMessage());
      throw new ContactaException("Cannot write {}, check location. "+extenHintUrl, e);
    }
    catch (IOException e)
    {
      log().warn("Cannot write profile lines\n{}", sb.toString(), e);
      throw new ContactaException("Cannot write profile lines", e);
    }
  }


}
