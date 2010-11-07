/* $Id: AppointmentDaoImpl.java 634 2010-05-08 11:14:28Z michele.bianchi $
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

import javax.persistence.TemporalType;

import mic.contacta.model.AppointmentModel;
import mic.contacta.server.api.ContactaConstants;
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
public class AppointmentDaoImpl extends AbstractDao<AppointmentModel> implements AppointmentDao
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  /*
   *
   */
  public AppointmentDaoImpl()
  {
    super(AppointmentModel.class, ContactaConstants.CODE);
  }


  /*
   * @see mic.contacta.ptool.dao.CocDao#findByLogin(java.lang.String)
   */
  @Transactional(readOnly=true)
  @Override
  public AppointmentModel findByCode(String code)
  {
    return findBySmth(code);
  }


  /*
   * @see mic.contacta.ptool.dao.CocDao#findByLogin(java.lang.String)
   */
  @Transactional(readOnly=true)
  @Override
  public List<AppointmentModel> findByMail(String mail)
  {
    List<AppointmentModel> list = entityManager.createQuery("from AppointmentModel where mail=?1 order by startTime").setParameter(1, mail).getResultList();
    return list;
  }


  /*
   * @see mic.contacta.ptool.dao.CocDao#findByLogin(java.lang.String)
   */
  @Transactional(readOnly=true)
  @Override
  public List<AppointmentModel> findOverlapping(AppointmentModel appointment)
  {
    List<AppointmentModel> list = entityManager.createQuery("from AppointmentModel where ?1 between startTime and endTime or ?2 between startTime and endTime")
                                               .setParameter(1, appointment.getStartTime(), TemporalType.TIMESTAMP)
                                               .setParameter(2, appointment.getEndTime(), TemporalType.TIMESTAMP)
                                               .getResultList();
    return list;
  }

}

