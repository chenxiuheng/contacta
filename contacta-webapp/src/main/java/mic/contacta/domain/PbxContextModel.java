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

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Index;


/**
 *
 * @author mic
 * @created May 30, 2010
 */
@Entity
@Table(name = "pxctx")
@org.hibernate.annotations.Table(appliesTo = "pxctx", indexes = { @Index(name="pxctx_code_idx", columnNames = { "code" } ) } )
public class PbxContextModel extends AbstractJsonModel
{
  private String label;


  /*
   * let use it only by jpa
   */
  public PbxContextModel()
  {
    super(null);
  }


  /**
   * @param label the label to set
   */
  @Basic
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


}
