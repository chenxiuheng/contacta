[#ftl]<!-- $Revision: 673 $  :encoding=UTF-8:-->
[#assign pad = "contacta.coverage"/]
[#assign ui = "ui.coverage"/]
<div class="organicTabContent" dojoType="dijit.layout.BorderContainer" gutters="false">
 <div dojoType="dijit.layout.ContentPane" region="top" class="organicToolbar">
  <div dojoType="dijit.form.Button" iconClass="ico53" showLabel="true" onclick="contacta.coverage.load()"><span>${m.t("label.reload")}</span></div>
 </div>
 <div dojoType="dijit.layout.ContentPane" region="center">
  <table jsId="ui.coverageGrid" dojoType="dojox.grid.DataGrid" sortInfo="1" noDataMessage="${m.t("label.detailPaneEmpty")}" rowsPerPage="20">
  </table>
 </div>
 <div dojoType="dijit.layout.ContentPane" region="right" style="background-color: #eeeeee; width:280px; margin:4px; border:1px dotted black;">
  <div jsId="ui.coverageDetail" dojoType="dijit.layout.ContentPane">
   ${m.t("label.coverage.detail")}
  </div>
 </div>
</div>

