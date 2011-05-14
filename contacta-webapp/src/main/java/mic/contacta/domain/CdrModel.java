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
import org.hibernate.annotations.CacheConcurrencyStrategy;
import mic.organic.core.Model;
import mic.organic.gateway.Json;


/**
 *
drop table stcdr;
CREATE TABLE stcdr (
calldate timestamp with time zone DEFAULT now() NOT NULL,
clid character varying(80) DEFAULT '' NOT NULL,
src character varying(80) DEFAULT '' NOT NULL,
dst character varying(80) DEFAULT '' NOT NULL,
dcontext character varying(80) DEFAULT '' NOT NULL,
channel character varying(80) DEFAULT '' NOT NULL,
dstchannel character varying(80) DEFAULT '' NOT NULL,
lastapp character varying(80) DEFAULT '' NOT NULL,
lastdata character varying(80) DEFAULT '' NOT NULL,
duration bigint DEFAULT 0 NOT NULL,
billsec bigint DEFAULT 0 NOT NULL,
disposition character varying(45) DEFAULT '' NOT NULL,
amaflags bigint DEFAULT 0 NOT NULL,
accountcode character varying(20) DEFAULT '' NOT NULL,
uniqueid character varying(32) DEFAULT '' NOT NULL,
userfield character varying(255) DEFAULT '' NOT NULL
);
 *
 * @author mic
 */
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name="stcdr")
@NamedQueries({
  @NamedQuery(name="cdrMissedSkype", query="from CdrModel where dst=:dst and disposition='NO ANSWER' and dstchannel like 'Skype/%'")
})
public class CdrModel implements Model, Json
{
  public static final String [] excludes = {  };

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
  //private boolean hasduration;
  private long billsec;
  //private boolean hasbillsec;
  private String disposition;
  private long amaflags;
  //private boolean hasamaflags;
  private String accountcode;
  private String uniqueid;
  private String userfield;


  /**
   *
   */
  public CdrModel()
  {
    super();
    //setExcludes(excludes);
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

//
//  /**
//   * @return the hasduration
//   */
//  @Basic(optional=true)
//  @Column(nullable=true,columnDefinition="text")
//  public boolean getHasduration()
//  {
//    return hasduration;
//  }
//
//
//  /**
//   * @param hasduration the hasduration to set
//   */
//  public void setHasduration(boolean hasduration)
//  {
//    this.hasduration = hasduration;
//  }


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

//
//  /**
//   * @return the hasbillsec
//   */
//  @Basic(optional=true)
//  @Column(nullable=true,columnDefinition="text")
//  public boolean getHasbillsec()
//  {
//    return hasbillsec;
//  }
//
//
//  /**
//   * @param hasbillsec the hasbillsec to set
//   */
//  public void setHasbillsec(boolean hasbillsec)
//  {
//    this.hasbillsec = hasbillsec;
//  }


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

//
//  /**
//   * @return the hasamaflags
//   */
//  @Basic(optional=true)
//  @Column(nullable=true,columnDefinition="text")
//  public boolean getHasamaflags()
//  {
//    return hasamaflags;
//  }
//
//
//  /**
//   * @param hasamaflags the hasamaflags to set
//   */
//  public void setHasamaflags(boolean hasamaflags)
//  {
//    this.hasamaflags = hasamaflags;
//  }


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
  @Id //@GeneratedValue
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


  /*
   * @see mic.organic.core.Model#getId()
   */
  @Transient
  @Override
  public int getId()
  {
    return 42;
  }


  /*
   * @see mic.organic.core.Model#setId(int)
   */
  @Override
  public void setId(int id)
  {
  }

}
