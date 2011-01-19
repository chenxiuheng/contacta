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
import java.util.Map;

import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 *
 * @author mic
 * @created Jun 3, 2009
 */
public abstract class ProvisioningResource
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  private String name;
  private boolean template;
  private String body;
  private String contentType;
  private String contentEncoding;

  protected FileObject fo;


  /**
   *
   * @param name
   * @param template
   */
  public ProvisioningResource(String name)
  {
    this.name = name;
  }


  /**
   * @return
   */
  public boolean needParams()
  {
    return template;
  }


  /**
   * @param needParams
   */
  protected void setNeedParams(boolean needParams)
  {
    this.template = needParams;
  }


  /**
   *
   * @param resourceName
   * @return
   */
  public boolean match(String resourceName)
  {
    return StringUtils.endsWith(resourceName, name);
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }


  /**
   * @return the template
   */
  public boolean getTemplate()
  {
    return template;
  }


  /**
   * @param template the template to set
   */
  public void setTemplate(boolean template)
  {
    this.template = template;
  }


  /**
   * @return the contentType
   */
  public String getContentType()
  {
    return contentType;
  }


  /**
   * @return the contentEncoding
   */
  public String getContentEncoding()
  {
    return contentEncoding;
  }


  /**
   * @return the fo
   */
  public FileObject getFo()
  {
    return fo;
  }


  /**
   *
   * @return
   */
  String getBody()
  {
    return body;
  }


  /**
   *
   * @return
   * @throws FileSystemException
   */
  public abstract FileObject nameToFo(String name) throws FileSystemException;


  /**
   * write the cfg file to the ServletResponse
   * @param resourceName TODO
   * @param ts TODO
   * @param response
   * @param params TODO
   * @throws IOException
   */
  public void write(String resourceName, long ts, ServletResponse response, Map<String, Object> params) throws IOException
  {
    FileObject fo = null;
    try
    {
      fo = nameToFo(resourceName);
    }
    catch (FileSystemException e)
    {
      log().error("cannot load {}", resourceName);
    }
    response.setContentType(contentType);
    response.setContentLength((int)(fo.getContent().getSize()));
    FileUtil.writeContent(fo, response.getOutputStream());
  }

}

