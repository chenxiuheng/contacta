/* $Id: PhoneConverter.java 666 2010-07-20 21:52:48Z michele.bianchi $
 *
 * Copyright(C) 2010 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.contacta.gateway;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import mic.contacta.json.PhoneJson;
import mic.contacta.model.PhoneModel;
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
  public PhoneModel jsonToModel(PhoneJson from, PhoneModel to)
  {
    if (to == null)
    {
      to = new PhoneModel();
    }
    super.jsonToModel(from, to);
    //this.silentCopy(from, to);

    to.setCode(from.getCode());
    to.setLocation(from.getLocation());
    // TODO to.getOwner(from.getOwner().getId());
    // FIXME to.setProduct(from.getProduct().getCode());
    to.setSerialNumber(from.getSerialNumber());
    to.setLocation(from.getLocation());
    to.setInfo(from.getInfo());
    if (from.getHasConfig() == false)
    {
      to.setConfig(null);
    }
    return to;
  }


  /*
   *
   */
  @Override
  public PhoneJson modelToJson(PhoneModel from, PhoneJson to)
  {
    if (to == null)
    {
      to = new PhoneJson();
    }
    super.modelToJson(from, to);
    //this.silentCopy(from, to);
//    try
//    {
//      PropertyUtils.copyProperties(from, to);
//      //BeanUtils.copyProperties(from, to);
//    }
//    catch (IllegalAccessException e)
//    {
//      log().warn(e.getMessage());
//    }
//    catch (InvocationTargetException e)
//    {
//      log().warn(e.getMessage());
//    }
//    catch (NoSuchMethodException e)
//    {
//      log().warn(e.getMessage());
//    }
    to.setCode(from.getCode());
    to.setHasConfig(StringUtils.isNotBlank(from.getConfig()));
    to.setIpAddress(from.getIpAddress());
    to.setLastBoot(from.getLastBoot());
    to.setLocation(from.getLocation());
    // TODO to.getOwner(from.getOwner().getId());
    to.setProduct(from.getProduct().getCode());
    to.setSerialNumber(from.getSerialNumber());
    to.setLocation(from.getLocation());
    to.setInfo(from.getInfo());


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


    return to;
  }


}
