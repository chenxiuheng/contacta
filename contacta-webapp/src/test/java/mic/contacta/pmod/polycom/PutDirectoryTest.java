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
import java.util.Map;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileUtil;
import org.apache.commons.vfs.VFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import static org.testng.Assert.*;


/**
 *
 * @author mic
 * @created Mar 22, 2009
 */
public class PutDirectoryTest
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  private FileSystemManager vfs;


  /*
   *
   */
  public PutDirectoryTest() throws FileSystemException
  {
    vfs = VFS.getManager();
  }


  /*
   *
   */
  private String readBody(String resName) throws IOException
  {
    FileObject fo = vfs.resolveFile(resName);
    String body = new String(FileUtil.getContent(fo));
    return body;
  }


  /*
   *
   */
  @Test
  public void testItemList() throws IOException
  {
    PolycomDirectory polycomDirectory = new PolycomDirectory();

    String body = readBody("res:put-directory.xml");
    assertNotNull(body);
    body = polycomDirectory.extractItemsAsString(body);
    assertNotNull(body);

    Map<String,String> map = polycomDirectory.extractItems(body);
    assertEquals(map.size(), 2);

    String outBody = polycomDirectory.serializeItems(map);
    log().debug("outBody:\n{}", outBody);
  }

}
