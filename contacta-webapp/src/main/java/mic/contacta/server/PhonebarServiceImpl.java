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

import org.apache.commons.lang.StringUtils;
import org.asteriskjava.manager.action.OriginateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mic.contacta.asterisk.spi.AsteriskService;
import mic.contacta.dao.SipUserDao;
import mic.contacta.domain.ChannelStatusLine;
import mic.contacta.domain.SipAccountModel;
import mic.contacta.domain.SipUserModel;
import mic.organic.aaa.ldap.PersonDao;


/**
 *
 *
 *
 *
 * @author         michele.bianchi@gmail.com
 * @version        $Revision: 660 $
 */
@Service(PhonebarService.SERVICE_NAME)
public class PhonebarServiceImpl implements PhonebarService
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private SipUserDao sipUserDao;
  @Autowired private AsteriskService asteriskService;

  @Autowired private PersonDao personDao;


  /**
   *
   */
  public PhonebarServiceImpl()
  {
    super();
  }


  /*
   *
   */
  private void redirect(String fromExten, String toExten, String toContext) throws ContactaException
  {
    ChannelStatusLine csl = asteriskService.findPeerChannel(fromExten);
    if (csl != null)
    {
      asteriskService.redirect(csl.getChannel(), toExten, toContext, 1);
    }
    else
    {
      log().info("no active channel for caller extension {}", fromExten);
    }
  }


  /*
   *
   */
  private void redirect(String fromExten, String toExten) throws ContactaException
  {
    ChannelStatusLine csl = asteriskService.findPeerChannel(fromExten);
    log().info("channelStatusLine: {}", csl);
    if (csl != null)
    {
      log().info("{}, {}, fromE:{} toE:{}", new Object[] { csl.getChannel(), csl.getContext(), fromExten, toExten });
      asteriskService.redirect(csl.getChannel(), toExten, csl.getContext(), 1);
    }
    else
    {
      log().info("no active channel for caller extension {}", fromExten);
    }
  }


  /*
   * @see mic.contacta.web.PhonebarService#answer()
   */
  @Override
  public String answer(String exten)
  {
    String fromExten = exten;
    String toExten = exten;
    String toContext = "autoanswer";
    try
    {
      ChannelStatusLine csl = asteriskService.findRingingChannel(fromExten);
      if (csl != null)
      {
        asteriskService.redirect(csl.getChannel(), toExten, toContext, 1);
      }
      else
      {
        log().info("no active channel for caller extension {}", fromExten);
      }
    }
    catch (ContactaException e)
    {
      log().warn(e.getMessage(), e);
    }
    return null;
  }


  /*
  @Autowired private AuthenticationProvider authenticationProvider;

  public void ldapBind(String login, String password)
  {
    String dirRoot = "dc=organic,dc=lab";
    Hashtable<String, String> myenv = new Hashtable<String, String>();
    myenv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    myenv.put(Context.PROVIDER_URL, "ldap://10.0.0.2:389/"+dirRoot);
    myenv.put(Context.SECURITY_AUTHENTICATION, "Simple"); // Simple, SSL, SASL
    myenv.put(Context.SECURITY_PRINCIPAL, "uid="+login+",ou=people,"+dirRoot);
    myenv.put(Context.SECURITY_CREDENTIALS, password);

    try
    {
      InitialDirContext context = new InitialDirContext(myenv);
      SearchControls ctrl = new SearchControls();
      // Set up how we want to search
      ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
      // in this case the entire subtree under the specified directory root.

      log().info("LOOKING FOR {}/{}", login, password);
      NamingEnumeration<SearchResult> enumeration = context.search("", "uid=" + login, ctrl);
      if (enumeration.hasMore())
      {
      }
    }
    catch (Exception e)
    {
      log().info("CANNOT LOGIN {}", login, e);
      throw new RuntimeException("CANNOT LOGIN");
    }
  }*/


  /*
   * @see mic.contacta.web.PhonebarService#dial(java.lang.String)
   */
  @Transactional(readOnly=true)
  @Override
  @Deprecated
  public String dial(String login, String password, String exten)
  {
    log().info("login={}, exten={}", login, exten);

    /*
    ldapBind(login, password);

    if (StringUtils.isNotEmpty(password))
    {
      Authentication authentication = new UsernamePasswordAuthenticationToken(login, password);
      log().info("authentication={}", authentication);
      //AuthenticationProvider authenticationProvider = (AuthenticationProvider)(OrganicSpringHelper.getService("authenticationProvider"));
      authentication = authenticationProvider.authenticate(authentication);
      log().info("authentication={}", authentication);
    }
    */

    SipUserModel sipUser = sipUserDao.findByLogin(login);
    //Person person = personDao.findByCn(login);
    //log().warn("person={}", person);
    if (sipUser != null && StringUtils.equals(password, sipUser.getSecret()) == false)
    {
      return "wrong password";
    }

    /*SipUserModel sipUser = sipUserDao.findByLogin(person.getTelephoneNumber());
    if (sipUser == null)
    {
      log().warn("sipUser.login={} not found", person.getTelephoneNumber());
      return "sipUser.login="+person.getTelephoneNumber()+" not found";
    }*/

    OriginateAction originateAction = new OriginateAction();
    originateAction.setAsync(false);
    originateAction.setChannel("SIP/"+login/*person.getTelephoneNumber()*/);
    originateAction.setContext(sipUser.getContext());
    originateAction.setExten(exten);
    originateAction.setPriority(1);
    originateAction.setCallerId(sipUser.getCallerid());

    log().info("originate: siplogin={}, context={}", login/*person.getTelephoneNumber()*/, sipUser.getContext());
    try
    {
      asteriskService.sendCommand(originateAction);
    }
    catch (ContactaException e)
    {
      log().warn(e.getMessage(), e);
      return e.getMessage();
    }
    return "success";
  }


  /*
   * @see mic.contacta.web.PhonebarService#dial(java.lang.String)
   */
  @Transactional(readOnly=true)
  @Override
  public String dial(SipAccountModel sipAccount, String exten)
  {
    log().info("displayName={}, exten={}", sipAccount.getLabel(), exten);

    log().warn("========= USER REGISTERED CHECK IS TURNED OFF =========");
    SipUserModel sipUser = sipAccount.getSipUser();
    if (sipUser == null)
    {
      log().warn("sipUser.login={} not found", sipAccount.getLogin());
      return "sipUser.login="+sipAccount.getLogin()+" not found";
    }
    String context = sipUser.getContext();
    String callerId = sipUser.getCallerid();

    OriginateAction originateAction = new OriginateAction();
    originateAction.setAsync(false);
    originateAction.setChannel("SIP/"+sipAccount.getLogin());
    originateAction.setContext(context);
    originateAction.setExten(exten);
    originateAction.setPriority(1);
    originateAction.setCallerId(callerId);

    log().info("originate: siplogin={}, context={}", sipAccount.getLogin(), context);
    try
    {
      asteriskService.sendCommand(originateAction);
    }
    catch (ContactaException e)
    {
      log().warn(e.getMessage(), e);
      return e.getMessage();
    }
    return "success";
  }


  /*
   * @see mic.contacta.web.PhonebarService#transfer()
   */
  @Transactional(readOnly=true)
  @Override
  public String transfer(String fromExten, String toExten, String toContext)
  {
    try
    {
      redirect(fromExten, toExten, toContext);
    }
    catch (ContactaException e)
    {
      log().warn(e.getMessage(), e);
    }
    return null;
  }


  /*
   * @see mic.contacta.web.PhonebarService#hold()
   */
  @Override
  public String hold(String exten)
  {
    String fromExten = exten;
    String toExten = ContactaConstants.PARK_PREFIX+exten;
    String toContext = "nazionali";
    try
    {
      ChannelStatusLine csl = asteriskService.findPeerChannel(fromExten);
      if (csl != null)
      {

        asteriskService.redirect(csl.getChannel(), toExten, toContext, 1);
      }
      else
      {
        log().info("no active channel for caller extension {}", fromExten);
      }
    }
    catch (ContactaException e)
    {
      log().warn(e.getMessage(), e);
    }
    return null;
  }


  /*
   * @see mic.contacta.web.PhonebarService#unhold()
   */
  @Override
  public String unhold(String exten)
  {
    String fromExten = ContactaConstants.PARK_PREFIX+exten;
    String toExten = exten;
    String toContext = "nazionali";
    try
    {
      ChannelStatusLine csl = asteriskService.findParkChannel(fromExten);
      if (csl != null)
      {
        asteriskService.redirect(csl.getChannel(), toExten, toContext, 1);
      }
      else
      {
        log().info("no active channel for caller extension {}", fromExten);
      }
    }
    catch (ContactaException e)
    {
      log().warn(e.getMessage(), e);
    }
    return null;
  }

}
