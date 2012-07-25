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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import mic.contacta.server.ContactaConstants;


/**
 * Startup a standalone server, hopefully listening on rmi port
 *
 * @author mic
 * @created Dec 15, 2008
 */
public class PtoolServer
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  private ApplicationContext applicationContext;


  /*
   *
   */
  public void start(String[] resourceNames)
  {
    if (applicationContext == null)
    {
      try
      {
        applicationContext = new ClassPathXmlApplicationContext(resourceNames);
        //this.wait();
        log().info("started");
      }
      catch (Throwable e)
      {
        log().error("Cannot init spring module configuration in some way", e);
      }
    }
  }


  /*
   *
   */
  public static void main(String[] args) throws IOException
  {
    Properties props = new Properties();
    InputStream stream = PtoolServer.class.getResourceAsStream(ContactaConstants.PTOOL_STANDALONE_LOG4J_PROPERTIES);
    props.load(stream);
    // BasicConfigurator replaced with PropertyConfigurator.
    PropertyConfigurator.configure(props);

    String[] resourceNames =
    {
        ContactaConstants.PTOOL_REMOTING_SPRING_XML,
        //ContactaConstants.PTOOL_AOP_SPRING_XML,
        ContactaConstants.PTOOL_STANDALONE_SPRING_XML
    };

    PtoolServer ptoolServer = new PtoolServer();
    ptoolServer.start(resourceNames);
  }
}