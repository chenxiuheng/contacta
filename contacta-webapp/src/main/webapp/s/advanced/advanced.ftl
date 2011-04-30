<#-- $Id: advanced.ftl 609 2010-03-14 20:23:16Z michele.bianchi $ -->
<div dojoType="dijit.layout.BorderContainer" gutters="false">
 <div dojoType="dijit.layout.ContentPane" region="center">
  <div style="border: 1px solid silver; margin-top:6px; height:80px; padding:5px;">
   <div><b>${m.t("label.exportCsv")}</b></div>
   <div>${m.t("short.exportCsv")}</div>
   <div style="margin-top:10px;">
    <div dojoType="dijit.form.Button" onclick="downloadCsv()"><span>${m.t("label.export")}</span></div>
   </div>
  </div>
 </div>
</div>

