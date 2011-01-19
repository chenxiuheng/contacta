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
package mic.contacta.webapp;

import java.util.List;
import org.apache.struts2.json.annotations.SMDMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import mic.contacta.json.SipAccountJson;
import mic.contacta.server.spi.SipService;
import mic.organic.core.Model;
import mic.organic.gateway.DatastoreJson;
import mic.organic.gateway.DefaultDatastoreJson;
import mic.organic.gateway.JsonException;


/**
 *
 *
 * @author mic
 * @created May 14, 2009
 */
@Service("sipAction")
@Scope("prototype")
public class SipAction extends AbstractContactaSmd<SipAccountJson>
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private SipService sipService;


  /**
   * @return the sipAccount
   */
  public Model getSipAccount()
  {
    return getModel();
  }


  /*
   *
   */
  public String star()
  {
    return detail();
  }





  /*
   * @see mic.organic.gateway.JsonSmd#persist(mic.organic.gateway.Json)
   */
  @Override
  @SMDMethod
  public SipAccountJson persist(SipAccountJson json) throws JsonException
  {
    try
    {
      return contactaGateway.accountCreateUpdate(json);
    }
    catch (Exception e)
    {
      throw new JsonException(e);
    }
  }


  /*
   * @see mic.organic.gateway.JsonSmd#remove(int[])
   */
  @Override
  @SMDMethod
  public Boolean[] remove(int[] ids) throws JsonException
  {
    try
    {
      return contactaGateway.accountDelete(ids);
    }
    catch (Exception e)
    {
      throw new JsonException("Cannot delete sip: already in use");
    }
  }


  /*
   * @see mic.organic.gateway.JsonSmd#find(int)
   */
  @Override
  @SMDMethod
  public SipAccountJson find(int id)
  {
    return null; //FIXME uncomment contactaGateway.sipFind(id);
  }


  /*
   * @see mic.organic.gateway.JsonSmd#findAll()
   */
  @Override
  @SMDMethod
  public DatastoreJson<SipAccountJson> findAll()
  {
    List<SipAccountJson> jsonList = contactaGateway.accountList();
    DefaultDatastoreJson<SipAccountJson> store = new DefaultDatastoreJson<SipAccountJson>(DatastoreJson.IDENTIFIER, "login", jsonList);
    return store;
  }


  /*
   * @see mic.contacta.webapp.AbstractContactaSmd#findModel(java.lang.Integer)
   */
  @Override
  public Model findModel(Integer oid)
  {
    return sipService.findAccount(oid);
  }

}
