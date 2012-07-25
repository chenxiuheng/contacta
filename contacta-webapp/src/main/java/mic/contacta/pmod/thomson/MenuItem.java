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
package mic.contacta.pmod.thomson;


/**
 *
 *
 * @author mic
 * @created Dec 19, 2010
 */
public class MenuItem
{
 private String name;
 private String url;


 /**
  *
  */
 public MenuItem()
 {
   super();
 }


 /**
  * @param name
  * @param url
  */
 public MenuItem(String name, String url)
 {
   this();

   this.name = name;
   this.url = url;
 }


 /**
  * @return the name
  */
 public String getName()
 {
   return name;
 }


 /**
  * @param name the name to set
  */
 public void setName(String name)
 {
   this.name = name;
 }


 /**
  * @return the url
  */
 public String getUrl()
 {
   return url;
 }


 /**
  * @param url the url to set
  */
 public void setUrl(String url)
 {
   this.url = url;
 }

}


