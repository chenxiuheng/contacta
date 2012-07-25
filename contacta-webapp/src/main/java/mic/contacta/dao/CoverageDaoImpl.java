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
package mic.contacta.dao;

import mic.contacta.domain.CoverageModel;
import mic.contacta.domain.SipAccountModel;
import mic.contacta.server.ContactaConstants;
import mic.organic.core.AbstractDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 *
 * @author mic
 * @created Feb 3, 2009
 */
@Repository
public class CoverageDaoImpl extends AbstractDao<CoverageModel> implements CoverageDao
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  /*
   *
   */
  public CoverageDaoImpl()
  {
    super(CoverageModel.class, ContactaConstants.CODE);
  }


  /*
   * @see mic.contacta.ptool.dao.CocDao#findByLogin(java.lang.String)
   */
  @Transactional(readOnly=true)
  @Override
  public CoverageModel findByCode(String code)
  {
    return findBySmth(code);
  }


  /*
   * @see mic.contacta.domain.CoverageDao#deleteReverse(mic.contacta.domain.SipAccountModel)
   */
  @Transactional
  @Override
  public void deleteReverse(SipAccountModel toSip)
  {
    entityManager().createQuery("delete from CoverageModel where toSip=:sip").setParameter("sip", toSip).executeUpdate();
    //FIXME convert from postgresql query to hql
    //entityManager.createQuery("UPDATE SipAccountModel c SET hasCoverage = false where c.id NOT IN (SELECT distinct fromsip_id from CoverageModel)").setParameter("sipId", toSip).executeUpdate();
    entityManager().createNativeQuery("UPDATE acacc c SET hascoverage = false where c.id NOT IN (SELECT distinct fromsip_id from cocvg)").executeUpdate();
  }

}

