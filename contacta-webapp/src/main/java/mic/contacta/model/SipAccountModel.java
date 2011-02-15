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

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

import javax.persistence.*;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;

import mic.contacta.util.ContactaUtils;
import mic.organic.aaa.model.AccountModel;


/**
 *
 * @author pencho
 * @created Apr 1, 2008
 */
@Entity
public class SipAccountModel extends AccountModel
{
  public enum Presence
  {
    Online,
    Dnd
  }
  public static final String PROFILE_OPTIONS_KEY = "PROFILE_OPTIONS";

  private static String voicemailContext = "utenti";

  private PhoneModel phone;
  private CocModel coc;

  private PbxProfileModel profile;
  private String profileOptions;
  private PbxContextModel context;
  private String callgroup;
  private String pickupgroup;
  private int ringTimeout;

  private SipUserModel sipUser;
  private String callerId;
  private Presence presence;
  private boolean hasCoverage;
  private List<CoverageModel> coverageList;

  private boolean vmEnabled;
  private boolean vmSendEmail;
  private VoicemailModel vmBox;


  /**
   * leave this only to jpa, use the other c'tor
   */
  public SipAccountModel()
  {
    super();
    callerId = "TBD";
    hasCoverage = false;
    coverageList = new ArrayList<CoverageModel>();
    presence = Presence.Online;
    sipUser = new SipUserModel();
    vmEnabled = false;

    //vmBox = new VoicemailModel();
    //vmBox.setContext(voicemailContext);
  }


  /*
   *
   */
  public SipAccountModel(String displayName, String email, String login, String password, PbxContextModel context)
  {
    this();
    setLabel(displayName);
    setEmail(email);

    callerId = ContactaUtils.mkCallerId(displayName, login);
    sipUser.setCallerid(callerId);

    setCode(login);
    setPassword(password);
    setContext(context);
    if (context != null)
    {
      sipUser.setContext(context.getCode());
    }
    sipUser.setMailbox(login+"@"+voicemailContext);

    vmBox = buildVmBox(login, password, getLabel(), getVmSendEmail() ? getEmail() : "");
  }


  /**
   * @param login
   * @param password
   * @param label
   * @param email
   */
  public static VoicemailModel buildVmBox(String login, String password, String label, String email)
  {
    VoicemailModel vmBox = new VoicemailModel();               // FIXME move me in default ctor when it will be not null
    vmBox.setContext(voicemailContext);         // FIXME move me in default ctor when it will be not null
    vmBox.setFullname(label);
    vmBox.setMailbox(login);
    vmBox.setPin(password);
    vmBox.setEmail(email);
    return vmBox;
  }


  /*
   * @see mic.organic.aaa.model.AccountModel#setLogin(java.lang.String)
   */
  @Override
  public void setCode(String login)
  {
    sipUser.setName(login);
    sipUser.setUsername(login);
    sipUser.setDefaultUser(login);
    if (vmBox != null)// FIXME remove me when it will be not null
    vmBox.setMailbox(login);
    super.setCode(login);
  }


  /*
   * @see mic.organic.aaa.model.AccountModel#setPassword(java.lang.String)
   */
  @Override
  public void setPassword(String password)
  {
    sipUser.setSecret(password);
    super.setPassword(password);
  }


  /**
   * @return the callerId
   */
  @Basic(optional=false)
  @Column(nullable=false,length=80)
  @NotNull
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


  /*
   *
   */
  @ManyToOne
  public PhoneModel getPhone()
  {
    return phone;
  }
  public void setPhone(PhoneModel phone)
  {
    this.phone = phone;
  }


  /**
   * @return the sipUser
   */
  @OneToOne(optional=false,cascade=CascadeType.ALL)
  @JoinColumn(nullable=false)
  @NotNull
  public SipUserModel getSipUser()
  {
    return sipUser;
  }


  /**
   * @param sipUser the sipUser to set
   */
  public void setSipUser(SipUserModel sipUser)
  {
    this.sipUser = sipUser;
  }


  /**
   * @return the presence
   */
  @Basic(optional=false)
  @Column(nullable=false)
  @NotNull
  public Presence getPresence()
  {
    return presence;
  }


  /**
   * @param presence the presence to set
   */
  public void setPresence(Presence presence)
  {
    this.presence = presence;
  }


  /**
   * @return the hasCoverage
   */
  @Basic(optional=false)
  @Column(nullable=false)
  public boolean getHasCoverage()
  {
    return hasCoverage;
  }


  /**
   * @param hasCoverage the hasCoverage to set
   */
  public void setHasCoverage(boolean hasCoverage)
  {
    this.hasCoverage = hasCoverage;
  }


  /**
   * TODO CascadeType was REMOVE
   *
   * @return
   */
  @OneToMany(cascade=CascadeType.ALL)
  @JoinColumn(nullable=true)
  @org.hibernate.annotations.OrderBy(clause="rank")
  public List<CoverageModel> getCoverageList()
  {
    return coverageList;
  }


