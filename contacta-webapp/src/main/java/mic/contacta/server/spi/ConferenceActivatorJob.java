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
package mic.contacta.server.spi;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * http://timezra.blogspot.com/2009/03/spring-30-quartz-batch-with-maven2.html
 * http://mmmsoftware.blogspot.com/2008/12/integrating-quartz-and-spring-using.html
 *
 * @author mic
 * @created Jun 11, 2009
 */
public class ConferenceActivatorJob extends SpringBeanJobFactory implements Job
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  private CalendarService calendarService;


  /**
   * @param calendarService the calendarService to set
   */
  public void setCalendarService(CalendarService calendarService)
  {
    this.calendarService = calendarService;
  }


  /*
   * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
   */
  @Transactional(propagation=Propagation.REQUIRED)
  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException
  {
    log().debug("Previous Fire Time: {}", context.getPreviousFireTime());
    log().debug("Current Fire Time: {}", context.getFireTime());
    log().debug("Next Fire Time: {}", context.getNextFireTime());

    Map<String,String> properties = context.getMergedJobDataMap();
    calendarService.activateConferences(properties);
  }

}
