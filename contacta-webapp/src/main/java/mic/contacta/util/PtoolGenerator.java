/* $Id$
 *
 * Contacta, http://openinnovation.it - roberto grasso, michele bianchi
 * Copyright(C) 1998-2009 [michele.bianchi@gmail.com]
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import mic.contacta.server.api.ContactaConstants;
import mic.contacta.server.spi.ContactaService;
import mic.organic.core.OrganicSpringHelper;


/**
 * Startup a standalone server, hopefully listening on rmi port
 *
 * @author mic
 * @created Dec 15, 2008
 */
public class PtoolGenerator
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  private ApplicationContext applicationContext;
  //private ProvisioningService provisioningService;
  @Autowired private ContactaService contactaService;


  /*
   *
   */
  public void start(String... resourceNames)
  {
    if (applicationContext == null)
    {
      try
      {
        applicationContext = new ClassPathXmlApplicationContext(resourceNames);
        OrganicSpringHelper.setApplicationContext(applicationContext);
        //this.wait();
        //provisioningService = ContactaHelper.getPtoolService();

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
  public void generate()
  {
    start(ContactaConstants.PTOOL_STANDALONE_SPRING_XML);

    contactaService.importFromCsv();
    //provisioningService.generate();
  }


  /*
   *
   */
  public static void main(String[] args) throws IOException
  {
    Properties props = new Properties();
    InputStream stream = PtoolGenerator.class.getResourceAsStream(ContactaConstants.PTOOL_STANDALONE_LOG4J_PROPERTIES);
    props.load(stream);
    // BasicConfigurator replaced with PropertyConfigurator.
    PropertyConfigurator.configure(props);

    PtoolGenerator ptoolGenerator = new PtoolGenerator();
    ptoolGenerator.generate();
  }
}
