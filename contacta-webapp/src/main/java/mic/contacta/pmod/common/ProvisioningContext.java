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
package mic.contacta.pmod.common;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mic.contacta.domain.PhoneModel;


/**
 *
 * @author mic
 * @created May 31, 2009
 */
public class ProvisioningContext
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  public static final String [] excludes = { "request", "response" };

  private String ipAddress;
  private String macAddress;

  private PhoneModel phone;

  private String method;
  private String servletPath;
  private String pathInfo;
  private String userAgent;

  private HttpServletRequest request;
  private HttpServletResponse response;

  private String resourceName;

  private Map<String, Object> params;


  /*
   *
   */
  public ProvisioningContext(HttpServletRequest request, HttpServletResponse response)
  {
    this.request = request;
    this.response = response;

    ipAddress = request.getRemoteAddr();
    method = request.getMethod();
    servletPath = request.getServletPath();
    pathInfo = request.getPathInfo();
    userAgent = request.getHeader("user-agent");

    resourceName = pathInfo.substring(1);

    params = new HashMap<String, Object>();

    //log().debug("{}", this);
  }


  /*
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames(excludes);
    return builder.toString();
  }


  /**
   * @return the ipAddress
   */
  public String getIpAddress()
  {
    return ipAddress;
  }


  /**
   * @param ipAddress the ipAddress to set
   */
  protected void setIpAddress(String ipAddress)
  {
    this.ipAddress = ipAddress;
  }


  /**
   * @return the macAddress
   */
  public String getMacAddress()
  {
    return macAddress;
  }


  /**
   * @param macAddress the macAddress to set
   */
  protected void setMacAddress(String macAddress)
  {
    this.macAddress = macAddress;
  }


  /**
   * @return the phone
   */
  public PhoneModel getPhone()
  {
    return phone;
  }


  /**
   * @param phone the phone to set
   */
  public void setPhone(PhoneModel phone)
  {
    this.phone = phone;
  }


  /**
   * @return the method
   */
  public String getMethod()
  {
    return method;
  }


  /**
   * @return the servletPath
   */
  public String getServletPath()
  {
    return servletPath;
  }


  /**
   * @return the pathInfo
   */
  public String getPathInfo()
  {
    return pathInfo;
  }


  /**
   * @return the userAgent
   */
  public String getUserAgent()
  {
    return userAgent;
  }


  /**
   * @return the request
   */
  public HttpServletRequest getRequest()
  {
    return request;
  }


  /**
   * @return the response
   */
  public HttpServletResponse getResponse()
  {
    return response;
  }


  /**
   * @return the resourceName
   */
  public String getResourceName()
  {
    return resourceName;
  }


  /**
   * @param resourceName the resourceName to set
   */
  public void setResourceName(String resourceName)
  {
    this.resourceName = resourceName;
  }


  /**
   * @return
   */
  public Map<String, Object> getParams()
  {
    return params;
  }
}
