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
import mic.contacta.domain.CdrModel;
import mic.contacta.json.CallsJson;
import mic.organic.gateway.AbstractJsonConverter;

/**
 *
 * @author mic
 * @created May 23, 2010
 */
@Service
public class CallsConverter extends AbstractJsonConverter<CdrModel, CallsJson>
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  /*
   *
   */
  public CdrModel jsonToModel(CallsJson src, CdrModel dst)
  {
    if (dst == null)
    {
      dst = new CdrModel();
    }

    super.jsonToModel(src, dst);
    //dst.setCode(src.getCode());
    dst.setAccountcode(src.getAccountcode());
    dst.setAmaflags(src.getAmaflags());
    dst.setBillsec(src.getBillsec());
    dst.setCalldate(src.getCalldate());
    dst.setChannel(src.getChannel());
    dst.setClid(src.getClid());
    dst.setDcontext(src.getDcontext());
    dst.setDisposition(src.getDisposition());
    dst.setDst(src.getDst());
    dst.setDstchannel(src.getDstchannel());
    dst.setDuration(src.getDuration());
    dst.setLastapp(src.getLastapp());
    dst.setLastdata(src.getLastdata());
    dst.setSrc(src.getSrc());
    dst.setUniqueid(src.getUniqueid());
    dst.setUserfield(src.getUserfield());

    return dst;
  }


  /*
   *
   */
  @Override
  public CallsJson modelToJson(CdrModel src, CallsJson dst)
  {
    if (dst == null)
    {
      dst = new CallsJson();
    }

    super.modelToJson(src, dst);
    //dst.setCode(src.getCode());
    dst.setAccountcode(src.getAccountcode());
    dst.setAmaflags(src.getAmaflags());
    dst.setBillsec(src.getBillsec());
    dst.setCalldate(src.getCalldate());
    dst.setChannel(src.getChannel());
    dst.setClid(src.getClid());
    dst.setDcontext(src.getDcontext());
    dst.setDisposition(src.getDisposition());
    dst.setDst(src.getDst());
    dst.setDstchannel(src.getDstchannel());
    dst.setDuration(src.getDuration());
    dst.setLastapp(src.getLastapp());
    dst.setLastdata(src.getLastdata());
    dst.setSrc(src.getSrc());
    dst.setUniqueid(src.getUniqueid());
    dst.setUserfield(src.getUserfield());

    return dst;
  }


}
