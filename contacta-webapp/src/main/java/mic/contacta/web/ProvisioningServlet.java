/**
 * Contacta webapp, http://www.openinnovation.it - Michele Bianchi
 * Copyright(C) 1999-2012 info@openinnovation.it
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
package mic.contacta.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mic.contacta.pmod.common.ProvisioningContext;
import mic.contacta.server.ProvisioningService;
import mic.contacta.util.ContactaHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author mic
 * @created Dec 30, 2008
 */
public class ProvisioningServlet extends HttpServlet
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  private ProvisioningService provisioningService;


  /*
   *
   */
  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);

    provisioningService = ContactaHelper.getPtoolService();
  }


  /*
   *
   */
  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException
  {
    if (provisioningService == null)
    {
      log().warn("this instance has not the provisioning support, please check the manual.");
      response.setStatus(404);
      return;
    }

    String method = request.getMethod();
    String servletPath = request.getServletPath();
    String pathInfo = request.getPathInfo();
    ContactaHelper.loggerProvisioning().info("{}, {}, {}", new Object[] { method, servletPath, pathInfo });

    ProvisioningContext provisioningContext = provisioningService.createProvisioningContext(request, response);
    boolean tmp = false;
    try
    {
      tmp = provisioningService.doProvisioning(provisioningContext);
    }
    catch (IOException e)
    {
      throw new ServletException(e);
    }

    if (!tmp)
    {
      ContactaHelper.loggerProvisioning().warn("unsupported {} pathInfo: {}", method, pathInfo);
      log().warn("unsupported {} pathInfo: {}", method, pathInfo);
      response.setStatus(404);
    }
  }

}
