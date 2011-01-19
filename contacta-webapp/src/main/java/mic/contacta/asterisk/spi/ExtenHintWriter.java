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
import org.springframework.stereotype.Service;

import mic.contacta.model.SipAccountModel;
import mic.contacta.model.SipAccountModel.Presence;


/**
 *
 * @author mic
 * @created Jun 7, 2009
 */
@Service
public class ExtenHintWriter
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  /**
   * @param findAccountList
   * @return
   */
  public StringBuilder generate(List<SipAccountModel> sipAccountList)
  {
    StringBuilder sb = new StringBuilder();
    for (SipAccountModel sipAccount : sipAccountList)
    {
      if (sipAccount.getHasCoverage())//(StringUtils.equals(profileConf.key, SipAccountModel.PROFILE_COVERAGE))
      {
        sb.append("exten => ");
        sb.append(sipAccount.getLogin());
        sb.append(",hint,SIP/");
        sb.append(sipAccount.getPresence() == Presence.Dnd ? "DND"+sipAccount.getLogin(): sipAccount.getLogin());
        sb.append("\n");
      }
      sb.append("\n");
    }
    return sb;
  }

}
