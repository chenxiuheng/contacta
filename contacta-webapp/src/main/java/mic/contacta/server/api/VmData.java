/* $Id: VmData.java 1488 2008-06-03 10:36:08Z michele.bianchi@gmail.com $
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
package mic.contacta.server.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author mic
 * @created May 25, 2008
 */
public class VmData
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  /*
   *
   */
  FilenameFilter filter = new FilenameFilter()
  {
    public boolean accept(File dir, String name)
    {
      return name.endsWith(".wav");
    }
  };

  private String basedir;
  private File file;
  private String pathFile; // urlfile
  private String callerId;
  private String calledId;
  private String duration;
  private String date;

  private String filedata = "111111";
  private String filehora = "222222";
  private String filesize;
  private String contentType;
  private String createdOn;
  private String title;

  private String user;


  /*
   *
   */
  public VmData(String basedir)
  {
    this.basedir = basedir == null ? ContactaConstants.VAR_SPOOL_ASTERISK_VOICEMAIL : basedir;
  }


  /**
   *
   */
  public VmData(String user, String fullfilename, String filedata2, String filehora2, String filesize2, String call_format, String datacreated2, String call_calledID, String title)
  {
    this(null);
    //log().info("ricevuto call_calledID " + call_calledID);

    this.user = user;
    this.title = title;

    pathFile = fullfilename;
    filedata = filedata2;
    filehora = filehora2;
    duration = filesize2;
    contentType = call_format;
    createdOn = datacreated2;
    this.setCalledId(call_calledID);
    //log().info("settato call_calledID " + this.getCalledId);

    file = new File(pathFile);
  }


  /**
   * @param nomefile
   * @return
   */
  public static VmData buildFromFilename(String nomefile)
  {
    String[] components = nomefile.split("_");
    String account = components[0].substring(components[0].lastIndexOf('/')+1);  // TODO questa e' una porcata
    String time = components[1];
    String[] time_comp = time.split("-");
    String filedata = time_comp[0];
    String filehora = time_comp[1];
    String call_format = nomefile.substring(nomefile.length() - 3, nomefile.length());
    String filesize = ContactaConstants._100; // TODO interrogazioen con httpclient
    String call_calledID = ContactaConstants._999;
    String datacreated = components[1];
    String title = ContactaConstants.MESSAGGIO_VOCALE;
    String fullfilename = /*asteriskFileDir + "/" +*/ nomefile;
    VmData vmData = new VmData(account, fullfilename, filedata, filehora, filesize, call_format, datacreated, call_calledID, title);
    return vmData;
  }



  /**
   * @return the user
   */
  public String getUser()
  {
    return user;
  }


  /**
   * @param user the user to set
   */
  public void setUser(String user)
  {
    this.user = user;
  }


  /**
   * @return the file
   */
  public File getFile()
  {
    return file;
  }


  /**
   * @return the calledId
   */
  public String getCalledId()
  {
    return calledId;
  }


  /**
   * @param calledId the calledId to set
   */
  public void setCalledId(String calledId)
  {
    this.calledId = calledId;
  }


  /**
   * @return the title
   */
  public String getTitle()
  {
    return title;
  }


  /**
   * @param title the title to set
   */
  public void setTitle(String title)
  {
    this.title = title;
  }


  /**
   * @return the callerId
   */
  public String getCallerId()
  {
    return callerId;
  }


  /**
   * @return the duration
   */
  public String getDuration()
  {
    return duration;
  }


  /**
   * @return the date
   */
  public String getDate()
  {
    return date;
  }


  /**
   * @return the basedir
   */
  public String getBasedir()
  {
    return basedir;
  }


  /**
   * @return the pathFile
   */
  public String getPathFile()
  {
    return pathFile;
  }


  /**
   * @return the filedata
   */
  public String getFiledata()
  {
    return filedata;
  }


  /**
   * @return the filehora
   */
  public String getFilehora()
  {
    return filehora;
  }


  /**
   * @return the filesize
   */
  public String getFilesize()
  {
    return filesize;
  }


  /**
   * @return the callformat
   */
  public String getContentType()
  {
    return contentType;
  }


  /**
   * @return the createdOn
   */
  public String getCreatedOn()
  {
    return createdOn;
  }


  /*
   *
   */
  public boolean locateMessage(String interno, String utenza) throws ContactaException
  {
    String inboxFolder = basedir+"/"+utenza+"/"+interno+"/"+ContactaConstants.INBOX;
    String filereg = "SIP-" + interno + "-";

    log().info("interno passato {}", interno);
    log().info("filereg {}", filereg);

    File f = new File(inboxFolder); // current dir
    if (f.exists() == false || f.isDirectory() == false)
    {
      String msg = "No files in " + inboxFolder;
      log().warn(msg);
      throw new ContactaException(msg);
    }

    String file_list[] = f.list(filter);
    if (file_list == null || file_list.length == 0)
    {
      return false;
    }
    Arrays.sort(file_list);
    String nomefile = file_list[file_list.length - 1]; // name of the registered file
    log().info("nomefile {}", nomefile);
    String completenf = inboxFolder + "/" + nomefile;
    pathFile = completenf;
    log().info("Found registered file {}", pathFile);

    file = new File(pathFile);
    if (file == null || file.exists() == false || file.canRead() == false)
    {
      throw new ContactaException("cannot find path "+pathFile);
    }

    contentType = pathFile.substring(pathFile.length() - 3, pathFile.length());

    String txtfile = pathFile.substring(0, pathFile.length() - 3) + "txt";
    log().info("Apro txt file {}", txtfile);
    try
    {
      BufferedReader in = new BufferedReader(new FileReader(txtfile));
      for (String str = in.readLine(); str != null; str = in.readLine())
      {
        if (str.indexOf(ContactaConstants.CALLERID) >= 0)
        {
          String[] result = str.split("=");
          callerId = result[1];
          log().info("callerId {}", callerId);
        }
        else if (str.indexOf(ContactaConstants.ORIGDATE) >= 0)
        {
          String[] result = str.split("=");
          date = result[1];
        }
        else if (str.indexOf(ContactaConstants.DURATION) >= 0)
        {
          String[] result = str.split("=");
          duration = result[1];
        }
      }
      in.close();
    }
    catch (IOException e)
    {
      log().warn(e.getMessage(), e);
      return false;
    }
    return true;
  }


  /*
   *
   */
  public boolean writeHtml(PrintWriter writer) throws IOException
  {
    if (file.exists() && file.canRead())
    {
      writer.println("<html>");
      writer.println("<head></head>");
      writer.println("<body><pre>");
      writer.println("Found_OK");
      writer.println("nomefile=" + pathFile);
      writer.println("duration=" + duration);
      writer.println("callerId=" + callerId);
      writer.println("toURL=" + file.toURI().toString());
      writer.println("lastmodified=" + file.lastModified());
      writer.println("date=" + date);
      writer.println("</pre></body>");
      writer.println("</html>");
      // VoiceMessage voiceMessage =
      // contactaService.processVoiceMessage(pathFile);
      // InputStream inputStream = voiceMessage.getInputStream();
      // String ack = voiceMessage.getAcknowledge();

      // InputStream inputStream = new FileInputStream(file);
      // IOUtils.copy(inputStream, response.getOutputStream());

      // response.addHeader("contacta-ack");
      return true;
    }
    else
    {
      writer.println("cannot find: " + pathFile);
      return false;
    }
  }
}
