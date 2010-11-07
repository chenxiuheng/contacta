/* $Id: PhoneDaoImpl.java 25 2008-12-16 18:30:51Z michele.bianchi@gmail.com $
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
package mic.contacta.server.dao;

import mic.contacta.model.VoicemailModel;
import mic.contacta.server.api.ContactaConstants;
import mic.organic.core.AbstractDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 *
 * @author michele.bianchi@gmail.com
 * @version $Revision: 634 $
 */
@Repository("voicemailDao")
public class VoicemailDaoImpl extends AbstractDao<VoicemailModel> implements VoicemailDao
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  /*
   *
   */
  public VoicemailDaoImpl()
  {
    super(VoicemailModel.class, ContactaConstants.VOICEMAIL_MAILBOX_KEY);
  }


  /*
   *
   */
  @Transactional(readOnly=true)
  @Override
  public VoicemailModel findByLogin(String login)
  {
    return super.findBySmth(login);
  }

}

