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
package mic.contacta.server.api;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * Channel:Context:Exten:Priority:Stats:Application:Data:CallerID:Accountcode:Amaflags:Duration:Bridged
 *
 * @author mic
 * @created Jan 28, 2009
 */
public class ChannelStatusLine
{
  public static final String [] channelStatusLineExcludes = { "location" };

  private String channel;
  private String context;
  private String exten;
  private String priority;
  private String state;
  private String application;
  private String data;
  private String callerId;
  private String accountCode;
  private String amaFlags;
  private String duration;
  private String bridged;

  private String location;



  /*
   *
   */
  @Override
  public String toString()
  {
    ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames(channelStatusLineExcludes);
    return builder.toString();
  }


  /**
   *
   * @param line
   * @param old TODO
   */
  public ChannelStatusLine(String line)
  {
    parseShowChannelConcise(line);
  }


  /**
   *
   * @param line
   * @param old TODO
   */
  public ChannelStatusLine(String line, boolean old)
  {
    parseShowChannel(line);
  }


  /**
   * show channels concise
   *
   * 11 call 8001 (answered)
   *
   * SIP/8001-094de4d8!co_outbound!!1!Up!Bridged Call!SIP/11-0932e120!8001!!3!6!SIP/11-0932e120
   * SIP/11-0932e120!macro-stdexten_nos!s!2!Up!Dial!SIP/8001|40|Ttfr!11!!3!6!SIP/8001-094de4d8
   *
   * caller:
   * 0 - SIP/11-0932e120
   * 1 - macro-stdexten_nos
   * 2 - s
   * 3 - 2
   * 4 - Up
   * 5 - Dial
   * 6 - SIP/8001|40|Ttfr
   * 7 - 11
   * 8 -
   * 9 - 3
   *10 - 6
   *11 - SIP/8001-094de4d8
   *
   * callee:
   * 0 - SIP/8001-094de4d8
   * 1 - co_outbound
   * 2 -
   * 3 - 1
   * 4 - Up
   * 5 - Bridged Call
   * 6 - SIP/11-0932e120
   * 7 - 8001
   * 8 -
   * 9 - 3
   *10 - 6
   *11 - SIP/11-0932e120
   *
   *
   * 11 call 8001 (ringing):
   *   SIP/11-0932cb90!macro-stdexten_nos!s!2!Ring!Dial!SIP/8001|40|Ttfr!11!!3!4!(None)
   *   SIP/8001-0932e3c0!co_outbound!8001!1!Ringing!AppDial!(Outgoing Line)!8001!!3!4!(None)
   *
   * caller:
   * 0 - SIP/11-0932cb90
   * 1 - macro-stdexten_nos
   * 2 - s
   * 3 - 2
   * 4 - Ring
   * 5 - Dial
   * 6 - SIP/8001|40|Ttfr
   * 7 - 11
   * 8 - 3
   * 9 - 4
   *10 - (None)
   *11 -
   *
   * callee:
   * 0 - SIP/8001-0932e3c0
   * 1 - co_outbound
   * 2 - 8001
   * 3 - 1
   * 4 - Ringing
   * 5 - AppDial
   * 6 - (Outgoing Line)
   * 7 - 8001
   * 8 - 3
   * 9 - 4
   *10 - (None)
   *11 -
   *
   *
   * Zap rings SIP/50:
   *   Zap/1-1!macro-co_dial_inbound!s!1!Ring!Dial!SIP/50|30|Ttfr!!!3!5!(None)
   *   SIP/50-094fd240!co_outbound!s!1!Ringing!AppDial!(Outgoing Line)!s!!3!1!(None)
   *
   * Zap talks SIP/50
   *   Zap/1-1!macro-co_dial_inbound!s!1!Up!Dial!SIP/50|30|Ttfr!!!3!11!SIP/50-094fd240
   *   SIP/50-094fd240!co_outbound!!1!Up!Bridged Call!Zap/1-1!s!!3!6!Zap/1-1
   */
  public void parseShowChannelConcise(String line)
  {
    String[] ss = line.split("!");
    channel = ss[0];
    context = ss[1];
    exten = ss[2];
    priority = ss[3];
    state = ss[4];
    application = ss[5];
    data = ss[6];
    callerId = ss[7];
    accountCode = ss[8];
    amaFlags = ss[9];
    duration = ss[10];
    bridged = ss[11];
  }


