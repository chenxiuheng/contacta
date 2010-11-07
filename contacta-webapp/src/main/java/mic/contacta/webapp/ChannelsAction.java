/* $Id: ChannelsAction.java 638 2010-05-16 21:38:53Z michele.bianchi $
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
package mic.contacta.webapp;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mic.contacta.server.api.ContactaException;
import mic.contacta.server.api.Line;
import mic.contacta.server.spi.ContactaService;


/**
 *
 * @author mic
 * @created Apr 16, 2008
 */
@Service("channelsAction")
@Scope("prototype")
public class ChannelsAction extends AbstractContactaAction
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private ContactaService contactaService;

  private List<Line> lineList;
  //private List<Call> callList;
  private String result;


  /**
   * @return the result
   */
  public String getResult()
  {
    return result;
  }


  /**
   * @return the callList
   */
  public List<Line> getLineList()
  {
    return lineList;
  }


  /**
   * @return the callList
   *
  public List<Call> getCallList()
  {
    return callList;
  }*/


  /**
   * just the default method, doing nothing
   */
  @Override
  public String execute()
  {
    try
    {
      lineList = contactaService.lineStatus();
    }
    catch (ContactaException e)
    {
      log().warn(e.getMessage());
      result = "non riesco a comunicare col centralino: "+e.getMessage();
      return ERROR;
    }
    return SUCCESS;
  }

}
