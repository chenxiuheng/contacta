<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<!-- $Id: local-settings.xml.ftl 681 2010-11-07 14:41:37Z michele.bianchi $ -->
<!-- :mode=xml:xml.validate=true: -->

<!-- Our local phone system common configuration in this file -->
<localcfg>
 <user_preferences up.oneTouchVoiceMail="1"/>
 <dnd divert.dnd.1.enabled="1" divert.dnd.1.contact="#99"/>

 <digitmap dialplan.digitmap="" dialplan.digitmap.timeOut=""/>
 <multilingual>
  <language lcl.ml.lang="${contacta.defaultLocale}">
   <clock lcl.ml.lang.clock.1.24HourClock="1" lcl.ml.lang.clock.1.format="D,dM"/>
   <menu lcl.ml.lang.menu.9="${contacta.defaultLocale}"/>
  </language>
 </multilingual>
 <datetime>
  <time lcl.datetime.time.24HourClock="1"/>
  <date lcl.datetime.date.format="D,Md" lcl.datetime.date.longFormat="1" lcl.datetime.date.dateTop="1"/>
 </datetime>

 <microbrowser mb.proxy="" mb.ssawc.call.mode="" mb.ssawc.enabled="">
  <main mb.main.home="http://${contacta.serverHost}:${contacta.serverPort?c}/o/ldap-form.action" mb.main.idleTimeout="" mb.main.statusbar=""/>
  <idleDisplay mb.idleDisplay.home="" mb.idleDisplay.refresh="0"/>
  <limits mb.limits.nodes="" mb.limits.cache=""/>
 </microbrowser>

  <feature
    feature.1.name="presence"
    feature.1.enabled="1"
    feature.2.name="messaging"
    feature.2.enabled="0"
    feature.3.name="directory"
    feature.3.enabled="1"
    feature.4.name="calllist"
    feature.4.enabled="1"
    feature.5.name="ring-download"
    feature.5.enabled="1"
    feature.6.name="calllist-received"
    feature.6.enabled="1"
    feature.7.name="calllist-placed"
    feature.7.enabled="1"
    feature.8.name="calllist-missed"
    feature.8.enabled="1"
    feature.9.name="url-dialing"
    feature.9.enabled="1"
    feature.10.name="call-park"
    feature.10.enabled="0"
    feature.11.name="group-call-pickup"
    feature.11.enabled="0"
    feature.12.name="directed-call-pickup"
    feature.12.enabled="0"
    feature.13.name="last-call-return"
    feature.13.enabled="0"
    feature.14.name="acd-login-logout"
    feature.14.enabled="0"
    feature.15.name="acd-agent-availability"
    feature.15.enabled="0"
    feature.16.name="nway-conference"
    feature.16.enabled="0"
    feature.17.name="call-recording"
    feature.17.enabled="0"
    feature.18.name="enhanced-feature-keys"
    feature.18.enabled="1"
    feature.19.name="corporate-directory"
    feature.19.enabled="1"
   />

 <logging>
  <level>
   <change log.level.change.ldap=""/>
  </level>
 </logging>

 <directory>
  <dirLocal
    dir.local.volatile.2meg="0"
    dir.local.nonVolatile.maxSize.2meg="20"
    dir.local.volatile.4meg="0"
    dir.local.nonVolatile.maxSize.4meg="50"
    dir.local.volatile.8meg="0"
    dir.local.nonVolatile.maxSize.8meg="100"
    dir.local.volatile.maxSize="200"
    dir.local.readonly="true"
    dir.search.field=""
   />
  <dirCorp>
   <cache dir.corp.pageSize="16" dir.corp.cacheSize="64"/>
   <addr dir.corp.address="ldap://10.0.0.2" dir.corp.port="389" dir.corp.transport="tcp"/>
   <baseDN dir.corp.baseDN="ou=people,dc=organic,dc=lab" dir.corp.filterPrefix="(objectclass=person)" dir.corp.scope="sub"/>
   <login dir.corp.user="XXX" dir.corp.password="XXX"/>
   <sync dir.corp.backGroundSync="0" dir.corp.backGroundSync.period="300"/>
   <view dir.corp.viewPersistence="0" dir.corp.leg.viewPersistence="1"/>
   <attr1 dir.corp.attribute.1.name="sn" dir.corp.attribute.1.label="Cognome" dir.corp.attribute.1.type="last_name" dir.corp.attribute.1.filter="" dir.corp.attribute.1.sticky="0"/>
   <attr2 dir.corp.attribute.2.name="givenName" dir.corp.attribute.2.label="Nome" dir.corp.attribute.2.type="first_name" dir.corp.attribute.2.filter="" dir.corp.attribute.2.sticky="0"/>
   <attr3 dir.corp.attribute.3.name="telephoneNumber" dir.corp.attribute.3.label="Telefono" dir.corp.attribute.3.type="phone_number" dir.corp.attribute.3.filter="" dir.corp.attribute.3.sticky="0"/>
  </dirCorp>
 </directory>

</localcfg>

