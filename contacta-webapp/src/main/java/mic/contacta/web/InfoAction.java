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
package mic.contacta.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.Preparable;

import static mic.organic.core.OrganicConstants.*;

import mic.contacta.server.ContactaConfiguration;
import mic.organic.util.Stats;


/**
 * this is a spring built action... see struts.xml+spring.xml
 * TODO create an annotation/or a abstractclass
 *
 * @author mic
 * @created Apr 16, 2008
 */
@Service("infoAction")
@Scope("request")
public class InfoAction implements Preparable
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  //private StatisticsCollector statisticsCollector;
  @Autowired private ContactaConfiguration configuration;


  /*
   * @see com.opensymphony.xwork2.Preparable#prepare()
   */
  @Override
  public void prepare() throws Exception
  {
    //super.prepare();
    //statisticsCollector = QuaeroHelper.getStatisticsCollector();
  }


  /*
   *
   */
  public ContactaConfiguration getConfiguration()
  {
    return configuration;
  }


  /*
   *
   */
  public Runtime getRuntime()
  {
    return Runtime.getRuntime();
  }


  /*
   *
   */
  @SuppressWarnings("unchecked")
  public Map getSystemProperties()
  {
    Properties properties = System.getProperties();
    return properties;
  }


  /*
   *
   */
  public Map<String, Stats> getStatsMap()
  {
    return new HashMap<String, Stats>();//statisticsCollector.getStatsMap();
  }


  /*
   *
   */
  public String execute()
  {
    return SUCCESS;
  }

}
