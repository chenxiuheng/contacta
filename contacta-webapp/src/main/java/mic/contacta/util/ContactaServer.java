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
package mic.contacta.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 *
 *
 * @author mic
 * @created Jan 20, 2008
 */
public class ContactaServer
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  private ApplicationContext applicationContext;


  /**
   * @return the applicationContext
   */
  public void start()
  {
    if (applicationContext == null)
    {
      try
      {
        String resourceName = "/contacta-standalone.spring.xml";
        applicationContext = new ClassPathXmlApplicationContext(new String[] { resourceName });
        //this.wait();
        log().info("started");
      }
      catch (Throwable e)
      {
        log().error("Cannot init spring module configuration in some way", e);
      }
    }
  }


  /**
   * @param args
   */
  public static void main(String[] args)
  {
    ContactaServer contactaServer = new ContactaServer();
    contactaServer.start();
  }
}
