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
package mic.contacta.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import mic.contacta.gateway.PbxGateway;
import mic.organic.core.Model;
import mic.organic.gateway.AbstractJsonSmd;
import mic.organic.gateway.Json;


/**
 *
 * @author mic
 * @created Apr 27, 2010
 */
public abstract class AbstractContactaSmd<T extends Json> extends AbstractJsonSmd<T>
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired protected PbxGateway pbxGateway;


  /*
   *
   */
  public AbstractContactaSmd()
  {
    super();
  }


  /**
   * just the default method, doing nothing
   */
  @Override
  public String execute()
  {
    return SUCCESS;
  }


  /**
   *
   */
  public String bind()
  {
    return SUCCESS;
  }


  /**
   * @param oid
   * @return
   */
  public abstract Model findModel(Integer oid);


  /*
   * @see mic.organic.gateway.AbstractJsonSmd#detail()
   */
  @Override
  public String detail()
  {
    if (getOid() == null) //StringUtils.isBlank(code))
    {
      return ERROR;
    }
    model = this.findModel(getOid());
    if (model != null)
    {
      return SUCCESS;
    }
    else
    {
      log().info("cannot find model.code='{}'", code);
      return ERROR;
    }
  }


}
