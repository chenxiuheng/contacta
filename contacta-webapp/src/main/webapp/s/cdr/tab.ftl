[#ftl]<!-- $Revision: 673 $  :encoding=UTF-8:-->
[#assign pad = "contacta.cdr"/]
[#assign ui = "ui.cdr"/]
[#-- ====================================================================== --]
[#--                                                                        --]
[#-- ====================================================================== --]
<div jsId="${ui}.crudDialog" dojoType="dijit.Dialog" style="width:600px;">
 <form jsId="${ui}.crudForm" dojoType="dijit.form.Form">
  <div class="dijitDialogPaneContentArea">
   <div jsId="${ui}.crudMessage" dojoType="dijit.layout.ContentPane">
   </div>
   <input dojoType="dijit.form.TextBox" name="id" style="display:none;"/>
   <table>
    <tr><td>${m.t("label.code")}</td><td><input dojoType="dijit.form.TextBox" name="code"/></td></tr>
   </table>
  </div>
  [@oko6 pad/]
 </form>
</div>

[#-- ====================================================================== --]
[#--                                                                        --]
[#-- ====================================================================== --]
<div dojoType="dijit.layout.BorderContainer" gutters="false" liveSplitters="false" class="gridpad">
 <div dojoType="dijit.layout.ContentPane" region="top" class="toolbarContainer">
  <div dojoType="dijit.Toolbar" class="toolbarToolbar">
   <div dojoType="dijit.form.Button" iconClass="icoRefresh" showLabel="true" onclick="${pad}.refresh()"><span>${m.t("label.reload")}</span></div>
  </div>
 </div>

 <div dojoType="dijit.layout.ContentPane" region="center" class="gridPane">
  <table jsId="${ui}.grid" dojoType="dojox.grid.DataGrid" sortInfo="1">
  </table>
 </div>

 <div dojoType="dijit.layout.ContentPane" splitter="true" region="right" class="detailPane">
  <div jsId="${ui}.detailPane" dojoType="dijit.layout.ContentPane" preventCache="true" class="detailPaneInner"></div>
 </div>
</div>

