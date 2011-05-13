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

import mic.contacta.domain.CocModel;
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
public class CocDaoImpl extends AbstractDao<CocModel> implements CocDao
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  /*
   *
   */
  public CocDaoImpl()
  {
    super(CocModel.class, ContactaConstants.LOGIN);
  }


  /*
   * @see mic.contacta.ptool.dao.CocDao#findByLogin(java.lang.String)
   */
  @Transactional(readOnly=true)
  @Override
  public CocModel findByLogin(String login)
  {
    return findBySmth(login);
  }

}

