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
package mic.contacta.gateway;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import mic.contacta.domain.PhoneModel;
import mic.contacta.json.PhoneJson;
import mic.organic.gateway.AbstractJsonConverter;

/**
 *
 * @author mic
 * @created May 23, 2010
 */
@Service
public class PhoneConverter extends AbstractJsonConverter<PhoneModel, PhoneJson>
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  /*
   *
   */
  @Override
  public PhoneModel jsonToModel(PhoneJson src, PhoneModel dst)
  {
    if (dst == null)
    {
      dst = new PhoneModel();
    }
    super.jsonToModel(src, dst);
    //this.silentCopy(from, to);

    dst.setCode(src.getCode());
    dst.setLocation(src.getLocation());
    // TODO to.getOwner(from.getOwner().getId());
    // FIXME to.setProduct(from.getProduct().getCode());
    dst.setSerialNumber(src.getSerialNumber());
    dst.setLocation(src.getLocation());
    dst.setInfo(src.getInfo());
    if (src.getHasConfig() == false)
    {
      dst.setConfig(null);
    }
    return dst;
  }


  /*
   *
   */
  @Override
  public PhoneJson modelToJson(PhoneModel src, PhoneJson dst)
  {
    if (dst == null)
    {
      dst = new PhoneJson();
    }
    super.modelToJson(src, dst);

    dst.setCode(src.getCode());
    dst.setHasConfig(StringUtils.isNotBlank(src.getConfig()));
    dst.setIpAddress(src.getIpAddress());
    dst.setLastBoot(src.getLastBoot());
    dst.setLocation(src.getLocation());
    // TODO to.getOwner(from.getOwner().getId());
    dst.setProduct(src.getProduct().getCode());
    dst.setSerialNumber(src.getSerialNumber());
    dst.setLocation(src.getLocation());
    dst.setInfo(src.getInfo());


//    //json.setVendor(model.getVendor().toString());
//    //json.setModel(model.getModel().toString());

    //URGENT
    //boolean first = true;
//    for(SipAccountModel account : model.getSipAccountList())
//    {
//      SipAccountJson accountJson = new SipAccountJson();//ContactaConverterImpl.accountModelToJson(account);
////      accountJson.setLogin("account.getLogin()");
//      json.getAccounts().add(accountJson);
//
//      /*if(first)
//      {
//        first = false;
//        json.setIpAddress(account.getSipUser().getIpaddr());
//
//        if(account.getSipUser() != null)
//        {
//          json.setRegseconds(account.getSipUser().getRegseconds());
//        }
//      }*/
//    }


    return dst;
  }


}
