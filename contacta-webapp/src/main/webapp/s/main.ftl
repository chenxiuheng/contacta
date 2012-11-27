[#ftl/][#-- :encoding=UTF-8: --]
[#setting number_format="0.####"/]

[#assign canDoAdmin = authz.canDo("OP_ADMIN")/]

[#assign gpad = "contacta"/]
[#assign gui = "ui.global"/]

[#include "../WEB-INF/organic/macro.ftl"/]
<html>
<head>
<title>Main</title>
<style>
@import url('${base}/skin/contacta.css');
</style>
<script type="text/javascript">
</script>
<script type="text/javascript">
var organic = null;
var contacta = null;
//var ui = null;

require(['dojo/parser', 'dojo/ready', 'dojo/_base/unload', 'dijit/registry', 'openinnovation/organic/Organic', 'dojo/i18n!contacta/nls/messages' ],
function(parser, ready, unload, dijit, Organic, i18n)
{
  organic = new Organic({
    appName:'contacta',
    baseUrl:'${base!""}',
    i18n:i18n,
    [#if organicSession??]
    jsessionid:'${organicSession.sessionId!""}',
    account:{id:'${organicSession.account.id}',login:'${organicSession.account.code}',displayName:'${organicSession.account.label}'},
    [#else]
    jsessionid:null,
    account:null,
    [/#if]
    authz:{ replanPartial:true, replanFull:true },
    aaa:null
  });
  try
  {
    //var r = parser.parse();
    //console.warn('parse result', r);
  }
  catch(e)
  {
   console.warn('cannot parse', e);
  }

  ready(function()
  {
    try
    {
      require(['openinnovation/organic/Aaa', 'openinnovation/contacta/Contacta'], function(Aaa, Contacta)
      {
        organic.aaa = new Aaa();
        contacta = new Contacta({
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
    }
    catch(e)
    {
     console.warn('cannot require', e);
    }
  });

  unload.addOnUnload(function()
  {
    console.log('You are leaving the page');
    //dojo.cookie('lpPrefs', dojo.toJson(contacta.prefs), { expires:14 });
    console.log('prefs saved');
  });
});


function downloadCsv()
{
  window.location.replace('/d/download-csv.action');
}


function uploadCleanInfo() /*dojox.FileInput*/
{
  dojo.byId("uploadNewInfo").innerHTML = "";
  dojo.byId("uploadUpdateInfo").innerHTML = "";
}


function watchCheckbox(checkbox, descriptionId, vtb)
{
  var description = dojo.byId(descriptionId);

  if(!checkbox.getValue())
  {
    vtb._setDisabledAttr(true);
    dojo.style(description, "color", "grey");
  }
  else
  {
    vtb._setDisabledAttr(false);
    dojo.style(description, "color", "black");
  }

}


</script>
</head>
<body>

[#-- ====================================================================== --]
[#--   THE PAGE                                                             --]
[#-- ====================================================================== --]
[#-- ui.thePage --]
<div id="thePage" data-dojo-id="ui.thePage" data-dojo-type="dijit.layout.BorderContainer" data-dojo-props="gutters:false, style:{minWidth:'900px', fontFamily:'Verdana,Arial,Helvetica,sansSerif'}">
 [#-- menubar
 <div jsId="ui.menubar" dojoType="dijit.layout.ContentPane" style="height:31px; border-bottom:1px solid #4E9595; border-right:1px solid #4E9595;" region="top">
  <div style="padding:2px 10px 1px 10px;" class="organicBg">
   <div style="float:right; padding:2px 4px;"><div class="ico25" title="Logout" onclick="organic.util.logout()"></div></div>
   <div style="float:left; margin-right:80px; width:152px; height:25px; background:transparent url('${base}/skin/static/logo.png') no-repeat left bottom;"></div>
   <div style="white-space:nowrap; color:navy; font-weight:bold;">
    [#if organicSession.admin]
    <div dojoType="dijit.form.Button" showLabel="true" onclick="ui.mainStackPane.selectChild(ui.contactaTabContainer)"><span>${m.t("label.user")}</span></div>
    <div dojoType="dijit.form.Button" showLabel="true" onclick="ui.mainStackPane.selectChild(ui.adminTabContainer)"><span>${m.t("label.admin")}</span></div>
    [#else]
    <div style="height:28px; color:yellow; vertical-align:bottom;"><p>Welcome ${organicSession.account.label}<p></div>
    [/#if]
   </div>
  </div>
 </div> --]

 [#-- menubar --]
 <div jsId="ui.menubar" dojoType="dijit.layout.ContentPane" region="top">
  <div dojoType="dijit.Toolbar" class="toolbarToolbar" style="background:#EAF5FD; border-top:1px solid #769DC0; border-bottom:1px solid #769DC0;">
   [#if organicSession.admin]
   <button dojoType="dijit.form.DropDownButton" iconClass="ico31" label="<b>${organicSession.account.label} (${organicSession.account.code})</b>">
    <div dojoType="dijit.Menu">
     <div dojoType="dijit.MenuItem" showLabel="true" onclick="ui.mainStackPane.selectChild(ui.contactaTabContainer)"><span>${m.t("label.user")}</span></div>
     <div dojoType="dijit.MenuItem" showLabel="true" onclick="ui.mainStackPane.selectChild(ui.adminTabContainer)"><span>${m.t("label.admin")}</span></div>
    </div>
   </button>
   [#else]
   <div style="" class="dijit dijitReset dijitLeft dijitInline dijitButton menubarLabel">
    <span class="ico31" style="padding-left:28px;"><b>${organicSession.account.label} (${organicSession.account.code})</b></span>
    [#--<span>
     </span>${m.t("label.site")}: <b>[#list organicSession.scopeRoleList("site") as s]${s.code}[#if s_has_next], [/#if][/#list]</b>,
     </span>${m.t("label.function")}: <b>[#list organicSession.scopeRoleList("function") as s]${s.code}[#if s_has_next], [/#if][/#list]</b>
    </span>
<div data-dojo-type="openinnovation.ToolbarSeparator" data-dojo-props='label:"${m.t("label.plan")}"'></div>
       --]
   </div>
   [/#if]
    <div dojoType="dijit.form.Button" iconClass="icoMissedCalls" onclick="contacta.missedCalls()"><span>${m.t("label.missedCalls")}</span></div>
   [#if organicSession.account??]
    <input jsId="ui.digitInput" dojoType="dijit.form.TextBox" value="" placeholder="555012345" style="width:10em;height:20px;"/>
    <div dojoType="dijit.form.Button" iconClass="ico30" onclick="contacta.dial()"><span>${m.t("label.dial")}</span></div>
    <div dojoType="dijit.form.Button" iconClass="ico33" onclick="contacta.transfer()"><span>${m.t("label.transfer")}</span></div>
    <div dojoType="dijit.form.Button" iconClass="ico32" onclick="contacta.hold()"><span>${m.t("label.hold")}</span></div>
    <div dojoType="dijit.form.Button" iconClass="ico32" onclick="contacta.unhold()"><span>${m.t("label.unhold")}</span></div>
    <div dojoType="dijit.form.Button" iconClass="ico30" onclick="contacta.answer()"><span>${m.t("label.answer")}</span></div>
   [#else]
    <b><i>${m.t("short.phonebar.unavailable")}</i></b>
   [/#if]
   <span data-dojo-type="dijit.ToolbarSeparator"></span>
   <button dojoType="dijit.form.Button" iconClass="icoKeys" title="Change password" onclick="location='${base}/o/password.action';"></button>
   <button dojoType="dijit.form.Button" iconClass="ico25" title="Logout" onclick="organic.util.logout();return false;"></button>
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

  [#-- =================== admin =================== --]
  [#--if authz.canDo("ADMIN")--]
  <div dojoType="dijit.layout.TabContainer" title="${m.t("label.administrator")}" nested="true">
    <div dojoType="dijit.layout.ContentPane" title="Person">
     [#include "../s/person/tab.ftl"/]
    </div>

   <div dojoType="dijit.layout.ContentPane" title="${m.t("label.organization")}">
     [#include "../s/organization/tab.ftl"/]
    </div>

   <div dojoType="dijit.layout.ContentPane" title="${m.t("label.account")}" selected="true">
     [#include "../s/account/tab.ftl"/]
    </div>

   <div dojoType="dijit.layout.ContentPane" title="${m.t("label.group")}">
     [#include "../s/group/tab.ftl"/]
    </div>

   <div dojoType="dijit.layout.ContentPane" title="${m.t("label.role")}">
     [#include "../s/role/tab.ftl"/]
    </div>

   <div dojoType="dijit.layout.ContentPane" title="${m.t("label.policy")}">
     [#include "../s/policy/tab.ftl"/]
    </div>

   <div dojoType="dijit.layout.ContentPane" title="${m.t("label.operation")}">
     [#include "../s/operation/tab.ftl"/]
    </div>
   </div>
  [#--/#if--]

  </div>
  [/#if]

  [#-- =================== user =================== --]
  <div jsId="ui.contactaTabContainer" dojoType="dijit.layout.TabContainer">
   <div dojoType="dijit.layout.ContentPane" title="${m.t("label.phonebar")}">
    [#include "./phonebook/tab.ftl"/]
   </div>

   <div dojoType="dijit.layout.ContentPane" title="${m.t("label.calls")}" jsId="ui.contacta.callsTab">
    [#include "./calls/tab.ftl"/]
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

