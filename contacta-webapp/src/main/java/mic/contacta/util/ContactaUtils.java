/* $Id: PtoolServer.java 27 2008-12-17 21:39:10Z michele.bianchi@gmail.com $
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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 *
 * @author mic
 * @created Jan 1, 2009
 */
public class ContactaUtils
{
  static private Logger logger;
  static protected Logger log()  { if (ContactaUtils.logger == null) ContactaUtils.logger = LoggerFactory.getLogger(ContactaUtils.class);  return ContactaUtils.logger; }


  /**
   * @param abyte
   * @return
   */
  public static String rot5(String in)
  {
    if (StringUtils.isBlank(in))
    {
      return null;
    }
    byte[] buff = new byte[in.length()];
    int i = 0;
    for (int b : in.getBytes())
    {
      b = ((b >= '0') && (b <= '9') ? ((b - '0' + 7) % 10 + '0') : b);

      buff[i] = (byte)(b);
      i++;
    }
    String out = new String(buff);
    try
    {
      i = Integer.parseInt(out);
      //log().info("in={}, out={}", in, out);
      return Integer.toHexString(i);
    }
    catch(NumberFormatException e)
    {
      // this is for the utest, they use string, not nr
      return in;
    }
  }


  /**
   * check the mac address: 12 char hex string, if separated by : strip it
   */
  public static String macAddressTextToHex(String macAddress)
  {
    if (macAddress.indexOf(':') > 0)
    {
      macAddress = StringUtils.replace(macAddress, ":", "");//macAddress.replaceAll(":", "");
    }
    return macAddress.toLowerCase();
  }


  /**
   * check the mac address: 12 char hex string, if separated by : strip it
   */
  public static String macAddressHexToText(String numeric)
  {
    String macAddress = numeric.substring(0, 2);
    for(int i = 1; i<6; i++)
    {
      macAddress += ":" + numeric.substring(i*2, i*2+2);
    }

    return macAddress.toLowerCase();
  }


  /**
   * TODO
   *
   * @param string
   * @return
   */
  public static String validateMacAddress(String macAddress)
  {
    return macAddress.toLowerCase();
  }


  /*
   *
   */
  public static String passwordToPin(String password)
  {
    if (StringUtils.isBlank(password))
    {
      return password;
    }
    char[] pwd = password.toCharArray();
    char[] pin = new char[pwd.length];

    int index = 0;
    for(char c : pwd)
    {
      char i = c;

      if(i>='0' && i<= '9')
      {
        pin[index] = c;
      }
      else if(i >= 'A' && i <= 'Z')
      {
        if(i<'D')
        {
          pin[index] = '2';
        }
        else if(i<'G')
        {
          pin[index] = '3';
        }
        else if(i<'J')
        {
          pin[index] = '4';
        }
        else if(i<'M')
        {
          pin[index] = '5';
        }
        else if(i<'P')
        {
          pin[index] = '6';
        }
        else if(i<'T')
        {
          pin[index] = '7';
        }
        else if(i<'W')
        {
          pin[index] = '8';
        }
        else if(i<='Z')
        {
          pin[index] = '9';
        }
      }
      else if(i >= 'a' && i <= 'z')
      {
        if(i<'d')
        {
          pin[index] = '2';
        }
        else if(i<'g')
        {
          pin[index] = '3';
        }
        else if(i<'j')
        {
          pin[index] = '4';
        }
        else if(i<'m')
        {
          pin[index] = '5';
        }
        else if(i<'p')
        {
          pin[index] = '6';
        }
        else if(i<'t')
        {
          pin[index] = '7';
        }
        else if(i<'w')
        {
          pin[index] = '8';
        }
        else if(i<='z')
        {
          pin[index] = '9';
        }
      }
      else
      {
        pin[index] = '0';
      }

      index++;
    }

    return new String(pin);
  }


  /**
   * @param person
   * @param login
   * @return
   *
  public static String mkCallerId(PersonModel person, String login)
  {
    String callerId = "\""+person.getLastName()+" "+person.getFirstName()+"\""+" <"+login+">";
    return callerId;
  }*/


  /**
   * @param displayName
   * @param login
   * @return
   */
  public static String mkCallerId(String displayName, String login)
  {
    String callerId = "\""+displayName+"\""+" <"+login+">";
    return callerId;
  }


}
