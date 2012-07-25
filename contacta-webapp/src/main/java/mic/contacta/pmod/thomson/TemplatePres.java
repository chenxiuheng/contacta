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

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import mic.contacta.pmod.common.AbstractTemplatePres;
import mic.contacta.server.TemplateService;


/**
 * handles the cfg file for one phone, that is its local cfg
 * it uses a template file and the variable substitutions is applied
 *
 * @author mic
 * @created Jun 3, 2009
 */
public class TemplatePres extends AbstractTemplatePres
{

  private FileObject confFo;


  /*
   *
   */
  public TemplatePres(String name, TemplateService templateService, FileObject confFo)
  {
    super(name, templateService);

    this.confFo = confFo;
  }


  /**
   * @param st2030sInf
   * @param templateService
   * @param confFo2
   * @param b
   */
  public TemplatePres(String name, TemplateService templateService, FileObject confFo, boolean needParams)
  {
    this(name, templateService, confFo);

    setNeedParams(needParams);
  }


  /*
   * @see mic.contacta.pmod.thomson.AbstractCfg#nameToFo(java.lang.String)
   */
  @Override
  public FileObject nameToFo(String name) throws FileSystemException
  {
    fo = confFo.resolveFile(name);
    return fo;
  }

}
