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
package mic.contacta.pmod.polycom;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import mic.contacta.pmod.common.AbstractTemplatePres;
import mic.contacta.server.TemplateService;

/**
 *
 * @author mic
 * @created May 29, 2010
 */
public class GlobalPres extends AbstractTemplatePres
{



  /**
   * @param name
   * @param templateService
   */
  public GlobalPres(String name, TemplateService templateService)
  {
    super(name, templateService);
    // TODO Auto-generated constructor stub
  }


  /*
   * @see mic.contacta.pmod.common.ProvisioningResource#nameToFo(java.lang.String)
   */
  @Override
  public FileObject nameToFo(String resourceName) throws FileSystemException
  {
    FileObject fo = null;
    return fo;
  }


}
