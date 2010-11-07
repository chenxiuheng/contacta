/* $Id: LocalPres.java 672 2010-07-24 22:18:23Z michele.bianchi $
 *
 * Copyright(C) 2010 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.contacta.pmod.polycom;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import mic.contacta.pmod.common.AbstractTemplatePres;
import mic.contacta.server.spi.TemplateService;

/**
 *
 * @author mic
 * @created May 29, 2010
 */
public class LocalPres extends AbstractTemplatePres
{

  /**
   * @param name
   * @param templateService
   */
  public LocalPres(String name, TemplateService templateService)
  {
    super(name, templateService);
    // TODO Auto-generated constructor stub
  }

  /*
   * @see mic.contacta.pmod.common.ProvisioningResource#nameToFo(java.lang.String)
   */
  @Override
  public FileObject nameToFo(String name) throws FileSystemException
  {
    // TODO Auto-generated method stub
    return null;
  }

}
