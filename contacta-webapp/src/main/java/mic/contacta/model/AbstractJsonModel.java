/* $Id: AbstractJsonModel.java 650 2010-06-15 21:36:25Z michele.bianchi $
 *
 * Copyright(C) 2010 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.contacta.model;

import mic.organic.core.AbstractCodeModel;
import mic.organic.gateway.Json;


/**
 *
 * @author mic
 * @created Jun 13, 2010
 */
public class AbstractJsonModel extends AbstractCodeModel implements Json
{

  /**
   * @param code
   */
  public AbstractJsonModel(String code)
  {
    super(code);
  }

}
