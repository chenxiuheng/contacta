/* $Id: VoiceMessage.java 616 2010-04-03 21:07:58Z michele.bianchi $
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
package mic.contacta.server.api;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author mic
 * @created Mar 21, 2008
 */
public class VoiceMessage
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }
  private long createdOn;
  private String filename;
  private InputStream inputStream;
  private String acknowledge;


  /**
   * @return the inputStream
   */
  public InputStream getInputStream()
  {
        //log().info("-----------VoiceMessage nomefile " + filename);
    if (filename != null)
    {
      try
      {
        inputStream = new FileInputStream(filename);
        return inputStream;
      }
      catch (FileNotFoundException e)
      {
        log().warn("VOICEMESSAGE File not found!" + e.getMessage());
      }
    }
    return null;
  }


  /**
   * @param inputStream the inputStream to set
   */
  public void setInputStream(InputStream inputStream)
  {
    this.inputStream = inputStream;
  }


  /**
   * @return the acknowledge
   */
  public String getAcknowledge()
  {
    return acknowledge;
  }


  /**
   * @param acknowledge the acknowledge to set
   */
  public void setAcknowledge(String ack)
  {
    this.acknowledge = ack;
  }


  /**
   * @return the createdOn
   */
  public long getCreatedOn()
  {
    return createdOn;
  }


  /**
   * @param createdOn the createdOn to set
   */
  public void setCreatedOn(long createdOn)
  {
    this.createdOn = createdOn;
  }


  /**
   * @return the filename
   */
  public String getFilename()
  {
    return filename;
  }


  /**
   * @param filename the filename to set
   */
  public void setFilename(String filename)
  {
    this.filename = filename;
  }

}
