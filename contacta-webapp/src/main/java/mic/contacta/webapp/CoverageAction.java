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

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import mic.contacta.gateway.ContactaGateway;
import mic.contacta.json.CoverageJson;
import mic.organic.gateway.DatastoreJson;
import mic.organic.gateway.DefaultDatastoreJson;
import mic.organic.web.struts2.AbstractDatastoreAction;

/**
 *
 * @author mic
 * @created Jun 13, 2010
 */
@Service("coverageAction")
@Scope("request")
public class CoverageAction extends AbstractDatastoreAction
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private ContactaGateway contactaGateway;


  /*
   *
   */
  public String findAll()
  {
    List<CoverageJson> jsonList = contactaGateway.coverageList();
    DatastoreJson<CoverageJson> datastore = new DefaultDatastoreJson<CoverageJson>(DatastoreJson.IDENTIFIER, "title", jsonList);
    setStore(datastore);
    return SUCCESS;
  }


}
