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

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.json.annotations.SMDMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import mic.contacta.asterisk.spi.AsteriskService;
import mic.contacta.dao.CdrDao;
import mic.contacta.domain.CdrModel;
import mic.contacta.domain.CdrQueryModel;
import mic.contacta.server.ContactaConstants;
import mic.organic.core.Model;
import mic.organic.gateway.DatastoreJson;
import mic.organic.gateway.DefaultDatastoreJson;
import mic.organic.gateway.JsonException;


/**
 *
 * @author mic
 * @created Apr 16, 2008
 */
@Service("cdrAction")
@Scope("session")
public class CdrAction extends AbstractContactaSmd<CdrModel>
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private CdrDao cdrDao;

  private String uniqueid;


  /*
   *
   */
  public CdrAction()
  {
    super();
  }


  /**
   * @return the uniqueid
   */
  public String getUniqueid()
  {
    return uniqueid;
  }


  /**
   * @param uniqueid the uniqueid to set
   */
  public void setUniqueid(String uniqueid)
  {
    this.uniqueid = uniqueid;
  }


  /*
   * @see mic.miirc.webapp.JsonSmd#persist(mic.miirc.json.PhoneJson)
   */
  @SMDMethod
  @Override
  public CdrModel persist(CdrModel json) throws JsonException
  {
//    try
//    {
//      return CdrDao.update(json);
//    }
//    catch (Exception e)
//    {
//      throw new JsonException(e, json);
//    }
    return null;
  }


  /*
   * @see mic.miirc.webapp.JsonSmd#remove(int[])
   */
  @SMDMethod
  @Override
  public Boolean[] remove(int[] ids) throws JsonException
  {
//    try
//    {
//      return CdrDao.delete(ids[0]);
//    }
//    catch (Exception e)
//    {
//      throw new JsonException(e);
//    }
    return null;
  }


  /*
   * @see mic.miirc.webapp.JsonSmd#find(int)
   */
  @SMDMethod
  @Override
  public CdrModel find(int id)
  {
    throw new NotImplementedException("cdr hasn't int id");
  }


  /*
   * @see mic.miirc.webapp.JsonSmd#findAll()
   */
  @SMDMethod
  @Override
  public DatastoreJson<CdrModel> findAll()
  {
    List<CdrModel> jsonList = cdrDao.findAll();
    DatastoreJson<CdrModel> store = new DefaultDatastoreJson<CdrModel>(CdrDao.UNIQUEID, CdrDao.UNIQUEID, jsonList);
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







  /*
   * @see mic.organic.gateway.AbstractJsonSmd#detail()
   */
  @Override
  public String detail()
  {
    if (StringUtils.isBlank(uniqueid))
    {
      return ERROR;
    }
    model = cdrDao.findByUniqueId(uniqueid);
    if (model != null)
    {
      return SUCCESS;
    }
    else
    {
      log().info("cannot find model.code='{}'", code);
      return ERROR;
    }
  }




  /*
   * TODO use or not?
   */
  @SMDMethod
  public CdrModel findByUniqueId(String uniqueid)
  {
    return cdrDao.findByUniqueId(uniqueid);
  }










  protected final static String[] CALL_DISPOSITION = { "ANSWERED", "FAILED", "NO ANSWER" };

  private AsteriskService asteriskService;
  private List<CdrModel> cdrList;
  private CdrQueryModel cdrQuery;


  /*
   *
   */
  public List<CdrModel> getCdrs()
  {
    return cdrList;
  }


  /*
   *
   */
  private List<String> findCdrSelectList(ContactaConstants.ListOf of)
  {
    List<String> list = null;
    try
    {
      list = null;//asteriskService.findCdrSelectList(of);
    }
    catch(Exception e)
    {
      log().warn(e.getMessage());
      list = new ArrayList<String>();
      list.add("ERROR");
    }
    return list;
  }


  /*
   *
   */
  public List<String> getListOfCaller()
  {
    return this.findCdrSelectList(ContactaConstants.ListOf.ListOfCaller);
  }


  /*
   *
   */
  public List<String> getListOfCallee()
  {
    return this.findCdrSelectList(ContactaConstants.ListOf.ListOfCallee);
  }


  /*
   *
   */
  public CdrQueryModel getCdrQuery()
  {
    return cdrQuery;
  }


  /*
   *
   */
  public void setCdrQuery(CdrQueryModel cdrQuery)
  {
    this.cdrQuery = cdrQuery;
  }


  /*
   *
   *
  public String last20()
  {
    cdrQuery = new CdrQueryModel();
    cdrQuery.setItems(20);
    this.runQuery();
    return Action.SUCCESS;
  }*/


  /*
   *
   *
  public String last200()
  {
    cdrQuery = new CdrQueryModel();
    cdrQuery.setItems(200);
    this.runQuery();
    return Action.SUCCESS;
  }*/

}
