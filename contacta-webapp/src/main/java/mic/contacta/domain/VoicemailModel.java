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

import java.sql.Timestamp;
import javax.persistence.*;

import mic.contacta.util.ContactaUtils;


/**
 * Asterisk voicemail
 *
 * @author pencho
 * @created Apr 1, 2008
 */
@Entity
@Table(name="stvmu")
public class VoicemailModel extends mic.organic.core.AbstractModel
{
  public enum YesNo { yes, no };

  private String context;
  private String mailbox;
  private String password;
  private String customer_id;
  private String email;
  private String pager;
  private String tz;
  private String attach;
  private String saycid;
  private String dialout;
  private String callback;
  private String review;
  private String operator;
  private String envelope;
  private String sayduration;
  private int saydurationm;
  private String sendvoicemail;
  private String nextaftercmd;
  private String forcename;
  private String forcegreetings;
  private String hidefromdir;
  private Timestamp stamp;
  private String delete;
  private String fullname;


  /*
   *
   */
  public VoicemailModel()
  {
    super();

    context = "0";
    mailbox = "0";
    setPin("0");
    customer_id = "0";
    email = "";
    pager = "";
    tz = "CET";
    attach = YesNo.yes.toString();
    saycid = YesNo.yes.toString();
    dialout = "";
    callback = "";
    review = YesNo.no.toString();
    operator = YesNo.no.toString();
    envelope = YesNo.no.toString();
    sayduration = YesNo.no.toString();
    saydurationm = 1;
    sendvoicemail = YesNo.no.toString();
    setDelete(YesNo.no.toString());
    nextaftercmd = YesNo.yes.toString();
    forcename = YesNo.no.toString();
    forcegreetings = YesNo.no.toString();
    hidefromdir = YesNo.yes.toString();
    stamp = new Timestamp(System.currentTimeMillis());
    fullname = "";
  }


  /*
   *
   */
  @Basic(optional=false)
  @Column(name="customer_id",length=31,nullable=false)
  public String getCustomer_id()
  {
    return customer_id;
  }
  public void setCustomer_id(String customer_id)
  {
    this.customer_id = customer_id;
  }


  /*
   *
   */
  @Basic(optional=false)
  @Column(name="context",length=50,nullable=false)
  public String getContext()
  {
    return context;
  }
  public void setContext(String context)
  {
    this.context = context;
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="mailbox",length=63,nullable=false)
  public String getMailbox()
  {
    return mailbox;
  }
  public void setMailbox(String mailbox)
  {
    this.mailbox = mailbox;
  }


  /*
   *
   */
  @Basic(optional=false)
  @Column(name="password",length=10,nullable=false)
  public String getPin()
  {
    return password;
  }
  public void setPin(String pin)
  {
    this.password = ContactaUtils.passwordToPin(pin);;
  }


  /**
   * @return the fullname
   */
  @Basic(optional=false)
  @Column(name="fullname",length=150,nullable=false)
  public String getFullname()
  {
    return fullname;
  }

  /**
   * @param fullname the fullname to set
   */
  public void setFullname(String fullname)
  {
    this.fullname = fullname;
  }


  /*
   *
   */
  @Basic(optional=false)
  @Column(name="email",length=50,nullable=false)
  public String getEmail()
  {
    return email;
  }
  public void setEmail(String email)
  {
    this.email = email;
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="",length=50,nullable=false)
  public String getPager()
  {
    return pager;
  }
  public void setPager(String pager)
  {
    this.pager = pager;
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="tz",length=10,nullable=false)
  public String getTz()
  {
    return tz;
  }
  public void setTz(String tz)
  {
    this.tz = tz;
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="attach",length=4,nullable=false)
  public String getAttach()
  {
    return attach;
  }

  public void setAttach(String attach)
  {
    this.attach = YesNo.valueOf(attach).toString();
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="saycid",length=4,nullable=false)
  public String getSaycid()
  {
    return saycid;
  }
  public void setSaycid(String saycid)
  {
    this.saycid = YesNo.valueOf(saycid).toString();
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="dialout",length=10,nullable=false)
  public String getDialout()
  {
    return dialout;
  }
  public void setDialout(String dialout)
  {
    this.dialout = dialout;
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="callback",length=10,nullable=false)
  public String getCallback()
  {
    return callback;
  }

  public void setCallback(String callback)
  {
    this.callback = callback;
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="review",length=4,nullable=false)
  public String getReview()
  {
    return review;
  }
  public void setReview(String review)
  {
    this.review = YesNo.valueOf(review).toString();
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="operator",length=4,nullable=false)
  public String getOperator()
  {
    return operator;
  }
  public void setOperator(String operator)
  {
    this.operator = YesNo.valueOf(operator).toString();
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="envelope",length=4,nullable=false)
  public String getEnvelope()
  {
    return envelope;
  }
  public void setEnvelope(String envelope)
  {
    this.envelope = YesNo.valueOf(envelope).toString();
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="sayduration",length=4,nullable=false)
  public String getSayduration()
  {
    return sayduration;
  }
  public void setSayduration(String sayduration)
  {
    this.sayduration = YesNo.valueOf(sayduration).toString();
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="saydurationm",nullable=false)
  public int getSaydurationm()
  {
    return saydurationm;
  }
  public void setSaydurationm(int saydurationm)
  {
    this.saydurationm = saydurationm;
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="sendvoicemail",length=4,nullable=false)
  public String getSendvoicemail()
  {
    return sendvoicemail;
  }
  public void setSendvoicemail(String sendvoicemail)
  {
    this.sendvoicemail = YesNo.valueOf(sendvoicemail).toString();
  }


  /*
   *
   */
  @Basic(optional=false)
  @Column(name="delete",length=4,nullable=false)
  public String getDelete()
  {
    return delete;
  }
  public void setDelete(String delete)
  {
    this.delete = YesNo.valueOf(delete).toString();
  }


  /*
   *
   */
  @Basic(optional=false)
  @Column(name="nextaftercmd",length=4,nullable=false)
  public String getNextaftercmd()
  {
    return nextaftercmd;
  }
  public void setNextaftercmd(String nextaftercmd)
  {
    this.nextaftercmd = YesNo.valueOf(nextaftercmd).toString();
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="forcename",length=4,nullable=false)
  public String getForcename()
  {
    return forcename;
  }
  public void setForcename(String forcename)
  {
    this.forcename = YesNo.valueOf(forcename).toString();
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="forcegreetings",length=4,nullable=false)
  public String getForcegreetings()
  {
    return forcegreetings;
  }
  public void setForcegreetings(String forcegreetings)
  {
    this.forcegreetings = YesNo.valueOf(forcegreetings).toString();
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="hidefromdir",length=4,nullable=false)
  public String getHidefromdir()
  {
    return hidefromdir;
  }
  public void setHidefromdir(String hidefromdir)
  {
    this.hidefromdir = YesNo.valueOf(hidefromdir).toString();
  }

  /*
   *
   */
  @Basic(optional=false)
  @Column(name="stamp",nullable=false)
  public Timestamp getStamp()
  {
    return stamp;
  }
  public void setStamp(Timestamp stamp)
  {
    this.stamp = stamp;
  }
}
