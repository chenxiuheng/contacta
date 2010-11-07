/* $Id: AbstractThomsonConfigurer.java 672 2010-07-24 22:18:23Z michele.bianchi $
 *
 * Copyright(C) 2010 [michele.bianchi@gmail.com]
 * All Rights Reserved
 */
package mic.contacta.pmod.thomson;

import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs.FileSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import mic.contacta.pmod.common.ProvisioningResource;
import mic.contacta.pmod.common.AbstractConfigurer;
import mic.contacta.pmod.common.ProvisioningContext;
import mic.contacta.pmod.common.ProvisioningSession;
import mic.contacta.util.ContactaUtils;


/**
 *
 * @author mic
 * @created Jul 1, 2010
 */
public abstract class AbstractThomsonConfigurer extends AbstractConfigurer
{
  static private Logger logger; @SuppressWarnings("static-access")
  protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

  private String toneCw = "Tone-CW.txt";
  private String toneMelodies = "Tone-Melodies.txt";
  private String toneRg = "Tone-RG.txt";

  private String macConf;// "ST2030S_001F9F16E7F8.txt";
  private String infConf;
  private String commonConf;
  private String telephoneConf;



  /**
   * @param name
   */
  public AbstractThomsonConfigurer(String code)
  {
    super(code);

    setVendor("Thomson");
  }


  /**
   * @param toneCw the toneCw to set
   */
  public void setToneCw(String toneCw)
  {
    this.toneCw = toneCw;
  }


  /**
   * @param toneMelodies the toneMelodies to set
   */
  public void setToneMelodies(String toneMelodies)
  {
    this.toneMelodies = toneMelodies;
  }


  /**
   * @param toneRg the toneRg to set
   */
  public void setToneRg(String toneRg)
  {
    this.toneRg = toneRg;
  }


  /**
   * @param macConf the macConf to set
   */
  public void setMacConf(String macConf)
  {
    this.macConf = macConf;
  }


  /**
   * @param infConf the infConf to set
   */
  public void setInfConf(String infConf)
  {
    this.infConf = infConf;
  }


  /**
   * @param commonConf the commonConf to set
   */
  public void setCommonConf(String commonConf)
  {
    this.commonConf = commonConf;
  }


  /**
   * @param telephoneConf the telephoneConf to set
   */
  public void setTelephoneConf(String telephoneConf)
  {
    this.telephoneConf = telephoneConf;
  }


  /**
   *
   */
  protected void addProvisioningResources()
  {
    addCfg(new GlobalPres(commonConf, confFo));
    addCfg(new GlobalPres(telephoneConf, confFo));
    addCfg(new GlobalPres(toneCw, confFo));
    addCfg(new GlobalPres(toneMelodies, confFo));
    addCfg(new GlobalPres(toneRg, confFo));
    addCfg(new LocalPres(macConf, templateService, confFo));
    addCfg(new LocalInfCfg(macConf, templateService, confFo));
    addCfg(new TemplatePres(infConf, templateService, confFo, false));
    addCfg(new BinaryPres("zz", binaryFo));
  }

  @Override
  protected void configurePath()
  {
    super.configurePath();
    try
    {
      confFo = confFo.resolveFile(getCode());
      if (confFo != binaryFo)
      {
        binaryFo = binaryFo.resolveFile(getCode());
      }
    }
    catch (FileSystemException e)
    {
      log().error("cannot lookup template directory for {}: {}", getCode(), e.getMessage());
    }
  }

  /*
   * e.g.: vendor ST2030 hw5 fw1.59 00-14-7F-00-C4-2A
   * @see mic.contacta.server.ptool.Configurer#match()
   */
  @Override
  public boolean match(ProvisioningContext provisioningContext)
  {
    return StringUtils.containsIgnoreCase(provisioningContext.getUserAgent(), getUserAgent());
  }


  /*
   * @see mic.contacta.pmod.common.Configurer#doProvisioning(mic.contacta.pmod.common.ProvisioningContext, mic.contacta.pmod.common.ProvisioningSession)
   */
  @Transactional(propagation = Propagation.MANDATORY)
  @Override
  public ProvisioningResource doProvisioning(ProvisioningContext provisioningContext, ProvisioningSession provisioningSession) throws IOException
  {
    String resourceName = provisioningContext.getResourceName().substring(getCode().length()+1); // +1 = leading "/"
    provisioningContext.setResourceName(resourceName);

    ProvisioningResource cfg = lookupCfg(resourceName);
    if (cfg != null)
    {
      log().debug("MATCH ======================= {}", resourceName);

      if (cfg instanceof LocalPres || cfg instanceof LocalInfCfg)
      {
        int i = resourceName.indexOf('_')+1;
        if (i > 0)
        {
          String macAddress = StringUtils.substring(resourceName, i, i+12).toLowerCase();
          String macText = ContactaUtils.macAddressHexToText(macAddress);
          log().info("macAddress={}/{}", macAddress, macText);
          provisioningSession.setMacAddress(macText);
          provisioningSession.setProductCode(getCode());
        }
      }
      provisioningSession.addResource(cfg);
    }
    else
    {
      log().warn("cannot match a valid resource cfg for {}", provisioningContext.getResourceName());
      return null;
    }
    return cfg;
  }


}
