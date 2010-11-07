/* $Id: PolycomDirectory.java 642 2010-05-30 09:15:27Z michele.bianchi $
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
package mic.contacta.pmod.polycom;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

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
public class PolycomDirectory
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired protected InventoryService inventoryService;


  /*
   *
   */
  public String extractItemsAsString(String body)
  {
    String directory = null;
    boolean empty = body.contains("<item_list/>");
    int begin = body.indexOf("<item_list>");
    if (begin > -1 || empty)
    {
      directory = "";
      if (!empty)
      {
        int end = body.indexOf("</item_list>", begin);
        directory = body.substring(begin+"<item_list>".length(), end);
      }
      //      String macAddress = resourceName.substring(0, 12);
      //      macAddress = ContactaUtils.numericToMacAddress(macAddress);
      //
      //      PhoneModel phone = inventoryService.findPhoneByMacAddress(macAddress);
      //      if (phone != null)
      //      {
      //        //log().info("setting for {} directory={}", macAddress, directory);
      //        phone.setCustomDirectory(directory);
      //        inventoryService.updatePhone(phone);
      //      }
      //      else
      //      {
      //        log().info("cannot find phone with mac={}", macAddress);
      //      }
    }
    return directory;
  }


  /*
   *
   */
  private String extractItem(String body, Map<String, String> map)
  {
    int itemBegin = body.indexOf("<item>");
    int itemEnd = -1;
    if (itemBegin > -1)
    {
      itemEnd = body.indexOf("</item>", itemBegin);
      String item = body.substring(itemBegin+"<item>".length(), itemEnd);

      int ctBegin = item.indexOf("<ct>");
      if (ctBegin > -1)
      {
        int ctEnd = item.indexOf("</ct>", ctBegin);
        String ct = item.substring(ctBegin+"<ct>".length(), ctEnd);

        map.put(ct, item);
      }
      else
      {
        log().info("unknown item, ct is not specified.  ignoring...");
      }
    }
    return (itemEnd > -1) ? body.substring(itemEnd+"</item>".length()) : null;
  }


  /*
   *
   */
  public Map<String,String> extractItems(String body)
  {
    Map<String,String> map = new HashMap<String, String>();
    while (body != null)
    {
      body = extractItem(body, map);
    }
    return map;
  }


  /*
   *
   */
  public String serializeItems(Map<String,String> map)
  {
    StringBuilder sb = new StringBuilder();
    for (String ct : map.keySet())
    {
      log().debug("adding {}", ct);
      sb.append("<item>");
      sb.append(map.get(ct));
      sb.append("</item>");
    }
    return sb.toString();
  }


  /*
   *
   */
  public void handleDirectoryPut(ServletRequest request, String resourceName, String body) throws IOException
  {
      //log().info("phone put: {}", body);

      // TODO qui e' fa un po' schifo questo if, ma funziona
      boolean empty = body.contains("<item_list/>");
      int begin = body.indexOf("<item_list>");
      if (begin > -1 || empty)
      {
        String directory = "";
        if (!empty)
        {
          int end = body.indexOf("</item_list>", begin);
          directory = body.substring(begin+"<item_list>".length(), end);

          Map<String,String> map = extractItems(directory);
          directory = serializeItems(map);
        }
        String macAddress = resourceName.substring(0, 12);
        macAddress = ContactaUtils.macAddressHexToText(macAddress);

        PhoneModel phone = inventoryService.findPhoneByMacAddress(macAddress);
        if (phone != null)
        {
          //log().info("setting for {} directory={}", macAddress, directory);
          phone.setDirectory(directory);
          inventoryService.updatePhone(phone);
        }
        else
        {
          log().info("cannot find phone with mac={}", macAddress);
        }
      }
  }

}
