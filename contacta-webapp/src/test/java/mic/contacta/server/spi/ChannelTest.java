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
package mic.contacta.server.spi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import mic.contacta.server.api.Call;
import mic.contacta.server.api.ChannelStatusLine;
import mic.contacta.server.api.ContactaException;
import mic.contacta.server.api.Line;

/**
 *
 * @author mic
 * @created May 26, 2009
 */
public class ChannelTest
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  private static Map<String,String> accountMap;
  static
  {
    accountMap = new HashMap<String,String>();
    accountMap.put("11", "A <11>");
    accountMap.put("12", "B <12>");
    accountMap.put("13", "C <13>");
    accountMap.put("14", "D <14>");
    accountMap.put("15", "E <15>");
    accountMap.put("16", "F <16>");
    accountMap.put("20", "G <20>");
    accountMap.put("22", "H <22>");
    accountMap.put("30", "I <30>");
    accountMap.put("31", "J <31>");
    accountMap.put("32", "K <32>");
    accountMap.put("33", "L <33>");
    accountMap.put("37", "M <37>");
    accountMap.put("38", "N <38>");
    accountMap.put("40", "O <40>");
    accountMap.put("41", "P <41>");
    accountMap.put("50", "Q <50>");
    accountMap.put("51", "R <51>");
    accountMap.put("8001", "S <8001>");
    accountMap.put("8002", "T <8002>");
  }


  /**
   * @param from
   * @param to
   */
  private void zapWorkaround(ChannelStatusLine from, ChannelStatusLine to)
  {
    if (StringUtils.startsWith(from.getChannel(), "Zap/"))
    {
      String ch = from.getData();
      int i = ch.indexOf('|');
      String cid = i > 0 ? ch.substring(4, i) : ch.substring(4);
      from.setCallerId(cid);
      to.setCallerId(cid);
    }
  }


  /*
   *
   */
  public List<Call> getActiveCalls(List<ChannelStatusLine> list) throws ContactaException
  {
    //List<ChannelStatusLine> list = getActiveChannels();
    Map<String,ChannelStatusLine> callerMap = new HashMap<String,ChannelStatusLine>();
    Map<String,ChannelStatusLine> calleeMap = new HashMap<String,ChannelStatusLine>();
    for (ChannelStatusLine csl : list)
    {
      if (StringUtils.equals("Dial", csl.getApplication()))
      {
        callerMap.put(csl.getChannel(), csl);
      }
      else if (StringUtils.equals("AppDial", csl.getApplication()) || StringUtils.equals("Bridged Call", csl.getApplication()) )
      {
        calleeMap.put(csl.getChannel(), csl);
      }
      else
      {
        log().warn("unknown application={}", csl.getApplication());
      }
    }

    List<Call> callList = new ArrayList<Call>();
    for (ChannelStatusLine to : calleeMap.values())
    {
      if (StringUtils.isNotBlank(to.getBridged()) && !StringUtils.equals(to.getBridged(), "(None)"))
      {
        ChannelStatusLine from = callerMap.get(to.getBridged());
        zapWorkaround(from, to);
        Call call = new Call(from, to);
        callList.add(call);
        log().debug("call={}->{}", call.getFrom().getCallerId(), call.getTo().getCallerId());
      }
      else
      {
        String search = "SIP/"+to.getExten();
        for (ChannelStatusLine from : callerMap.values())
        {
          if (StringUtils.contains(from.getData(), search))
          {
            zapWorkaround(from, to);
            Call call = new Call(from, to);
            callList.add(call);
            log().debug("call={}->{}", call.getFrom().getCallerId(), call.getTo().getCallerId());
            break;
          }
        }
      }
    }
    return callList;
  }


  /**
   * @param lineMap
   * @param call
   */
  private void trySetCall(Map<String, Line> lineMap, Call call)
  {
    String cid = call.getFrom().getCallerId();
    if (StringUtils.isBlank(cid) && call.getFrom().getChannel().startsWith("SIP/"))
    {
      String ch = call.getFrom().getChannel();
      cid = ch.substring(4, ch.indexOf('-'));
      log().debug("cid={}", cid);
    }
    Line line = lineMap.get(cid);
    if (line == null && call.getTo() != null)
    {
      line = lineMap.get(call.getTo().getCallerId());
    }
    if (line != null)
    {
      line.setCall(call);
    }
  }


  /**
   * @param lineMap
   * @param call
   */
  private void trySetCall2(Map<String, Line> lineMap, Call call)
  {
    String cid = call.getTo().getCallerId();
    if (StringUtils.isBlank(cid) && call.getFrom().getChannel().startsWith("SIP/"))
    {
      String ch = call.getFrom().getChannel();
      cid = ch.substring(4, ch.indexOf('-'));
      log().debug("cid={}", cid);
    }
    Line line = lineMap.get(cid);
    if (line == null && call.getFrom() != null)
    {
      line = lineMap.get(call.getFrom().getCallerId());
    }
    if (line != null)
    {
      line.setCall(call);
    }
  }


  /*
   * Zap talks SIP/50
   *   Zap/1-1!macro-co_dial_inbound!s!1!Up!Dial!SIP/50|30|Ttfr!!!3!11!SIP/50-094fd240
   *   SIP/50-094fd240!co_outbound!!1!Up!Bridged Call!Zap/1-1!s!!3!6!Zap/1-1
   */
  @Test
  public void testA() throws Exception
  {
    List<ChannelStatusLine> list = new ArrayList<ChannelStatusLine>();
    //ChannelStatusLine csl0 = new ChannelStatusLine("Zap/1-1!macro-co_dial_inbound!s!1!Up!Dial!SIP/50|30|Ttfr!!!3!11!SIP/50-094fd240");
    //ChannelStatusLine csl1 = new ChannelStatusLine("SIP/50-094fd240!co_outbound!!1!Up!Bridged Call!Zap/1-1!s!!3!6!Zap/1-1");
    // RING:
    ChannelStatusLine csl0 = new ChannelStatusLine("Zap/1-1!macro-co_dial_inbound!s!1!Ring!Dial!SIP/50|30|Ttfr!!!3!5!(None)");
    ChannelStatusLine csl1 = new ChannelStatusLine("SIP/50-094fd240!co_outbound!s!1!Ringing!AppDial!(Outgoing Line)!s!!3!1!(None)");
    log().debug("csl0 {}", csl0.toString());
    log().debug("csl1 {}", csl1.toString());
    list.add(csl0);
    list.add(csl1);
    List<Call> callList = getActiveCalls(list);//new ArrayList<Call>();

    //AsteriskService asteriskService = new AsteriskServiceImpl();
    //asteriskService.getActiveCalls();

    Map<String,Line> lineMap = new HashMap<String,Line>();
    for (String cid : accountMap.keySet())
    {
      Line line = new Line(cid, accountMap.get(cid));
      lineMap.put(cid, line);
    }
    for (Call call : callList)
    {
      trySetCall(lineMap, call);
      trySetCall2(lineMap, call);
    }
    List<Line> lineList = new ArrayList<Line>(lineMap.values());
    //LineComparator comparator = new LineComparator();
    //Collections.sort(lineList, comparator);

    for (Line line : lineList)
    {
      log().debug("{} {}", line.getCall() != null, line.toString());
    }
  }
}