  /*
   * show channels
   *
   * 11 call 8001 (answered)
   *
   * Channel              Location             State   Application(Data)
   * SIP/8001-094de4d8    (None)               Up      Bridged Call(SIP/11-0932e120)
   * SIP/11-0932e120      s@macro-stdexten_nos Up      Dial(SIP/8001|40|Ttfr)
   * 2 active channels
   * 1 active call
   *
   *
   * 11 call 8001 (ringing):
   *
   * Channel              Location             State   Application(Data)
   * SIP/8001-0932e3c0    8001@co_outbound:1   Ringing AppDial((Outgoing Line))
   * SIP/11-0932cb90      s@macro-stdexten_nos Ring    Dial(SIP/8001|40|Ttfr)
   * 2 active channels
   * 1 active call
   *
   */
  public void parseShowChannel(String line)
  {
    String[] ss = line.split(" +");
    /*for (String s : ss)
    {
      log().info("ss: {}", s);
    }*/
    channel = ss[0];
    location = ss[1];
    state = ss[2];
    application = "";
    for (int i = 3; i < ss.length; i++)
    {
      application += ss[i]+" ";
    }
    application = application.trim();

    if (StringUtils.startsWith(location, "s@") == false)
    {
      int i = location.indexOf('@');
      int j = location.indexOf(':');
      if (i > -1 && j > -1)
      {
        exten = location.substring(0, i);
        context = location.substring(i+1, j);
      }
    }
  }


  /**
   * @return the channel
   */
  public String getChannel()
  {
    return channel;
  }


  /**
   * @param channel the channel to set
   */
  public void setChannel(String channel)
  {
    this.channel = channel;
  }


  /**
   * @return the location
   */
  public String getLocation()
  {
    return location;
  }


  /**
   * @param location the location to set
   */
  public void setLocation(String location)
  {
    this.location = location;
  }


  /**
   * Ring, Ringing, Up, ...
   *
   * @return the state
   */
  public String getState()
  {
    return state;
  }


  /**
   * @param state the state to set
   */
  public void setState(String state)
  {
    this.state = state;
  }


  /**
   * @return the application
   */
  public String getApplication()
  {
    return application;
  }


  /**
   * Dial, AppDial, Bridged Call, ...
   *
   * @param application the application to set
   */
  public void setApplication(String application)
  {
    this.application = application;
  }


  /**
   * @return the exten
   */
  public String getExten()
  {
    return exten;
  }


  /**
   * @param exten the exten to set
   */
  public void setExten(String exten)
  {
    this.exten = exten;
  }


  /**
   * @return the context
   */
  public String getContext()
  {
    return context;
  }


  /**
   * @param context the context to set
   */
  public void setContext(String context)
  {
    this.context = context;
  }


  /**
   * @return the priority
   */
  public String getPriority()
  {
    return priority;
  }


  /**
   * @param priority the priority to set
   */
  public void setPriority(String priority)
  {
    this.priority = priority;
  }


  /**
   * @return the data
   */
  public String getData()
  {
    return data;
  }


  /**
   * @param data the data to set
   */
  public void setData(String data)
  {
    this.data = data;
  }


  /**
   * @return the callerId
   */
  public String getCallerId()
  {
    return callerId;
  }


  /**
   * @param callerId the callerId to set
   */
  public void setCallerId(String callerId)
  {
    this.callerId = callerId;
  }


  /**
   * @return the accountCode
   */
  public String getAccountCode()
  {
    return accountCode;
  }


  /**
   * @param accountCode the accountCode to set
   */
  public void setAccountCode(String accountCode)
  {
    this.accountCode = accountCode;
  }


  /**
   * @return the amaFlags
   */
  public String getAmaFlags()
  {
    return amaFlags;
  }


  /**
   * @param amaFlags the amaFlags to set
   */
  public void setAmaFlags(String amaFlags)
  {
    this.amaFlags = amaFlags;
  }


  /**
   * @return the duration
   */
  public String getDuration()
  {
    return duration;
  }


  /**
   * @param duration the duration to set
   */
  public void setDuration(String duration)
  {
    this.duration = duration;
  }


  /**
   * @return the bridged
   */
  public String getBridged()
  {
    return bridged;
  }


  /**
   * @param bridged the bridged to set
   */
  public void setBridged(String bridged)
  {
    this.bridged = bridged;
  }


  /**
   * @return the channelStatusLineExcludes
   */
  public static String[] getChannelStatusLineExcludes()
  {
    return channelStatusLineExcludes;
  }

}

