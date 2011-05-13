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
package mic.contacta.dao;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import mic.contacta.domain.CdrModel;
import mic.organic.core.AbstractDao;


/**
 *
 *
 * @author mic
 * @created Feb 3, 2009
 */
@Repository
public class CdrDaoImpl extends AbstractDao<CdrModel> implements CdrDao
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  /*
   *
   */
  public CdrDaoImpl()
  {
    super(CdrModel.class, UNIQUEID);
  }


  /*
   * @see mic.contacta.dao.CdrDao#findByUniqueId(java.lang.String)
   */
  @Transactional(readOnly=true)
  @Override
  public CdrModel findByUniqueId(String uniqueid)
  {
    return findBySmth(uniqueid);
  }


  /*
   * @see mic.contacta.dao.CdrDao#missedSkypeCalls(java.lang.String)
   */
  @Transactional(readOnly=true)
  @Override
  public List<CdrModel> missedSkypeCalls(String exten)
  {
    List<CdrModel> list = entityManager.createNamedQuery("cdrMissedSkype").setParameter("dst", exten).getResultList();
    return list;
  }

  /*
  CdrModel cdr = new CdrModel();
  //cdr.setId();
  cdr.setCalldate(rs.getString(1));
  cdr.setClid(rs.getString(2));
  cdr.setSrc(rs.getString(3));
  cdr.setDst(rs.getString(4));
  cdr.setDcontext(rs.getString(5));
  cdr.setChannel(rs.getString(6));
  cdr.setDstchannel(rs.getString(7));
  cdr.setLastapp(rs.getString(8));
  cdr.setLastdata(rs.getString(9));
  cdr.setDuration(rs.getInt(10));
  cdr.setBillsec(rs.getInt(11));
  cdr.setDisposition(rs.getString(12));
  cdr.setAmaflags(rs.getLong(13));
  cdr.setAccountcode(rs.getString(14));
  cdr.setUniqueid(rs.getString(15));
  cdr.setUserfield(rs.getString(16));
  list.add(cdr);
*/

}

