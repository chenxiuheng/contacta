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
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import mic.contacta.dao.PbxContextDao;
import mic.contacta.dao.PbxProfileDao;
import mic.contacta.domain.PbxContextModel;
import mic.contacta.domain.PbxProfileModel;
import mic.contacta.server.ContactaConfiguration;
import mic.contacta.server.ContactaSession;
import mic.organic.web.OrganicLoginAction;


/**
 *
 * @author mic
 * @created Apr 16, 2008
 */
@Service("mainAction")
@Scope("request")
public class MainAction extends OrganicLoginAction
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired protected ContactaSession contactaSession;

  //@Autowired private mic.organic.aaa.ldap.PersonDao personDao;
  @Autowired private ContactaConfiguration contactaConfiguration;
  @Autowired private PbxContextDao pbxContextDao;
  @Autowired private PbxProfileDao pbxProfileDao;
//  private List<PbxContextModel> contextList;
//  private List<PbxProfileModel> profileList;
  //@Autowired private AccountService accountService;


  /**
   * @return the contactaConfiguration
   */
  public Map<String,String> getConfigurationAsList()
  {
    Map<String,String> map = new TreeMap<String, String>();
    try
    {
      Map<String,Object> props = PropertyUtils.describe(contactaConfiguration);
      for (String key : props.keySet())
      {
        Object o = props.get(key);
        log().debug("{}={}", key, o);
        map.put(key, o == null ? "null" : o.toString());
      }
    }
    catch (Exception e)
    {
      log().warn(e.getMessage(), e);
    }
    return map;
  }


  /**
   * @return the contextList
   */
  public List<PbxContextModel> getContextList()
  {
    return pbxContextDao.findAll();

  }


  /**
   * @return the profileList
   */
  public List<PbxProfileModel> getProfileList()
  {
    return pbxProfileDao.findAll();
  }

//
//  /**
//   * just the default method, doing nothing
//   */
//  @Override
//  public String execute()
//  {
//    return login();
//  }
//
//
//  /**
//   *
//   */
//  @Override
//  public String login()
//  {
//    ActionContext context = ActionContext.getContext();
//    Locale locale = context.getLocale();
//    contactaSession.setLocale(locale);
//
//    log().info("ActionContext: locale={}", locale);
//    log().info("ActionContext: name={}", context.getName());
//
//    String result = SUCCESS;
//    try
//    {
//      accountService.login(contactaSession);
//    }
//    catch (AccountExpiredException e)
//    {
//      log().info("account expired: {}", e.getLoginState());
//      result = e.getLoginState().toString();
//    }
//
//    return result;
//  }

}
