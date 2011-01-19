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
package mic.contacta.server.api;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;


/**
 *
 * @author mic
 * @created Jul 22, 2008
 */
public class LineComparator implements Comparator<Line>
{
  /*
   *  @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
   */
  @Override
  public int compare(Line o1, Line o2)
  {
    int r = new CompareToBuilder()
    //.appendSuper(super.compareTo(o)
    .append(o1.getExten(), o2.getExten())
    .toComparison();
    return r;
  }
}
