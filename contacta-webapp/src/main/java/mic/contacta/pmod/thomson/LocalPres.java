/* $Id: LocalPres.java 672 2010-07-24 22:18:23Z michele.bianchi $
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
