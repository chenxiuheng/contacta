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

import static org.testng.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import mic.contacta.util.CsvUtil;
import mic.organic.core.OrganicException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;


/**
 * Class providing unit testing for the Organic.
 *
 * @author    mic
 * @version   $Revision: 616 $
 */
public class CsvUtilTest
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  /*
   *
   */
  @Test
  public void testCleanCsvTrim() throws OrganicException, IOException
  {
    File dirtyFile = File.createTempFile("dirty", ".tmp");
    File cleanFile = File.createTempFile("clean", ".tmp");

    BufferedWriter dbw = new BufferedWriter(new FileWriter(dirtyFile));
    dbw.write("\" \"\"spip450 mic\"\"\", 401 , 401 , \"true\" ,3,11, \"interni\" ,      \"00:04:f2:1f:98:bd\" ,\"POLYCOM\",\"SPIP_450\",7001,501,,,,,,,,,,");
    dbw.newLine();
    dbw.write("         \" \"\"spip450 mic\"\"\", 401 , 401 , \"true\" ,3,11, \"interni\" ,      \"00:04:f2:1f:98:bd\" ,\"POLYCOM\",\"SPIP_450\",7001,501,,,,,,,,,,      ");
    dbw.newLine();

    dbw.flush();
    dbw.close();

    //log().info(readFileAsString(dirtyFile.getAbsolutePath()));

    CsvUtil.cleanCsv(dirtyFile, cleanFile);

    //log().info(readFileAsString(cleanFile.getAbsolutePath()));

    String expected = "\" \"\"spip450 mic\"\"\",401,401,\"true\",3,11,\"interni\",\"00:04:f2:1f:98:bd\",\"POLYCOM\",\"SPIP_450\",7001,501\n";
    expected += "\" \"\"spip450 mic\"\"\",401,401,\"true\",3,11,\"interni\",\"00:04:f2:1f:98:bd\",\"POLYCOM\",\"SPIP_450\",7001,501";

    assertEquals(readFileAsString(cleanFile.getAbsolutePath()), expected);

    dirtyFile.delete();
    cleanFile.delete();
  }


  /*
   *
   */
  @Test
  public void testCleanCsvEmptyLines() throws OrganicException, IOException
  {
    File dirtyFile = File.createTempFile("dirty", ".tmp");
    File cleanFile = File.createTempFile("clean", ".tmp");

    BufferedWriter dbw = new BufferedWriter(new FileWriter(dirtyFile));
    dbw.newLine();
    dbw.newLine();
    dbw.write("\" \"\"spip450 mic\"\"\", 401 , 401 , \"true\" ,3,11, \"interni\" ,      \"00:04:f2:1f:98:bd\" ,\"POLYCOM\",\"SPIP_450\",7001,501,,,,,,,,,,");
    dbw.newLine();
    dbw.newLine();
    dbw.newLine();
    dbw.write("         \" \"\"spip450 mic\"\"\", 401 , 401 , \"true\" ,3,11, \"interni\" ,      \"00:04:f2:1f:98:bd\" ,\"POLYCOM\",\"SPIP_450\",7001,501,,,,,,,,,,      ");
    dbw.newLine();
    dbw.newLine();
    dbw.newLine();
    dbw.newLine();

    dbw.flush();
    dbw.close();

    //log().info(readFileAsString(dirtyFile.getAbsolutePath()));

    CsvUtil.cleanCsv(dirtyFile, cleanFile);

    //log().info(readFileAsString(cleanFile.getAbsolutePath()));

    String expected = "\" \"\"spip450 mic\"\"\",401,401,\"true\",3,11,\"interni\",\"00:04:f2:1f:98:bd\",\"POLYCOM\",\"SPIP_450\",7001,501\n";
    expected += "\" \"\"spip450 mic\"\"\",401,401,\"true\",3,11,\"interni\",\"00:04:f2:1f:98:bd\",\"POLYCOM\",\"SPIP_450\",7001,501";

    assertEquals(readFileAsString(cleanFile.getAbsolutePath()), expected);

    dirtyFile.delete();
    cleanFile.delete();
  }


  /*
   *
   */
  private String readFileAsString(String filePath) throws java.io.IOException
  {
    StringBuffer fileData = new StringBuffer(1000);
    BufferedReader reader = new BufferedReader(
        new FileReader(filePath));
    char[] buf = new char[1024];
    int numRead=0;
    while((numRead=reader.read(buf)) != -1){
      fileData.append(buf, 0, numRead);
    }
    reader.close();
    return fileData.toString();
  }
}
