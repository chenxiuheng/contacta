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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mic.contacta.json.SipAccountJson;
import mic.contacta.model.CocModel;
import mic.contacta.model.SipAccountModel;
import mic.contacta.server.dao.PbxContextDao;
import mic.contacta.server.spi.CocService;
import mic.contacta.server.spi.SipService;


/**
 *
 * @author mic
 * @created May 27, 2009
 */
@Service
public class SipAdapter
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private SipService sipService;
  @Autowired private CocService cocService;
  @Autowired PbxContextDao pbxContextDao;

  @Autowired private SipAccountConverter sipAccountConverter;


  /*
   *
   */
  private void addCoc(SipAccountJson json, SipAccountModel model)
  {
    if (StringUtils.isNotBlank(json.getCocLogin()) /*&& StringUtils.isNotBlank(json.getCocPassword())*/)
    {
      if (model.getCoc() == null)
      {
        CocModel coc = new CocModel(json.getCocLogin(), json.getCocPin());
        model.setCoc(coc);
      }
      else
      {
        model.getCoc().setLogin(json.getCocLogin());
        model.getCoc().setPin(json.getCocPin());
      }
    }
  }


  /*
   *
   */
  public SipAccountModel accountCreate(SipAccountJson json)
  {
    SipAccountModel model = sipAccountConverter.jsonToModel(json, null);
    model = sipService.sipCreate(model);

    addCoc(json, model);
    return model;
  }


  /*
   *
   */
  public void accountUpdate(SipAccountJson json, SipAccountModel model)
  {
    sipAccountConverter.jsonToModel(json, model);

    if(StringUtils.isBlank(json.getCocLogin()))
    {
      CocModel coc = model.getCoc();

      if(coc != null)
      {
        model.setCoc(null);
        cocService.deleteCoc(coc.getId());
      }
    }
    else
    {
      addCoc(json, model);
    }

    sipService.sipUpdate(model);
  }



}
