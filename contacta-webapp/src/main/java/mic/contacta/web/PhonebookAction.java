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
package mic.contacta.web;

import java.util.ArrayList;
import java.util.List;
import org.apache.struts2.json.annotations.SMDMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import mic.contacta.pmod.thomson.MenuItem;
import mic.contacta.server.ContactaConfiguration;
import mic.organic.aaa.model.PersonModel;
import mic.organic.aaa.spi.AddressbookService;
import mic.organic.aaa.web.AaaGateway;
import mic.organic.aaa.web.PersonJson;
import mic.organic.core.Model;
import mic.organic.gateway.DatastoreJson;
import mic.organic.gateway.DefaultDatastoreJson;
import mic.organic.gateway.JsonException;


/**
 * @author mic
 * @created Apr 16, 2008
 */
@Service("phonebookAction")
@Scope("request")
public class PhonebookAction extends AbstractContactaSmd<PersonJson>
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private AaaGateway aaaGateway;// PersonDao cocDao;
  @Autowired private AddressbookService addressbookService;
  @Autowired private ContactaConfiguration contactaConfiguration;

  private String ipAddr;
  private String mac;
  private String search;
  private Integer page;
  private List<PersonModel> personList;
  private List<MenuItem> menuList;


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
   * @return the page
   */
  public Integer getPage()
  {
    return page;
  }


  /**
   * @param page the page to set
   */
  public void setPage(Integer page)
  {
    this.page = page;
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
   * @return the menuList
   */
  public List<MenuItem> getMenuList()
  {
    return menuList;
  }


  /**
   *
   */
  public String search()
  {
    log().info("ipAddr={}, mac={}, search={}", new Object[] { ipAddr, mac, search });
    String displayName = search + "%";

    String result = "dir-"+SUCCESS;
    List<PersonModel> list = addressbookService.personLikeDisplayName(displayName);
    int s = list.size();
    if (page != null)
    {
      int x0 = Math.min(32*page, s);
      int x1 = Math.min(32*(page+1), s);
      list = list.subList(x0, x1);
      //log().info("displayName={}, page={}, size={} x0={}, x1={}, dx={}", new Object[] { displayName, page, s, x0, x1, list.size() });

      result = "dir-"+SUCCESS;
      personList = list;
    }
    else
    {
      if (s > 32)
      {
        result = "menu-"+SUCCESS;
        menuList = new ArrayList<MenuItem>();
        int pages = s/32 + 1;
        for (int i = 0; i < pages; i++)
        {
          String url = "http://"+contactaConfiguration.getServerHost()+":"+contactaConfiguration.getServerPort()+"/o/phonebook-search.action";
          url += "?ipAddr="+ipAddr+"&amp;mac="+mac+"&amp;search="+search+"&amp;page=";

          int x0 = Math.min(32*i, s);
          int x1 = Math.min(32*(i+1), s);
          String label = list.get(x0).getDisplayName()+" - "+list.get(x1-1).getDisplayName();
          menuList.add(new MenuItem(label, url+i));
        }
      }
      else
      {
        result = "dir-"+SUCCESS;
        personList = list;
      }
    }
    return result;
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
   * @see mic.contacta.web.AbstractContactaSmd#findModel(java.lang.Integer)
   */
  @Override
  public Model findModel(Integer oid)
  {
    return addressbookService.personFind(oid);
  }

}
