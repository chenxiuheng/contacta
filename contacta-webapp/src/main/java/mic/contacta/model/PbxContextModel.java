/* $Id: PbxContextModel.java 650 2010-06-15 21:36:25Z michele.bianchi $
 *
 * Copyright(C) 2010 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.contacta.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Index;


/**
 *
 * @author mic
 * @created May 30, 2010
 */
@Entity
@Table(name = "pxctx")
@org.hibernate.annotations.Table(appliesTo = "pxctx", indexes = { @Index(name="pxctx_code_idx", columnNames = { "code" } ) } )
public class PbxContextModel extends AbstractJsonModel
{
  private String label;


  /*
   * let use it only by jpa
   */
  @Deprecated
  public PbxContextModel()
  {
    super(null);
  }


  /**
   * @param label the label to set
   */
  @Basic
  public void setLabel(String label)
  {
    this.label = label;
  }


  /**
   * @return the label
   */
  public String getLabel()
  {
    return label;
  }


}
