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
package mic.contacta.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Index;
import mic.organic.core.AbstractCodeModel;

/**
 *
 * @author mic
 * @created May 30, 2010
 */
@Entity
@Table(name = "coclg")
@org.hibernate.annotations.Table(appliesTo = "coclg", indexes = { @Index(name="coclg_code_idx", columnNames = { "code" } ) } )
public class PbxCallgroup extends AbstractCodeModel
{
  private String label;


  /*
   * let use it only by jpa
   */
  @Deprecated
  public PbxCallgroup()
  {
    super(null);
  }


}
