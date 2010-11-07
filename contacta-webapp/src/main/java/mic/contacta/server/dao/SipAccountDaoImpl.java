/* $Id: PhoneDaoImpl.java 25 2008-12-16 18:30:51Z michele.bianchi@gmail.com $
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
package mic.contacta.server.dao;

import java.util.List;

import mic.contacta.model.SipAccountModel;
import mic.organic.core.AbstractForeverDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 *
 * @author michele.bianchi@gmail.com
 * @version $Revision: 660 $
 */
@Repository("sipAccountDao")
public class SipAccountDaoImpl extends AbstractForeverDao<SipAccountModel> implements SipAccountDao
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  /*
   *
   */
  public SipAccountDaoImpl()
  {
    super(SipAccountModel.class);
  }


  /*
   *
   */
  @Transactional(readOnly=true)
  @Override
  public SipAccountModel findByLogin(String login)
  {
    return findByCode(login);
  }


  /*
   * @see mic.contacta.ptool.dao.SipAccountDao#findAccountByPerson(mic.organic.aaa.model.PersonModel)
   */
  @Transactional(readOnly=true)
  @Override
  public List<Object[]> findAccountBriefList()
  {
    String select = "ca.id as CAID,ca.code as CALOGIN,ca.password as CAPWD,ca.profilename as CAPRO,ca.profileoptions as CAPROOPT,ca.context as CACTX,ca.callgroup as CACG,ca.pickupgroup as CAPG,ca.vmEnabled as CAVE";
    select += ",vo.password as VOPWD";
    select += ",ca.email as PEML,ca.displayName as PF,ca.displayName as PL,ca.displayName as PFULL"; // TODO was first/lastName
    select += ",coc.login as COCLOG,coc.pin as COCPWD";
    select += ",ph.macaddress as PHMAC,ph.id as PHID,ph.config as PHCC,ph.product_id as PHV,ph.product_id as PHM, ca.vmSendEmail, ca.ringTimeout";
    select += ",sip.port";
    String from = " from" +
                  " acacc ca left join copho ph on ca.phone_id=ph.id" +
                  " left join cococ coc on ca.coc_id=coc.id" +
                  " left join stvmu vo on ca.vm_id=vo.uniqueid" +
                  //" left join abper per on ca.person_id=per.id" +
                  " left join stsip sip on ca.sipuser_id=sip.id";

    String query = "select "+select+from;
    List<Object[]> oList = entityManager.createNativeQuery(query).getResultList();
    return oList;
  }


  /*
   * @see mic.contacta.ptool.dao.SipAccountDao#findAccountByCallgroup(java.lang.String)
   */
  @Transactional(readOnly=true)
  @Override
  public List<SipAccountModel> findAccountByCallgroup(String callgroup)
  {
    String query = "from SipAccountModel where callgroup=:value";
    List<SipAccountModel> list = entityManager.createQuery(query).setParameter("value", callgroup).getResultList();
    return list;
  }


}

