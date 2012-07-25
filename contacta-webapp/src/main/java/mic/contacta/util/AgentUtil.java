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

import java.io.*;


/**
 * This class implements...
 *

 * @author michele.bianchi@gmail.com
 * @version $Revision: 621 $
 */
public class AgentUtil
{

  /*
   *
   */
  public static String percent(long l, long totalTime)
  {
    double d = (l/(double)totalTime);
    return String.valueOf(((int)(d*1000)));
  }


  /*
   *
   */
  public static long parseLong(String value)
  {
    long i = -1;
    try
    {
      i = Long.parseLong(value);
    }
    catch(NumberFormatException e)
    {
    }
    return i;
  }


  /*
   *
   */
  public static int parseInt(String value)
  {
    int i = -1;
    try
    {
      i = Integer.parseInt(value);
    }
    catch(NumberFormatException e)
    {
    }
    return i;
  }


  /*
   *
   */
  public static float parseFloat(String value)
  {
    float i = -1;
    try
    {
      i = Float.parseFloat(value);
    }
    catch(NumberFormatException e)
    {
    }
    return i;
  }


  /*
   *
   */
  public static void printItemLong(PrintWriter writer) throws IOException
  {

  }


  /*
   *
   */
  public static void printItemShort(PrintWriter writer) throws IOException
  {
  }


  /*
   *
   *
  private String sendCommand(String command) throws IOException, TimeoutException
  {
    CommandAction commandAction = new CommandAction();
    commandAction.setCommand(command);

    CommandResponse commandResponse = (CommandResponse)(managerConnection.sendAction(commandAction, 1000));

    StringBuffer sb = new StringBuffer();
    sb.append("actionId: "+commandResponse.getActionId());
    sb.append("\nuniqueId: "+commandResponse.getUniqueId());
    sb.append("\nmessage: "+commandResponse.getMessage());
    sb.append("\nresponse: "+commandResponse.getResponse());
    Map attrs = commandResponse.getAttributes();
    for (Object o : attrs.keySet())
    {
      sb.append("\nattribute: "+o.toString()+"="+attrs.get(o).toString());
    }
    for (Object o : commandResponse.getResult())
    {
      sb.append("\nresult: "+o.toString());
    }
    sb.append("\n\n");
    return sb.toString();
  }*/


  /*
   *
   *
  public String sendEventGenerating(EventGeneratingAction action) throws IOException, TimeoutException
  {
    ResponseEvents responseEvents = managerConnection.sendEventGeneratingAction(action);

    StringBuffer sb = new StringBuffer();
    sb.append(responseEvents.getResponse().toString());
    for (Object o : responseEvents.getEvents())
    {
      sb.append("\nevent: ["+o.getClass().getName()+"] "+o.toString());
    }
    sb.append("\n\n");
    return sb.toString();
  }*/


  /*
   *
   *
  public void measureAsterisk(StatisticsBean statistics) throws IOException
  {
    String commandResult = "cannot access to asterisk";
    if (managerConnection == null)
    {
      return commandResult;
    }
    try
    {
      commandResult = this.sendCommand("show channels");
      commandResult += this.sendEventGenerating(new SipPeersAction());
      commandResult += this.sendEventGenerating(new ZapShowChannelsAction());
    }
    catch(TimeoutException e)
    {
      log().warn(e.getMessage());
    }
    return commandResult;
  }*/
}

