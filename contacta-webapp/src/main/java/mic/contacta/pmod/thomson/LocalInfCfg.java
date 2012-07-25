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
package mic.contacta.pmod.thomson;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import mic.contacta.pmod.common.AbstractTemplatePres;
import mic.contacta.server.TemplateService;

/**
 *
 * @author mic
 * @created Jul 20, 2010
 */
public class LocalInfCfg extends AbstractTemplatePres
{
  private FileObject confFo;


  /*
   *
   */
  public LocalInfCfg(String name, TemplateService templateService, FileObject confFo)
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
    return StringUtils.startsWith(resourceName, getName()) && StringUtils.endsWith(resourceName, ".inf") && !StringUtils.endsWith(resourceName, ".ser");
  }


  /*
   * @see mic.contacta.pmod.thomson.AbstractCfg#nameToFo(java.lang.String)
   */
  @Override
  public FileObject nameToFo(String name) throws FileSystemException
  {
    name = "st2030s.inf";//name.substring(0, name.indexOf('_')+1)+"MAC.txt";
    fo = confFo.resolveFile(name);//, MIMETYPE_XML, null);
    return fo;
  }

}
