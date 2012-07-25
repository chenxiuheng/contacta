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
package mic.contacta.asterisk.handlers;

import java.util.*;

import org.asteriskjava.manager.event.HangupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mic.contacta.asterisk.spi.AbstractHandler;


/**
 * URGENT complete me, check: hash_name and the function flow
 *
 * @author mic
 * @created Jan 19, 2009
 */
@Service("hangupHandler")
public class HangupHandler extends AbstractHandler<HangupEvent>
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired(required=false) private VoiceMessageProcessor voiceMessageProcessor;  // TODO required is trueeeee!

  private Map<String, String> hash_name = new HashMap<String, String>();


  /*
   *
   */
  public HangupHandler()
  {
  }


  /*
   * @see org.springframework.beans.factory.FactoryBean#getObject()
   */
  @Override
  public Object getObject() throws Exception
  {
    HangupHandler handler = new HangupHandler();
    afterFactory(handler);
    handler.voiceMessageProcessor = voiceMessageProcessor;
    return handler;
  }


  /*
   * @see org.springframework.beans.factory.FactoryBean#getObjectType()
   */
  @Override
  public Class<?> getObjectType()
  {
    return HangupHandler.class;
  }


  /*
   * @see mic.contacta.server.Handler#handleEvent()
   */
  @Override
  public void handleEvent(HangupEvent event)
  {
    log().debug("event={}", event);
    String nomefile = null;
    String Ident = event.getUniqueId();
    try
    {
      nomefile = hash_name.get(Ident).toString();
    }
    catch (NullPointerException ee)
    {
    }

    if (nomefile != null)
    {
      log().info("Trovato messaggio vocale o voicemail " + nomefile);
      // it is a voicemail if it start with "u"
      hash_name.remove(event.getUniqueId());
      char aChar = nomefile.charAt(0);
      if (aChar == 'u')
      {
        log().info("Trovato voicemail ");
        try
        {
          voiceMessageProcessor.processVoiceMail(nomefile);
        }
        catch (/*Contacta*/Exception e1)
        {
          log().warn(e1.getMessage(), e1);
        }
        log().info("Processato messaggio vocale");
      }
      else
      {
        voiceMessageProcessor.processVoiceMessage(nomefile);
        log().info("Processato messaggio vocale");
      }

    }
    else
    {
      // hangup of a channel not belonging to a stored call (999 Service
      // Message Saving)
      return;
    }
    //log().debug("Channel: " + event.getChannel() + " Unique Id " + event.getUniqueId() + "Causa: " + event.getCauseTxt());
  }

}
