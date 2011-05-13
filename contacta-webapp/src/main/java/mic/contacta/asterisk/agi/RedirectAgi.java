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
package mic.contacta.asterisk.agi;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import mic.contacta.asterisk.agi.AbstractContactaAgi;
import mic.contacta.asterisk.spi.AsteriskService;
import mic.contacta.domain.ChannelStatusLine;
import mic.contacta.server.ContactaException;


/**
 * This is just a sample or a dead code...  TODO probably dead:)
 *
 * @author mic
 * @created Jan 20, 2009
 */
//@Service("redirectAgi")
public class RedirectAgi extends AbstractContactaAgi
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private AsteriskService asteriskService;

  /*
   *
   */
  public RedirectAgi()
  {
    super(null);
  }


  /*
   *
   */
  private void redirect(String pickerExten, String calleeExten) throws AgiException
  {
    try
    {
      ChannelStatusLine csl = asteriskService.findChannelByExtension(calleeExten);
      //log().info("channelStatusLine: {}", csl);
      if (csl != null)
      {
        log().info("{}, {}, fromE:{} toE:{}", new Object[] { csl.getChannel(), csl.getContext(), calleeExten, pickerExten });
        asteriskService.redirect(csl.getChannel(), pickerExten, csl.getContext(), 1);
      }
      else
      {
        log().info("no active channel for caller extension {}", calleeExten);
      }
    }
    catch (ContactaException e)
    {
      log().warn(e.getMessage());
      throw new AgiException(e.getMessage(), e);
    }
  }


  /*
   * @see org.asteriskjava.fastagi.AgiScript#service(org.asteriskjava.fastagi.AgiRequest, org.asteriskjava.fastagi.AgiChannel)
   */
  @Transactional(readOnly=true)
  @Override
  public void service(AgiRequest request, AgiChannel channel) throws AgiException
  {
    redirect("pickerExten", "calleeExten");
  }

}
