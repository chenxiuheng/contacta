/* $Id: LocalInfCfg.java 672 2010-07-24 22:18:23Z michele.bianchi $
 *
 * Copyright(C) 2010 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.contacta.pmod.thomson;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import mic.contacta.pmod.common.AbstractTemplatePres;
import mic.contacta.server.spi.TemplateService;

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
