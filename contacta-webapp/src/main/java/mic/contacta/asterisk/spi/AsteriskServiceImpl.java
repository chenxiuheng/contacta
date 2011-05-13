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
package mic.contacta.asterisk.spi;

import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat; // add for remote download

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.asteriskjava.fastagi.AgiServer;
import org.asteriskjava.manager.*;
import org.asteriskjava.manager.event.*;
import org.asteriskjava.manager.action.*;
import org.asteriskjava.manager.response.*;
import org.asteriskjava.live.ManagerCommunicationException;

import mic.contacta.domain.Call;
import mic.contacta.domain.ChannelStatusLine;
import mic.contacta.server.ContactaConstants;
import mic.contacta.server.ContactaException;
import mic.contacta.util.AgentUtil;
import mic.contacta.util.StatisticsBean;


/**
 * This is a connector to asterisk.
 *
 * NOTE: usually there is an AOP advice that protects the interface methods
 * checking if asterisk is connected.  So it is not necessary to call checkConnection()
 *
 * @author michele.bianchi@gmail.com
 * @version $Revision: 681 $
 */
@Service(AsteriskService.SERVICE_NAME)
public class AsteriskServiceImpl implements AsteriskService
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  private static final String SHOW_CHANNELS = "show channels";
  private static final String SHOW_CHANNELS_CONCISE = "show channels concise";

  @Autowired private AgiServer agiServer;
  @Autowired private EventDispatcher eventDispatcher;

  private ManagerConnectionFactory factory;
  private ManagerConnection managerConnection;

  private String managerHost ="127.0.0.1";
  private String managerUsername = "register";
  private String managerPassword = "XXXX";
  private String events = "off";

  private String statusMessage = "uninitialized";

  private boolean disabled;
  private int connections;


  /*
   *
   */
  public AsteriskServiceImpl()
  {
    super();
  }


  /**
   * @return the disabled
   */
  @Override
  public boolean getDisabled()
  {
    return disabled;
  }


  /**
   * @param disabled the disabled to set
   */
  public void setDisabled(boolean disabled)
  {
    this.disabled = disabled;
  }


  /**
   * @return the connections
   */
  @Override
  public int getConnections()
  {
    return connections;
  }

  /**
   * @return the managerHost
   */
  public String getManagerHost()
  {
    return managerHost;
  }


  /**
   * @param managerHost  the managerHost to set
   */
  public void setManagerHost(String managerHost)
  {
    this.managerHost = managerHost;
  }


  /**
   * @return the managerUsername
   */
  public String getManagerUsername()
  {
    return managerUsername;
  }


  /**
   * @param managerUsername  the managerUsername to set
   */
  public void setManagerUsername(String managerUsername)
  {
    this.managerUsername = managerUsername;
  }


  /**
   * @return the managerPassword
   */
  public String getManagerPassword()
  {
    return managerPassword;
  }


  /**
   * @param managerPassword
   *                the managerPassword to set
   */
  public void setManagerPassword(String managerPassword)
  {
    this.managerPassword = managerPassword;
  }


  /**
   * @return the statusMessage
   */
  public String getStatusMessage()
  {
    return statusMessage;
  }


  /*
   *
   */
  private void debugResponse(ManagerResponse response)
  {
    if (response != null)
    {
      ReflectionToStringBuilder builder = new ReflectionToStringBuilder(response, ToStringStyle.MULTI_LINE_STYLE);// .setExcludeFieldNames(excludes);
      log().info(builder.toString());
    }
    else
    {
      log().warn("response is null");
    }
  }


  /*
   *
   */
  private String createFileName(String channel, String calledID)
  {
    StringBuilder name = new StringBuilder(channel.replaceAll("/", "-"));
    name.append("-");
    name.append(calledID);
    name.append("-");
    DateFormat df = new SimpleDateFormat(ContactaConstants.DATE_FORMAT);
    name.append(df.format(new Date()));
    return name.toString();
  }


  /*
   *
   */
  @PostConstruct
  public void configure()
  {
    if (disabled)
    {
      log().warn("AsteriskServiceImpl is disabled");
      return;
    }

    log().warn("agiServer={}", agiServer);

    factory = new ManagerConnectionFactory(managerHost, managerUsername, managerPassword);
    managerConnection = factory.createManagerConnection();
    if (eventDispatcher.getDisabled() == false)
    {
      managerConnection.addEventListener(eventDispatcher);
    }

    try
    {
      connectAsterisk();
      disconnectAsterisk();
    }
    catch (ContactaException e)
    {
      log().warn("=====================================================================");
      log().warn("asterisk is not working or not reachable");
      log().warn("=====================================================================");
      statusMessage = "asterisk not reachable: " + e.getMessage();
      return;
    }
  }


  /*
   *
   */
  @Override
  public boolean checkConnection()
  {
    boolean r = managerConnection != null &&
    (managerConnection.getState() == ManagerConnectionState.CONNECTED ||
        managerConnection.getState() == ManagerConnectionState.CONNECTING ||
        managerConnection.getState() == ManagerConnectionState.RECONNECTING);
    log().debug("connections={}, connected={}", connections, r);
    return r;
  }


  /*
   *
   */
  @Override
  public synchronized void connectAsterisk() throws ContactaException
  {
    //log().debug("connections={}, connected={}", connections, checkConnection());
  if (connections == 0)
    {
      try
      {
        log().debug("connecting to host: {} as: {}/{}", new Object[] { managerHost, managerUsername, managerPassword });
        managerConnection.login(events);
        if (managerConnection.getState() != ManagerConnectionState.CONNECTED)
        {
          log().warn("cannot connect");
          throw new ContactaException("asterisk is not running");
        }
        else
        {
        	try
        	{
			String astversion = managerConnection.getVersion().toString();
                        log().info("The version of Asterisk is "+ astversion);
        	}
    		catch (ManagerCommunicationException ever) {
    			log().warn("Unable to get Asterisk version", ever);
    			throw new ContactaException(ever.getMessage(), ever);
    		}
        }
      }
      catch (IOException e)
      {
        log().warn("state: {}, due to {}", managerConnection.getState(), e.getMessage());
        throw new ContactaException(e.getMessage(), e);
      }
      catch (AuthenticationFailedException e)
      {
        log().warn("invalid credential: {}", e.getMessage());
        throw new ContactaException(e.getMessage(), e);
      }
      catch (TimeoutException e)
      {
        //disconnectAsterisk(); Errato se e' stato disconnect
        log().warn("timeout: {}", e.getMessage());
        throw new ContactaException(e.getMessage(), e);
      }
    }
    connections++;
  }


  /*
   *
   */
  @Override
  public synchronized void disconnectAsterisk()
  {
    //log().debug("connections={}, connected={}", connections, checkConnection());
    if (connections > 0)
    {
      log().debug("disconnecting");
      connections--;
      if (connections == 0)
      {
        log().debug("logging off");
        managerConnection.logoff(); // and finally log off and disconnect
      }
    }
  }


  /*
   *
   */
  @Override
  public void measureAsterisk(StatisticsBean statistics) throws ContactaException
  {
    try
    {
      CommandAction commandAction = new CommandAction();
      commandAction.setCommand(SHOW_CHANNELS);
      CommandResponse commandResponse = (CommandResponse) (managerConnection.sendAction(commandAction, 30000));
      int sipChannels = 0;
      int zapChannels = 0;
      int capiChannels = 0;
      for (Object o : commandResponse.getResult())
      {
        if (o instanceof String)
        {
          String line = (String) (o);
          if (line.startsWith("SIP"))
          {
            sipChannels++;
          }
          else if (line.startsWith("DAHDI"))
          {
            zapChannels++;
          }
          else if (line.contains("CAPI"))
          {
             capiChannels++;
          }

          else if (line.contains("active channel"))
          {
            statistics.setActiveChannels(AgentUtil.parseInt(line.substring(0, line.indexOf(' '))));
          }
          else if (line.contains("active call"))
          {
            statistics.setActiveCalls(AgentUtil.parseInt(line.substring(0, line.indexOf(' '))));
          }

        }
      }
      statistics.setSipChannels(sipChannels);
      statistics.setZapChannels(zapChannels);
      statistics.setZapChannels(capiChannels);

      ResponseEvents responseEvents = managerConnection.sendEventGeneratingAction(new SipPeersAction());
      int sipPeersCount = 0;
      for (Object o : responseEvents.getEvents())
      {
        if (o instanceof PeerEntryEvent)
        {
          sipPeersCount++;
        }
      }
      statistics.setSipPeers(sipPeersCount);
    }
    catch (IOException e)
    {
      log().warn(e.getMessage());
      throw new ContactaException(e.getMessage(), e);
    }
    catch (TimeoutException e)
    {
      log().warn(e.getMessage());
      throw new ContactaException(e.getMessage(), e);
    }
  }


  /*
   *
   */
  @Override
  public List<SipStatus> getSipStatusList() throws ContactaException
  {
    List<SipStatus> sipStatusList = new ArrayList<SipStatus>();
    try
    {
      ResponseEvents responseEvents = managerConnection.sendEventGeneratingAction(new SipPeersAction());
      // filter only the sip peers
      List<PeerEntryEvent> eventList = new ArrayList<PeerEntryEvent>();
      for (Object o : responseEvents.getEvents())
      {
        if (o instanceof PeerEntryEvent)
        {
          PeerEntryEvent peer = (PeerEntryEvent) (o);
          eventList.add(peer);
          //log().info("got peer: {}", peer);
        }
        else if (o instanceof PeerlistCompleteEvent)
        {
          log().info("peer list completed");
        }
        else
        {
          log().warn("uhm... ignoring {}", o);
          // responseEvents.getEvents().remove(o);
        }
      }

      // TODO couple createdBy to sip peers
      // List<PeerEntryEvent> unknownPeerList = new ArrayList<PeerEntryEvent>();
      for (PeerEntryEvent peer : eventList)
      {
        SipStatus sipStatus = new SipStatus(peer);
        sipStatusList.add(sipStatus);
      }
    }
    catch (TimeoutException e)
    {
      log().warn(e.getMessage());
    }
    catch (IOException e)
    {
      log().warn(e.getMessage());
    }
    catch (Exception e)
    {
      throw new ContactaException(e.getMessage(), e);
    }
    return sipStatusList;
  }


  /*
   *
   */
  private CommandResponse sendReloadCommand(String command) throws ContactaException
  {
    try
    {
      CommandAction commandAction = new CommandAction(command);
      CommandResponse response = (CommandResponse) (managerConnection.sendAction(commandAction));
      /*log().info("response: {}", response);
      for (String line : response.getResult())
      {
        log().info(line);
      }*/
      return response;
    }
    catch (Exception e)
    {
      log().warn(e.getMessage());
      throw new ContactaException(e.getMessage(), e);
    }
  }


  /*
   * @see mic.contacta.server.AsteriskService#sendCommand(java.lang.String)
   */
  @Override
  public Object sendCommand(String command) throws ContactaException
  {
    CommandResponse response = sendReloadCommand(command);
    return response;
  }


  /*
   *
   * @see mic.contacta.AsteriskService#getActiveCalls()
   */
  @Override
  public List<Call> getActiveCalls() throws ContactaException
  {
    List<ChannelStatusLine> list = getActiveChannels();
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
        log().info("call={}->{}", call.getFrom().getCallerId(), call.getTo().getCallerId());
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
            log().info("call={}->{}", call.getFrom().getCallerId(), call.getTo().getCallerId());
            break;
          }
        }
      }
    }
    return callList;
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
   * return e.g.
   * ManagerResponse: actionId='null'; message='Redirect successful'; response='Success'; uniqueId='null'; systemHashcode=30505467
   */
  @Override
  public void redirect(String channel, String extension, String context, int priority) throws ContactaException
  {
    try
    {
      RedirectAction action = new RedirectAction(channel, context, extension, priority);
      ManagerResponse response = managerConnection.sendAction(action, 30000);
      log().info("response: {}", response);
    }
    catch (Exception e)
    {
      log().warn(e.getMessage());
      throw new ContactaException(e.getMessage(), e);
    }
  }


  /*
   *
   */
  @Override
  public ChannelStatusLine findChannelByExtension(String callerExten) throws ContactaException
  {
    try
    {
      CommandAction commandAction = new CommandAction();
      commandAction.setCommand(SHOW_CHANNELS);
      CommandResponse commandResponse = (CommandResponse) (managerConnection.sendAction(commandAction, 30000));
      String prefix = "SIP/"+callerExten;
      for (Object o : commandResponse.getResult())
      {
        if (o instanceof String)
        {
          if (o.toString().startsWith(prefix))
          {
            return new ChannelStatusLine((String)(o), true);
          }
        }
      }
    }
    catch (Exception e)
    {
      log().warn(e.getMessage());
      throw new ContactaException(e.getMessage(), e);
    }
    return null;
  }


  /*
   * 8000 chiama 8001:
   *
   * Channel              Location             State   Application(Data)
   * SIP/8001-08585078    (None)               Up      Bridged Call(SIP/8000-085b3468
   * SIP/8000-085b3468    s@macro-stdexten:4   Up      Dial(SIP/8001|30)
   *
   * 8001 chiama 8000:
   *
   * Channel              Location             State   Application(Data)
   * SIP/8000-08585078    (None)               Up      Bridged Call(SIP/8001-085b3468
   * SIP/8001-085b3468    s@macro-stdexten_nos Up      Dial(SIP/8000|30)
   *
   */
  @Override
  public ChannelStatusLine findPeerChannel(String exten) throws ContactaException
  {
    try
    {
      CommandAction commandAction = new CommandAction();
      commandAction.setCommand(SHOW_CHANNELS);
      CommandResponse commandResponse = (CommandResponse) (managerConnection.sendAction(commandAction, 30000));
      String prefix = "Dial(SIP/"+exten;
      String prefixB = "Bridged Call(SIP/"+exten;
      for (Object o : commandResponse.getResult())
      {
        if (o instanceof String)
        {
          if (o.toString().contains(prefixB))
          {
            return new ChannelStatusLine((String)(o), true);
          }
          else if (o.toString().contains(prefix))
          {
            return new ChannelStatusLine((String)(o), true);
          }
        }
      }
    }
    catch (Exception e)
    {
      log().warn(e.getMessage());
      throw new ContactaException(e.getMessage(), e);
    }
    return null;
  }


  /*
   * @see mic.contacta.server.AsteriskService#findPeerChannel(java.lang.String)
   */
  @Override
  public ChannelStatusLine findSelfChannel(String exten) throws ContactaException
  {
    try
    {
      CommandAction commandAction = new CommandAction();
      commandAction.setCommand(SHOW_CHANNELS);
      CommandResponse commandResponse = (CommandResponse) (managerConnection.sendAction(commandAction, 30000));
      String prefix = "SIP/"+exten;
      for (Object o : commandResponse.getResult())
      {
        if (o instanceof String)
        {
          if (o.toString().startsWith(prefix))
          {
            return new ChannelStatusLine((String)(o), true);
          }
        }
      }
    }
    catch (Exception e)
    {
      log().warn(e.getMessage());
      throw new ContactaException(e.getMessage(), e);
    }
    return null;
  }


  /*
   * @see mic.contacta.server.AsteriskService#findParkChannel(java.lang.String)
   */
  @Override
  public ChannelStatusLine findParkChannel(String exten) throws ContactaException
  {
    try
    {
      CommandAction commandAction = new CommandAction();
      commandAction.setCommand(SHOW_CHANNELS);
      CommandResponse commandResponse = (CommandResponse) (managerConnection.sendAction(commandAction, 30000));
      String prefix = " "+exten+"@";
      for (Object o : commandResponse.getResult())
      {
        if (o instanceof String)
        {
          if (o.toString().contains(prefix))
          {
            return new ChannelStatusLine((String)(o), true);
          }
        }
      }
    }
    catch (Exception e)
    {
      log().warn(e.getMessage());
      throw new ContactaException(e.getMessage(), e);
    }
    return null;
  }


  /*
   * Channel              Location             State   Application(Data)
   * SIP/8001-084edd60    8001@nazionali:1     Ringing AppDial((Outgoing Line))
   * SIP/8000-08587748    s@macro-stdexten:4   Ring    Dial(SIP/8001|30)
   *
   * @see mic.contacta.server.AsteriskService#findParkChannel(java.lang.String)
   */
  @Override
  public ChannelStatusLine findRingingChannel(String exten) throws ContactaException
  {
    try
    {
      CommandAction commandAction = new CommandAction();
      commandAction.setCommand(SHOW_CHANNELS);
      CommandResponse commandResponse = (CommandResponse) (managerConnection.sendAction(commandAction, 30000));
      String prefix = "SIP/"+exten;
      for (Object o : commandResponse.getResult())
      {
        if (o instanceof String)
        {
          if (o.toString().startsWith(prefix) && o.toString().contains("Ringing"))
          {
            return new ChannelStatusLine((String)(o), true);
          }
        }
      }
    }
    catch (Exception e)
    {
      log().warn(e.getMessage());
      throw new ContactaException(e.getMessage(), e);
    }
    return null;
  }


  /*
   *
   */
  @Override
  public List<ChannelStatusLine> getActiveChannels() throws ContactaException
  {
    List<ChannelStatusLine> cslList = new ArrayList<ChannelStatusLine>();
    try
    {
      CommandAction commandAction = new CommandAction();
      commandAction.setCommand(SHOW_CHANNELS_CONCISE);
      CommandResponse commandResponse = (CommandResponse) (managerConnection.sendAction(commandAction, 30000));
      for (Object o : commandResponse.getResult())
      {
        if (o instanceof String)
        {
          String line = (String) (o);
          ChannelStatusLine csl = new ChannelStatusLine(line);
          cslList.add(csl);
        }
        else
        {
          log().info("[{}]=", o.getClass().getName(), o.toString());
        }
      }
    }
    catch (Exception e)
    {
      log().warn(e.getMessage());
      throw new ContactaException(e.getMessage(), e);
    }
    return cslList;
  }


  /*
   *
   */
  @Override
  public String getSipPeers() throws ContactaException
  {
    StringBuilder sb = new StringBuilder();
    try
    {
      CommandAction commandAction = new CommandAction();
      commandAction.setCommand("sip show peers");
      CommandResponse commandResponse = (CommandResponse) (managerConnection.sendAction(commandAction, 30000));
      for (Object o : commandResponse.getResult())
      {
        if (o instanceof String)
        {
          String line = (String) (o);
          sb.append(line);
          sb.append('\n');
        }
        log().info("[" + o.getClass().getName() + "]=" + o.toString());
      }
    }
    catch (Exception e)
    {
      log().warn(e.getMessage());
      throw new ContactaException(e.getMessage(), e);
    }
    return sb.toString();
  }


  /*
   *
   */
  public String getCalledID(String managerAnswer, int index) throws ContactaException
  {
    if (managerAnswer == null)
    {
      log().warn("Manager Answer string is null");
      return null;
    }
    char[] ra = managerAnswer.toCharArray();
    String calledID = null;
    int k = 0;
    log().info("Entrato in getCalledID");

    for (int i = index; i < ra.length - 8; i++)
    {
      if (ra[i] == 'D' && ra[i + 1] == 'i' && ra[i + 2] == 'a' && ra[i + 3] == 'l' && ra[i + 4] == '(')
      {
        log().info("Sono in mezzo a getCalledID");

        // for (k=i+9; ((ra[k]!='@' || ra[k]!='|')&&(k<ra.length)); k++ )
        for (k = i + 9; (ra[k] != '@' && ra[k] != '|'); k++)
          log().info("ra" + k + "pari a " + ra[k]);
        calledID = managerAnswer.substring((i + 9), k);
        // calledID = new String(ra,(i+9),(k-1));
        log().info("OK sono uscito getCalledID");
        return calledID; // OK found called id
      }
    }
    log().warn("Something wrong: it was not possible to find caller ID");
    return null; // TO ADDD!!!!!
  }


  /*
   * @see mic.contacta.AsteriskService#pauseRegister(java.lang.String)
   */
  @Override
  public void recordPause(String src) throws ContactaException
  {
    try
    {
      PauseMonitorAction action = new PauseMonitorAction();
      String ckchannel = null;// TODO getActiveChannels();
      String compTo = new String(ContactaConstants.SIP + src);
      int fo = ckchannel.indexOf(compTo);
      if (fo < 0)
      {
        log().warn("Non ci sono conversazioni attive da mettere in pause registrar sul canale {}", src);
        return;
      }

      int lenghtch = 4 + src.length() + 9;
      String channel = ckchannel.substring(fo, (fo + lenghtch));
      action.setChannel(channel);
      ManagerResponse response = managerConnection.sendAction(action, 3000);
      debugResponse(response);
    }
    catch (Exception e)
    {
      log().warn(e.getMessage());
      throw new ContactaException(e.getMessage(), e);
    }
  }


  /*
   * @see mic.contacta.AsteriskService#stopRegister(java.lang.String)
   */
  @Override
  public void recordStop(String src) throws ContactaException
  {
    StopMonitorAction action = new StopMonitorAction();
    String ckchannel = null;// TODO this.getActiveChannels();
    String compTo = new String(ContactaConstants.SIP + src);
    int fo = ckchannel.indexOf(compTo);
    if (fo < 0)
    {
      log().warn("Non ci sono conversazioni attive da registrare sul canale {}", src);
      return;
    }
    else
    {
      int lenghtch = 4 + src.length() + 9;
      String channel = ckchannel.substring(fo, (fo + lenghtch));
      action.setChannel(channel);
      ManagerResponse response;
      try
      {
        response = managerConnection.sendAction(action, 3000);
      }
      catch (Exception e)
      {
        log().warn(e.getMessage());
        throw new ContactaException(e.getMessage(), e);
      }

      this.debugResponse(response);
    } // close else if fo < 0
  }


  /*
   * @see mic.contacta.AsteriskService#register(java.lang.String)
   */
  @Override
  public void recordStart(String src) throws ContactaException
  {
    final String FILE_FORMAT = ContactaConstants.WAV; // to be modified it should be an env
    // variable
    final boolean MIX_FILE = true;
    String calledID = null;

    try
    {
      MonitorAction action = new MonitorAction();
      /*
       * MonitorAction(java.lang.String channel, java.lang.String file,
       * java.lang.String format, java.lang.Boolean mix) Creates a new
       * MonitorAction that starts monitoring the given channel and writes voice
       * data to the given file(s).
       */
      String ckchannel = null;// TODO this.getActiveChannels();
      String compTo = new String(ContactaConstants.SIP + src);
      int fo = ckchannel.indexOf(compTo);
      if (fo < 0)
      {
        log().warn("Non ci sono conversazioni attive da registrare sul canale {}", src);
        return;
      }

      int lenghtch = 4 + src.length() + 9;
      String channel = ckchannel.substring(fo, (fo + lenghtch));
      // getting called ID
      log().info("Canale selezionato {}", channel);
      log().info("Passo ckchannel {} fo {}", ckchannel, fo);
      calledID = new String(this.getCalledID(ckchannel, fo));
      log().info(" calledID is " + calledID);
      action.setChannel(channel);
      String nomefile = createFileName(channel, calledID);
      action.setFile(nomefile);
      action.setFormat(FILE_FORMAT);
      action.setMix(MIX_FILE);

      ManagerResponse response = managerConnection.sendAction(action, 3000);
      this.debugResponse(response);
    }
    catch (Exception e)
    {
      log().warn(e.getMessage());
      throw new ContactaException(e.getMessage(), e);
    }
  }


  /*
   * @see mic.contacta.AsteriskService#dial(java.lang.String, java.lang.String)
   */
  @Override
  public void dial(String src, String dst) throws ContactaException
  {
    try
    {
      OriginateAction action = new OriginateAction();
      action.setChannel(ContactaConstants.SIP + src);
      action.setContext(ContactaConstants.INTERNI);
      action.setExten(dst);
      action.setPriority(new Integer(1));
      // action.setTimeout(new Integer(30000));
      action.setCallerId(src);

      ManagerResponse response = managerConnection.sendAction(action, 30000);

      this.debugResponse(response);
    }
    catch (Exception e)
    {
      log().warn(e.getMessage());
      throw new ContactaException(e.getMessage(), e);
    }
  }


  /*
   * URGENT improve me :)
   * @see mic.contacta.server.AsteriskService#sendCommand(org.asteriskjava.manager.action.OriginateAction)
   */
  @Override
  public void sendCommand(OriginateAction originateAction) throws ContactaException
  {
    try
    {
      ManagerResponse response = managerConnection.sendAction(originateAction);
      log().debug("response: {}", response);
    }
    catch (Exception e)
    {
      log().warn(e.getMessage());
      //throw new ContactaException(e.getMessage(), e);
    }
  }

}
