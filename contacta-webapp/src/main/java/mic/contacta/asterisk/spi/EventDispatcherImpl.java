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

import java.util.HashMap;
import java.util.Map;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.asteriskjava.manager.event.ManagerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * @author mic
 * @created May 24, 2008
 */
@Service(EventDispatcher.SERVICE_NAME)
public class EventDispatcherImpl implements EventDispatcher
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  private boolean disabled = false;
  private ExecutorService executorService;
  private Map<String,Handler<?>> handlerMap;


  /*
   *
   */
  public EventDispatcherImpl()
  {
    super();
  }


  /*
   * @see mic.contacta.server.EventDispatcher#getDisabled()
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


  /**
   * @return the handlerMap
   */
  public Map<String, Handler<?>> getHandlerMap()
  {
    return handlerMap;
  }


  /**
   * @param handlerMap the handlerMap to set
   */
  public void setHandlerMap(Map<String, Handler<?>> handlerMap)
  {
    this.handlerMap = handlerMap;
  }


  /*
   *
   */
  @PostConstruct
  public void setup()
  {
    handlerMap = new HashMap<String,Handler<?>>();
    if (disabled)
    {
      log().warn("Contacta EventDispatcher is disabled");
    }
    else
    {
      log().warn("Contacta EventDispatcher is starting");
      executorService = Executors.newCachedThreadPool();
    }
  }


  /*
   * @see mic.contacta.server.EventDispatcher#onManagerEvent(org.asteriskjava.manager.event.ManagerEvent)
   */
  public void onManagerEvent(ManagerEvent managerEvent)
  {
    Handler<?> handler = handlerMap.get(managerEvent.getClass().getName());
    if (handler == null)
    {
      log().debug("ignore event: {}", managerEvent.getClass().getCanonicalName());
      return;
    }

    handler.setEvent(managerEvent);
    Future<Void> future = executorService.submit(handler);
    try
    {
      /*Void result =*/ future.get(1000, TimeUnit.MILLISECONDS);
      //log().info("result={}", result);
    }
    catch (InterruptedException e)
    {
      log().warn(e.getMessage(), e);
    }
    catch (ExecutionException e)
    {
      log().warn(e.getMessage(), e);
    }
    catch (TimeoutException e)
    {
    }

  }

}
