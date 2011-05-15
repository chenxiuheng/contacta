/* $Id$
 *
 * Copyright(C) 2011 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.contacta.json;

import mic.organic.gateway.BasicJson;


/**
 * @author mic
 * @created May 13, 2011
 */
public class CallsJson extends BasicJson
{
  private String calldate;
  private String clid;
  private String src;
  private String dst;
  private String dcontext;
  private String channel;
  private String dstchannel;
  private String lastapp;
  private String lastdata;
  private long duration;
  private long billsec;
  private String disposition;
  private long amaflags;
  private String accountcode;
  private String uniqueid;
  private String userfield;

  private String exten;  // used by skype integration missed calls


  /**
   *
   */
  public CallsJson()
  {
    super();
  }


  /**
   * @return the calldate
   */
  public String getCalldate()
  {
    return calldate;
  }


  /**
   * @param calldate the calldate to set
   */
  public void setCalldate(String calldate)
  {
    this.calldate = calldate;
  }


  /**
   * @return the clid
   */
  public String getClid()
  {
    return clid;
  }


  /**
   * @param clid the clid to set
   */
  public void setClid(String clid)
  {
    this.clid = clid;
  }


  /**
   * @return the src
   */
  public String getSrc()
  {
    return src;
  }


  /**
   * @param src the src to set
   */
  public void setSrc(String src)
  {
    this.src = src;
  }


  /**
   * @return the dst
   */
  public String getDst()
  {
    return dst;
  }


  /**
   * @param dst the dst to set
   */
  public void setDst(String dst)
  {
    this.dst = dst;
  }


  /**
   * @return the dcontext
   */
  public String getDcontext()
  {
    return dcontext;
  }


  /**
   * @param dcontext the dcontext to set
   */
  public void setDcontext(String dcontext)
  {
    this.dcontext = dcontext;
  }


  /**
   * @return the channel
   */
  public String getChannel()
  {
    return channel;
  }


  /**
   * @param channel the channel to set
   */
  public void setChannel(String channel)
  {
    this.channel = channel;
  }


  /**
   * @return the dstchannel
   */
  public String getDstchannel()
  {
    return dstchannel;
  }


  /**
   * @param dstchannel the dstchannel to set
   */
  public void setDstchannel(String dstchannel)
  {
    this.dstchannel = dstchannel;
  }


  /**
   * @return the lastapp
   */
  public String getLastapp()
  {
    return lastapp;
  }


  /**
   * @param lastapp the lastapp to set
   */
  public void setLastapp(String lastapp)
  {
    this.lastapp = lastapp;
  }


  /**
   * @return the lastdata
   */
  public String getLastdata()
  {
    return lastdata;
  }


  /**
   * @param lastdata the lastdata to set
   */
  public void setLastdata(String lastdata)
  {
    this.lastdata = lastdata;
  }


  /**
   * @return the duration
   */
  public long getDuration()
  {
    return duration;
  }


  /**
   * @param duration the duration to set
   */
  public void setDuration(long duration)
  {
    this.duration = duration;
  }


  /**
   * @return the billsec
   */
  public long getBillsec()
  {
    return billsec;
  }


  /**
   * @param billsec the billsec to set
   */
  public void setBillsec(long billsec)
  {
    this.billsec = billsec;
  }


  /**
   * @return the disposition
   */
  public String getDisposition()
  {
    return disposition;
  }


  /**
   * @param disposition the disposition to set
   */
  public void setDisposition(String disposition)
  {
    this.disposition = disposition;
  }


  /**
   * @return the amaflags
   */
  public long getAmaflags()
  {
    return amaflags;
  }


  /**
   * @param amaflags the amaflags to set
   */
  public void setAmaflags(long amaflags)
  {
    this.amaflags = amaflags;
  }


  /**
   * @return the accountcode
   */
  public String getAccountcode()
  {
    return accountcode;
  }


  /**
   * @param accountcode the accountcode to set
   */
  public void setAccountcode(String accountcode)
  {
    this.accountcode = accountcode;
  }


  /**
   * @return the uniqueid
   */
  public String getUniqueid()
  {
    return uniqueid;
  }


  /**
   * @param uniqueid the uniqueid to set
   */
  public void setUniqueid(String uniqueid)
  {
    this.uniqueid = uniqueid;
  }


  /**
   * @return the userfield
   */
  public String getUserfield()
  {
    return userfield;
  }


  /**
   * @param userfield the userfield to set
   */
  public void setUserfield(String userfield)
  {
    this.userfield = userfield;
  }


  /**
   * @return the exten
   */
  public String getExten()
  {
    return exten;
  }


  /**
   * @param exten the exten to set
   */
  public void setExten(String exten)
  {
    this.exten = exten;
  }

}
