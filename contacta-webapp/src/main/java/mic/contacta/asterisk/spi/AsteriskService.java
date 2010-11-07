/* $Id: AsteriskService.java 1468 2008-05-25 14:03:10Z michele.bianchi@gmail.com $
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
package mic.contacta.asterisk.spi;

import java.util.*;

import org.asteriskjava.manager.action.OriginateAction;

import mic.organic.core.Service;
import mic.contacta.server.api.Call;
import mic.contacta.server.api.ChannelStatusLine;
import mic.contacta.server.api.ContactaException;
import mic.contacta.util.StatisticsBean;


/**
 * @author mic
 */
public interface AsteriskService extends Service
{
  String SERVICE_NAME = "asteriskService";


  /**
   * When this service is marked disable all methods return null.
   * To work properly the AsteriskConnectAspect advice must be enabled.
   *
   * @return true when disabled
   */
  boolean getDisabled();


  /**
   * @param src
   * @param dst
   */
  void dial(String src, String dst) throws ContactaException;


  /**
   * @param src
   */
  void recordStart(String src) throws ContactaException;


  /**
   * @param src
   */
  void recordPause(String src) throws ContactaException;


  /**
   * @param src
   * @param createdBy
   */
  void recordStop(String src) throws ContactaException, Exception;

  // =================================================================================
  // boh follows....
  // =================================================================================

  /**
   * @param statistics
   * @throws ContactaException
   */
  void measureAsterisk(StatisticsBean statistics) throws ContactaException;


  /**
   * @return
   * @throws ContactaException
   */
  List<SipStatus> getSipStatusList() throws ContactaException;


  /**
   * @return
   * @throws ContactaException
   */
  List<ChannelStatusLine> getActiveChannels() throws ContactaException;


  /**
   * @throws ContactaException
   */
  List<Call> getActiveCalls() throws ContactaException;


  /**
   * @return
   * @throws ContactaException
   */
  String getSipPeers() throws ContactaException;

  // =================================================================================
  //   currently used
  // =================================================================================

  /**
   * @return the number of active connection to the manager port
   */
  int getConnections();

  /**
   * @return true if logged in to the manager port
   */
  boolean checkConnection();


  /**
   * Login to the manager port.  If connected just increase the nr of ''connections''
   *
   * @throws ContactaException
   */
  void connectAsterisk() throws ContactaException;


  /**
   * Logoff from the manager port.  Decrease the nr of ''connections'', when 0 logoff.
   */
  void disconnectAsterisk();


  /**
   * Send a string command to asterisk via manager socket, e.g. "sip reload"
   *
   * @param command like "sip reload"
   * @return TODO define return object
   * @throws ContactaException
   */
  Object sendCommand(String command) throws ContactaException;


  /**
   * TODO this is almost dead, check it again before use
   *
   * @param callerExten
   * @return
   * @throws ContactaException
   */
  ChannelStatusLine findChannelByExtension(String callerExten) throws ContactaException;


  /**
   * Find the ''self'' channel of the active phone call for the extension exten.
   * Self means the exten own side
   *
   * It assumes one call per exten, if more call are active the result is unpredictable.
   *
   * @param exten
   * @return
   * @throws ContactaException
   */
  ChannelStatusLine findSelfChannel(String exten) throws ContactaException;


  /**
   * Find the ''peer'' channel of the active phone call for the extension exten.
   * Peer means the other side, so if you transfer it the exten phone call will be terminated
   *
   * It assumes one call per exten, if more call are active the result is unpredictable.
   *
   * @param exten
   * @return
   * @throws ContactaException
   */
  ChannelStatusLine findPeerChannel(String exten) throws ContactaException;


  /**
   * move a call in progress to another extension
   *
   * @param channel the active channel
   * @param extension the extension
   * @param context the context
   * @param priority the priority
   * @return TODO
   * @throws ContactaException
   */
  void redirect(String channel, String extension, String context, int priority) throws ContactaException;


  /**
   * TODO tmp method, encapsulate me
   *
   * @param originateAction
   * @throws ContactaException
   */
  void sendCommand(OriginateAction originateAction) throws ContactaException;


  /**
   * @param exten
   * @return
   * @throws ContactaException
   */
  ChannelStatusLine findParkChannel(String exten) throws ContactaException;


  /**
   * @param exten
   * @return
   * @throws ContactaException
   */
  ChannelStatusLine findRingingChannel(String exten) throws ContactaException;

}
