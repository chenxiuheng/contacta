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

  <div style="border: 1px solid silver; margin-top:6px; height:80px; padding:5px;">
   <div><b>${m.t("label.importAdd")}</b></div>
   <div>${m.t("short.importAdd")}</div>
   <div style="margin-top:10px;">
    <div dojoType="dijit.form.Button" showLabel="false" iconClass="ico46" style="float:left;"><span>${m.t("label.upload")}</span></div>
    <div style="margin-top:8px; float:left; width:350px;">
     <form id="uploadUpdateForm">
      <input dojoType="dojox.form.FileInputAuto" name="uploadUpdate" label="${m.t("label.import")}" uploadMessage="${m.t("label.wait")}" url="${base}/s/upload-csv.action?importType=update" onClick="uploadCleanInfo" onComplete="uploadCsvCallback"></input>
     </form>
    </div>
    <div id="uploadUpdateInfo" style="margin-top:10px; float:left;"></div>
   </div>
  </div>

  <div style="border: 1px solid silver; margin-top:6px; height:80px; padding:5px;">
   <div><b>${m.t("label.importAsNew")}</b></div>
   <div>${m.t("short.importAsNew")}</div>
   <div style="margin-top:10px;">
    <div dojoType="dijit.form.Button" showLabel="false" iconClass="ico46" style="float:left;"><span>${m.t("label.upload")}</span></div>
    <div style="margin-top:8px; float:left; width:350px;">
     <form id="uploadNewForm">
      <input dojoType="dojox.form.FileInputAuto" name="uploadNew" label="${m.t("label.import")}" uploadMessage="${m.t("label.wait")}" url="${base}/s/upload-csv.action?importType=new" onClick="uploadCleanInfo" onComplete="uploadCsvCallback"></input>
     </form>
    </div>
    <div id="uploadNewInfo" style="margin-top:10px; float:left;"></div>
   </div>
  </div>
 </div>
</div>

