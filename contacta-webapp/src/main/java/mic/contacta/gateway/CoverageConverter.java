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
package mic.contacta.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import mic.contacta.domain.CoverageModel;
import mic.contacta.json.CoverageJson;
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
  public CoverageModel jsonToModel(CoverageJson src, CoverageModel dst)
  {
    if (dst == null)
    {
      dst = new CoverageModel();
    }

    super.jsonToModel(src, dst);
    //to.setCode(from.getCode());

    return dst;
  }


  /*
   *
   */
  @Override
  public CoverageJson modelToJson(CoverageModel src, CoverageJson dst)
  {
    if (dst == null)
    {
      dst = new CoverageJson();
    }

    super.modelToJson(src, dst);
    //to.setCode(from.getCode());

    dst.setFromCid(src.getFromCid());
    dst.setToCid(src.getToCid());
    //json.setCode(model.getCode());
    dst.setType(src.getType().toString());
    dst.setOptions(src.getOptions());
    dst.setRank(src.getRank());
    dst.setRingTimeout(src.getRingTimeout());

    return dst;
  }


}
