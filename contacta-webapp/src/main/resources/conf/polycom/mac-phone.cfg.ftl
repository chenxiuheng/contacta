<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<!-- $Id: mac-phone.cfg.ftl 629 2010-05-01 15:20:48Z michele.bianchi $ -->
<!-- :mode=freemarker:xml.validate=true: -->
<!-- Per-phone configuration in this file -->
${config!""}
<reginfo>
<#assign x=1/>
<#list accountList as account>
 <reg reg.${x}.displayName="${account.login}"
      reg.${x}.address="${account.login}"
      reg.${x}.label="${account.login}"
      reg.${x}.auth.userId="${account.login}"
      reg.${x}.auth.password="${account.sipUser.secret}"
      reg.${x}.server.1.address="${contacta.sipHost}"
      reg.${x}.server.1.port="${contacta.sipPort}"
      reg.${x}.server.1.expires="300"
      reg.${x}.server.2.address="${contacta.sipHost2}"
      reg.${x}.server.2.port="${contacta.sipPort2}"
      reg.${x}.server.2.expires="300"
      <#if phone.product.code == "SPIP_330">
      reg.${x}.lineKeys="1"
      reg.${x}.callsPerLineKey="1"
      </#if>
      <#if phone.product.code == "SPIP_450">
      reg.${x}.lineKeys="1"
      reg.${x}.callsPerLineKey="2"
      </#if>
      <#if phone.macAddress == "00:04:f2:1f:34:11">
      reg.${x}.lineKeys="2"
      reg.${x}.callsPerLineKey="2"
      </#if>
      <#if phone.macAddress == "00:04:f2:1f:34:c2">
       reg.${x}.lineKeys="2"
       reg.${x}.callsPerLineKey="2"
      </#if>
      <#if phone.product.code == "SPIP_670">
      reg.${x}.lineKeys="1"
      reg.${x}.callsPerLineKey="2"
      </#if>

      />
 <msg msg.bypassInstantMessage="1">
  <mwi
   msg.mwi.${x}.callBackMode="contact"
   msg.mwi.${x}.callBack="5050"
   />
 </msg>
 <#assign x=x+1/>
</#list>

 <!-- reset: $FMenu$$FDialpad3$$FDialpad1$$FDialpad5$ -->
 <softkey
   softkey.feature.newcall="1"
   softkey.feature.endcall="0"
   softkey.feature.split="0"
   softkey.feature.join="0"
   softkey.feature.forward="0"
   softkey.feature.directories="0"
   softkey.feature.callers="0"
   softkey.feature.mystatus="0"
   softkey.feature.buddies="0"
   softkey.feature.basicCallManagement.redundant="1"
   <#if phone.product.code == "SPIP_330">
   <#else>
     softkey.2.label="Chiamate"
   <#if phone.product.code == "SPIP_450">
   softkey.2.action="$FMenu$$FDialpad1$$FDialpad5$"
   <#else>
    softkey.2.action="$FMenu$$FDialpad1$$FDialpad4$"
   </#if>
   softkey.2.enable="1"
   softkey.2.precede=""
   softkey.2.use.idle="1"
   softkey.2.use.active="0"
   softkey.2.use.alerting="0"
   softkey.2.use.dialtone="0"
   softkey.2.use.proceeding="0"
   softkey.2.use.setup="0"
   softkey.2.use.hold="0"

   softkey.3.label="Rubrica"
   softkey.3.action="$FDirectories$$FDialpad2$"
   softkey.3.enable="1"
   softkey.3.precede=""
   softkey.3.use.idle="1"
   softkey.3.use.active="0"
   softkey.3.use.alerting="0"
   softkey.3.use.dialtone="0"
   softkey.3.use.proceeding="0"
   softkey.3.use.setup="0"
   softkey.3.use.hold="0"
   softkey.4.label="Contatti"
   softkey.4.action="$FDirectories$$FDialpad1$"
   softkey.4.enable="1"
   softkey.4.precede=""
   softkey.4.use.idle="1"
   softkey.4.use.active="0"
   softkey.4.use.alerting="0"
   softkey.4.use.dialtone="0"
   softkey.4.use.proceeding="0"
   softkey.4.use.setup="0"
   softkey.4.use.hold="0"
   </#if>
  />
</reginfo>

