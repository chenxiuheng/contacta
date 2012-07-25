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

import java.util.List;

import org.apache.commons.vfs2.FileObject;

import mic.contacta.domain.Line;
import mic.organic.core.Component;
import mic.organic.core.OrganicException;


/**
 * @author michele.bianchi@gmail.com
 * @version $Revision: 616 $
 */
public interface ContactaService extends Component
{
  public static final String SERVICE_NAME = "contactaService";

  // TODO uhm... trovare un posto per queste
  public static final String EXTENSIONS_RELOAD = "extensions reload";
  public static final String SIP_RELOAD = "sip reload";
  public static final String RELOAD = "reload";


  /**
   * Send a string command to asterisk via manager socket, e.g. "sip reload"
   *
   * @param command like "sip reload"
   * @return TODO define return object
   */
  Object asteriskCommand(String command);


  /**
   * Configure the system, i.e. drop data, load csv and import data...
   *
   * This method must be called outside this service to support the transactional advice (and 4 good design;)
   */
  void importFromCsv();


  /**
   *
   * @param csvFile TODO
   * @param drop
   * @throws OrganicException
   */
  void importFromWebappCsv(FileObject csvFile, boolean drop) throws OrganicException;


  /**
   * Query PBX and list the status of the lines: up, ringing or free and who is connected to whom.
   *
   * @return the list of lines according to the accounts
   * @throws ContactaException
   */
  List<Line> lineStatus() throws ContactaException;

}
