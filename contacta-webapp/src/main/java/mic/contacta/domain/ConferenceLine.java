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


/**
 * Describe a conference
 *
 * @author mic
 * @created Jun 10, 2009
 */
public class ConferenceLine
{
  private String exten;
  private String description;
  private String color;
  private boolean active;
  private String owner;



  /**
   * @param exten
   */
  public ConferenceLine(String exten)
  {
    super();
    this.exten = exten;
  }


  /**
   * @return the exten
   */
  public String getExten()
  {
    return exten;
  }


  /**
   * @param exten the exten to set
   */
  public void setExten(String exten)
  {
    this.exten = exten;
  }


  /**
   * @return the description
   */
  public String getDescription()
  {
    return description;
  }


  /**
   * @param description the description to set
   */
  public void setDescription(String description)
  {
    this.description = description;
  }


  /**
   * @return the color
   */
  public String getColor()
  {
    return color;
  }


  /**
   * @param color the color to set
   */
  public void setColor(String color)
  {
    this.color = color;
  }


  /**
   * @return the active
   */
  public boolean getActive()
  {
    return active;
  }


  /**
   * @param active the active to set
   */
  public void setActive(boolean active)
  {
    this.active = active;
  }


  /**
   * @return the owner
   */
  public String getOwner()
  {
    return owner;
  }


  /**
   * @param owner the owner to set
   */
  public void setOwner(String owner)
  {
    this.owner = owner;
  }
}
