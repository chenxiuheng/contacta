/* $Id: PbxProfileAction.java 669 2010-07-22 21:38:16Z michele.bianchi $
 *
 * Miirc, http://openinnovation.it - roberto grasso, michele bianchi
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
package mic.contacta.struts2;

import java.util.List;
import org.apache.struts2.json.annotations.SMDMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import mic.contacta.model.PbxProfileModel;
import mic.contacta.server.dao.PbxProfileDao;
import mic.contacta.webapp.AbstractContactaSmd;
import mic.organic.core.Model;
import mic.organic.gateway.DatastoreJson;
import mic.organic.gateway.DefaultDatastoreJson;
import mic.organic.gateway.JsonException;


/**
 *
 * @author mic
 * @created Apr 16, 2008
 */
@Service("pbxprofileAction")
@Scope("session")
public class PbxProfileAction extends AbstractContactaSmd<PbxProfileModel>
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private PbxProfileDao pbxProfileDao;


  /*
   *
   */
  public PbxProfileAction()
  {
    super();
  }


  /*
   * @see mic.miirc.webapp.JsonSmd#persist(mic.miirc.json.PhoneJson)
   */
  @SMDMethod
  @Override
  public PbxProfileModel persist(PbxProfileModel json) throws JsonException
  {
    try
    {
      if (json.getId() == 0)
      {
        return pbxProfileDao.create(json);
      }
      else
      {
        return pbxProfileDao.update(json);
      }
    }
    catch (Exception e)
    {
      throw new JsonException(e, json);
    }
  }


  /*
   * @see mic.miirc.webapp.JsonSmd#remove(int[])
   */
  @SMDMethod
  @Override
  public Boolean[] remove(int[] ids) throws JsonException
  {
    try
    {
      return new Boolean[] { pbxProfileDao.delete(ids[0]) };
    }
    catch (Exception e)
    {
      throw new JsonException(e);
    }
  }


  /*
   * @see mic.miirc.webapp.JsonSmd#find(int)
   */
  @SMDMethod
  @Override
  public PbxProfileModel find(int id)
  {
    return pbxProfileDao.find(id);
  }


  /*
   * @see mic.miirc.webapp.JsonSmd#findAll()
   */
  @SMDMethod
  @Override
  public DatastoreJson<PbxProfileModel> findAll()
  {
    List<PbxProfileModel> jsonList = pbxProfileDao.findAll();
    DatastoreJson<PbxProfileModel> store = new DefaultDatastoreJson<PbxProfileModel>(DatastoreJson.IDENTIFIER, DatastoreJson.LABEL, jsonList);
    return store;
  }


  /*
   * @see mic.contacta.webapp.AbstractContactaSmd#findModel(java.lang.Integer)
   */
  @Override
  public Model findModel(Integer oid)
  {
    return find(oid);
  }

}
