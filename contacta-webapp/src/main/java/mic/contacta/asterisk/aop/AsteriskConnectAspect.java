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
package mic.contacta.asterisk.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mic.contacta.asterisk.spi.AsteriskService;


/**
 * This aspect works on all methods of AsteriskService except checkConnection
 *
 * @author mic
 * @created Nov 23, 2008
 */
@Service
@Aspect
public class AsteriskConnectAspect
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  @Autowired private AsteriskService asteriskService;


  /*
   * Pointcut("within(mic.TbeanImpl)")
   */
  @Pointcut("execution(* mic.contacta.asterisk.spi.AsteriskService.*(..)) && !execution(* mic.contacta.asterisk.spi.AsteriskService.getDisabled(..))")
  public void starCut()
  {
  }


  /*
   *
   */
  @Pointcut("execution(* mic.contacta.asterisk.spi.AsteriskService.getConnections(..)) || execution(* mic.contacta.asterisk.spi.AsteriskService.checkConnection(..)) || execution(* mic.contacta.asterisk.spi.AsteriskService.connectAsterisk(..)) || execution(* mic.contacta.asterisk.spi.AsteriskService.disconnectAsterisk(..))")
  public void starConnectCut()
  {
  }


  /*
   *
   */
  @Around("starCut() && !starConnectCut()")
  public Object verifyIfConnected(ProceedingJoinPoint pjp) throws Throwable
  {
    //log().info("---cut-here---");
    if (asteriskService.getDisabled())
    {
      log().warn("AsteriskServiceImpl is disabled");
      return null;
    }

    boolean connected = asteriskService.checkConnection();
    log().debug("connected={}", connected);
    if (connected == false)
    {
      asteriskService.connectAsterisk();
    }

    Object result = null;
    try
    {
      result = pjp.proceed();
    }
    catch(Throwable t)
    {
      throw t;
    }
    finally
    {
      asteriskService.disconnectAsterisk();
    }
    return result;
  }

}
