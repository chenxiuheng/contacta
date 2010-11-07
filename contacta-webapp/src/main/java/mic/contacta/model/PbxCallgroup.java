/* $Id: PbxCallgroup.java 647 2010-06-07 21:41:41Z michele.bianchi $
 *
 * Copyright(C) 2010 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.contacta.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Index;
import mic.organic.core.AbstractCodeModel;

/**
 *
 * @author mic
 * @created May 30, 2010
 */
@Entity
@Table(name = "coclg")
@org.hibernate.annotations.Table(appliesTo = "coclg", indexes = { @Index(name="coclg_code_idx", columnNames = { "code" } ) } )
public class PbxCallgroup extends AbstractCodeModel
{
  private String label;


  /*
   * let use it only by jpa
   */
  @Deprecated
  public PbxCallgroup()
  {
    super(null);
  }


}
