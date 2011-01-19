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
import mic.contacta.model.ProductModel;


/**
 *
 * @author mic
 * @created Jan 25, 2009
 */
@Service("thomson2022Configurer")
public class Thomson2022Configurer extends AbstractThomsonConfigurer
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  public static final String ST2022 = "ST2022";


  /*
   *
   */
  public Thomson2022Configurer()
  {
    super(ST2022);

    setUserAgent("THOMSON ST2022");

    setMacConf("ST2022S_");// "ST2030S_001F9F16E7F8.txt";
    setInfConf("st2022s.inf");
    setCommonConf("ComConf2022SG_v3.66.2.txt");
    setTelephoneConf("TelConf2022SG_v3.66.2.txt");
  }


  /*
   * @see mic.contacta.server.ptool.Configurer#configure()
   */
  @PostConstruct
  public void configure()
  {
    configurePath();

    addProvisioningResources();

    addSupportedProduct(new ProductModel(ST2022, getVendor(), ST2022, "2.x", getUserAgent()));
  }

}
