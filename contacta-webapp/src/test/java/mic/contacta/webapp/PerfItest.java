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

import mic.contacta.gateway.ContactaGateway;
import mic.contacta.json.PhoneJson;
import mic.contacta.json.SipAccountJson;
import mic.contacta.server.api.ContactaConstants;
import mic.contacta.server.dao.SipAccountDao;
import mic.organic.core.OrganicException;
import mic.organic.gateway.DatastoreJson;
import mic.organic.gateway.Json;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


/**
 *
 *
 * @author mic
 * @created Dec 1, 2008
 */
@ContextConfiguration(locations = { ContactaConstants.SPRING_TEST_ORM, ContactaConstants.TEST_PROVISIONING_SPRING })
public class PerfItest extends AbstractTransactionalTestNGSpringContextTests
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private ContactaGateway contactaGateway;
  @Autowired private SipAccountDao sipDao;

  private String method;
  private long ts0;


  /*
   *
   */
  @BeforeMethod
  public void beforeMethod()
  {
    ts0 = System.currentTimeMillis();
  }


  /*
   *
   */
  @AfterMethod
  public void afterMethod()
  {
    long ts1 = System.currentTimeMillis();
    log().info("{} takes: {}", method, (ts1-ts0));
  }


  /**
   * @param key
   */
  private DatastoreJson<? extends Json> readDataStore(String key)
  {
    method = key;

    DatastoreJson<? extends Json> store = null;
    /*if (StringUtils.equalsIgnoreCase(key, "person"))
    {
      List<PersonJson> jsonList = contactaGateway.personLoad();
      //List<ContactModel> modelList = ContactaConverterImpl.sampleContactList();
      //List<ContactJson> jsonList = ContactaConverterImpl.contactModelToJson(modelList);
      store = new mic.organic.dojo.DefaultDatastoreJson<PersonJson>(DEFAULT_DATASTORE_ID, /*DEFAULT_DATASTORE_TITLE* /"lastName", jsonList);
    }
    else*/ if (StringUtils.equalsIgnoreCase(key, "account"))
    {
      List<SipAccountJson> jsonList = contactaGateway.sipList();
      store = new mic.organic.gateway.DefaultDatastoreJson<SipAccountJson>(DatastoreJson.IDENTIFIER, "login", jsonList);
    }
    else if (StringUtils.equalsIgnoreCase(key, "phone"))
    {
      List<PhoneJson> jsonList = contactaGateway.phoneList();
      store = new mic.organic.gateway.DefaultDatastoreJson<PhoneJson>(DatastoreJson.IDENTIFIER, "macAddress", jsonList);
    }
    return store;
  }


  /*
   *
   */
  @Test
  public void testPerf() throws OrganicException, JSONException
  {
    assertTrue(true);

    DatastoreJson<? extends Json> store = readDataStore("phone");
    log().info("store.size={}", store.getItems().size());

    //String a = JSONUtil.serialize(store);
    //log().info("{}", a);
  }


  /*
  *
  */
 @Test
 public void testSipDao() throws OrganicException, JSONException
 {
   List<Object[]> list = sipDao.findAccountBriefList();
   assertNotNull(list);
 }

}
