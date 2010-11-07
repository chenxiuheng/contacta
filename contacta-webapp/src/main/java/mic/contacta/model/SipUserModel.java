/* $Id: ExtensionModel.java 25 2008-12-16 18:30:51Z michele.bianchi@gmail.com $
 *
 * Contacta, http://openinnovation.it - roberto grasso, michele bianchi
 * Copyright(C) 1998-2009 [michele.bianchi@gmail.com]
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

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import mic.organic.core.AbstractModel;


/**
 *
 * @author pencho
 * @created Apr 1, 2008
 */
@Entity
@Table(name="stsip")
public class SipUserModel extends AbstractModel
{
  public enum YesNo { yes, no };

  // Asterisk Sip
  public enum PeerType { user, peer, friend };
  public enum NatValue { yes, no, never, route };

  private String name;
  private String host;
  private NatValue nat;
  private PeerType type;
  private String accountcode;
  private String amaflags;
  private String callgroup;
  private String callerid;
  private YesNo cancallforward;
  private YesNo canreinvite;
  private String context;
  private String defaultip;
  private String dtmfmode;
  private String fromuser;
  private String fromdomain;
  private String insecure;
  private String language;
  private String mailbox;
  private String md5secret;
  private String deny;
  private String permit;
  private String mask;
  private String musiconhold;
  private String pickupgroup;
  private String qualify;
  private String regexten;
  private String restrictcid;
  private String rtptimeout;
  private String rtpholdtimeout;
  private String secret;
  private String setvar;
  private String disallow;
  private String allow;
  private String fullcontact;
  private String ipaddr;
  private String port;
  private String regserver;
  private int regseconds;
  private String username;
  private String callLimit = "2";
  private String defaultUser;
  private String userAgent;
  private String lastMs = "";


  /*
   *
   */
  public SipUserModel()
  {
    super();

    name = "";
    username = "";
    fullcontact = "";
    type = PeerType.friend;
    host = "dynamic";
    dtmfmode = "rfc2833";
    nat = NatValue.no;
    cancallforward = YesNo.yes;
    canreinvite = YesNo.yes;
    disallow = "all";
    allow = "alaw;ulaw";
    ipaddr = "0.0.0.0";
    port = "";
    regseconds = 0;
  }


