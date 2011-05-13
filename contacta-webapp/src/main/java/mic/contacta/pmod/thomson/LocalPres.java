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
package mic.contacta.pmod.thomson;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import mic.contacta.pmod.common.AbstractTemplatePres;
import mic.contacta.server.TemplateService;


/**
 * handles the cfg file for one phone, that is its local cfg
 * it uses a template file and the variable substitutions is applied
 *
 * e.g., this is /thomson/config/ST2030S_001F9F16E7F8.txt
 *
 * @author mic
 * @created Jun 3, 2009
 */
public class LocalPres extends AbstractTemplatePres
{
  private FileObject confFo;


  /*
   *
   */
  public LocalPres(String name, TemplateService templateService, FileObject confFo)
  {
    super(name, templateService);

    this.confFo = confFo;
  }


  /*
   * @see mic.contacta.pmod.thomson.AbstractCfg#match(java.lang.String)
   */
  @Override
  public boolean match(String resourceName)
  {
    return StringUtils.startsWith(resourceName, getName()) && StringUtils.endsWith(resourceName, ".txt") && !StringUtils.endsWith(resourceName, ".ser");
  }


  /*
   * @see mic.contacta.pmod.thomson.AbstractCfg#nameToFo(java.lang.String)
   */
  @Override
  public FileObject nameToFo(String name) throws FileSystemException
  {
    name = name.substring(0, name.indexOf('_')+1)+"MAC.txt";
    fo = confFo.resolveFile(name);//, MIMETYPE_XML, null);
    return fo;
  }

}
