[#ftl]<!-- $Revision: 673 $  :encoding=UTF-8:-->
[#assign pad = "contacta.pbxcontext"/]
[#assign ui = "ui.pbxcontext"/]
[#-- ====================================================================== --]
[#--                                                                        --]
[#-- ====================================================================== --]
<div jsId="${ui}.crudDialog" dojoType="dijit.Dialog" style="width:400px;">
 <form jsId="${ui}.crudForm" dojoType="dijit.form.Form">
  <div class="dijitDialogPaneContentArea">
   <div jsId="${ui}.crudMessage" dojoType="dijit.layout.ContentPane">
   </div>
   <input dojoType="dijit.form.TextBox" name="id" style="display:none;"/>
   <table>
    <tr><td>${m.t("label.pbxcontext.code")}</td><td><input dojoType="dijit.form.TextBox" name="code"/></td></tr>
    <tr><td>${m.t("label.pbxcontext.label")}</td><td><input dojoType="dijit.form.TextBox" name="label"/></td></tr>
   </table>
  </div>
  [@oko5 pad/]
 </form>
</div>

[#-- ====================================================================== --]
[#--                                                                        --]
[#-- ====================================================================== --]
<div dojoType="dijit.layout.BorderContainer" gutters="false" liveSplitters="false" class="gridpad">
 <div dojoType="dijit.layout.ContentPane" region="top" class="toolbarContainer">
  <div dojoType="dijit.Toolbar" class="toolbarToolbar">
   <div dojoType="dijit.form.Button" iconClass="icoNew" showLabel="false" onclick="${pad}.showCreate()"><span>${m.t("label.pbxcontext.create")}</span></div>
   <div dojoType="dijit.form.Button" iconClass="icoRemove" showLabel="false" onclick="${pad}.showDeleteGridSelected()"><span>${m.t("label.pbxcontext.delete")}</span></div>
   <div dojoType="dijit.form.Button" iconClass="icoEdit" showLabel="false" onclick="${pad}.showUpdateGridSelected()"><span>${m.t("label.pbxcontext.edit")}</span></div>
   <div dojoType="dijit.form.Button" iconClass="icoRefresh" showLabel="false" onclick="${pad}.refresh()"><span>${m.t("label.reload")}</span></div>
  </div>
 </div>

 <div dojoType="dijit.layout.ContentPane" region="center" class="gridPane">
  <table jsId="${ui}.grid" dojoType="dojox.grid.DataGrid" selectable="true">
  </table>
 </div>

 [#--
 <div dojoType="dijit.layout.ContentPane" splitter="true" region="right" class="detailPane">
   <div jsId="${ui}.detailPane" dojoType="dijit.layout.ContentPane" preventCache="true"></div>
 </div>
 --]
</div>

