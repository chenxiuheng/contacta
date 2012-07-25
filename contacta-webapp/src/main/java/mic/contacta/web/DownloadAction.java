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
package mic.contacta.web;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static mic.organic.core.OrganicConstants.*;

import mic.contacta.server.ImportService;
import mic.organic.vfs.OrganicVfs;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


/**
 *
 * @author mic
 * @created Dec 30, 2008
 */
@Service("downloadAction")
@Scope("request")
public class DownloadAction extends AbstractContactaAction
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  private String filename = "NO_NAME";
  private String contentType = MIMETYPE_BINARY;
  private long contentLength;
  private InputStream inputStream;

  @Autowired private OrganicVfs organicVfs;
  @Autowired private ImportService importService;

  private String fileType;


  /*
   *
   */
  public String getFilename()
  {
    return filename;
  }


  /*
   *
   */
  public void setFilename(String filename)
  {
    this.filename = filename;
  }


  /*
   *
   */
  public String getContentType()
  {
    return contentType;
  }


  /*
   *
   */
  public long getContentLength()
  {
    return contentLength;
  }


  /*
   *
   */
  public InputStream getInputStream() throws Exception
  {
    return inputStream;
  }


  /*
   *
   */
  public void setFileType(String fileType)
  {
    this.fileType = fileType;
  }


  /*
   *
   */
  public String getFileType()
  {
    return fileType;
  }


  /*
   *
   */
  protected void download(FileObject fo) throws FileSystemException
  {
    inputStream = fo.getContent().getInputStream();
    filename = fo.getName().getBaseName();
    contentType = fo.getContent().getContentInfo().getContentType();
    contentLength = fo.getContent().getSize();
  }


  /*
   *
   */
  protected void download(FileObject fo, String filename) throws FileSystemException
  {
    inputStream = fo.getContent().getInputStream();
    this.filename = filename;
    contentType = fo.getContent().getContentInfo().getContentType();
    contentLength = fo.getContent().getSize();
  }


  /*
   *
   */
  public String file()
  {
    try
    {
      FileObject fo = organicVfs.tempFileObject("boh/mime", ".zip");
      download(fo);
    }
    catch (Exception e)
    {
      log().warn(e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }


  /*
   *
   */
  public String csv()
  {
    try
    {
      FileObject fo = organicVfs.tempFileObject("text/csv", "UTF-8", ".csv");
      importService.exportToCsv(fo);
      SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
      download(fo, "data_" + formatter.format(new Date()) + ".csv");
    }
    catch (Exception e)
    {
      log().warn(e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }


  /*
   *
   */
  public String configuration()
  {
    try
    {
      String path = SystemUtils.getUserDir()+"/target/conf/";
      if(fileType.equals("dhcpd"))
      {
        path += fileType+".conf";
      }
      else
      {
        path += "asterisk/"+fileType+".conf";
      }
      FileObject fo = organicVfs.resolve(path);
      download(fo);
    }
    catch (Exception e)
    {
      log().warn(e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }

}