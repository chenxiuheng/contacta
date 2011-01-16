/* $Id: ContactaBuilder.java 665 2010-07-19 17:21:54Z michele.bianchi $
 *
 * Copyright(C) 2010 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.contacta.server.spi;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import mic.contacta.model.PbxContextModel;
import mic.contacta.model.PbxProfileModel;
import mic.contacta.model.PhoneModel;
import mic.contacta.model.ProductModel;
import mic.contacta.model.SipAccountModel;
import mic.organic.aaa.spi.OrganicBuilder;
import mic.organic.vfs.OrganicVfs;


/**
 *
 * @author mic
 * @created Jul 2, 2010
 */
@Service
public class ContactaBuilder extends OrganicBuilder
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  /**
   *
   */
  public ContactaBuilder()
  {
    super();
  }


  /*
   *
   */
  public PbxContextModel buildPbxContext(String code, String label)
  {
    PbxContextModel model = new PbxContextModel();
    model.setCode(code);
    model.setLabel(label);
    return model;
  }


  /**
   * @param command TODO
   * @param macroUrl TODO
   * @param string
   * @param string2
   * @return
   */
  public PbxProfileModel buildPbxProfile(OrganicVfs organicVfs, String code, String label, String command, String macroUrl)
  {
    PbxProfileModel model = new PbxProfileModel();
    model.setCode(code);
    model.setLabel(label);
    model.setCommand(command);

    try
    {
      String macro = organicVfs.loadAsString(macroUrl);
      model.setMacro(macro);
    }
    catch (IOException e)
    {
      log().warn("setting url as body, cannot read {}:", new Object[] { macroUrl, e.getMessage() }, e);
      model.setMacro(macroUrl);
    }
    return model;
  }


  /**
   * @param suffix
   * @return
   */
  public SipAccountModel buildSipAccount(String login, String password)
  {
    SipAccountModel account = new SipAccountModel();
    account.setCode(login);
    account.setPassword(password);
    account.setLabel("contacta administrator");
    account.setEmail(login+"@localhost");
    //account.getSipUser().setCallgroup("callGroup" + suffix);
    //account.getSipUser().setPickupgroup("pickup" + suffix);
    //account.setCallWaiting(callWaiting);
    account.getSipUser().setContext("root");
    account.getSipUser().setName(login);
    account.getSipUser().setUsername(login);
    account.getSipUser().setCallerid(account.getCallerId());
    account.getSipUser().setSecret(password);
    return account;
  }


  /**
   * @param productCode
   *
   */
  public ProductModel buildProduct(String productCode)
  {
    ProductModel product = new ProductModel(productCode, "vendor", productCode, "version", "UA "+productCode);
    return product;
  }


  /**
   *
   * @param macAddress
   * @return
   */
  public PhoneModel buildPhone(String macAddress)
  {
    PhoneModel phone = new PhoneModel(/*PhoneModel.Vendor.POLYCOM, PhoneModel.Model.SPIP_450,*/ macAddress);
    phone.setProduct(buildProduct("STxxxx"));
    return phone;
  }

}
