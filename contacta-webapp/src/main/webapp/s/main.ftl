[#ftl]<!-- $Revision: 671 $  :encoding=UTF-8:-->
[#include "../WEB-INF/organic/macro.ftl"/]
<html>
<head>
<title>Main</title>
<style>
</style>
<script type="text/javascript">
</script>
[#if organicConfiguration.firebugLite]
<script type="text/javascript" src="http://getfirebug.com/releases/lite/1.2/firebug-lite-compressed.js"></script>
[/#if]
<script type="text/javascript" src="${base}/r/openinnovation/organic/Organic.js"></script>
<script type="text/javascript" src="${base}/r/openinnovation/organic/gridpad/Controller.js"></script>
<script type="text/javascript" src="${base}/js/openinnovation/organic/Aaa.js"></script>
<script type="text/javascript" src="${base}/js/openinnovation/organic/Mc.js"></script>
<script type="text/javascript" src="${base}/js/openinnovation/contacta/Phonebook.js"></script>
<script type="text/javascript" src="${base}/js/openinnovation/contacta/PbxContext.js"></script>
<script type="text/javascript" src="${base}/js/openinnovation/contacta/Phone.js"></script>
<script type="text/javascript" src="${base}/js/openinnovation/contacta/Coc.js"></script>
<script type="text/javascript" src="${base}/js/openinnovation/contacta/Sip.js"></script>
<script type="text/javascript" src="${base}/js/openinnovation/contacta/Coverage.js"></script>
<script type="text/javascript" src="${base}/js/openinnovation/contacta/Contacta.js"></script>

<script type="text/javascript">

var organic =
{
  util:new openinnovation.organic.Organic({ baseUrl:'${base!""}' }),
  appName:'contacta',
  baseUrl:'${base!""}',
  aaa:null
};
var contacta = null;

dojo.addOnLoad(function()
{
 contacta = new openinnovation.contacta.Contacta({
     develMode:${organicConfiguration.develMode?string},
     session:
     {
       admin:${organicSession.admin?string},
       user:${organicSession.user?string},
       guest:${organicSession.guest?string}
     }
   });
  contacta.init();
});
</script>
</head>
<body>

[#-- ====================================================================== --]
[#--   THE PAGE                                                             --]
[#-- ====================================================================== --]
<div id="thePage" jsId="ui.thePage" dojoType="dijit.layout.BorderContainer" gutters="false" style="width:100%; height:100%; display:none;">
 [#-- menubar --]
 <div jsId="ui.menubar" dojoType="dijit.layout.ContentPane" style="height:31px; border-bottom:1px solid #4E9595; border-right:1px solid #4E9595;" region="top">
  <div style="padding:2px 10px 1px 10px;" class="organicBg">
   <div style="float:right; padding:2px 4px;"><div class="ico25" title="Logout" onclick="organic.util.logout()"></div></div>
   [#--<div style="float:right; padding-top:13px; padding-bottom:0px; padding-right:20px; color:white; font-size:9px;">${m.t("label.version")}</div>--]
   <div style="float:left; margin-right:80px; width:152px; height:25px; background:transparent url('${base}/r/static/contacta.png') no-repeat left bottom;"></div>
   <div style="white-space:nowrap; color:navy; font-weight:bold;">
    [#if organicSession.admin]
    <div dojoType="dijit.form.Button" showLabel="true" onclick="ui.mainStackPane.selectChild(ui.contactaTabContainer)"><span>${m.t("label.user")}</span></div>
    <div dojoType="dijit.form.Button" showLabel="true" onclick="ui.mainStackPane.selectChild(ui.adminTabContainer)"><span>${m.t("label.admin")}</span></div>
    [#else]
    <div style="height:28px; color:yellow; vertical-align:bottom;"><p>Welcome ${organicSession.account.label}<p></div>
    [/#if]
   </div>
  </div>
 </div>

 [#-- statusbar --]
 <div jsId="ui.statusbar" dojoType="dijit.layout.ContentPane" style="height:16px;" region="bottom">
  [@organicStatusbar/]
 </div>

 [#-- center region: stack'n'tabs --]
 <div jsId="ui.mainStackPane" dojoType="dijit.layout.StackContainer" region="center">
 [#-- =================== admin =================== --]
  [#if organicSession.admin]

  <div jsId="ui.adminTabContainer" dojoType="dijit.layout.TabContainer">
   [#-- telephony --]
   <div dojoType="dijit.layout.TabContainer" title="Telephony" nested="true">
    <div dojoType="dijit.layout.ContentPane" title="${m.t("label.phone")}">
     [#include "./phone/tab.ftl"/]
    </div>

    <div dojoType="dijit.layout.ContentPane" title="${m.t("label.sip")}" selected="true">
     [#include "./sip/tab.ftl"/]
    </div>

    <div dojoType="dijit.layout.ContentPane" title="${m.t("label.coverage")}">
     [#include "./coverage/tab.ftl"/]
    </div>

    <div dojoType="dijit.layout.ContentPane" title="ChangeOfContext">
     [#include "./coc/tab.ftl"/]
    </div>
   </div>

   [#-- pbx --]
   <div dojoType="dijit.layout.TabContainer" title="PBX" nested="true">
    <div jsId="ui.ptool.consolePane" dojoType="dijit.layout.ContentPane" title="${m.t("label.menu")}"
         refreshOnShow="false" preventCache="true" preload="true"
         >
     [#include "./ptool/ptool.ftl"/]
    </div>

    <div dojoType="dijit.layout.ContentPane" title="${m.t("label.cdr")}">
     [#include "./cdr/tab.ftl"/]
    </div>

    <div dojoType="dijit.layout.ContentPane" title="Context">
     [#include "./pbxcontext/tab.ftl"/]
    </div>

    <div dojoType="dijit.layout.ContentPane" title="Profile">
     [#include "./pbxprofile/tab.ftl"/]
    </div>

    <div dojoType="dijit.layout.ContentPane" title="Product">
     [#include "./product/tab.ftl"/]
    </div>
   </div>

   [#-- advanced --]
   <div dojoType="dijit.layout.TabContainer" title="Advanced" nested="true">
    <div dojoType="dijit.layout.ContentPane" title="${m.t("label.advanced")}">
     [#include "./advanced/advanced.ftl"/]
    </div>

    <div dojoType="dijit.layout.ContentPane" title="${m.t("label.audit")}">
     ${m.t("short.disabled")}[#--include "./XXX/tab.ftl"/--]
    </div>

    <div dojoType="dijit.layout.ContentPane" title="Webservices">
     ${m.t("short.disabled")}[#--include "./XXX/tab.ftl"/--]
    </div>
   </div>

   [#-- administration --]
   [#if organicConfiguration.develMode]
   [/#if]
   <div dojoType="dijit.layout.TabContainer" title="Administrator" nested="true">
    <div dojoType="dijit.layout.ContentPane" title="Person">
     [#include "../s/person/tab.ftl"/]
    </div>

    <div dojoType="dijit.layout.ContentPane" title="Organization">
     [#include "../s/organization/tab.ftl"/]
    </div>

    <div dojoType="dijit.layout.ContentPane" title="Account" selected="true">
     [#include "../s/account/tab.ftl"/]
    </div>

    <div dojoType="dijit.layout.ContentPane" title="Group">
     [#include "../s/group/tab.ftl"/]
    </div>

    <div dojoType="dijit.layout.ContentPane" title="Role">
     [#include "../s/role/tab.ftl"/]
    </div>

    <div dojoType="dijit.layout.ContentPane" title="Policy">
     [#include "../s/policy/tab.ftl"/]
    </div>

    <div dojoType="dijit.layout.ContentPane" title="Operation">
     [#include "../s/operation/tab.ftl"/]
    </div>
   </div>

  </div>
  [/#if]

  [#-- =================== user =================== --]
  <div jsId="ui.contactaTabContainer" dojoType="dijit.layout.TabContainer">
   <div dojoType="dijit.layout.ContentPane" title="${m.t("label.phonebar")}">
    [#include "./phonebook/tab.ftl"/]
   </div>

   [#if organicConfiguration.develMode]
   <div dojoType="dijit.layout.ContentPane" title="${m.t("label.calendar")}">
    [#include "./calendar/calendar.ftl"/]
   </div>

   <div dojoType="dijit.layout.ContentPane" title="${m.t("label.mybooking")}">
    [#include "./conference/conference.ftl"/]
   </div>

   <div dojoType="dijit.layout.ContentPane" title="${m.t("label.channels")}">
    [#include "./channels/channels.ftl"/]
   </div>

   <div dojoType="dijit.layout.ContentPane" title="${m.t("label.selfcare")}"
        refreshOnShow="false" preventCache="true" preload="true"
        >
    ${m.t("short.disabled")}[#--include "./XXX/tab.ftl"/--]
   </div>
   [/#if]

  </div>
 </div>
</div>

</body>
</html>

