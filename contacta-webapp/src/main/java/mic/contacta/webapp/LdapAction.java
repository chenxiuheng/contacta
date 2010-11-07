/* $Id: LdapAction.java 642 2010-05-30 09:15:27Z michele.bianchi $
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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.Preparable;

import static mic.organic.core.OrganicConstants.*;


/**
 *
 * @author mic
 * @created Jan 24, 2009
 */
@Service("ldapAction")
@Scope("prototype")
public class LdapAction implements Preparable
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  //@Autowired private ProvisioningService provisioningService;

  private String firstName = "";
  private String lastName = "";
  private List<String> personList;


  /**
   * @return the firstName
   */
  public String getFirstName()
  {
    return firstName;
  }


  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }


  /**
   * @return the lastName
   */
  public String getLastName()
  {
    return lastName;
  }


  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }


  /**
   * @return the personList
   */
  public List<String> getPersonList()
  {
    return personList;
  }


  /*
   * @see com.opensymphony.xwork2.Preparable#prepare()
   */
  @Override
  public void prepare() throws Exception
  {
    //super.prepare();
    //log().info("hashCode={}", this.hashCode());
  }


  /*
   *
   */
  public String form()
  {
    return SUCCESS;
  }


  /*
   *
   */
  public String query()
  {
    personList = new ArrayList<String>();
    for (int i = 0; i < 12; i++)
    {
      personList.add("person "+i);
    }
    return SUCCESS;
  }
}
