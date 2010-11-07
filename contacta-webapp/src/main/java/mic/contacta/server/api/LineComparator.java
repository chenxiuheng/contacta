/* $Id: LineComparator.java 616 2010-04-03 21:07:58Z michele.bianchi $
 *
 * Copyright(C) 2009 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.contacta.server.api;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;


/**
 *
 * @author mic
 * @created Jul 22, 2008
 */
public class LineComparator implements Comparator<Line>
{
  /*
   *  @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
   */
  @Override
  public int compare(Line o1, Line o2)
  {
    int r = new CompareToBuilder()
    //.appendSuper(super.compareTo(o)
    .append(o1.getExten(), o2.getExten())
    .toComparison();
    return r;
  }
}
