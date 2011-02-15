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
package mic.contacta.webapp;

import mic.contacta.server.api.ContactaConstants;
import mic.contacta.server.spi.ContactaService;
import mic.organic.core.OrganicException;

import org.apache.commons.vfs.FileObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


/**
 * Description of the Class
 *
 * @author    mic
 * @version  $Revision: 641 $
 *
 */
@Service("uploadAction")
@Scope("request")
public class UploadAction extends AbstractContactaAction
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private ContactaService contactaService;

  private FileObject csvFile;
  private String importType;

  private String fileName;
  private String errorMessage;


  /**
   * @return the uploadList
   */
  public long getTstamp()
  {
    return System.currentTimeMillis();
  }


  /*
   *
   */
  public String csv() //throws Exception
  {
    if (csvFile != null)
    {
      fileName = csvFile.getName().getBaseName();

      try
      {
        boolean drop = getImportType().equals(ContactaConstants.PTOOL_IMPORT_NEW);
        contactaService.importFromWebappCsv(csvFile, drop);
      }
      catch (OrganicException e)
      {
        log().warn("Cannot import data from {}", csvFile.getName().getBaseName());
        log().warn(e.getMessage(), e);
        //this.addActionError(e.getMessage());
        return ERROR;
      }
    }
    else
    {
      log().info("no files imported");
    }

    return SUCCESS;
  }


  /*
   *
   */
  public FileObject getCsvFile()
  {
    return csvFile;
  }

  /*
   *
   */
  public void setCsvFile(FileObject csvFile)
  {
    this.csvFile = csvFile;
  }

  /*
   *
   */
  public void setImportType(String importType)
  {
    this.importType = importType;
  }

  /*
   *
   */
  public String getImportType()
  {
    return importType;
  }

  /*
   *
   */
  public String getFileName()
  {
    return fileName;
  }

  /*
   *
   */
  public void setFileName(String fileName)
  {
    this.fileName = fileName;
  }

  /*
   *
   */
  public String getErrorMessage()
  {
    return errorMessage;
  }

  /*
   *
   */
  public void setErrorMessage(String errorMessage)
  {
    this.errorMessage = errorMessage;
  }

}
