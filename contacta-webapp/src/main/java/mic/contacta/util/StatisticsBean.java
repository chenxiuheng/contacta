/* $Id: StatisticsBean.java 875 2008-01-18 18:20:53Z michele.bianchi@gmail.com $
 *
 * Contacta, http://openinnovation.it - roberto grasso, michele bianchi
 * Copyright(C) 1998-2009 [michele.bianchi@gmail.com]
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
package mic.contacta.util;


/**
 * This class implements...,
 *
 * @author michele.bianchi@gmail.com
 * @version $Revision: 621 $
 */
public class StatisticsBean
{
  private float uptime1;
  private float uptime5;
  private float uptime15;

  private int memTotal;
  private int memUsed;
  private int memFree;
  private int memCached;

  private int [] irqCount;
  private String [] irqLabels;

  private long bootTime;
  private long ioWait;
  private long userMode;
  private long userModeNice;
  private long systemMode;
  private long idleTask;
  private long irq;
  private long softIrq;
  private long softIrqs;

  private int activeChannels;
  private int activeCalls;
  private int sipPeers;
  private int sipChannels;
  private int zapChannels;


   /*
   *
   */
  public float getUptime1()
  {
    return uptime1;
  }


  /*
   *
   */
  public void setUptime1(float uptime1)
  {
    this.uptime1 = uptime1;
  }


  /*
   *
   */
  public float getUptime5()
  {
    return uptime5;
  }


  /*
   *
   */
  public void setUptime5(float uptime5)
  {
    this.uptime5 = uptime5;
  }


  /*
   *
   */
  public float getUptime15()
  {
    return uptime15;
  }


  /*
   *
   */
  public void setUptime15(float uptime15)
  {
    this.uptime15 = uptime15;
  }

  /*
   *
   */
  public int getMemTotal()
  {
    return memTotal;
  }


  /*
   *
   */
  public void setMemTotal(int memTotal)
  {
    this.memTotal = memTotal;
  }


  /*
   *
   */
  public int getMemFree()
  {
    return memFree;
  }


  /*
   *
   */
  public void setMemFree(int memFree)
  {
    this.memFree = memFree;
  }


  /*
   *
   */
  public int getMemUsed()
  {
    return memUsed;
  }


  /*
   *
   */
  public void setMemUsed(int memUsed)
  {
    this.memUsed = memUsed;
  }


  /*
   *
   */
  public int getMemCached()
  {
    return memCached;
  }


  /*
   *
   */
  public void setMemCached(int memCached)
  {
    this.memCached = memCached;
  }


  /*
   *
   */
  public long getIoWait()
  {
    return ioWait;
  }


  /*
   *
   */
  public void setIoWait(long ioWait)
  {
    this.ioWait = ioWait;
  }


  /*
   *
   */
  public long getBootTime()
  {
    return bootTime;
  }


  /*
   *
   */
  public void setBootTime(long bootTime)
  {
    this.bootTime = bootTime;
  }


  /*
   *
   */
  public long getUserMode()
  {
    return userMode;
  }


  /*
   *
   */
  public void setUserMode(long userMode)
  {
    this.userMode = userMode;
  }


  /*
   *
   */
  public long getUserModeNice()
  {
    return userModeNice;
  }


  /*
   *
   */
  public void setUserModeNice(long userModeNice)
  {
    this.userModeNice = userModeNice;
  }


  /*
   *
   */
  public long getSystemMode()
  {
    return systemMode;
  }


  /*
   *
   */
  public void setSystemMode(long systemMode)
  {
    this.systemMode = systemMode;
  }


  /*
   *
   */
  public long getIdleTask()
  {
    return idleTask;
  }


  /*
   *
   */
  public void setIdleTask(long idleTask)
  {
    this.idleTask = idleTask;
  }


  /*
   *
   */
  public long getIrq()
  {
    return irq;
  }


  /*
   *
   */
  public void setIrq(long irq)
  {
    this.irq = irq;
  }


  /*
   *
   */
  public long getSoftIrq()
  {
    return softIrq;
  }


  /*
   *
   */
  public void setSoftIrq(long softIrq)
  {
    this.softIrq = softIrq;
  }


  /*
   *
   */
  public long getSoftIrqs()
  {
    return softIrqs;
  }


  /*
   *
   */
  public void setSoftIrqs(long softIrqs)
  {
    this.softIrqs = softIrqs;
  }


  /*
   *
   */
  public int[] getIrqCount()
  {
    return irqCount;
  }


  /*
   *
   */
  public void setIrqCount(int[] irqCount)
  {
    this.irqCount = irqCount;
  }


  /*
   *
   */
  public String[] getIrqLabels()
  {
    return irqLabels;
  }


  /*
   *
   */
  public void setIrqLabels(String[] irqLabels)
  {
    this.irqLabels = irqLabels;
  }


  /*
   *
   */
  public int getActiveChannels()
  {
    return activeChannels;
  }


  /*
   *
   */
  public void setActiveChannels(int activeChannels)
  {
    this.activeChannels = activeChannels;
  }


  /*
   *
   */
  public int getActiveCalls()
  {
    return activeCalls;
  }


  /*
   *
   */
  public void setActiveCalls(int activeCalls)
  {
    this.activeCalls = activeCalls;
  }


  /*
   *
   */
  public int getSipPeers()
  {
    return sipPeers;
  }


  /*
   *
   */
  public void setSipPeers(int sipPeers)
  {
    this.sipPeers = sipPeers;
  }


  /*
   *
   */
  public int getSipChannels()
  {
    return sipChannels;
  }


  /*
   *
   */
  public void setSipChannels(int sipChannels)
  {
    this.sipChannels = sipChannels;
  }


  /*
   *
   */
  public int getZapChannels()
  {
    return zapChannels;
  }


  /*
   *
   */
  public void setZapChannels(int zapChannels)
  {
    this.zapChannels = zapChannels;
  }








}

