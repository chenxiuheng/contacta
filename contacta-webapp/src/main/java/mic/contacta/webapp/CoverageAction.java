/* $Id: CoverageAction.java 651 2010-06-15 21:36:35Z michele.bianchi $
 *
 * Copyright(C) 2010 [michele.bianchi@gmail.com]
 * All Rights Reserved
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
@Scope("prototype")
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
    DatastoreJson<CoverageJson> datastore = new DefaultDatastoreJson<CoverageJson>(DatastoreJson.IDENTIFIER, DEFAULT_DATASTORE_TITLE, jsonList);
    setStore(datastore);
    return SUCCESS;
  }


}
