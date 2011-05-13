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
package mic.contacta.domain;

import javax.validation.constraints.NotNull;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Index;
import mic.contacta.util.ContactaUtils;


/**
 *
 * @author mic
 * @created Feb 3, 2009
 */
@Entity
@Table(name="cococ")
@org.hibernate.annotations.Table(appliesTo = "cococ", indexes = { @Index(name="cococ_code_idx", columnNames = { "code" } ) } )
public class CocModel extends AbstractJsonModel
{
  public static final String [] cocExcludes = { };

  private String pin;


  /*
   *
   */
  @Deprecated
  public CocModel()
  {
    super(null);
    this.setExcludes(cocExcludes);
  }


  /*
   *
   */
  public CocModel(String login, String pin)
  {
    this();
    setCode(login);
    this.pin = pin;
  }


  /**
   * @return the login
   */
  @Transient
  public String getLogin()
  {
    return getCode();
  }


  /**
   * @param login the login to set
   */
  public void setLogin(String login)
  {
    setCode(login);
  }


  /**
   * @return the pin
   */
  @Basic(optional=false)
  @Column(nullable=false,length=8)
  @NotNull
  public String getPin()
  {
    return pin;
  }


  /**
   * @param pin the pin to set
   */
  public void setPin(String pin)
  {
    this.pin = ContactaUtils.passwordToPin(pin);
  }

}
