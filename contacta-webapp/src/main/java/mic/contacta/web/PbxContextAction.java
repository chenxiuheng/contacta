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
package mic.contacta.web;

import java.util.List;
import org.apache.struts2.json.annotations.SMDMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import mic.contacta.dao.PbxContextDao;
import mic.contacta.domain.PbxContextModel;
import mic.organic.core.Model;
import mic.organic.gateway.DatastoreJson;
import mic.organic.gateway.DefaultDatastoreJson;
import mic.organic.gateway.JsonException;


/**
 *
 * @author mic
 * @created Apr 16, 2008
 */
@Service("pbxcontextAction")
@Scope("session")
public class PbxContextAction extends AbstractContactaSmd<PbxContextModel>
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private PbxContextDao pbxContextDao;


  /*
   *
   */
  public PbxContextAction()
  {
    super();
  }


  /*
   * @see mic.miirc.webapp.JsonSmd#persist(mic.miirc.json.PhoneJson)
   */
  @SMDMethod
  @Override
  public PbxContextModel persist(PbxContextModel json) throws JsonException
  {
    try
    {
      return pbxContextDao.update(json);
    }
    catch (Exception e)
    {
      throw new JsonException(e, json);
    }
  }


  /*
   * @see mic.miirc.webapp.JsonSmd#remove(long[])
   */
  @SMDMethod
  @Override
  public Boolean[] remove(int[] ids) throws JsonException
  {
    try
    {
      return new Boolean[] { pbxContextDao.delete(ids[0]) };
    }
    catch (Exception e)
    {
      throw new JsonException(e);
    }
  }


  /*
   * @see mic.miirc.webapp.JsonSmd#find(long)
   */
  @SMDMethod
  @Override
  public PbxContextModel find(int id)
  {
    return pbxContextDao.find(id);
  }


  /*
   * @see mic.miirc.webapp.JsonSmd#findAll()
   */
  @SMDMethod
  @Override
  public DatastoreJson<PbxContextModel> findAll()
  {
    List<PbxContextModel> jsonList = pbxContextDao.findAll();
    DatastoreJson<PbxContextModel> store = new DefaultDatastoreJson<PbxContextModel>(DatastoreJson.IDENTIFIER, DatastoreJson.LABEL, jsonList);
    return store;
  }


  /*
   * @see mic.contacta.web.AbstractContactaSmd#findModel(java.lang.Integer)
   */
  @Override
  public Model findModel(Integer oid)
  {
    return find(oid);
  }

}
