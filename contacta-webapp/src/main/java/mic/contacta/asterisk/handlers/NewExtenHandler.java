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
package mic.contacta.asterisk.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.asteriskjava.manager.event.NewExtenEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import mic.contacta.asterisk.spi.AbstractHandler;
import mic.contacta.server.api.ContactaConstants;


/**
 * URGENT complete me, check: hash_name and the function flow
 *
 * @author mic
 * @created Jan 19, 2009
 */
@Service("newExtenHandler")
public class NewExtenHandler extends AbstractHandler<NewExtenEvent>
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  private Map<String, String> hash_name = new HashMap<String, String>();


  /*
   *
   */
  public NewExtenHandler()
  {
  }


  /*
   * @see org.springframework.beans.factory.FactoryBean#getObject()
   */
  @Override
  public Object getObject() throws Exception
  {
    NewExtenHandler handler = new NewExtenHandler();
    afterFactory(handler);
    return handler;
  }


  /*
   * @see org.springframework.beans.factory.FactoryBean#getObjectType()
   */
  @Override
  public Class<?> getObjectType()
  {
    return NewExtenHandler.class;
  }


  /*
   *
   */
  private Boolean processLineVoiceMail(String aLine)
  {
    // use a second Scanner to parse the content of each line
    Scanner scanner = new Scanner(aLine);
    scanner.useDelimiter(",");
    while (scanner.hasNext())
    {
      String str = scanner.next();
      // log().info(" stringa " + str);
      // if (str.indexOf("application=\'VoiceMail\'")>0)
      if (str.indexOf(ContactaConstants.VOICE_MAIL) > 0)
      {
        log().info(" Trovato VoiceMail ");
        return true;
      }

    }
    // (no need for finally here, since String is source)
    scanner.close();
    return false;
  }


  /*
   *
   */
  private String processLine(String aLine)
  {
    // use a second Scanner to parse the content of each line
    Scanner scanner = new Scanner(aLine);
    scanner.useDelimiter(",");
    while (scanner.hasNext())
    {
      String str = scanner.next();
      // log().info(" stringa " + str);
      if (str.indexOf("CALLFILENAME") > 0)
      {
        String[] result = str.split("=");
        String help = result[2];
        String nomefile = help.substring(0, (help.length() - 1)) + ".wav";
        return nomefile;
        // log().info("Trovato " + result[2]+" !!!!!!" );
      }
    }
    // (no need for finally here, since String is source)
    scanner.close();
    return null;
  }


  /*
   *
   */
  private String getVoiceMailExt(String aLine)
  {
    // use a second Scanner to parse the content of each line
    Scanner scanner = new Scanner(aLine);
    // log().info(" stringa passata " + aLine);
    scanner.useDelimiter(",");
    while (scanner.hasNext())
    {
      String str = scanner.next();
      // log().info(" stringa " + str);
      if (str.indexOf("appdata") >= 0)
      {
        log().info(" stringa " + str);
        String[] result = str.split("=");
        String help = result[1];
        // the format is application='VoiceMail'
        // I m deleting the first ' and the last '

        String estensione = help.substring(1, (help.length() - 1));
        scanner.close();
        return estensione;
        // log().info("Trovato " + result[1]+" !!!!!!" );
      }
    }
    // (no need for finally here, since String is source)
    scanner.close();
    return null;
  }


  /*
   * @see mic.contacta.server.spi.Handler#handleEvent()
   */
  @Override
  public void handleEvent(NewExtenEvent event)
  {
    String event_string = event.toString();
    log().debug("event={}", event);

    // check if NewExtenHandler refers to VoiceMail application
    if (processLineVoiceMail(event_string))
    {
      // this event refers to a Voicemail
      log().info("FOUND Voicemail!");
      String uniqueId = event.getUniqueId();
      // log().info(" uniqueId " + uniqueId);
      String vmExt = getVoiceMailExt(event_string);
      log().info("  vmExt " + vmExt);
      hash_name.put(uniqueId, vmExt);
    }
    else
    {
      // looking for voice message
      String nomefile = processLine(event_string);
      if (nomefile != null)
      {
        log().info("FOUND !!!!!" + nomefile);
        String uniqueId = event.getUniqueId();
        hash_name.put(uniqueId, nomefile);
      }
      log().info("Evento NewExtenHandler; ");
      log().info("Channel: " + event.getChannel() + " Unique Id " + event.getUniqueId());
    }
  }

}
