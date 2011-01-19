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
package mic.contacta.model;


/**
 * @author mic
 *
 */
public class CdrQueryModel
{

  /**
   * Field caller
   */
  private String caller;

  /**
   * Field callee
   */
  private int callee;

  /**
   * keeps track of state for field: callee
   */
  private boolean hascallee;

  /**
   * Field callerId
   */
  private String callerId;

  /**
   * Field callStatus
   */
  private String callStatus;

  /**
   * Field callApplication
   */
  private int callApplication;

  /**
   * keeps track of state for field: callApplication
   */
  private boolean hascallApplication;

  /**
   * Field begin
   */
  private long begin;

  /**
   * keeps track of state for field: begin
   */
  private boolean hasbegin;

  /**
   * Field end
   */
  private long end;

  /**
   * keeps track of state for field: end
   */
  private boolean hasend;

  /**
   * Field items
   */
  private int items = 200;

  /**
   * keeps track of state for field: items
   */
  private boolean hasitems;

  /**
   * @return the caller
   */
  public String getCaller()
  {
    return caller;
  }

  /**
   * @param caller the caller to set
   */
  public void setCaller(String caller)
  {
    this.caller = caller;
  }

  /**
   * @return the callee
   */
  public int getCallee()
  {
    return callee;
  }

  /**
   * @param callee the callee to set
   */
  public void setCallee(int callee)
  {
    this.callee = callee;
  }

  /**
   * @return the hascallee
   */
  public boolean isHascallee()
  {
    return hascallee;
  }

  /**
   * @param hascallee the hascallee to set
   */
  public void setHascallee(boolean hascallee)
  {
    this.hascallee = hascallee;
  }

  /**
   * @return the callerId
   */
  public String getCallerId()
  {
    return callerId;
  }

  /**
   * @param callerId the callerId to set
   */
  public void setCallerId(String callerId)
  {
    this.callerId = callerId;
  }

  /**
   * @return the callStatus
   */
  public String getCallStatus()
  {
    return callStatus;
  }

  /**
   * @param callStatus the callStatus to set
   */
  public void setCallStatus(String callStatus)
  {
    this.callStatus = callStatus;
  }

  /**
   * @return the callApplication
   */
  public int getCallApplication()
  {
    return callApplication;
  }

  /**
   * @param callApplication the callApplication to set
   */
  public void setCallApplication(int callApplication)
  {
    this.callApplication = callApplication;
  }

  /**
   * @return the hascallApplication
   */
  public boolean isHascallApplication()
  {
    return hascallApplication;
  }

  /**
   * @param hascallApplication the hascallApplication to set
   */
  public void setHascallApplication(boolean hascallApplication)
  {
    this.hascallApplication = hascallApplication;
  }

  /**
   * @return the begin
   */
  public long getBegin()
  {
    return begin;
  }

  /**
   * @param begin the begin to set
   */
  public void setBegin(long begin)
  {
    this.begin = begin;
  }

  /**
   * @return the hasbegin
   */
  public boolean isHasbegin()
  {
    return hasbegin;
  }

  /**
   * @param hasbegin the hasbegin to set
   */
  public void setHasbegin(boolean hasbegin)
  {
    this.hasbegin = hasbegin;
  }

  /**
   * @return the end
   */
  public long getEnd()
  {
    return end;
  }

  /**
   * @param end the end to set
   */
  public void setEnd(long end)
  {
    this.end = end;
  }

  /**
   * @return the hasend
   */
  public boolean isHasend()
  {
    return hasend;
  }

  /**
   * @param hasend the hasend to set
   */
  public void setHasend(boolean hasend)
  {
    this.hasend = hasend;
  }

  /**
   * @return the items
   */
  public int getItems()
  {
    return items;
  }

  /**
   * @param items the items to set
   */
  public void setItems(int items)
  {
    this.items = items;
  }

  /**
   * @return the hasitems
   */
  public boolean isHasitems()
  {
    return hasitems;
  }

  /**
   * @param hasitems the hasitems to set
   */
  public void setHasitems(boolean hasitems)
  {
    this.hasitems = hasitems;
  }


}
