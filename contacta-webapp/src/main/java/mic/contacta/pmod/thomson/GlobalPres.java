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
import mic.contacta.pmod.common.ProvisioningResource;


/**
 * handles all the cfg files common to all phones, that is the global configuration
 *
 * @author mic
 * @created Jun 3, 2009
 */
public class GlobalPres extends ProvisioningResource
{
  private FileObject globalFo;


  /*
   *
   */
  public GlobalPres(String name, FileObject globalFo)
  {
    super(name);

    this.globalFo = globalFo;
  }


  /*
   * @see mic.contacta.pmod.thomson.AbstractCfg#nameToFo(java.lang.String)
   */
  @Override
  public FileObject nameToFo(String name) throws FileSystemException
  {
    fo = globalFo.resolveFile(name);//, MIMETYPE_XML, null);
    return fo;
  }
}

