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
package mic.contacta.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs.FileObject;

import mic.organic.core.OrganicException;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;


/**
 * Description of the Class
 *
 * @author mic
 * @version $Revision: 616 $
 */
public class CsvUtil
{
  /**
   * @throws IOException
   */

  public static List<String[]> getCsvValues(FileObject fo) throws IOException, OrganicException
  {
    File tempFile = File.createTempFile("clean", ".tmp");
    tempFile.deleteOnExit();

    File dirtyFile = new File(fo.getName().getPath());

    cleanCsv(dirtyFile, tempFile);

    CSVReader reader = new CSVReader(new FileReader(tempFile));
    List<String[]> entries = reader.readAll();

    return entries;
  }


  /**
   * @param fo
   * @throws IOException
   */
  public static void cleanCsv(File dirtyFile, File cleanFile) throws IOException
  {
    BufferedReader br = new BufferedReader(new FileReader(dirtyFile));
    BufferedWriter bw = new BufferedWriter(new FileWriter(cleanFile));

    boolean first = true;
    String line;
    while((line = br.readLine()) != null)
    {
      line = line.trim();

      if(line.length() > 0)
      {
        if(!first)
        {
          bw.newLine();
        }
        else
        {
          first = false;
        }

        String[] values = line.split(",");

        for(int i=0; i<values.length; i++)
        {
          values[i] = values[i].trim();
        }

        String cleanLine = StringUtils.join(values,",");

        bw.write(cleanLine);
      }
    }

    bw.flush();
    bw.close();
    br.close();
  }


  /**
   *
   */
  public static FileObject writeToCsv(List<String[]> lineList, FileObject csvFile) throws OrganicException, IOException
  {
    CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile.getName().getPath()));
    csvWriter.writeAll(lineList);
    csvWriter.flush();
    csvWriter.close();

    return csvFile;
  }

}
