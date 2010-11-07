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

import mic.contacta.model.SipUserModel;
import mic.organic.core.AbstractDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 *
 * @author michele.bianchi@gmail.com
 * @version $Revision: 634 $
 */
@Repository
public class SipUserDaoImpl extends AbstractDao<SipUserModel> implements SipUserDao
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  /*
   *
   */
  public SipUserDaoImpl()
  {
    super(SipUserModel.class, "name");
  }


  /*
   * @see mic.contacta.ptool.dao.SipUserDao#findByLogin(java.lang.String)
   */
  @Transactional(readOnly=true)
  @Override
  public SipUserModel findByLogin(String name)
  {
    return findBySmth(name);
  }


  /*
   * @see mic.contacta.ptool.dao.SipUserDao#deleteByLogin(java.lang.String)
   */
  @Transactional
  @Override
  public void deleteByLogin(String login)
  {
    SipUserModel sipUser = findByLogin(login);
    if (sipUser != null)
    {
      delete(sipUser);
    }
  }

}

