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
package mic.contacta.pmod.polycom;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs.FileObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mic.contacta.model.PhoneModel;
import mic.contacta.server.spi.InventoryService;
import mic.contacta.util.ContactaUtils;


/**
 *
 * @author mic
 * @created May 30, 2009
 */
@Service
public class PolycomPutLog
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired protected InventoryService inventoryService;

  private FileObject logFo;


  /**
   * @param logFo the logFo to set
   */
  public void setLogFo(FileObject logFo)
  {
    this.logFo = logFo;
  }


  /*
   *
   */
  public String getPutContent(ServletRequest request, String resourceName) throws IOException
  {
    InputStream inputStream = request.getInputStream();
    String body = IOUtils.toString(inputStream);
    if (StringUtils.isNotBlank(body))
    {
      log().debug("PUT.body.length={}", body.length());
      // log().debug("PUT.body:\n{}", body);

      if (logFo != null)
      {
        try
        {
          // String subdir = resourceName+"-"+String.valueOf(System.currentTimeMillis());
          FileObject fo = logFo.resolveFile(resourceName);
          fo.createFolder();
          fo = fo.resolveFile(String.valueOf(System.currentTimeMillis()));
          fo.getContent().getOutputStream().write(body.getBytes());
          fo.close();
        }
        catch (IOException e)
        {
          log().warn("cannot write log file: {}", e.getMessage());
        }
      }
      return body;
    }
    return null;
  }


  /*
   *
   */
  public boolean handlePhoneConfigPut(ServletRequest request, String resourceName) throws IOException
  {
    boolean tmp = false;

    String body = getPutContent(request, resourceName);

    if (body != null)
    {
      tmp = true;

      //log().info("phone put: {}", body);

      int begin = body.indexOf("<PHONE_CONFIG>");
      int end = body.indexOf("</PHONE_CONFIG>", begin) + "</PHONE_CONFIG>".length();
      String config = body.substring(begin, end);

      String macAddress = resourceName.substring(0, 12);
      macAddress = ContactaUtils.macAddressHexToText(macAddress);

      PhoneModel phone = inventoryService.findPhoneByMacAddress(macAddress);
      if (phone != null)
      {
        //log().info("setting for {} directory={}", macAddress, configuration);
        phone.setConfig(config);
        inventoryService.updatePhone(phone);
      }
      else
      {
        log().info("cannot find phone with mac={}", macAddress);
      }

    }

    return tmp;
  }


  /*
   *
   */
  public boolean handlePut(ServletRequest request, String resourceName) throws IOException
  {
    boolean tmp = false;

    String body = getPutContent(request, resourceName);

    if (body != null)
    {
      tmp = true;
      // TODO
    }

    return tmp;
  }


  /*
   *
   */
  public boolean handleAppflashPut(ServletRequest request, String resourceName) throws IOException
  {
    boolean tmp = false;

    String body = getPutContent(request, resourceName);

    if (body != null)
    {
      tmp = true;
      // TODO
    }

    return tmp;
  }


  /*
   *
   */
  public boolean handleApplogPut(ServletRequest request, String resourceName) throws IOException
  {
    boolean tmp = false;

    String body = getPutContent(request, resourceName);

    //log().info(body);

    if (body != null)
    {
      tmp = true;

      // Starting manual reboot PUT
      String manualReboot = "Manual Reboot\n";
      String lastWord = body.substring(body.length() - manualReboot.length());

      if(lastWord.equals(manualReboot))
      {
        // TODO Would you like to save last log before phone restart?
        return tmp;
      }


      // Starting manual reconfiguration PUT
      String manualReconfig = "Manual Reconfiguration\n";
      lastWord = body.substring(body.length() - manualReconfig.length());

      if(lastWord.equals(manualReconfig))
      {
        // TODO Would you like to save last log before phone restart?
        return tmp;
      }


      // Just before reboot PUT
      String beforeReboot = "Watchdog Expired: tSupObjs\n";
      lastWord = body.substring(body.length() - beforeReboot.length());
      if(lastWord.equals(beforeReboot))
      {
        // TODO Would you like to save last log before phone restart?
        return tmp;
      }

    }

    return tmp;
  }


  /*
   *
   */
  public boolean handleBootlogPut(ServletRequest request, String resourceName) throws IOException
  {
    boolean tmp = false;

    String body = getPutContent(request, resourceName);

    if (body != null)
    {
      tmp = true;
      // TODO
    }

    return tmp;
  }

}
