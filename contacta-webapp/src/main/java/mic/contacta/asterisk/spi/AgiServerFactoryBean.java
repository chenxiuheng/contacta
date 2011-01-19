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
package mic.contacta.asterisk.spi;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.asteriskjava.fastagi.AgiServer;
import org.asteriskjava.fastagi.DefaultAgiServer;
import org.asteriskjava.fastagi.MappingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;


/**
 *
 * @author mic
 * @created Jan 20, 2009
 */
//@Service
public class AgiServerFactoryBean /*extends DefaultAgiServer*/ implements FactoryBean<AgiServer>
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  private AgiServer agiServer;
  private Thread thread;

  private MappingStrategy mappingStrategy;
  private int port = 4573;
  private int poolSize = 10;
  private int maximumPoolSize = 100;
  private boolean disabled;


  /*
   *
   */
  public AgiServerFactoryBean()
  {
  }


  /*
   * @see org.springframework.beans.factory.FactoryBean#getObject()
   */
  @Override
  public AgiServer getObject() throws Exception
  {
    if (agiServer == null)
    {
      final DefaultAgiServer defaultAgiServer = new DefaultAgiServer();
      defaultAgiServer.setMaximumPoolSize(maximumPoolSize);
      defaultAgiServer.setPoolSize(poolSize);
      defaultAgiServer.setPort(port);
      if (mappingStrategy != null)
      {
        defaultAgiServer.setMappingStrategy(mappingStrategy);
      }
      if (disabled)
      {
        log().warn("AgiServer is disabled");
      }
      else
      {
        thread = new Thread(new Runnable()
        {
          @Override
          public void run()
          {
            try
            {
              log().info("thread is starting up agiServer ({})", thread);
              defaultAgiServer.startup();
              log().info("thread is going to end ({})", thread);
            }
            catch (Exception e)
            {
              log().warn(e.getMessage(), e);
            }
          }
        });
        thread.start();
      }
      agiServer = defaultAgiServer;
    }
    return agiServer;
  }


  /*
   * @see org.springframework.beans.factory.FactoryBean#getObjectType()
   */
  @Override
  public Class<?> getObjectType()
  {
    return (/*agiServer != null ? agiServer.getClass() :*/ AgiServer.class);
  }


  /*
   * @see org.springframework.beans.factory.FactoryBean#isSingleton()
   */
  @Override
  public boolean isSingleton()
  {
    return true;
  }


  /**
   * Unregisters the <code>MBeanServer</code> instance, if necessary.
   */
  @PreDestroy
  public void destroy()
  {
    if (agiServer != null)
    {
      agiServer.shutdown();
    }
  }


  /*
   * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
   */
  @PostConstruct
  public void afterPropertiesSet() throws Exception
  {
  }


  /**
   * @return the mappingStrategy
   */
  public MappingStrategy getMappingStrategy()
  {
    return mappingStrategy;
  }


  /**
   * @param mappingStrategy the mappingStrategy to set
   */
  public void setMappingStrategy(MappingStrategy mappingStrategy)
  {
    this.mappingStrategy = mappingStrategy;
  }


  /**
   * @return the port
   */
  public int getPort()
  {
    return port;
  }


  /**
   * @param port the port to set
   */
  public void setPort(int port)
  {
    this.port = port;
  }


  /**
   * @return the poolSize
   */
  public int getPoolSize()
  {
    return poolSize;
  }


  /**
   * @param poolSize the poolSize to set
   */
  public void setPoolSize(int poolSize)
  {
    this.poolSize = poolSize;
  }


  /**
   * @return the maximumPoolSize
   */
  public int getMaximumPoolSize()
  {
    return maximumPoolSize;
  }


  /**
   * @param maximumPoolSize the maximumPoolSize to set
   */
  public void setMaximumPoolSize(int maximumPoolSize)
  {
    this.maximumPoolSize = maximumPoolSize;
  }


  /**
   * @return the disabled
   */
  public boolean getDisabled()
  {
    return disabled;
  }


  /**
   * @param disabled the disabled to set
   */
  public void setDisabled(boolean disabled)
  {
    this.disabled = disabled;
  }

}
