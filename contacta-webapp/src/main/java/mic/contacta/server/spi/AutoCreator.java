/* $Id: AutoCreator.java 660 2010-07-17 23:14:21Z michele.bianchi $
 *
 * Copyright(C) 2010 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.contacta.server.spi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mic.contacta.model.*;
import mic.contacta.server.dao.PbxContextDao;


/**
 *
 * @author mic
 * @created May 29, 2010
 */
@Service
public class AutoCreator
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired protected InventoryService inventoryService;
  @Autowired private PbxContextDao pbxContextDao;
  @Autowired private SipService sipService;


  /**
   * @param macAddress
   * @param macText
   * @return
   */
  public PhoneModel autocreator(String macText, String productCode)
  {
    log().warn("Phone macaddress {} had not been registered by administrator. Creating a new one", macText);

    ProductModel product = inventoryService.findProductByCode(productCode); // TODO extract product model
    PhoneModel phone = new PhoneModel(macText);
    phone = inventoryService.createPhone(phone, product);

    PbxContextModel pbxContext = pbxContextDao.findByCode("sipphones");
    SipAccountModel account = new SipAccountModel("NEW@"+macText, macText+"@new", "999_"+macText, "999", pbxContext);
    account.setCallgroup("");
    account.setPickupgroup("");
    account.setProfile(null); // URGENT use the ''new unregistered phone profile''
    account.setProfileOptions("");
    account = sipService.createAccount(account);
    sipService.createAccount(account);

    sipService.assignPhoneToAccount(phone, account);

    return phone;
  }
}