  /*
   *
   */
  @Basic(optional=false)
  @Column(name="name",length=80,nullable=false,unique=true)
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }


  /*
   *
   */
  @Basic(optional=false)
  @Column(name="host",length=31,nullable=false)
  public String getHost()
  {
    return host;
  }
  public void setHost(String host)
  {
    this.host = host;
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="nat",length=5,nullable=false)
  public String getNat()
  {
    return nat.toString();
  }
  public void setNat(String nat)
  {
    this.nat = NatValue.valueOf(nat);
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="type",length=255,nullable=false)
  public String getType()
  {
    return type.toString();
  }
  public void setType(String type)
  {
    this.type = PeerType.valueOf(type);
  }

  @Basic
  @Column(name="accountcode",length=20)
  public String getAccountcode()
  {
    return accountcode;
  }
  public void setAccountcode(String accountcode)
  {
    this.accountcode = accountcode;
  }

  @Basic
  @Column(name="amaflags",length=13)
  public String getAmaflags()
  {
    return amaflags;
  }
  public void setAmaflags(String amaflags)
  {
    this.amaflags = amaflags;
  }

  @Basic
  @Column(name="callgroup",length=20)
  public String getCallgroup()
  {
    return callgroup;
  }
  public void setCallgroup(String callgroup)
  {
    this.callgroup = callgroup;
  }

  @Basic
  @Column(name="callerid",length=80)
  public String getCallerid()
  {
    return callerid;
  }
  public void setCallerid(String callerid)
  {
    this.callerid = callerid;
  }

  @Basic
  @Column(name="cancallforward",length=3)  // bpchar
  public String getCancallforward()
  {
    return cancallforward.toString();
  }
  public void setCancallforward(String cancallforward)
  {
    this.cancallforward = YesNo.valueOf(cancallforward);
  }

  @Basic
  @Column(name="canreinvite",length=3)  // bpchar
  public String getCanreinvite()
  {
    return canreinvite.toString();
  }
  public void setCanreinvite(String canreinvite)
  {
    this.canreinvite = YesNo.valueOf(canreinvite);
  }

  @Basic
  @Column(name="context",length=80)
  public String getContext()
  {
    return context;
  }
  public void setContext(String context)
  {
    this.context = context;
  }

  @Basic
  @Column(name="defaultip",length=15)
  public String getDefaultip()
  {
    return defaultip;
  }
  public void setDefaultip(String defaultip)
  {
    this.defaultip = defaultip;
  }

  @Basic
  @Column(name="dtmfmode",length=7)
  public String getDtmfmode()
  {
    return dtmfmode;
  }
  public void setDtmfmode(String dtmfmode)
  {
    this.dtmfmode = dtmfmode;
  }

  @Basic
  @Column(name="fromuser",length=80)
  public String getFromuser()
  {
    return fromuser;
  }
  public void setFromuser(String fromuser)
  {
    this.fromuser = fromuser;
  }

  @Basic
  @Column(name="fromdomain",length=80)
  public String getFromdomain()
  {
    return fromdomain;
  }
  public void setFromdomain(String fromdomain)
  {
    this.fromdomain = fromdomain;
  }

  @Basic
  @Column(name="insecure",length=4)
  public String getInsecure()
  {
    return insecure;
  }
  public void setInsecure(String insecure)
  {
    this.insecure = insecure;
  }

  @Basic
  @Column(name="language",length=2)
  public String getLanguage()
  {
    return language;
  }
  public void setLanguage(String language)
  {
    this.language = language;
  }

  /*
   *
   */
  @Basic
  @Column(name="mailbox",length=50,unique=true)
  public String getMailbox()
  {
    return mailbox;
  }
  public void setMailbox(String mailbox)
  {
    this.mailbox = mailbox;
  }

  @Basic
  @Column(name="md5secret",length=80)
  public String getMd5secret()
  {
    return md5secret;
  }
  public void setMd5secret(String md5secret)
  {
    this.md5secret = md5secret;
  }

  @Basic
  @Column(name="deny",length=95)
  public String getDeny()
  {
    return deny;
  }
  public void setDeny(String deny)
  {
    this.deny = deny;
  }

  @Basic
  @Column(name="permit",length=95)
  public String getPermit()
  {
    return permit;
  }
  public void setPermit(String permit)
  {
    this.permit = permit;
  }

  @Basic
  @Column(name="mask",length=95)
  public String getMask()
  {
    return mask;
  }
  public void setMask(String mask)
  {
    this.mask = mask;
  }

  @Basic
  @Column(name="musiconhold",length=100)
  public String getMusiconhold()
  {
    return musiconhold;
  }
  public void setMusiconhold(String musiconhold)
  {
    this.musiconhold = musiconhold;
  }

  @Basic
  @Column(name="pickupgroup",length=20)
  public String getPickupgroup()
  {
    return pickupgroup;
  }
  public void setPickupgroup(String pickupgroup)
  {
    this.pickupgroup = pickupgroup;
  }

  @Basic
  @Column(name="qualify",length=3) // bpchar
  public String getQualify()
  {
    return qualify;
  }
  public void setQualify(String qualify)
  {
    this.qualify = qualify;
  }

  @Basic
  @Column(name="regexten",length=80)
  public String getRegexten()
  {
    return regexten;
  }
  public void setRegexten(String regexten)
  {
    this.regexten = regexten;
  }

  @Basic
  @Column(name="restrictcid",length=3) // bpchar
  public String getRestrictcid()
  {
    return restrictcid;
  }
  public void setRestrictcid(String restrictcid)
  {
    this.restrictcid = restrictcid;
  }

  @Basic
  @Column(name="rtptimeout",length=3) // bpchar
  public String getRtptimeout()
  {
    return rtptimeout;
  }
  public void setRtptimeout(String rtptimeout)
  {
    this.rtptimeout = rtptimeout;
  }

  @Basic
  @Column(name="rtpholdtimeout",length=3) // bpchar
  public String getRtpholdtimeout()
  {
    return rtpholdtimeout;
  }
  public void setRtpholdtimeout(String rtpholdtimeout)
  {
    this.rtpholdtimeout = rtpholdtimeout;
  }

  @Basic
  @Column(name="secret",length=80)
  public String getSecret()
  {
    return secret;
  }
  public void setSecret(String secret)
  {
    this.secret = secret;
  }

  @Basic
  @Column(name="setvar",length=100)
  public String getSetvar()
  {
    return setvar;
  }
  public void setSetvar(String setvar)
  {
    this.setvar = setvar;
  }

  @Basic
  @Column(name="disallow",length=100)
  public String getDisallow()
  {
    return disallow;
  }
  public void setDisallow(String disallow)
  {
    this.disallow = disallow;
  }

  @Basic
  @Column(name="allow",length=100)
  public String getAllow()
  {
    return allow;
  }
  public void setAllow(String allow)
  {
    this.allow = allow;
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="fullcontact",length=80,nullable=false)
  public String getFullcontact()
  {
    return fullcontact;
  }
  public void setFullcontact(String fullcontact)
  {
    this.fullcontact = fullcontact;
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="ipaddr",length=15,nullable=false)
  public String getIpaddr()
  {
    return ipaddr;
  }
  public void setIpaddr(String ipaddr)
  {
    this.ipaddr = ipaddr;
  }

  /*
   * this was int
   */
  @Basic//(optional=false)
  @Column(name="port",length=5)//,nullable=false)
  public String getPort()
  {
    return port;
  }
  public void setPort(String port)
  {
    this.port = port;
  }

  @Column(name="regserver",length=100)
  public String getRegserver()
  {
    return regserver;
  }
  public void setRegserver(String regserver)
  {
    this.regserver = regserver;
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="regseconds",length=10,nullable=false)
  public int getRegseconds()
  {
    return regseconds;
  }
  public void setRegseconds(int regseconds)
  {
    this.regseconds = regseconds;
  }

  /*
   * The 'username' field for sip peers has been deprecated in favor of the term 'defaultuser'
   */
  @Deprecated
  @Basic(optional=false)
  @Column(name="username",length=80,nullable=false)
  public String getUsername()
  {
    return username;
  }
  public void setUsername(String username)
  {
    this.username = username;
  }

  /**
   * @return the callLimit
   */
  @Basic(optional=false)
  @Column(name="\"call-limit\"",length=4,nullable=false)
  public String getCallLimit()
  {
    return callLimit;
  }

  /**
   * @param callLimit the callLimit to set
   */
  public void setCallLimit(String callLimit)
  {
    this.callLimit = callLimit;
  }

  /**
   * @param nat the nat to set
   */
  public void setNat(NatValue nat)
  {
    this.nat = nat;
  }

  /**
   * @param type the type to set
   */
  public void setType(PeerType type)
  {
    this.type = type;
  }

  /**
   * @param cancallforward the cancallforward to set
   */
  public void setCancallforward(YesNo cancallforward)
  {
    this.cancallforward = cancallforward;
  }

  /**
   * @param canreinvite the canreinvite to set
   */
  public void setCanreinvite(YesNo canreinvite)
  {
    this.canreinvite = canreinvite;
  }


  /**
   * length=10
   *
   * @return the defaultUser
   */
  @Basic(optional=true)
  @Column(name="defaultuser",length=40,nullable=true)
  public String getDefaultUser()
  {
    return defaultUser;
  }

  /**
   * @param defaultUser the defaultUser to set
   */
  public void setDefaultUser(String defaultUser)
  {
    this.defaultUser = defaultUser;
  }

  /**
   * @return the userAgent
   */
  @Basic(optional=true)
  @Column(name="useragent",length=80,nullable=true)
  public String getUserAgent()
  {
    return userAgent;
  }

  /**
   * length=20
   *
   * @param userAgent the userAgent to set
   */
  public void setUserAgent(String userAgent)
  {
    this.userAgent = userAgent;
  }

  /**
   * length=16
   *
   * @return the lastMs
   */
  @Basic(optional=true)
  @Column(name="lastms",length=16,nullable=true)
  public String getLastMs()
  {
    return lastMs;
  }

  /**
   * @param lastMs the lastMs to set
   */
  public void setLastMs(String lastMs)
  {
    this.lastMs = lastMs;
  }

}
