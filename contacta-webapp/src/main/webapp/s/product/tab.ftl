[#ftl]<!-- $Revision: 673 $  :encoding=UTF-8:-->
[#assign pad = "contacta.product"/]
[#assign ui = "ui.product"/]
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
    <tr><td>${m.t("label.product.code")}</td><td><input dojoType="dijit.form.TextBox" name="code"/></td></tr>
    <tr><td>${m.t("label.product.vendor")}</td><td><input dojoType="dijit.form.TextBox" name="vendor"/></td></tr>
    <tr><td>${m.t("label.product.model")}</td><td><input dojoType="dijit.form.TextBox" name="model"/></td></tr>
    <tr><td>${m.t("label.product.version")}</td><td><input dojoType="dijit.form.TextBox" name="version"/></td></tr>
    <tr><td>${m.t("label.product.userAgent")}</td><td><input dojoType="dijit.form.TextBox" name="userAgent"/></td></tr>
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
   <div dojoType="dijit.form.Button" iconClass="icoNew" showLabel="false" onclick="${pad}.showCreate()"><span>${m.t("label.product.create")}</span></div>
   <div dojoType="dijit.form.Button" iconClass="icoRemove" showLabel="false" onclick="${pad}.showDeleteGridSelected()"><span>${m.t("label.product.delete")}</span></div>
   <div dojoType="dijit.form.Button" iconClass="icoEdit" showLabel="false" onclick="${pad}.showUpdateGridSelected()"><span>${m.t("label.product.edit")}</span></div>
   <div dojoType="dijit.form.Button" iconClass="icoRefresh" showLabel="false" onclick="${pad}.refresh()"><span>${m.t("label.reload")}</span></div>
  </div>
 </div>

 <div dojoType="dijit.layout.ContentPane" region="center" class="gridPane">
  <table jsId="${ui}.grid" dojoType="dojox.grid.DataGrid" selectable="true">
  </table>
 </div>

 <div dojoType="dijit.layout.ContentPane" splitter="true" region="right" class="detailPane">
  <div jsId="${ui}.detailPane" dojoType="dijit.layout.ContentPane"></div>
 </div>
</div>

