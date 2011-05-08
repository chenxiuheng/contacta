[#ftl]<!-- $Revision: 673 $  :encoding=UTF-8:-->
[#assign pad = "contacta.pbxprofile"/]
[#assign ui = "ui.pbxprofile"/]
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
    <tr><td>${m.t("label.label")}</td><td><input dojoType="dijit.form.TextBox" name="label"/></td></tr>
    <tr><td>${m.t("label.command")}</td><td><input dojoType="dijit.form.TextBox" name="command"/></td></tr>
    <tr><td>${m.t("label.macro")}</td><td></td></tr>
   </table>
   <div style="width:600px; height:300px;"> [#--style:{width:'90%', height:'90%'} dojoType="dijit.form.Textarea" jsId="${ui}.macroEditor" name="macro" cols="120" rows="20" maxLength:'50',  --]
    <textarea data-dojo-type="dijit.form.Textarea" data-dojo-props="id:'${ui}.macroEditor', name:'macro', style:{width:'550px', height:'100px'}"></textarea>
   </div>
   [@oko6 pad/]
  </form>
 </div>
</div>

[#-- ====================================================================== --]
[#--                                                                        --]
[#-- ====================================================================== --]
<div dojoType="dijit.layout.BorderContainer" gutters="false" liveSplitters="false" class="gridpad">
 <div dojoType="dijit.layout.ContentPane" region="top" class="toolbarContainer">
  <div dojoType="dijit.Toolbar" class="toolbarToolbar">
   [@organicPadButtons pad ui/]
  </div>
 </div>

 <div dojoType="dijit.layout.ContentPane" region="center" class="gridPane">
  <table jsId="${ui}.grid" dojoType="dojox.grid.DataGrid">
  </table>
 </div>

 <div dojoType="dijit.layout.ContentPane" splitter="true" region="right" class="detailPane">
  <div jsId="${ui}.detailPane" dojoType="dijit.layout.ContentPane" preventCache="true" class="detailPaneInner"></div>
 </div>
</div>

