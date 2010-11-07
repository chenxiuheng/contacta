/* $Id: CdrDaoImpl.java 667 2010-07-20 21:54:43Z michele.bianchi $
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import mic.contacta.model.CdrModel;
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
    super(CdrModel.class, "uniqueid");
  }


  /*
   * @see mic.contacta.server.dao.CdrDao#findByUniqueId(java.lang.String)
   */
  @Override
  public CdrModel findByUniqueId(String uniqueid)
  {
    return findBySmth(uniqueid);
  }

}

