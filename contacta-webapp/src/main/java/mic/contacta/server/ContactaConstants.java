/**
 * Contacta webapp, http://www.openinnovation.it - Michele Bianchi
 * Copyright(C) 1999-2012 info@openinnovation.it
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
package mic.contacta.server;

import mic.organic.core.OrganicConstants;


/**
 *
 * @author mic
 * @created May 24, 2008
 */
public interface ContactaConstants extends OrganicConstants
{

  public static enum ListOf
  {
    ListOfCaller("select distinct src from cdr"), ListOfCallee("select distinct dst from cdr");

    public final String sql;


    ListOf(String sql)
    {
      this.sql = sql;
    }
  }

  String VOICEMAIL_MAILBOX_KEY = "mailbox";

  String VAR_SPOOL_ASTERISK_MONITOR = "/var/spool/asterisk/monitor"; // Local directory where to look for Asterisk recorded file
  String VAR_SPOOL_ASTERISK_VOICEMAIL = "/var/spool/asterisk/voicemail/";
  String INBOX = "/INBOX";
  String DURATION = "duration";
  String ORIGDATE = "origdate";
  String CALLERID = "callerid";
  String VOICE_MAIL = "VoiceMail";
  String WAV = "wav";
  String SIP = "SIP/";
  String MESSAGGIO_VOCALE = "Messaggio Vocale";
  String _100 = "100";
  String _999 = "999";
  String INTERNI = "interni";

  String DATE_FORMAT = "yyyy-MM-dd'_'HH-mm-ss";

  String PARK_PREFIX = "park";

  String PTOOL_IMPORT_NEW = "new";
  String PTOOL_IMPORT_UPDATE = "update";

  String PTOOL_REMOTING_SPRING_XML = "ptool-remoting.spring.xml";
  String PTOOL_AOP_SPRING_XML = "ptool-aop.spring.xml";
  String PTOOL_STANDALONE_SPRING_XML = "/ptool-standalone.spring.xml";
  String PTOOL_STANDALONE_LOG4J_PROPERTIES = "/ptool-standalone.log4j.properties";

  String TEST_PROVISIONING_SPRING = "/mic/contacta/test-provisioning.spring.xml";
  String TEST_DS_SPRING = "/mic/contacta/test-orm.spring.xml";
  String TEST_DAO_SPRING = "/mic/contacta/test-dao.spring.xml";
  String TEST_JMX_SPRING = "/ptool-jmx.spring.xml";
  String TEST_IMPORT_SPRING = "/mic/contacta/test-import.spring.xml";
}
