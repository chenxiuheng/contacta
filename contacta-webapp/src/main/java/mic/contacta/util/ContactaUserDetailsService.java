/* $Id: ContactaUserDetailsService.java 664 2010-07-18 18:47:19Z michele.bianchi $
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
package mic.contacta.util;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import mic.contacta.model.VoicemailModel;
import mic.contacta.server.dao.SipAccountDao;
import mic.contacta.server.dao.VoicemailDao;
import mic.organic.aaa.model.AccountModel;
import mic.organic.aaa.model.Role;
import mic.organic.aaa.spi.OrganicUserDetailsService;


/**
 * Retrieves user details (username, password, enabled flag, and authorities) from a JDBC location.
 * <p>
 * A default database structure is assumed, (see {@link #DEF_USERS_BY_USERNAME_QUERY} and
 * {@link #DEF_AUTHORITIES_BY_USERNAME_QUERY}, which most users of this class will need to override, if using an
 * existing scheme. This may be done by setting the default query strings used.
 * <p>
 * In order to minimise backward compatibility issues, this DAO does not recognise the expiration of user accounts or
 * the expiration of user credentials. However, it does recognise and honour the user enabled/disabled column.
 * <p>
 * Support for group-based authorities can be enabled by setting the <tt>enableGroups</tt> property to <tt>true</tt>
 * (you may also then wish to set <tt>enableAuthorities</tt> to <tt>false</tt> to disable loading of authorities
 * directly). With this approach, authorities are allocated to groups and a user's authorities are determined based on
 * the groups they are a member of. The net result is the same (a UserDetails containing a set of
 * <tt>GrantedAuthority</tt>s is loaded), but the different persistence strategy may be more suitable for the
 * administration of some applications.
 *
 * @author michele bianchi
 * @version $Id: ContactaUserDetailsService.java 664 2010-07-18 18:47:19Z michele.bianchi $
 */
public class ContactaUserDetailsService extends OrganicUserDetailsService
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

  private UserDetailsBackend backend = UserDetailsBackend.None;

  @Autowired private SipAccountDao accountDao;
  @Autowired private VoicemailDao voicemailDao;


  /*
   *
   */
  public ContactaUserDetailsService()
  {
    super();

    addAdminAuthority(Role.ROLE_VOICEMAIL.toString());
  }


  /**
   * @return the backend
   */
  public UserDetailsBackend getBackend()
  {
    return backend;
  }


  /**
   * @param backend the backend to set
   */
  public void setBackend(UserDetailsBackend backend)
  {
    this.backend = backend;
  }


  /*
   * @see org.springframework.security.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
   */
  @Transactional(readOnly=true)
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
  {
    UserDetails user = checkAdmin(username);
    if (user == null)
    {
      List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
      switch(backend)
      {
      case SipAccount:
        AccountModel account = accountDao.findByLogin(username);
        if (account == null)
        {
          throw new UsernameNotFoundException(messages.getMessage("ContactaUserDetailsService.notFound", new Object[] { username }, "Username {0} not found"), username);
        }

        authorities.add(new GrantedAuthorityImpl(Role.ROLE_VOICEMAIL.toString()));
        user = new User(account.getLogin(), account.getPassword(), true, true, true, true, authorities);
        break;

      case VoiceMail:
        VoicemailModel voicemail = voicemailDao.findByLogin(username);
        if (voicemail == null)
        {
          throw new UsernameNotFoundException(messages.getMessage("ContactaUserDetailsService.notFound", new Object[] { username }, "Username {0} not found"), username);
        }

        authorities.add(new GrantedAuthorityImpl(Role.ROLE_VOICEMAIL.toString()));
        user = new User(voicemail.getMailbox(), voicemail.getPin(), true, true, true, true, authorities);
        break;

      case None:
        log().info("none backend selected");
        break;

      default:
        log().warn("unknown backend: {}", backend);
      }
    }
    return user;
  }

}
