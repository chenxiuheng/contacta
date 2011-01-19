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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import mic.contacta.json.CoverageJson;
import mic.contacta.model.CoverageModel;
import mic.organic.gateway.AbstractJsonConverter;

/**
 *
 * @author mic
 * @created May 23, 2010
 */
@Service
public class CoverageConverter extends AbstractJsonConverter<CoverageModel, CoverageJson>
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  /*
   *
   */
  @Override
  public CoverageModel jsonToModel(CoverageJson from, CoverageModel to)
  {
    if (to == null)
    {
      to = new CoverageModel();
    }

    super.jsonToModel(from, to);
    //to.setCode(from.getCode());

    // TODO

    return to;
  }


  /*
   *
   */
  @Override
  public CoverageJson modelToJson(CoverageModel from, CoverageJson to)
  {
    if (to == null)
    {
      to = new CoverageJson();
    }

    super.modelToJson(from, to);

    // TODO: non e' da mettere nell'AbstractConverter e cambiare l'interfaccia?
    to.setId(from.getId());
    //to.setCode(from.getCode());

    to.setFromCid(from.getFromCid());
    to.setToCid(from.getToCid());
    //json.setCode(model.getCode());
    to.setType(from.getType().toString());
    to.setOptions(from.getOptions());
    to.setRank(from.getRank());
    to.setRingTimeout(from.getRingTimeout());

    return to;
  }


}
