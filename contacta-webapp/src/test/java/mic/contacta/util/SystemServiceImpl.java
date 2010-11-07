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
package mic.contacta.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 *
 *
 * @author mic
 * @created Nov 30, 2008
 */
@Service(SystemService.SERVICE_NAME)
public class SystemServiceImpl implements SystemService
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  /*
   * @see mic.contacta.server.ptool.SystemService#checkAsterisk()
   */
  @Override
  public String checkAsterisk()
  {
    String asteriskStatus = "Cannot reach asterisk";

    String[] command = { "pidof", "asterisk" };
    ProcessBuilder pb = new ProcessBuilder( command );

    try
    {
      Process process = pb.start();

      if(process.waitFor()==0)
      {
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        if ( ( line = br.readLine() ) != null )
        {
          String[] tokens = line.split("\\s");
          if(tokens.length == 1)
          {
            asteriskStatus = "Asterisk is up and running";
          }
        }

        asteriskStatus = "Asterisk is down. Plese restart";
      }

      process.getInputStream().close();
      process.getOutputStream().close();
      process.getErrorStream().close();
    }
    catch (Exception e)
    {
      log().info(e.getMessage());
    }

    return asteriskStatus;
  }


  /*
   * @see mic.contacta.server.ptool.SystemService#restartAsterisk()
   */
  @Deprecated
  public boolean restartAsterisk()
  {
    boolean restarted = false;

    String[] command = { "asterisk", "-rx", "'sip reload'" };
    ProcessBuilder pb = new ProcessBuilder( command );

    try
    {
      Process process = pb.start();

      if(process.waitFor()==0)
      {
        restarted = true;
      }

      process.getInputStream().close();
      process.getOutputStream().close();
      process.getErrorStream().close();
    }
    catch (Exception e)
    {
      log().info(e.getMessage());
    }

    return restarted;
  }

}
