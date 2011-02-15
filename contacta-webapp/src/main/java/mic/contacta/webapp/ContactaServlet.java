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
package mic.contacta.webapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mic.contacta.gateway.ContactaGateway;
import mic.contacta.server.api.ContactaConstants;
import mic.contacta.server.spi.ContactaService;
import mic.contacta.server.spi.PhonebarService;
import mic.contacta.util.ContactaHelper;
import mic.organic.core.OrganicSpringHelper;


/**
 *
 *
 * @author michele.bianchi@gmail.com
 * @version $Revision: 649 $
 */
public class ContactaServlet extends HttpServlet
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  private static final String REPLICA = "/replica";
  private static final String OUTLOOK = "/outlook";

  private ContactaGateway contactaGateway;


  /*
   *
   */
  protected void sendError(HttpServletResponse response, String message) throws IOException
  {
    log().warn(message);

    // something other, invalid request
    response.setContentType("text/plain");
    response.getWriter().write(message);
  }


  /*
   *
   */
  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);

    log().warn("starting up");

    ContactaService contactaService = ContactaHelper.getContactaService();
    try
    {
      contactaService.importFromCsv();
      //log().info("ptool-webapp is started up");
    }
    catch (Exception e)
    {
      log().warn("cannot import data from CSV: {}", e.getMessage(), e);
      //throw new ServletException(e);
    }

    contactaGateway = (ContactaGateway) (OrganicSpringHelper.getService(ContactaGateway.SERVICE_NAME));
  }


  /*
   *
   */
  public void destroy()
  {
    log().warn("shutting down");
    super.destroy();
  }


  /**
   *  usa come baseurl http://host:port/contacta/outlook/
   *
   *  * per la login: http://host:port/contacta/outlook/login/<loginname>
   *  dove <loginname> e' la login dell'utente, sara' tipo 8500, 8712, ...
   *  e contenuto del post http la password
   *
   *  ti tornera' un token (stringa) che va usato successivamente
   *
   *  * per la chiamata: http://host:port/contacta/outlook/call/token/exten
   *  dove token e' il token di prima
   *  exten e' il nr da chiamare, cosi' come compare in outlook
   *
   *  questa pure in get
   *
   *  se ci fossero problemi per mantenere il token ripetiamo la login al posto del token, e usiamo una post con dentro la password
   *
   *
   * @param path
   */
  private String serveOutlook(String path)
  {
    log().info("outlook path={}", path);

    PhonebarService phonebarService = (PhonebarService)(OrganicSpringHelper.getService(PhonebarService.SERVICE_NAME));
    String result = "ko";
    if (path.startsWith("/call"))
    {
      String[] parts = path.split("/");
      log().info("parts={}, parts.length={}", parts, parts.length);
      if (parts.length == 5)
      {
        String login = parts[2];
        String password = parts[3];
        String exten = parts[4];

        log().info("login={}, password={}", login, password);
        //if (StringUtils.equals(login, "test") && StringUtils.equals(password, "test"))
        {
          result = phonebarService.dial(login, password, exten);
        }
        //else
        {
          //result = "invalid user: "+login;
        }
      }
      else
      {
        result = "invalid format: "+path+", use /call/<username>/<password>/<exten>";
      }
    }
    else if (path.startsWith("/login"))
    {
      String[] parts = path.split("/");
      if (parts.length == 4)
      {
        String login = parts[2];
        String password = parts[3];

        log().info("login={}, password={}", login, password);
      }
      else
      {
        result = "format is incorrect: "+path+", use /login/<username>/<password>";
      }
    }
    else
    {
      log().warn("unknown command={}", path);
    }
    return result;
  }


  /*
   *
   */
  public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String pathInfo = request.getPathInfo();
    String servletPath = request.getServletPath();
    log().debug("servletPath={}, pathInfo={}", servletPath, pathInfo);

    PrintWriter writer = null;
    try
    {
      writer = new PrintWriter(response.getOutputStream());
      if (pathInfo != null && pathInfo.startsWith(OUTLOOK) == true)
      {
        response.setContentType(ContactaConstants.MIMETYPE_TEXT_PLAIN);
        String path = pathInfo.substring(OUTLOOK.length());
        String result = serveOutlook(path);
        log().info("result={}", result);
        writer.println(result);
      }
      else if (servletPath != null && servletPath.equals(REPLICA) == true)
      {
        response.setContentType(ContactaConstants.MIMETYPE_TEXT_PLAIN);
        try
        {
          contactaGateway.extenProfileUpdate();
          writer.println("OK");
        }
        catch(Exception e)
        {
          writer.print("ERROR: ");
          writer.println(e.getMessage());
        }
      }
      else
      {
        response.setContentType(ContactaConstants.MIMETYPE_TEXT_HTML);
        writer.println("<html>");
        writer.println("<head><meta http-equiv='refresh' content='10'></head>");
        writer.println("<body><pre>");
        writer.println("refresh in 10 seconds");
        writer.println("</pre></body>");
        writer.println("</html>");
      }
    }
    catch(Exception e)
    {
      writer = new PrintWriter(response.getOutputStream());
      e.printStackTrace(writer);
    }
    writer.flush();
    writer.close();
  }

}

