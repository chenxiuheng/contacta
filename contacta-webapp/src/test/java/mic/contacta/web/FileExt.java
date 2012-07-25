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
package mic.contacta.web;

import java.io.File;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 *
 *
 * @author mic
 * @created Apr 13, 2008
 */
public class FileExt
{
  public File file;
  public String filename;
  public String contentType;
  public long contentLength;

  @Override
  public String toString()
  {
    ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this, ToStringStyle.NO_FIELD_NAMES_STYLE);//.setExcludeFieldNames({ "XXXX" });
    return builder.toString();
  }
}
