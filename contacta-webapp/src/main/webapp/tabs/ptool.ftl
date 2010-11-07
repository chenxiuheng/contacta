[#ftl][#-- $Id$ --]
<div dojoType="dijit.layout.BorderContainer" gutters="false">
 [#--
 <div dojoType="dijit.layout.ContentPane" region="top" class="organicToolbar" style="border: 1px solid silver; padding:16px; height:140px;">
  <div dojoType="dijit.form.Button" iconClass="ico10" showLabel="false" onclick="contacta.phone.show(true)"><span>${m.t("label.phone.create")}</span></div>
  <div dojoType="dijit.form.Button" iconClass="ico13" showLabel="false" onclick="contacta.phone.wizardAssignPhone()"><span>${m.t("label.phone.assign")}</span></div>
  <div dojoType="dijit.form.Button" iconClass="ico12" showLabel="false" onclick="contacta.phone.wizardPhonePersonShow()"><span>${m.t("label.phone.createWithSip")}</span></div>
 </div>
 --]
 <div dojoType="dijit.layout.ContentPane" region="center">
  <div style="border: 1px solid silver; margin-top:6px; padding:5px;">
   <div><b>${m.t("label.pbx")}</b></div>
   <div>
    <div dojoType="dijit.form.Button" onclick="contacta.checkAsterisk()"><span>${m.t("label.pbx.check")}</span></div>
    <div dojoType="dijit.form.Button" onclick="contacta.restartAsterisk()"><span>${m.t("label.pbx.reload")}</span></div>
    <div dojoType="dijit.form.Button" onclick="contacta.updateExtensionProfile()"><span>${m.t("label.pbx.updateExtensionProfile")}</span></div>
   </div>
   <div jsId="ui.asteriskStatus" dojoType="dijit.layout.ContentPane" style="margin:1em;"></div>
  </div>

  <div style="border: 1px solid silver; margin-top:6px; padding:5px;">
   <ul>[#list configurationAsList?keys as key]<li>[]${key}=$ {configurationAsList[key]!"null"}</li>[/#list]</ul>
  </div>
 </div>
</div>

