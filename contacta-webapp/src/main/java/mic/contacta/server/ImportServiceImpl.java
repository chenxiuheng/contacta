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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import mic.contacta.dao.PbxContextDao;
import mic.contacta.dao.PbxProfileDao;
import mic.contacta.domain.CocModel;
import mic.contacta.domain.PhoneModel;
import mic.contacta.domain.ProductModel;
import mic.contacta.domain.SipAccountModel;
import mic.contacta.util.CsvUtil;
import mic.contacta.util.ContactaUtils;
import mic.organic.core.OrganicException;
import mic.organic.vfs.OrganicVfs;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.vfs2.FileObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Description of the Class
 *
 * @author mic
 * @version $Revision: 664 $
 */
@Service(ImportService.SERVICE_NAME)
public class ImportServiceImpl implements ImportService
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private OrganicVfs organicVfs;
  @Autowired private InventoryService inventoryService;
  @Autowired private PbxService pbxService;
  @Autowired private CocService cocService;
  @Autowired private PbxContextDao pbxContextDao;
  @Autowired private PbxProfileDao pbxProfileDao;

  private String[] csvOrder =
  {
      "fullName",
      "login",
      "password",
      "voicemail",
      "voicemailPin",
      "callGroup",
      "pickupGroup",
      "profile",
      "profileOptions",
      "context",
      "macAddress",
      "vendor",
      "model",
      "cocLogin",
      "cocPassword",
      "speedDial"
  };


  /**
   *
   */
  public ImportServiceImpl()
  {
    super();
  }


  /*
   *
   */
  private void importCsvLine(String[] line, Collection<String> macaddressCollection) throws IOException, OrganicException, ContactaException
  {
    if(line.length > 1)
    {
      //      String fullName = line[ArrayUtils.indexOf(csvOrder, "fullName")];
      //      String firstName = null;
      //      String lastName = null;
      //      String login = line[ArrayUtils.indexOf(csvOrder, "login")];
      //      String password = line[ArrayUtils.indexOf(csvOrder, "password")];
      //      String profile = line[ArrayUtils.indexOf(csvOrder, "profile")];
      //      String profileOptions = line[ArrayUtils.indexOf(csvOrder, "profileOptions")];
      //      Boolean voicemail = Boolean.parseBoolean(line[ArrayUtils.indexOf(csvOrder, "voicemail")]);
      //      String callGroup = line[ArrayUtils.indexOf(csvOrder, "callGroup")];
      //      String pickupGroup = line[ArrayUtils.indexOf(csvOrder, "pickupGroup")];
      //      String context = line[ArrayUtils.indexOf(csvOrder, "context")];
      //      String pin = ContactaUtils.passwordToPin(password);
      //      String macAddress = ContactaUtils.validateMacAddress(line[ArrayUtils.indexOf(csvOrder, "macAddress")]);
      //      String vendor = line[ArrayUtils.indexOf(csvOrder, "vendor")];
      //      String model = line[ArrayUtils.indexOf(csvOrder, "model")];
      //      String cocLogin = line[ArrayUtils.indexOf(csvOrder, "cocLogin")];
      //      String cocPassword = line[ArrayUtils.indexOf(csvOrder, "cocPassword")];
      //      String email = null;//login+"@"+context+".local";
      //      int speedIndex = ArrayUtils.indexOf(csvOrder, "speedDial");

      String displayName = line[0];
      String login = line[1];
      String password = line[2];
      String profile = line[7];
      String profileOptions = line[8];
      //Boolean voicemail = Boolean.parseBoolean(line[3]);
      //String voicemailPin = line[4];
      String callGroup = line[5];
      String pickupGroup = line[6];
      String context = line[9];
      //String pin = ContactaUtils.passwordToPin(password);
      String macAddress = ContactaUtils.validateMacAddress(line[10]);
      String productCode = line[12];

      String cocLogin = "";
      String cocPassword = "";

      try
      {
        cocLogin = line[13];
        cocPassword = line[14];
      }
      catch(Exception e)
      {
        cocLogin = "";
        cocPassword = "";
      }

      String email = null;//login+"@"+context+".local";
      if (StringUtils.isBlank(email))
      {
        email = displayName+"@"+new Date().getTime()+".local";
      }

      SipAccountModel account = new SipAccountModel(displayName, email, login, password, pbxContextDao.findByCode(context));
      account.setCallgroup(callGroup);
      account.setPickupgroup(pickupGroup);

      if(cocLogin != null && cocLogin.length()>0)
      {
        CocModel coc = new CocModel(cocLogin, cocPassword);
        cocService.createCoc(coc);
        account.setCoc(coc);
      }

      account.setProfile(pbxProfileDao.findByCode(profile));
      account.setProfileOptions(profileOptions);
      account = pbxService.sipPersist(account);

      /*URGENT if(voicemail)
      {
        pbxService.assignUpdateVoicemailToAccount(account, voicemailPin);
      }*/

      // Support more than a line per phone
      if(macAddress.length() > 0)
      {
        PhoneModel phone = null;
        if(macaddressCollection.contains(macAddress))
        {
          phone = inventoryService.phoneByMacAddress(macAddress);
        }
        else
        {
          ProductModel product = inventoryService.productByCode(productCode);
          phone = new PhoneModel(macAddress);
          phone = inventoryService.phonePersist(phone, product);
          macaddressCollection.add(macAddress);
        }
        pbxService.assignPhoneToAccount(phone, account);
      }
    }
  }


  /**
   * @throws OrganicException
   *
   */
  @Override
  public void importFromCsv(String bootFilename) throws OrganicException
  {
    log().info("Importing from CSV {}", bootFilename);
    try
    {
      FileObject fo = organicVfs.resolve(SystemUtils.getUserDir());
      fo = fo.resolveFile(bootFilename);

      importFromCsv(fo);
    }
    catch (Exception e)
    {
      log().warn(e.getMessage(), e);
      throw new OrganicException("cannot import csv");
    }
  }


  /**
   * @throws ContactaException
   *
   */
  @Override
  public void importFromCsv(FileObject csvFile) throws OrganicException, IOException, ContactaException
  {
    List<String> macaddressList = inventoryService.macAddressList();

    List<String[]> entries = CsvUtil.getCsvValues(csvFile);

    for(String[] line : entries)
    {
      importCsvLine(line, macaddressList);
    }
  }


  /**
   *
   */
  @Override
  public FileObject exportToCsv(FileObject csvFile) throws OrganicException, IOException
  {
    List<Object[]> accountList = pbxService.sipBriefList();


    List<String[]> lineList = new ArrayList<String[]>();

    for(Object[] account : accountList)
    {
      String[] line = exportCsvLine(account);
      lineList.add(line);
    }

    CsvUtil.writeToCsv(lineList, csvFile);
    return csvFile;
  }


  /**
   *
   */
  private String[] exportCsvLine(Object[] account)
  {
    String[] line = new String[csvOrder.length-1];

    line[0] = account[13] == null? (String) account[11] + (String) account[12] : (String) account[13]; //fullname
    line[1] = (String) account[1]; // login
    line[2] = (String) account[2]; // password
    line[3] = ((Boolean) account[8]).toString(); // voicemailEnabled;
    line[4] = (String) account[9]; // voicemailPin
    line[7] = (String) account[3]; // profileName
    line[8] = (String) account[4]; // profileOptions
    line[5] = (String) account[6]; // callgroup();
    line[6] = (String) account[7]; // pickupgroup();
    line[9] = (String) account[5]; // context();

    if(account[16] != null)
    {
      line[10] = (String) account[16]; //macaddress

      /*Vendor vendor = PhoneModel.Vendor.values()[(Integer) account[19]]; // vendor
      line[11] = vendor.toString();
      Model pmodel = PhoneModel.Model.values()[(Integer) account[20]]; // model
      line[12] = pmodel.toString();
      */
    }

    line[13] = (String) account[14];
    line[14] = (String) account[15];
    return line;
  }

}
