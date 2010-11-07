/* $Id: CacheJmxAgent.java 37 2009-01-14 20:37:53Z michele.bianchi $
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

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.management.MBeanServer;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.management.ManagementService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * @author mic
 * @created Dec 8, 2008
 */
//@Service(CacheJmxAgent.SERVICE_NAME)
public class CacheJmxAgent
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  public static final String SERVICE_NAME = "jmxAgent";

  @Autowired MBeanServer mbeanServer;
  @Resource(name="cacheManager") private CacheManager cacheManager;


  /*
   *
   */
  public CacheJmxAgent()
  {
    super();
  }


  /*
   *
   */
  @PostConstruct
  public void init() throws Exception
  {
    // Enable Ehcache JMX Statistics
    ManagementService.registerMBeans(cacheManager, mbeanServer, true, true, true, true);
  }

}
