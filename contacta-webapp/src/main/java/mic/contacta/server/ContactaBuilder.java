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
package mic.contacta.server;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import mic.contacta.domain.PbxContextModel;
import mic.contacta.domain.PbxProfileModel;
import mic.contacta.domain.PhoneModel;
import mic.contacta.domain.ProductModel;
import mic.contacta.domain.SipAccountModel;
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
   * @param label TODO
   * @param context TODO
   * @param suffix
   * @return
   */
  public SipAccountModel buildSipAccount(String login, String password, String label, String context)
  {
    if (context == null)
    {
      context = "root";
    }
    SipAccountModel account = new SipAccountModel();
    account.setCode(login);
    account.setPassword(password);
    account.setLabel(label);
    account.setEmail(login+"@localhost");
    //account.getSipUser().setCallgroup("callGroup" + suffix);
    //account.getSipUser().setPickupgroup("pickup" + suffix);
    //account.setCallWaiting(callWaiting);
    account.getSipUser().setContext(context);
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
