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
package mic.contacta.server;

import java.util.List;

import mic.contacta.dao.CocDao;
import mic.contacta.domain.CocModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Description of the Class
 *
 * @author mic
 * @version $Revision: 669 $
 */
@Service(CocService.SERVICE_NAME)
public class CocServiceImpl implements CocService
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private CocDao cocDao;

  /**
   *
   */
  public CocServiceImpl()
  {
    super();
  }


  /*
   * @see mic.contacta.server.ptool.SipService#createSipCoc(mic.contacta.ptool.dao.CocModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public CocModel createCoc(CocModel model)
  {
    return cocDao.create(model);
  }


  /*
   * @see mic.contacta.server.ptool.SipService#deleteSipCoc(int)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public Boolean[] deleteCoc(int id)
  {
    return new Boolean[] { cocDao.delete(id) };
  }


  /*
   * @see mic.contacta.server.ptool.SipService#findAllSipCocs()
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public List<CocModel> findCocList()
  {
    return cocDao.findAll();
  }


  /*
   * @see mic.contacta.server.ptool.SipService#find(int)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public CocModel findCoc(int id)
  {
    return cocDao.find(id);
  }


  /*
   * @see mic.contacta.server.ptool.SipService#find(int)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public CocModel findCocByLogin(String login)
  {
    return cocDao.findByLogin(login);
  }


  /*
   * @see mic.contacta.server.ptool.CocService#updateCoc(mic.contacta.ptool.dao.CocModel)
   */
  @Transactional(propagation=Propagation.MANDATORY)
  @Override
  public CocModel updateCoc(CocModel model)
  {
    return cocDao.update(model);
  }

}
