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

import org.asteriskjava.manager.event.PeerEntryEvent;


/**
 * Description of the Class
 *
 * @author    mic
 * @created   May 17, 2006
 */
public class SipStatus
{
  public static final String SIP_IS_NOT_CONNECTED_TO_IP = "-none-";

  private PeerEntryEvent peerEntryEvent;
  private boolean connected;

  /*
   *
   */
  public SipStatus(PeerEntryEvent peerEntryEvent)
  {
    this.peerEntryEvent = peerEntryEvent;
    this.connected = (SIP_IS_NOT_CONNECTED_TO_IP.equals(peerEntryEvent.getIpAddress()) == false);
  }


  /*
   *
   */
  public boolean isConnected()
  {
    return connected;
  }


  /*
   *
   */
  public PeerEntryEvent getPeer()
  {
    return peerEntryEvent;
  }


  /*
   *
   */
  public void setPeer(PeerEntryEvent peerEntryEvent)
  {
    this.peerEntryEvent = peerEntryEvent;
    this.connected = peerEntryEvent != null;
  }

}

