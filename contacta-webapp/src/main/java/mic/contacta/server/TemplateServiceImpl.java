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
package mic.contacta.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mic.organic.scripting.ScriptRunner;
import mic.organic.vfs.OrganicVfs;


/**
 * Generate files from starting from a template in located in a base directory.
 * It saves the files in an output base directory.
 *
 * @author mic
 * @created Jan 1, 2009
 */
@Service(TemplateService.SERVICE_NAME)
public class TemplateServiceImpl implements TemplateService
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private OrganicVfs organicVfs;
  @Autowired private ScriptRunner scriptRunner;
  @Autowired private ContactaConfiguration configuration;

  //private String templateUrl = "res:conf";
  //private String outputUrl = "./target/conf";
  private FileObject templateBaseFo;
  private FileObject outputFo;


  /*
   *
   */
  public TemplateServiceImpl()
  {
    super();
  }


  /*
   * @see mic.contacta.server.ptool.TemplateService#getTemplateUrl()
   */
  @Override
  public FileObject getTemplateBaseFo()
  {
    return templateBaseFo;
  }


  /*
   * @see mic.contacta.server.ptool.TemplateService#setTemplateDir(java.lang.String)
   *
  @Override
  public void setTemplateUrl(String templateDir)
  {
    this.templateUrl = templateDir;
  }*/


  /*
   * @see mic.contacta.server.ptool.TemplateService#setOutputDir(java.lang.String)
   *
  @Override
  public void setOutputUrl(String outputDir)
  {
    this.outputUrl = outputDir;
  }*/


  /*
   *
   */
  @PostConstruct
  public void configure()
  {
    String templateUrl = configuration.getTemplateUrl();
    String outputUrl = configuration.getOutputUrl();
    try
    {
      if (templateUrl.startsWith("./"))
      {
        templateBaseFo = organicVfs.resolve(SystemUtils.getUserDir());
        templateBaseFo = templateBaseFo.resolveFile(templateUrl);
      }
      else
      {
        templateBaseFo = organicVfs.resolve(templateUrl);
      }
    }
    catch (FileSystemException e)
    {
      log().error("cannot resolve templateUrl {}: {}", templateUrl, e.getMessage());
      log().error("cannot continue, exiting...");
      System.exit(0);
    }

    try
    {
      if (outputUrl.startsWith("./"))
      {
        outputFo = organicVfs.resolve(SystemUtils.getUserDir());
        outputFo = outputFo.resolveFile(outputUrl);
      }
      else
      {
        outputFo = organicVfs.resolve(outputUrl);
      }
      if (false)//outputFo.exists())
      {
        String subdir = String.valueOf(System.currentTimeMillis());
        //log().info("outputUrl {} exists, using subdir {}", outputUrl, subdir);
        outputFo = outputFo.resolveFile(subdir);
      }
      outputFo.createFolder();
    }
    catch (FileSystemException e)
    {
      log().error("cannot resolve outputUrl {}: {}", outputUrl, e.getMessage());
    }
  }


  /*
   * @see mic.contacta.server.ptool.TemplateService#generate(java.lang.String, java.lang.String, java.util.Map, boolean)
   */
  @Override
  public void generate(String confIn, String confOut, Map<String, Object> params, boolean dollarstar) throws ScriptException, IOException
  {
    FileObject fo = templateBaseFo.resolveFile(confIn);
    String body = generate(fo, params);
    if (dollarstar)
    {
      body = body.replaceAll("\\$\\*", "\\$");
    }

    FileObject outFo = outputFo.resolveFile(confOut);
    IOUtils.copy(new ByteArrayInputStream(body.getBytes()), outFo.getContent().getOutputStream());
    outFo.close();
    //FileUtils.writeStringToFile(, data)
  }


  /*
   * @see mic.contacta.server.ptool.TemplateService#generateConfFile(java.lang.String, java.lang.String, java.util.Map)
   */
  @Override
  public void generate(String confIn, String confOut, Map<String,Object> params) throws ScriptException, IOException
  {
    generate(confIn, confOut, params, false);
  }


  /*
   * @see mic.contacta.server.ptool.TemplateService#generateConfFile(java.lang.String, java.lang.String, java.util.Map)
   */
  @Override
  public String generate(FileObject template, Map<String,Object> params) throws ScriptException, IOException
  {
    Object result = null;
    SimpleScriptContext context = new SimpleScriptContext();
    context.setWriter(new StringWriter());

    scriptRunner.runScript(ScriptRunner.FREEMARKER, template, result, params, context);

    String body = context.getWriter().toString();
    return body;
  }

}
