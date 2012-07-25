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
package mic.contacta.util;

import mic.organic.util.OrganicApplicationContext;

/**
 *
 * @author mic
 * @created Jun 27, 2009
 */
public class ContactaApplicationContext extends OrganicApplicationContext
{

  static
  {
    OrganicApplicationContext.DEFAULT_BOOTFILE = "classpath:/mic/contacta/contacta-webapp.spring.xml";
    OrganicApplicationContext.DEFAULT_BOOTFILE = "classpath:/mic/contacta/ptool.spring.xml";
    OrganicApplicationContext.DEFAULT_BOOTFILE = "classpath:/mic/contacta/contacta.spring.xml";
  }


  /*
   *
   */
  public ContactaApplicationContext()
  {
    super();
  }

}
