/* $Id: MainAction.java 672 2010-07-24 22:18:23Z michele.bianchi $
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
package mic.contacta.webapp;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import mic.contacta.model.PbxContextModel;
import mic.contacta.model.PbxProfileModel;
import mic.contacta.model.SipAccountModel;
import mic.contacta.server.dao.PbxContextDao;
import mic.contacta.server.dao.PbxProfileDao;
import mic.contacta.server.dao.SipAccountDao;
import mic.contacta.server.spi.ContactaConfiguration;
import mic.organic.aaa.ldap.PersonDao;


/**
 *
 * @author mic
 * @created Apr 16, 2008
 */
@Service("mainAction")
@Scope("prototype")
public class MainAction extends AbstractContactaAction
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private PersonDao personDao;
  @Autowired private SipAccountDao sipAccountDao;
  @Autowired private ContactaConfiguration contactaConfiguration;
  @Autowired private PbxContextDao pbxContextDao;
  @Autowired private PbxProfileDao pbxProfileDao;
//  private List<PbxContextModel> contextList;
//  private List<PbxProfileModel> profileList;


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


  /**
   * just the default method, doing nothing
   */
  @Override
  public String execute()
  {
    return login();
  }


  /**
   *
   */
  public String login()
  {
    if (contactaSession == null)
    {
      log().warn("no session at all");
    }
    else
    {
      if (contactaSession.getAccount() == null)
      {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && StringUtils.isNotBlank(authentication.getName()))
        {
          log().info("authentication.name={}", authentication.getName());

          SipAccountModel account = sipAccountDao.findByLogin(authentication.getName());
          contactaSession.setAccount(account);
          contactaSession.refreshAuthenticationContext();
        }
        contactaSession.setWeekN(0);
      }
    }
    return SUCCESS;
  }

}
