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
import java.io.OutputStream;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mic.organic.core.OrganicException;


/**
 *
 * @author mic
 * @created Apr 29, 2008
 */
public class HttpDownloader
{
  private static Logger logger;  @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  protected HttpClient httpClient;


  /**
   *
   */
  public HttpDownloader()
  {
    httpClient = new HttpClient();
  }


  /**
   *
   * @param url
   * @param outputStream
   * @return
   * @throws OrganicException
   */
  public FileExt copyUrl(String url, OutputStream outputStream) throws OrganicException
  {
    GetMethod method = new GetMethod(url);
    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));           // Provide custom retry handler is necessary
    try
    {
      int statusCode = httpClient.executeMethod(method);

      if (statusCode != HttpStatus.SC_OK)
      {
        log().warn("Method failed: {}", method.getStatusLine());
        throw new OrganicException("Method failed for url: "+url+" caused by:"+method.getStatusLine());
      }
      //byte[] responseBody = method.getResponseBody();
      //InputStream inputStream = method.getResponseBodyAsStream();

      IOUtils.copy(method.getResponseBodyAsStream(), outputStream);
      outputStream.close();

      //InputStream inputStream = new FileInputStream(tmpfile);//new ByteArrayInputStream(responseBody);
      //if (inputStream != null)
      //{
        Header headers[] = method.getResponseHeaders("Content-Type");

        String filename = method.getURI().getEscapedName();
        String mimetype = headers.length > 0 ? headers[0].getValue() : null;

        if (filename == null)
        {
          filename = "filename.wav";
        }
        if (mimetype == null)
        {
          mimetype = "audio/x-wav";
        }
      //}
      /*else
      {
        log().warn("nothing to load from url: {}", attachmentUrl);
        throw new OrganicException("nothing to load from url: "+attachmentUrl);
      }
      try
      {
        inputStream.close();
      }
      catch (IOException e)
      {
        log().warn("inputStream already closed: {}", e.getMessage());
      }*/
      FileExt fileExt = new FileExt();
      fileExt.filename = filename;
      fileExt.contentType = mimetype;
      fileExt.contentLength = method.getResponseContentLength();
      return fileExt;
    }
    catch (HttpException e)
    {
      log().warn("Fatal protocol violation: {}", e.getMessage());
      throw new OrganicException(e);
    }
    catch (IOException e)
    {
      log().warn("Fatal transport error: {}", e.getMessage());
      throw new OrganicException(e);
    }
    finally
    {
      // Release the connection.
      method.releaseConnection();
    }
  }

}
