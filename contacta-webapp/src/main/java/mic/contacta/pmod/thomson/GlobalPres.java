/* $Id: GlobalPres.java 672 2010-07-24 22:18:23Z michele.bianchi $
 *
 * Copyright(C) 2010 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.contacta.pmod.thomson;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
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

