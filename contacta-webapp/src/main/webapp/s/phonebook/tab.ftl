[#ftl]<!-- $Revision: 673 $  :encoding=UTF-8:-->
[#assign pad = "contacta.phonebook"/]
[#assign ui = "ui.phonebook"/]
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
    <tr><td>${m.t("label.firstName")}</td><td><input jsId="${ui}.firstName" dojoType="dijit.form.ValidationTextBox" name="firstName" required="true"/></td></tr>
    <tr><td>${m.t("label.lastName")}</td><td><input jsId="${ui}.lastName" dojoType="dijit.form.ValidationTextBox" name="lastName" required="true"/></td></tr>
    <tr><td>${m.t("label.displayName")}</td><td><input jsId="${ui}.displayName" dojoType="dijit.form.ValidationTextBox" name="displayName" required="true"/><div dojoType="dijit.form.Button" onclick="${ui}.displayName.set('value', ${ui}.firstName.get('value')+' '+${ui}.lastName.get('value'));return false;"><span>Auto</span></div></td></tr>
    <tr><td>${m.t("label.email")}</td><td><input dojoType="dijit.form.ValidationTextBox" name="email"/></td></tr>
    <tr><td>${m.t("label.phone")}</td><td><input dojoType="dijit.form.TextBox" name="phone"/></td></tr>
    <tr><td>${m.t("label.extension")}</td><td><input dojoType="dijit.form.TextBox" name="extension"/></td></tr>
    <tr><td>Titolo</td><td><input dojoType="dijit.form.TextBox" name="title"/></td></tr>
    <tr><td>${m.t("label.address")}</td><td><input dojoType="dijit.form.TextBox" name="address"/></td></tr>
    <tr><td>${m.t("label.city")}</td><td><input dojoType="dijit.form.TextBox" name="city"/></td></tr>
    <tr><td>${m.t("label.zip")}</td><td><input dojoType="dijit.form.TextBox" name="zip"/></td></tr>
    <tr><td>${m.t("label.country")}</td><td><input dojoType="dijit.form.TextBox" name="country"/></td></tr>
    <tr><td>Codice fiscale</td><td><input dojoType="dijit.form.TextBox" name="code"/></td></tr>
    <tr><td>${m.t("label.bornDate")}</td><td><input dojoType="dijit.form.DateTextBox" name="bornDate" constraints="{formatLength:'short'}"/></td></tr>
    <tr><td>${m.t("label.bornIn")}</td><td><input dojoType="dijit.form.TextBox" name="bornIn"/></td></tr>
    <tr><td>${m.t("label.uri")}</td><td><input dojoType="dijit.form.TextBox" name="uri"/></td></tr>
    <tr><td>${m.t("label.status")}</td><td><input dojoType="dijit.form.TextBox" name="status" value="Online" readOnly="true"/></td></tr>
    <tr><td>Note</td><td><textarea dojoType="dijit.form.Textarea" name="info"></textarea></td></tr>
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
  [#--
  <div dojoType="dijit.Toolbar" class="toolbarToolbar">
  </div>
  --]
  <div dojoType="dijit.Toolbar" class="toolbarToolbar">
   [#assign hide = ["filter"] /]
   [@organicPadButtons2 pad ui hide /]
   <span class="dijit dijitReset dijitLeft dijitInline dijitButton" style="margin-top:0px; font-size:11px;">
    <label for="${ui}.quickSelect" style="font-size:9px;">${m.t("label.quickSearch")}</label><br/>
    <select jsId="${ui}.quickSelect" dojoType="dijit.form.ComboBox" style="width:80px;">
      <option value="displayName" selected="true">${m.t("label.displayName")}</option>
      <option value="lastName">${m.t("label.lastName")}</option>
      <option value="firstName">${m.t("label.firstName")}</option>
      <option value="login">${m.t("label.login")}</option>
      <option value="phone">${m.t("label.phone")}</option>
    </select>
    <input jsId="${ui}.quickFilter" dojoType="dijit.form.TextBox" trim="true" intermediateChanges="true" style="width:100px;"/>
   </span>
  </div>
 </div>

 <div dojoType="dijit.layout.ContentPane" region="center" class="gridPane">
  <table jsId="${ui}.grid" dojoType="dojox.grid.DataGrid" sortInfo="1">
  </table>
 </div>

 <div dojoType="dijit.layout.ContentPane" splitter="true" region="right" class="detailPane">
  <div jsId="${ui}.detailPane" dojoType="dijit.layout.ContentPane"></div>
 </div>
</div>

