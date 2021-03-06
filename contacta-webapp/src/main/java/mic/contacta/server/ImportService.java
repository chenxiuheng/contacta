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
package mic.contacta.server;

import java.io.IOException;

import org.apache.commons.vfs2.FileObject;

import mic.organic.core.OrganicException;


/**
 * TODO documentme
 *
 * @author mic
 * @created Dec 29, 2007
 */
public interface ImportService
{
  public final static String SERVICE_NAME = "importService";


  /**
   * TODO documentme
   * @throws OrganicException
   */
  void importFromCsv(String bootFilename) throws OrganicException;


  /**
   *
   * @param csvFile
   * @throws IOException
   * @throws ContactaException
   */
  void importFromCsv(FileObject csvFile) throws OrganicException, IOException, ContactaException;


  /**
   *
   */
  FileObject exportToCsv(FileObject csvFile) throws OrganicException, IOException;
}