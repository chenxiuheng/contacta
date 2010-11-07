/* $Id: Combo.java 616 2010-04-03 21:07:58Z michele.bianchi $
 *
 * Copyright(C) 2009 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.organic.dojo;

/**
 *
 * @author mic
 * @created Jul 7, 2009
 */

public class Combo
{
  private String[] options;



  /**
   * @param options
   */
  public Combo(String[] options)
  {
    super();
    this.options = options;
  }


  /**
   * @return the options
   */
  public String[] getOptions()
  {
    return options;
  }


  /**
   * @param options the options to set
   */
  public void setOptions(String[] options)
  {
    this.options = options;
  }

}
