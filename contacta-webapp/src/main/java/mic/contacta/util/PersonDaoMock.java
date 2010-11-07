/* $Id: PersonDaoMock.java 642 2010-05-30 09:15:27Z michele.bianchi $
 *
 * Copyright(C) 2009 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.contacta.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mic.organic.aaa.ldap.Person;
import mic.organic.aaa.ldap.PersonDao;

/**
 *
 * @author mic
 * @created May 25, 2009
 */
@Deprecated
public class PersonDaoMock implements PersonDao
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }


  /*
   * @see mic.addressbook.PersonDao#create(mic.addressbook.Person)
   */
  @Override
  public void create(Person person)
  {
    // TODO Auto-generated method stub

  }
  /*
   * @see mic.addressbook.PersonDao#delete(mic.addressbook.Person)
   */
  @Override
  public void delete(Person person)
  {
    // TODO Auto-generated method stub

  }
  /*
   * @see mic.addressbook.PersonDao#findAll()
   */
  @Override
  public List<Person> findAll()
  {
    // TODO Auto-generated method stub
    return null;
  }
  /*
   * @see mic.addressbook.PersonDao#findByCn(java.lang.String)
   */
  @Override
  public Person findByCn(String cn)
  {
    // TODO Auto-generated method stub
    return null;
  }
  /*
   * @see mic.addressbook.PersonDao#findByPrimaryKey(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public Person findByPrimaryKey(String ou, String cn)
  {
    // TODO Auto-generated method stub
    return null;
  }
  /*
   * @see mic.addressbook.PersonDao#findByUid(java.lang.String)
   */
  @Override
  public Person findByUid(String cn)
  {
    // TODO Auto-generated method stub
    return null;
  }
  /*
   * @see mic.addressbook.PersonDao#getAllPersonNames()
   */
  @Override
  public List<String> getAllPersonNames()
  {
    // TODO Auto-generated method stub
    return null;
  }
  /*
   * @see mic.addressbook.PersonDao#update(mic.addressbook.Person)
   */
  @Override
  public void update(Person person)
  {
    // TODO Auto-generated method stub

  }


  /*
   * @see mic.organic.aaa.ldap.PersonDao#findByField(java.lang.String, java.lang.String)
   */
  @Override
  public List<Person> findByField(String key, String value)
  {
    // TODO Auto-generated method stub
    return null;
  }


}
