<!-- $Id: phonebar.ftl 579 2009-08-09 13:24:50Z michele.bianchi $ -->
<div dojoType="dijit.layout.BorderContainer" gutters="false">
 <div dojoType="dijit.layout.ContentPane" region="center">
  <div style="border: 1px solid silver; margin-top:6px; padding:5px;">
   <#if organicSession.account??>
   <div style="font-size:16px;">${m.t("short.phonebar", [organicSession.account.login, organicSession.account.callerId])}</div>
   </#if>
   <div>
    <div dojoType="dijit.form.Button" onclick="ui.channelPane.set('href', '${base}/o/channels.action');"><span>${m.t("label.reloadChannels")}</span></div>
   </div>
   <div jsId="ui.channelPane" dojoType="dijit.layout.ContentPane" style="margin:1em;" href="${base}/o/channels.action"></div>
  </div>
 </div>
</div>