  /**
   *
   * @param coverageList
   */
  public void setCoverageList(List<CoverageModel> coverageList)
  {
    this.coverageList = coverageList;
  }


  /**
   * @return the vmEnabled
   */
  @Basic
  public boolean getVmEnabled()
  {
    return vmEnabled;
  }


  /**
   * @param vmEnabled the vmEnabled to set
   */
  public void setVmEnabled(boolean vmEnabled)
  {
    this.vmEnabled = vmEnabled;
  }


  /**
   * TODO move to NOT NULL
   *
   * @return the vmBox
   */
  @OneToOne(optional=true, cascade=CascadeType.ALL, orphanRemoval=true)
  @JoinColumn(nullable=true, name="vm_id")
  public VoicemailModel getVmBox()
  {
    return vmBox;
  }


  /**
   * @param vmBox the vmBox to set
   */
  public void setVmBox(VoicemailModel voicemail)
  {
    this.vmBox = voicemail;
  }


  /**
   * @return the vmSendEmail
   */
  @Basic
  public boolean getVmSendEmail()
  {
    return vmSendEmail;
  }


  /**
   * @param vmSendEmail the vmSendEmail to set
   */
  public void setVmSendEmail(boolean vmSendEmail)
  {
    this.vmSendEmail = vmSendEmail;
  }


  /**
   * @return the vmBox
   */
  @OneToOne(optional=true,cascade=CascadeType.ALL)
  public CocModel getCoc()
  {
    return coc;
  }


  /**
   * @param vmBox the vmBox to set
   */
  public void setCoc(CocModel coc)
  {
    this.coc = coc;
  }


  /**
   * @return the profileName
   */
  @ManyToOne(optional=true)
  @JoinColumn(nullable=true)
  public PbxProfileModel getProfile()
  {
    return profile;
  }


  /**
   * @param profileName the profileName to set
   */
  public void setProfile(PbxProfileModel profile)
  {
    this.profile = profile;
  }


  /**
   * @return the profileOptions
   */
  @Basic
  @Column(length=200)
  public String getProfileOptions()
  {
    return profileOptions;
  }


  /**
   * @param profileOptions the profileOptions to set
   */
  public void setProfileOptions(String profileOptions)
  {
    this.profileOptions = profileOptions;
  }


  /**
   * @return the context
   */
  @ManyToOne(optional=true)
  @JoinColumn(nullable=true)
  public PbxContextModel getContext()
  {
    return context;
  }


  /**
   * After this set is better to invoke getSipUser().setContext(context);
   * i cannot write it here cos jpa invokes this setter during the merge.
   *
   * @param context the context to set
   */
  public void setContext(PbxContextModel context)
  {
    this.context = context;
  }


  /**
   * the contacta callgroup can be anything
   * @return
   */
  @Basic
  @Column(length=200)
  public String getCallgroup()
  {
    return callgroup;
  }


  /**
   * the contacta callgroup can be anything, if it is a number between 0-63 it
   * is copied to asterisk sip users table
   * @param callgroup
   */
  public void setCallgroup(String callgroup)
  {
    Integer i = null;
    try
    {
      i = new Integer(callgroup);
    }
    catch(Exception e)
    {
    }
    sipUser.setCallgroup((i != null && i < 64) ? callgroup : "");
    this.callgroup = callgroup;
  }


  /**
   * the contacta pickupgroup can be anything separated by comma
   * but no ranges like 1-5
   * @return
   */
  @Basic
  @Column(length=64)
  public String getPickupgroup()
  {
    return pickupgroup;
  }


  /**
   * the contacta pickupgroup can be anything separated by comma,
   * but no ranges like 1-5, if it is a number between 0-63 it
   * is copied to asterisk sip users table
   * @param pickupgroup
   */
  public void setPickupgroup(String pickupgroup)
  {
    String asteriskPickupgroup = "";
    if (StringUtils.isNotBlank(pickupgroup))
    {
      String[] pickupgroups = pickupgroup.split(",");
      for (String s : pickupgroups)
      {
        Integer i = null;
        try
        {
          i = new Integer(s);
        }
        catch(Exception e)
        {
        }
        if (i != null && i < 64)
        {
          asteriskPickupgroup += i+",";
        }
      }
    }
    if (StringUtils.isNotBlank(asteriskPickupgroup))
    {
      sipUser.setPickupgroup(asteriskPickupgroup.substring(0, asteriskPickupgroup.length()-1));
    }
    else
    {
      sipUser.setPickupgroup("");
    }
    this.pickupgroup = pickupgroup;
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
   * @return the voicemailContext
   */
  @Transient
  public String getVoicemailContext()
  {
    return voicemailContext;
  }


  /**
   * @param voicemailContext the voicemailContext to set
   */
  public void setVoicemailContext(String voicemailContext)
  {
    //this.voicemailContext = voicemailContext;
    throw new NotImplementedException();
  }


}
