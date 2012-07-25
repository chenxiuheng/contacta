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
package mic.contacta.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import static org.testng.Assert.*;


/**
 *
 * @author mic
 * @created Mar 22, 2009
 */
public class Rot13Test
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  /**
   * @param abyte
   * @return
   */
  private String rot13(String in)
  {
    byte[] buff = new byte[in.length()];
    int i = 0;
    for (int b : in.getBytes())
    {
      int cap = b & 32;
      b &= ~cap;
      b = ((b >= 'A') && (b <= 'Z') ? ((b - 'A' + 13) % 26 + 'A') : b) | cap;

      buff[i] = (byte)(b);
      i++;
    }
    String out = new String(buff);
    log().debug("in={}, out={}", in, out);
    return out;
  }


  /**
   * @param abyte
   * @return
   */
  private String rot5(String in)
  {
    byte[] buff = new byte[in.length()];
    int i = 0;
    for (int b : in.getBytes())
    {
      b = ((b >= '0') && (b <= '9') ? ((b - '0' + 7) % 10 + '0') : b);

      buff[i] = (byte)(b);
      i++;
    }
    String out = new String(buff);
    i = Integer.parseInt(out);
    log().debug("in={}, i={}, out={}", new Object[] { in, i, Integer.toHexString(i)} );
    return out;
  }


  /*
   *
   */
  @Test
  public void testRot()
  {
    String out = rot13("XXXX");
    assertNotNull(out);

    boolean[] checks = new boolean[10000];
    for (int in = 1000; in < 10000; in++)
    {
      out = rot5(String.valueOf(in));
      assertNotNull(out);

      int j = Integer.parseInt(out);
      if (checks[j] == true)
      {
        log().info("DOPPIO: in={}, out={}", in, out);
      }
      else
      {
        checks[j] = true;
      }
    }
    int t = 0;
    for (int i = 0; i < 10000; i++)
    {
      t += checks[i] == true ? 1 : 0;
    }
    log().debug("true: {}/{}", t, 10000);
    assertEquals(t, 9000);
  }

}
