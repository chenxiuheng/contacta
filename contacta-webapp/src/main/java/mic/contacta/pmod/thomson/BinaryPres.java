/* $Id: BinaryPres.java 672 2010-07-24 22:18:23Z michele.bianchi $
 *
 * Copyright(C) 2010 [michele.bianchi@gmail.com]
 * All Rights Reserved
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

