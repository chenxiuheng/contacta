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
package mic.contacta.asterisk.spi;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mic.contacta.dao.PbxProfileDao;
import mic.contacta.domain.PbxProfileModel;
import mic.contacta.domain.SipAccountModel;
import mic.contacta.server.ContactaConstants;

/**
 *
 * @author mic
 * @created May 17, 2009
 */
@Service
public class ExtenProfileWriter
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private PbxProfileDao pbxProfileDao;

  private String agiBaseUrl = "agi://127.0.0.1";
  private boolean generateParking = false;

  //private Map<String,ProfileConf> profileMap;


  /**
   *
   *
   * @author mic
   * @created Feb 10, 2009
   *
  class ProfileConf
  {
    String key;
    String value;
    boolean hasOptions;

    public ProfileConf(String key, String value)
    {
      this.key = key;
      this.value = value;
      if (value != null)
      {
        hasOptions = value.contains(SipAccountModel.PROFILE_OPTIONS_KEY);
      }
    }
  }*/


  /**
   *
   */
  public ExtenProfileWriter()
  {
    //profileMap = new HashMap<String,ProfileConf>();
  }


  /**
   * @return the agiBaseUrl
   */
  public String getAgiBaseUrl()
  {
    return agiBaseUrl;
  }



  /**
   * @param agiBaseUrl the agiBaseUrl to set
   */
  public void setAgiBaseUrl(String agiBaseUrl)
  {
    this.agiBaseUrl = agiBaseUrl;
  }


  /**
   * @param key
   * @param value
   *
  public void addProfile(String key, String value)
  {
    log().debug("adding profile: {}={}", key, value);
    profileMap.put(key, new ProfileConf(key, value));
  }*/


  /**
   * @param sipAccount
   * @param sb
   */
  private void generateParking(SipAccountModel sipAccount, StringBuilder sb)
  {
    if (generateParking)
    {
      sb.append("exten => "+ContactaConstants.PARK_PREFIX);
      sb.append(sipAccount.getLogin());
      sb.append(",1(Musica),Playback(moh-park)\n");
      sb.append("exten => "+ContactaConstants.PARK_PREFIX);
      sb.append(sipAccount.getLogin());
      sb.append(",n,GoTo(Musica)\n");
    }
  }


  /**
   * @param sipAccountList
   * @param drop612
   * @return
   */
  public StringBuilder generate(List<SipAccountModel> sipAccountList, boolean drop612)
  {
    StringBuilder sb = new StringBuilder();
    for (SipAccountModel sipAccount : sipAccountList)
    {
      log().info("sip: {}, {}", sipAccount.getLogin(), sipAccount.getProfile() != null ? sipAccount.getProfile().getCode() : null);
      if (sipAccount.getProfile() != null)
      {
        //ProfileConf profileConf = profileMap.get(sipAccount.getProfile().getCode());
        PbxProfileModel profile = pbxProfileDao.findByCode(sipAccount.getProfile().getCode());
        if (profile != null) //profileConf != null && StringUtils.isNotBlank(profileConf.value))
        {
          String line = "";
          if (drop612)
          {
            sb.append("exten => ");
            sb.append(sipAccount.getLogin());
            sb.append(",1,AGI(");
            sb.append(agiBaseUrl);
            sb.append("/drop612)\n");

            line = "exten => "+sipAccount.getLogin()+",n,"+profile.getCommand();//profileConf.value;
          }
          else
          {
            line = "exten => "+sipAccount.getLogin()+",1,"+profile.getCommand();//profileConf.value;
          }

          if (profile.getHasOptions() && sipAccount.getProfileOptions() != null)
          {
            line = line.replaceAll(SipAccountModel.PROFILE_OPTIONS_KEY, sipAccount.getProfileOptions());
          }
          //log().info("write: {}", line);
          sb.append(line);
          sb.append('\n');
          /*if (sipAccount.getHasCoverage())//(StringUtils.equals(profileConf.key, SipAccountModel.PROFILE_COVERAGE))
          {
            sb.append("exten => ");
            sb.append(sipAccount.getLogin());
            sb.append(",hint,SIP/");
            sb.append(sipAccount.getLogin());
            sb.append("\n");
          }*/
        }

        generateParking(sipAccount, sb);
        sb.append("\n");
      }
    }
    return sb;
  }

}
