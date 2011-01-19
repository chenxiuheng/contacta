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
package mic.contacta.json;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import mic.organic.gateway.AbstractJson;

/**
 *
 * @author mic
 * @created May 22, 2009
 */
public class CoverageJson extends AbstractJson
{
  private String code;
  private String type;
  private String options;
  private int ringTimeout = 15;
  private int rank;
  private String fromCid;
  private String toCid;


  /*
   *
   */
  @Override
  public String toString()
  {
    ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
    return builder.toString();
  }


  /*
   *
   */
  public CoverageJson()
  {
    super();
  }


  /*
   *
   */
  public CoverageJson(String fromCid, String toCid, String type, String options, int timeout)
  {
    this();
    this.fromCid = fromCid;
    this.toCid = toCid;
    //this.code = code;
    this.type = type;
    this.options = options;
    setRingTimeout(timeout);
  }


  /**
   * @return the code
   */
  public String getCode()
  {
    return code;
  }


  /**
   * @param code the code to set
   */
  public void setCode(String code)
  {
    this.code = code;
  }


  /**
   * @return the type
   */
  public String getType()
  {
    return type;
  }


  /**
   * @param type the type to set
   */
  public void setType(String type)
  {
    this.type = type;
  }


  /**
   * @return the options
   */
  public String getOptions()
  {
    return options;
  }


  /**
   * @param options the options to set
   */
  public void setOptions(String options)
  {
    this.options = options;
  }


  /**
   * @return the ringTimeout
   */
  public int getRingTimeout()
  {
    return ringTimeout;
  }


  /**
   * @param ringTimeout the ringTimeout to set
   */
  public void setRingTimeout(int ringTimeout)
  {
    ringTimeout = Math.min(ringTimeout, 120);
    ringTimeout = Math.max(ringTimeout, 5);
    this.ringTimeout = ringTimeout;
  }



  /**
   * @return the rank
   */
  public int getRank()
  {
    return rank;
  }


  /**
   * @param rank the rank to set
   */
  public void setRank(int rank)
  {
    this.rank = rank;
  }


  /**
   * @return the fromCid
   */
  public String getFromCid()
  {
    return fromCid;
  }


  /**
   * @param fromCid the fromCid to set
   */
  public void setFromCid(String fromCid)
  {
    this.fromCid = fromCid;
  }


  /**
   * @return the toCid
   */
  public String getToCid()
  {
    return toCid;
  }


  /**
   * @param toCid the toCid to set
   */
  public void setToCid(String toCid)
  {
    this.toCid = toCid;
  }


}
