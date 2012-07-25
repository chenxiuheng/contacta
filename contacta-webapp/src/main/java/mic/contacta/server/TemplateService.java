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
import java.util.Map;

import javax.script.ScriptException;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;

import mic.organic.core.Service;


/**
 * Generate files from starting from a template in located in a base directory.
 * It saves the files in an output base directory.
 *
 * @author mic
 * @created Jan 1, 2009
 */
public interface TemplateService extends Service
{
  String SERVICE_NAME = "templateService";

  /**
   * This is a VFS url
   *
   * @param templateUrl the templateUrl to set
   */
  //void setTemplateUrl(String templateUrl);


  /**
   * This is a VFS url, if it starts with ./ it uses the environment userDir as base path
   *
   * @param outputUrl the outputUrl to set
   */
  //void setOutputUrl(String outputUrl);


  /**
   * This is a VFS url
   *
   * @return
   */
  FileObject getTemplateBaseFo();


  /**
   * @param confIn
   * @param confOut
   * @param params
   * @throws FileSystemException
   * @throws ScriptException
   * @throws IOException
   */
  void generate(String confIn, String confOut, Map<String, Object> params) throws ScriptException, IOException;


  /**
   * as the generate, but at the end substitute the "$*" in "$" to support asterisk variable substitution notation in extension.conf
   */
  void generate(String confIn, String confOut, Map<String, Object> params, boolean dollarstar) throws ScriptException, IOException;


  /**
   * Generate a string from a template fo.
   *
   * @param template the template fo
   * @param params the parameters used by the template
   * @return thh instantiated string
   * @throws ScriptException
   * @throws IOException
   */
  String generate(FileObject template, Map<String, Object> params) throws ScriptException, IOException;


}
