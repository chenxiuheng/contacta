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

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import mic.contacta.pmod.common.ProvisioningResource;


/**
 * handles all the roms files, that is usually binary files
 * they are stored outside the jar or even the assembly of contacta
 * download them from the vendor website
 *
 * @author mic
 * @created Jun 3, 2009
 */
public class BinaryPres extends ProvisioningResource
{
  private FileObject sipromFo;


  /*
   *
   */
  public BinaryPres(String name, FileObject sipromFo)
  {
    super(name);

    this.sipromFo = sipromFo;
  }


  /*
   * @see mic.contacta.pmod.thomson.AbstractCfg#nameToFo(java.lang.String)
   */
  @Override
  public FileObject nameToFo(String name) throws FileSystemException
  {
    fo = sipromFo.resolveFile(name);
    return fo;
  }
}

