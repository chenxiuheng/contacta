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
package mic.contacta.pmod.thomson;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import mic.contacta.domain.ProductModel;


/**
 *
 * @author mic
 * @created Jan 25, 2009
 */
@Service("thomson2030Configurer")
public class Thomson2030Configurer extends AbstractThomsonConfigurer
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  public static final String ST2030 = "ST2030";


  /*
   *
   */
  public Thomson2030Configurer()
  {
    super(ST2030);

    setUserAgent("THOMSON ST2030");

    setMacConf("ST2030S_");
    setInfConf("st2030s.inf");
    setCommonConf("ComConf2030SG.R11.1.SED.091020.2.69.2.txt");
    setTelephoneConf("TelConf2030SG.R11.1.SED.091020.2.69.2.txt");
  }


  /*
   * @see mic.contacta.server.ptool.Configurer#configure()
   */
  @PostConstruct
  public void configure()
  {
    configurePath();

    addProvisioningResources();

    addSupportedProduct(new ProductModel(ST2030, getVendor(), ST2030, "2.x", getUserAgent()));
  }

}
