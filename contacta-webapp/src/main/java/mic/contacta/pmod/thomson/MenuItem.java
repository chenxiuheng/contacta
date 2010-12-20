/* $Id$
 *
 * Copyright(C) 2010 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.contacta.pmod.thomson;


/**
 *
 *
 * @author mic
 * @created Dec 19, 2010
 */
public class MenuItem
{
 private String name;
 private String url;


 /**
  *
  */
 public MenuItem()
 {
   super();
 }


 /**
  * @param name
  * @param url
  */
 public MenuItem(String name, String url)
 {
   this();

   this.name = name;
   this.url = url;
 }


 /**
  * @return the name
  */
 public String getName()
 {
   return name;
 }


 /**
  * @param name the name to set
  */
 public void setName(String name)
 {
   this.name = name;
 }


 /**
  * @return the url
  */
 public String getUrl()
 {
   return url;
 }


 /**
  * @param url the url to set
  */
 public void setUrl(String url)
 {
   this.url = url;
 }

}


