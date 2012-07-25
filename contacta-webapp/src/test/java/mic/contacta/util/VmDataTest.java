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

import java.io.IOException;

import org.apache.commons.vfs2.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import mic.contacta.server.ContactaConstants;
import mic.contacta.server.ContactaException;


import static org.testng.Assert.*;


/**
 *
 * @author mic
 * @created May 25, 2008
 */
public class VmDataTest
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  protected FileSystemManager vfs;
  private FileObject basedir;


  /*
   *
   */
  @BeforeClass
  public void beforeClass() throws IOException
  {
    vfs = VFS.getManager();
    basedir = vfs.resolveFile("file:/tmp/contacta/var/spool/asterisk/voicemail");
    basedir.createFolder();
  }


  /*
   *
   */
  @AfterClass
  public void afterClass() throws IOException
  {
    //basedir.delete(new AllFileSelector());
  }


  /*
   *
   */
  public FileObject resolveResource(String resourceName) throws FileSystemException
  {
    resourceName = "res:"+resourceName.substring(1);
    FileObject fo = vfs.resolveFile(resourceName);
    return fo;
  }


  /**
   * @param utenza
   * @param interno
   * @throws FileSystemException
   */
  private FileObject mkInbox(String utenza, String interno) throws FileSystemException
  {
    FileObject inboxPath = basedir.resolveFile(utenza+"/"+interno+"/"+ContactaConstants.INBOX);
    inboxPath.createFolder();
    return inboxPath;
  }


  /**
   * @param inboxPath
   * @throws IOException
   */
  private void createSampleVm(FileObject inboxPath, String name) throws IOException
  {
    inboxPath.resolveFile(name+".wav").createFile();

    FileObject src = resolveResource("/samples/msg.txt");
    FileObject dst = inboxPath.resolveFile(name+".txt");
    FileUtil.copyContent(src, dst);
  }


  /*
   *
   */
  //@Test
  public void testLastMessage() throws IOException, ContactaException
  {
    String utenza = "utenti";
    String interno = "400";
    FileObject inboxPath = mkInbox(utenza, interno);

    createSampleVm(inboxPath, "msg0000");
    createSampleVm(inboxPath, "msg0001");
    createSampleVm(inboxPath, "msg0012");

    VmData vmData = new VmData(basedir.getName().getPathDecoded());
    assertNotNull(vmData);

    boolean r = vmData.locateMessage(interno, utenza);
    assertTrue(r);
    assertEquals(vmData.getCallerId(), "\"demo\" <300>");
    assertEquals(vmData.getDuration(), "6");
    assertEquals(vmData.getDate(), "Sat May 24 07:54:50 PM CEST 2008");
    assertEquals(vmData.getContentType(), "wav");
  }


  /*
   *
   */
  @Test
  public void testWithoutDir() throws IOException
  {
    String utenza = "utenti";
    String interno = "402";

    VmData vmData = new VmData(basedir.getName().getPathDecoded());
    try
    {
      vmData.locateMessage(interno, utenza);
      assertTrue(false);
    }
    catch(ContactaException e)
    {
      assertNotNull(e);
    }
  }


  /*
   *
   */
  //@Test
  public void testWithoutMsg() throws IOException, ContactaException
  {
    String utenza = "utenti";
    String interno = "401";
    mkInbox(utenza, interno);

    VmData vmData = new VmData(basedir.getName().getPathDecoded());
    boolean r = vmData.locateMessage(interno, utenza);
    assertFalse(r);
  }

}
