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
package mic.contacta.server.spi;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.vfs.FileObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import mic.contacta.server.api.ContactaConstants;
import mic.organic.core.OrganicException;


/**
 *
 *
 * @author mic
 * @created Dec 1, 2008
 */
@ContextConfiguration(locations = { ContactaConstants.TEST_IMPORT_SPRING })
public class ImportServiceTest extends AbstractProvisioningTests
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private ImportService importService;


  /*
   *
   */
  @Test
  public void testImportFromCsv() throws OrganicException
  {
    addSampleProducts();

    importService.importFromCsv("./src/test/resources/data.csv");

    int lines = 10;
    assertEquals(sipService.findAccountList().size(), lines);
    assertEquals(inventoryService.findPhoneList().size(), lines);
    assertEquals(addressbookService.personList().size(), 0);
    assertEquals(addressbookService.organizationList().size(), 0);
  }


  /*
   *
   */
  //@Test TODO
  public void testExportToCsv() throws OrganicException, IOException
  {
    importService.importFromCsv("./src/test/resources/data.csv");

    FileObject csvFile = organicVfs.tempFileObject();
    importService.exportToCsv(csvFile);

    FileObject startFile = organicVfs.resolve(System.getProperty("user.dir") + "/src/test/resources/data.csv");

    //log().info(readFileAsString(startFile.getName().getPath()));
    //log().info(readFileAsString(csvFile.getName().getPath()));

    assertEquals(csvFile.getContent().getSize(), startFile.getContent().getSize());
    assertEquals(readFileAsString(csvFile.getName().getPath()).substring(10, 30), readFileAsString(startFile.getName().getPath()).substring(10, 30));
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
