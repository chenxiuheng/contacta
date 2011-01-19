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
package mic.contacta.pmod.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.servlet.ServletResponse;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.VFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mic.contacta.server.spi.TemplateService;
import mic.organic.core.OrganicException;


/**
 * handles the cfg file for one phone, that is its local cfg
 * it uses a template file and the variable substitutions is applied
 *
 * @author mic
 * @created Jun 3, 2009
 */
public abstract class AbstractTemplatePres extends ProvisioningResource
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
  private TemplateService templateService;


  /*
   *
   */
  public AbstractTemplatePres(String name, TemplateService templateService)
  {
    super(name);
    setTemplate(true);
    this.templateService = templateService;
  }


  /*
   *
   */
  public String transform(Map<String, Object> params, FileObject fo) throws OrganicException
  {
    String path = fo.getName().getPath();
    String body = null;
    try
    {
      // = templateService.getTemplateBaseFo().resolveFile(path);
      body = templateService.generate(fo, params);
      log().debug("generated body from: {}, length={}", path, body.length());
    }
    catch (Exception e)
    {
      log().warn("cannot transform: {}", path);
      throw new OrganicException("cannot transform: "+path, e);
    }
    return body;
  }


  /*
   *
   */
  private void debugWriteBody(String resourceName, String body)
  {
    try
    {
      FileObject ttt = VFS.getManager().resolveFile("/tmp/XXXX.txt");
      ttt.getContent().getOutputStream().write(body.getBytes());
      ttt.close();
    }
    catch (IOException e)
    {
      log().warn("cannot write a copy of {}: {}", resourceName, e.getMessage());
    }
  }


  /*
   * @see mic.contacta.pmod.thomson.AbstractCfg#write(java.lang.String, javax.servlet.ServletResponse)
   */
  @Override
  public void write(String resourceName, long ts, ServletResponse response, Map<String, Object> params) throws IOException
  {
    String body = null;
    FileObject fo = null;
    try
    {
      fo = nameToFo(resourceName);

      long snTs = fo.getContent().getLastModifiedTime();
      if (ts != 0)
      {
        snTs = Math.max(snTs, ts);
      }
      String configSn = sdf.format(new Date(snTs));
      log().info("configSn={}", configSn);
      params.put("lastModified", configSn);
      body = transform(params, fo);
    }
    catch (FileSystemException e)
    {
      log().error("cannot load {}", resourceName);
    }
    catch (OrganicException e)
    {
      log().warn(e.getMessage(), e);
    }

    if (body != null)
    {
      response.setContentType(getContentType());
      response.setContentLength(body.length());
      response.getOutputStream().write(body.getBytes());

      //debugWriteBody(resourceName, body);
    }
  }

}

