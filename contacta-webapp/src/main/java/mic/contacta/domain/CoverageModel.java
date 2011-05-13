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
package mic.contacta.domain;

import javax.persistence.*;

import mic.organic.core.AbstractModel;


/**
 *
 * @author mic
 * @created Feb 3, 2009
 */
@Entity
@Table(name="cocvg")
public class CoverageModel extends AbstractModel
{
  public static final String [] coverageExcludes = { "fromSip", "toSip" };

  public enum CoverageType { Presence, Manager, Assistant, Assistant2nd };

  private String code;
  private CoverageType type = CoverageType.Presence;
  private String options;
  private int ringTimeout = 15;
  private int rank;
  private SipAccountModel fromSip;
  private SipAccountModel toSip;


  /*
   *
   */
  public CoverageModel()
  {
    setExcludes(coverageExcludes);
  }


  /*
   *
   */
  public CoverageModel(SipAccountModel fromSip, SipAccountModel toSip, CoverageType type, String options, int timeout)
  {
    this();
    this.fromSip = fromSip;
    this.toSip = toSip;
    //this.code = code;
    this.type = type;
    this.options = options;
    setRingTimeout(timeout);
  }


  /**
   * @return the code
   */
  @Basic//(optional=false)
  //@Column(unique=true,nullable=false)
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
  @Basic(optional=false)
  @Column(nullable=false)
  public CoverageType getType()
  {
    return type;
  }


  /**
   * @param type the type to set
   */
  public void setType(CoverageType type)
  {
    this.type = type;
  }


  /**
   * @return the options
   */
  @Basic
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
  @Basic
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
  @Basic
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
   * @return the fromSip
   */
  @ManyToOne(optional=false)
  public SipAccountModel getFromSip()
  {
    return fromSip;
  }


  /**
   * @param fromSip the fromSip to set
   */
  public void setFromSip(SipAccountModel fromSip)
  {
    this.fromSip = fromSip;
  }


  /**
   * @return the toSip
   */
  @ManyToOne(optional=false)
  public SipAccountModel getToSip()
  {
    return toSip;
  }


  /**
   * @param toSip the toSip to set
   */
  public void setToSip(SipAccountModel toSip)
  {
    this.toSip = toSip;
  }


  /**
   * @return
   */
  @Transient
  public String getFromCid()
  {
    return fromSip.getCallerId();
  }


  /**
   * @return
   */
  @Transient
  public String getToCid()
  {
    return toSip.getCallerId();
  }

}
