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
package mic.contacta.server;


/**
 * This exception is thrown if the...
 *
 * @author michele bianchi
 * @version $Revision: 616 $
 *
 * @todo document me
 */
public class ContactaException extends Exception
{
  private String text;


  public ContactaException(String text, Throwable cause)
  {
    super(text, cause);
  }

  public ContactaException(Throwable cause)
  {
    super(cause.getMessage(), cause);
  }

  public ContactaException(String text)
  {
    this.text = text;
  }

  public ContactaException()
  {
    text = "Unknown exception message";
  }

  public String getMessage()
  {
    String superMessage = super.getMessage();
    return superMessage == null ? text : text + " / super: "+superMessage;
  }

  public String toString()
  {
    return text;
  }
}

