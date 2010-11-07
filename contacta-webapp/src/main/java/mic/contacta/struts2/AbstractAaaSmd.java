/* $Id: AbstractAaaSmd.java 669 2010-07-22 21:38:16Z michele.bianchi $
 *
 * Copyright(C) 2010 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.contacta.struts2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.opensymphony.xwork2.Action;
import mic.organic.core.Model;
import mic.organic.gateway.AbstractJsonSmd;
import mic.organic.gateway.Json;


/**
 *
 * @author mic
 * @created Apr 27, 2010
 */
public abstract class AbstractAaaSmd<J extends Json, M extends Model> extends AbstractJsonSmd<J> implements Action
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  /*
   *
   */
  public AbstractAaaSmd()
  {
    super();
  }


  /**
   * just the default method, doing nothing
   */
  @Override
  public String execute()
  {
    return SUCCESS;
  }


  /**
   *
   */
  public String bind()
  {
    return SUCCESS;
  }


  /**
   * @param oid
   * @return
   */
  public abstract Model findModel(Integer oid);


  /*
   * @see mic.organic.gateway.AbstractJsonSmd#detail()
   */
  @Override
  public String detail()
  {
    if (getOid() == null) //StringUtils.isBlank(code))
    {
      return ERROR;
    }
    model = this.findModel(getOid());
    if (model != null)
    {
      return SUCCESS;
    }
    else
    {
      log().info("cannot find model.code='{}'", code);
      return ERROR;
    }
  }


}
