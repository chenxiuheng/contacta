/* $Id: CoverageConverter.java 642 2010-05-30 09:15:27Z michele.bianchi $
 *
 * Copyright(C) 2010 [michele.bianchi@gmail.com]
 * All Rights Reserved
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
