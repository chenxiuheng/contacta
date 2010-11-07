/* $Id: DownloadItest.java 1319 2008-04-29 20:34:26Z michele.bianchi@gmail.com $
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
package mic.contacta.webapp;

import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.testng.annotations.*;
import static org.testng.AssertJUnit.*;


/**
 *
 * @author mic
 * @created Apr 29, 2008
 */
public class DownloadItest
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  @Test
  public void testDownload() throws Exception
  {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    HttpDownloader downloader = new HttpDownloader();
    downloader.copyUrl("http://localhost:8082/contacta/download/etc/resolv.conf", outputStream);

    assertTrue(true);
  }
}
