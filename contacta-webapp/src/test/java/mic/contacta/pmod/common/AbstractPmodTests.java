/* $Id: AbstractPmodTests.java 672 2010-07-24 22:18:23Z michele.bianchi $
 *
 * Copyright(C) 2010 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.contacta.pmod.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import mic.contacta.model.SipAccountModel;
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
