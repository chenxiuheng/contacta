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
package mic.contacta.model;

import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import org.hibernate.envers.Audited;

/**
 *
 * @author mic
 * @created May 30, 2010
 */
@Entity
@Table(name = "pxprf")
//@org.hibernate.annotations.Table(appliesTo = "pxprf", indexes = { @Index(name="pxprf_code_idx", columnNames = { "code" } ) } )
@Audited
public class PbxProfileModel extends AbstractJsonModel
{
  public static final String PROFILE_1 = "dial";
  public static final String PROFILE_COVERAGE = "coverage";
  /*
  public static final String PROFILE_2 = "Dial + segreteria";
  public static final String PROFILE_3 = "Dial a cascata";
  public static final String PROFILE_4 = "Segretaria4";
  public static final String PROFILE_5 = "Direttore";
  public static final String PROFILE_6 = "Nullo";
  public static final String PROFILE_7 = "Dial a cascata + segreteria";
  public static final String PROFILE_8 = "Segretaria";
  public static final String[] PROFILES = { PROFILE_1, PROFILE_2, PROFILE_3, PROFILE_4, PROFILE_5, PROFILE_6, PROFILE_7, PROFILE_8 };
  */

  private String label;
  private String command;
  private boolean hasOptions;
  private String macro;
  private Timestamp version;


  /*
   * let use it only by jpa
   */
  public PbxProfileModel()
  {
    super(null);
  }


  /*
   * TODO envers seems not to like it very much, overriderd in .orm.xml
   *
  @Basic(optional=false)
  @Column(nullable=false, unique=false)
  @Override
  public String getCode()
  {
    return super.getCode();
  }*/


  /**
   * @return the version
   */
  @Version
  public Timestamp getVersion()
  {
    return version;
  }


  /**
   * @param version the version to set
   */
  public void setVersion(Timestamp version)
  {
    this.version = version;
  }


  /**
   * @param label the label to set
   */
  @Basic(optional=false)
  @Column(nullable=false,length=64)
  public void setLabel(String label)
  {
    this.label = label;
  }


  /**
   * @return the label
   */
  public String getLabel()
  {
    return label;
  }


  /**
   * @return the command
   */
  @Basic(optional=false)
  @Column(nullable=false,length=1024)
  public String getCommand()
  {
    return command;
  }


  /**
   * @param command the command to set
   */
  public void setCommand(String command)
  {
    this.command = command;
  }


  /**
   * @return the hasOptions
   */
  @Basic(optional=false)
  @Column(nullable=false)
  public boolean getHasOptions()
  {
    return hasOptions;
  }


  /**
   * @param hasOptions the hasOptions to set
   */
  public void setHasOptions(boolean hasOptions)
  {
    this.hasOptions = hasOptions;
  }


  /**
   * @return the macro
   */
  @Basic(optional=false)
  @Column(nullable=false,columnDefinition="text")
  public String getMacro()
  {
    return macro;
  }


  /**
   * @param macro the macro to set
   */
  public void setMacro(String macro)
  {
    this.macro = macro;
  }


}
