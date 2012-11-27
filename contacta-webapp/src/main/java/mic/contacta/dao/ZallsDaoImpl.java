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

import java.util.List;
import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import mic.contacta.domain.ZallsModel;
import mic.organic.aaa.model.PersonModel;
import mic.organic.core.AbstractDao;


/**
 *
 *
 * @author michele.bianchi@gmail.com
 * @version $Revision: 664 $
 */
@Repository
public class ZallsDaoImpl extends AbstractDao<ZallsModel> implements ZallsDao
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  /*
   *
   */
  public ZallsDaoImpl()
  {
    super(ZallsModel.class, "boh");
  }


  /*
   * @see mic.contacta.ptool.dao.CallsDao#findByMacAddress(java.lang.String)
   */
  @Transactional(readOnly=true)
  @Override
  public ZallsModel findByMacAddress(String macAddress)
  {
    throw new NotImplementedException("return findByCode(macAddress);");
  }


  /*
   * @see mic.contacta.ptool.dao.SipAccountDao#findAccountByPerson(mic.organic.aaa.model.PersonModel)
   */
  @Transactional(readOnly=true)
  @Override
  public List<ZallsModel> findByPerson(PersonModel person)
  {
    String query = "from ZallsModel where person=:value";
    List<ZallsModel> list = entityManager().createQuery(query).setParameter("value", person).getResultList();
    return list;
  }


  /*
   * @see mic.contacta.ptool.dao.CallsDao#findMacAddressList()
   */
  @Transactional(readOnly=true)
  @Override
  public List<String> findMacAddressList()
  {
    List<String> list = entityManager().createQuery("select p.code from ZallsModel as p").getResultList();
    return list;
  }


}

