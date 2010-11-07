<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<!-- $Id Revision: 1.3$ -->
<!-- :mode=freemarker:xml.validate=true: -->
<directory>
 <item_list>
 <#list phone.sipAccountList as sip>
  <#if sip.hasCoverage>
  <#assign i = 1/>
  <#list sip.coverageList as cvg>
   <item>
    <lb>${cvg.toSip.login}</lb>
    <fn><#if cvg.toSip.person??>${cvg.toSip.person.firstName!""}</#if></fn>
    <ln><#if cvg.toSip.person??>${cvg.toSip.person.lastName!""}</#if></ln>
    <ct>${cvg.toSip.login}</ct>
    <sd>${i}</sd>
    <rt>3</rt>
    <dc/>
    <ad>0</ad>
    <ar>0</ar>
    <bw>1</bw>
    <bb>0</bb>
   </item>
   <#if cvg.type != "Presence">
   <#assign i = i+1/>
   <item><#if false>TODO this will be to Sip</#if>
    <lb>P${cvg.fromSip.login}</lb>
    <fn><#if cvg.toSip.person??>${cvg.fromSip.person.firstName!""}</#if></fn>
    <ln><#if cvg.toSip.person??>${cvg.fromSip.person.lastName!""}</#if></ln>
    <ct>p${cvg.fromSip.login}</ct>
    <sd>${i}</sd>
    <rt>3</rt>
    <dc/>
    <ad>0</ad>
    <ar>0</ar>
    <bw>0</bw>
    <bb>0</bb>
   </item>
   </#if>
   <#assign i = i+1/>
  </#list>
  </#if>
 </#list>
 <#if phone.directory??>${phone.directory}</#if>

 <#if false><!--speedDialList
  <item>
   <lb>${speed.label}</lb>
   <ln>${speed.lastName}</ln>
   <fn>${speed.firstName}</fn>
   <ct>${speed.contact}</ct>
   <sd>${speed.speedDialIndex}</sd>
   <rt>${speed.ringType}</rt>
   <dc>${speed.divertContact?string("1", "0")}</dc>
   <ad>${speed.autoDivert?string("1", "0")}</ad>
   <ar>${speed.autoReject?string("1", "0")}</ar>
   <bw>${speed.buddyWatching?string("1", "0")}</bw>
   <bb>${speed.buddyBlock?string("1", "0")}</bb>
  </item>-->
 </#if>
 </item_list>
</directory>
