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
package mic.contacta.asterisk.mock;

import java.util.List;

import org.asteriskjava.manager.action.OriginateAction;
import org.springframework.stereotype.Service;

import mic.contacta.asterisk.spi.AsteriskService;
import mic.contacta.asterisk.spi.SipStatus;
import mic.contacta.server.api.Call;
import mic.contacta.server.api.ChannelStatusLine;
import mic.contacta.server.api.ContactaException;
import mic.contacta.util.StatisticsBean;


/**
 *
 * @author mic
 * @created Jun 27, 2009
 */
@Service(AsteriskService.SERVICE_NAME)
public class AsteriskServiceMock implements AsteriskService
{

  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#checkConnection()
   */
  @Override
  public boolean checkConnection()
  {
    // TODO Auto-generated method stub
    return false;
  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#connectAsterisk()
   */
  @Override
  public void connectAsterisk() throws ContactaException
  {
    // TODO Auto-generated method stub

  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#dial(java.lang.String, java.lang.String)
   */
  @Override
  public void dial(String src, String dst) throws ContactaException
  {
    // TODO Auto-generated method stub

  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#disconnectAsterisk()
   */
  @Override
  public void disconnectAsterisk()
  {
    // TODO Auto-generated method stub

  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#findChannelByExtension(java.lang.String)
   */
  @Override
  public ChannelStatusLine findChannelByExtension(String callerExten) throws ContactaException
  {
    // TODO Auto-generated method stub
    return null;
  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#findParkChannel(java.lang.String)
   */
  @Override
  public ChannelStatusLine findParkChannel(String exten) throws ContactaException
  {
    // TODO Auto-generated method stub
    return null;
  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#findPeerChannel(java.lang.String)
   */
  @Override
  public ChannelStatusLine findPeerChannel(String exten) throws ContactaException
  {
    // TODO Auto-generated method stub
    return null;
  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#findRingingChannel(java.lang.String)
   */
  @Override
  public ChannelStatusLine findRingingChannel(String exten) throws ContactaException
  {
    // TODO Auto-generated method stub
    return null;
  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#findSelfChannel(java.lang.String)
   */
  @Override
  public ChannelStatusLine findSelfChannel(String exten) throws ContactaException
  {
    // TODO Auto-generated method stub
    return null;
  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#getActiveCalls()
   */
  @Override
  public List<Call> getActiveCalls() throws ContactaException
  {
    // TODO Auto-generated method stub
    return null;
  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#getActiveChannels()
   */
  @Override
  public List<ChannelStatusLine> getActiveChannels() throws ContactaException
  {
    // TODO Auto-generated method stub
    return null;
  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#getConnections()
   */
  @Override
  public int getConnections()
  {
    // TODO Auto-generated method stub
    return 0;
  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#getDisabled()
   */
  @Override
  public boolean getDisabled()
  {
    // TODO Auto-generated method stub
    return false;
  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#getSipPeers()
   */
  @Override
  public String getSipPeers() throws ContactaException
  {
    // TODO Auto-generated method stub
    return null;
  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#getSipStatusList()
   */
  @Override
  public List<SipStatus> getSipStatusList() throws ContactaException
  {
    // TODO Auto-generated method stub
    return null;
  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#measureAsterisk(mic.contacta.plugins.mpbx.StatisticsBean)
   */
  @Override
  public void measureAsterisk(StatisticsBean statistics) throws ContactaException
  {
    // TODO Auto-generated method stub

  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#recordPause(java.lang.String)
   */
  @Override
  public void recordPause(String src) throws ContactaException
  {
    // TODO Auto-generated method stub

  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#recordStart(java.lang.String)
   */
  @Override
  public void recordStart(String src) throws ContactaException
  {
    // TODO Auto-generated method stub

  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#recordStop(java.lang.String)
   */
  @Override
  public void recordStop(String src) throws ContactaException, Exception
  {
    // TODO Auto-generated method stub

  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#redirect(java.lang.String, java.lang.String, java.lang.String, int)
   */
  @Override
  public void redirect(String channel, String extension, String context, int priority) throws ContactaException
  {
    // TODO Auto-generated method stub

  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#sendCommand(java.lang.String)
   */
  @Override
  public Object sendCommand(String command) throws ContactaException
  {
    // TODO Auto-generated method stub
    return null;
  }


  /*
   * @see mic.contacta.asterisk.spi.AsteriskService#sendCommand(org.asteriskjava.manager.action.OriginateAction)
   */
  @Override
  public void sendCommand(OriginateAction originateAction) throws ContactaException
  {
    // TODO Auto-generated method stub

  }

}
