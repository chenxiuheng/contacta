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
package mic.contacta.pmod.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import mic.contacta.domain.SipAccountModel;
import mic.contacta.pmod.common.Configurer;
import mic.contacta.pmod.common.ProvisioningSession;

/**
 *
 * @author mic
 * @created May 29, 2010
 */
public class AbstractPmodTests extends AbstractTestNGSpringContextTests
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  protected String macAddress = "00:04:f2:1f:98:bd";
  protected String ipAddress = "10.1.2.3";

  protected SipAccountModel sipAccount;
  private ProvisioningSession provisioningSession;


  /**
   * @param configurer
   * @return
   */
  protected ProvisioningSession createPhoneSession(Configurer configurer)
  {
    provisioningSession = provisioningSession != null ? provisioningSession : new ProvisioningSession(configurer.getCode(), ipAddress);
    return provisioningSession;
  }


}
