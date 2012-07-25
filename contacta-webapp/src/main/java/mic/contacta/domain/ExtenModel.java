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
package mic.contacta.domain;

import javax.persistence.*;

import mic.organic.core.AbstractModel;


/**
 *
 * @author mic
 * @created Feb 3, 2009
 */
@Entity
@Table(name="stext")
public class ExtenModel extends AbstractModel
{
  public static final String [] extenExcludes = { };

  private String context;
  private String exten;
  private int priority;
  private String app;
  private String data;


  /*
   *
   */
  @Deprecated
  public ExtenModel()
  {
    setExcludes(extenExcludes);
  }


  /*
   *
   */
  public ExtenModel(String context, String exten, int priority, String app, String data)
  {
    this();
    this.context = context;
    this.exten = exten;
    this.priority = priority;
    this.app = app;
    this.data = data;
  }


  /**
   * @return the context
   */
  @Basic(optional=false)
  @Column(name="context",nullable=false,length=20)
  public String getContext()
  {
    return context;
  }


  /**
   * @param context the context to set
   */
  public void setContext(String code)
  {
    this.context = code;
  }


  /**
   * @return the exten
   */
  @Basic(optional=false)
  @Column(name="exten",nullable=false,length=20)
  public String getExten()
  {
    return exten;
  }


  /**
   * @param exten the exten to set
   */
  public void setExten(String options)
  {
    this.exten = options;
  }


  /**
   * @return the priority
   */
  @Basic(optional=false)
  @Column(name="priority",nullable=false)
  public int getPriority()
  {
    return priority;
  }


  /**
   * @param priority the priority to set
   */
  public void setPriority(int priority)
  {
    this.priority = priority;
  }


  /**
   * @return the app
   */
  @Basic(optional=false)
  @Column(name="app",nullable=false,length=20)
  public String getApp()
  {
    return app;
  }


  /**
   * @param app the app to set
   */
  public void setApp(String app)
  {
    this.app = app;
  }


  /**
   * @return the data
   */
  @Basic(optional=false)
  @Column(name="appdata",nullable=false,length=128)
  public String getData()
  {
    return data;
  }


  /**
   * @param data the data to set
   */
  public void setData(String data)
  {
    this.data = data;
  }

}
