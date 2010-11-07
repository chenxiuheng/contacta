/* $Id: TemplatePres.java 672 2010-07-24 22:18:23Z michele.bianchi $
 *
 * Copyright(C) 2010 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.contacta.pmod.thomson;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import mic.contacta.pmod.common.AbstractTemplatePres;
import mic.contacta.server.spi.TemplateService;


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
