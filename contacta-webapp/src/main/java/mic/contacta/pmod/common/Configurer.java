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

import java.io.IOException;
import java.util.List;
import mic.contacta.domain.ProductModel;
import mic.organic.core.Service;


/**
 *
 * @author mic
 * @created Jan 25, 2009
 */
public interface Configurer extends Service
{
  String CONFIGURATIONS = "configurations";
  String LOGS_PATH = "logs";


  /**
   * return the name of the configurar, e.g.: polycom, thomson, ...
   *
   * @return
   */
  String getCode();


  /**
   * @return
   */
  List<ProductModel> getSupportedProductList();


  /**
   *
   * @param provisioningContext
   * @return
   */
  ProvisioningSession createSession(ProvisioningContext provisioningContext);


  /**
   * check if the request is from a phone for this configurer
   * @param provisioningContext
   *
   * @return
   */
  boolean match(ProvisioningContext provisioningContext);


  /*
   * TODO uhm... non so se mi piace veramente questa signature...
   */
  ProvisioningResource doProvisioning(ProvisioningContext provisioningContext, ProvisioningSession provisioningSession) throws IOException;


  /**
   * @return
   */
  List<String> getResourceNameList();


}
