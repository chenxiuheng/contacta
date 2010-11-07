/* $Id: PhonebookAction.java 671 2010-07-22 22:06:44Z michele.bianchi $
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
import mic.contacta.webapp.AbstractContactaSmd;
import mic.organic.aaa.model.PersonModel;
import mic.organic.aaa.spi.AddressbookService;
import mic.organic.core.Model;
import mic.organic.gateway.AaaGateway;
import mic.organic.gateway.DatastoreJson;
import mic.organic.gateway.DefaultDatastoreJson;
import mic.organic.gateway.JsonException;
import mic.organic.json.PersonJson;


/**
 *
 * @author mic
 * @created Apr 16, 2008
 */
@Service("phonebookAction")
@Scope("request")
public class PhonebookAction extends AbstractContactaSmd<PersonJson>
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private AaaGateway aaaGateway;//PersonDao cocDao;
  @Autowired private AddressbookService addressbookService;

  private String ipAddr;
  private String mac;
  private String search;
  private List<PersonModel> personList;



  /*
   *
   */
  public PhonebookAction()
  {
    super();
  }



  /**
   * @return the ipAddr
   */
  public String getIpAddr()
  {
    return ipAddr;
  }



  /**
   * @param ipAddr the ipAddr to set
   */
  public void setIpAddr(String ipAddr)
  {
    this.ipAddr = ipAddr;
  }



  /**
   * @return the mac
   */
  public String getMac()
  {
    return mac;
  }



  /**
   * @param mac the mac to set
   */
  public void setMac(String mac)
  {
    this.mac = mac;
  }



  /**
   * @return the search
   */
  public String getSearch()
  {
    return search;
  }



  /**
   * @param search the search to set
   */
  public void setSearch(String search)
  {
    this.search = search;
  }



  /**
   * @return the personList
   */
  public List<PersonModel> getPersonList()
  {
    return personList;
  }



  /**
   *
   */
  public String search()
  {
    log().info("ipAddr={}, mac={}, search={}", new Object[] { ipAddr, mac, search });
    String displayName = search+"%";
    personList = addressbookService.findPersonLikeDisplayName(displayName);
    return SUCCESS;
  }



  /*
   * @see mic.miirc.webapp.JsonSmd#persist(mic.miirc.json.PhoneJson)
   */
  @SMDMethod
  @Override
  public PersonJson persist(PersonJson json) throws JsonException
  {
    try
    {
      return aaaGateway.personPersist(json);
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
      return aaaGateway.personRemove(ids);
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
  public PersonJson find(int id)
  {
    return aaaGateway.personFind(id);
  }


  /*
   * @see mic.miirc.webapp.JsonSmd#findAll()
   */
  @SMDMethod
  @Override
  public DatastoreJson<PersonJson> findAll()
  {
    List<PersonJson> jsonList = aaaGateway.personList();
    DatastoreJson<PersonJson> store = new DefaultDatastoreJson<PersonJson>(jsonList);
    return store;
  }


  /*
   * @see mic.contacta.webapp.AbstractContactaSmd#findModel(java.lang.Integer)
   */
  @Override
  public Model findModel(Integer oid)
  {
    return addressbookService.findPerson(oid);
  }

}
