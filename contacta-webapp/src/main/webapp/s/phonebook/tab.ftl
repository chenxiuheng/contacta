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
    <tr><td>Nome</td><td><input jsId="${ui}.firstNameInput" dojoType="dijit.form.ValidationTextBox" name="firstName" required="true"/></td></tr>
    <tr><td>Cognome</td><td><input jsId="${ui}.lastNameInput" dojoType="dijit.form.ValidationTextBox" name="lastName" required="true"/></td></tr>
    <tr><td>Nome Completo</td><td><input jsId="${ui}.displayNameInput" dojoType="dijit.form.ValidationTextBox" name="displayName" required="true"/><div dojoType="dijit.form.Button" onclick="${ui}.displayNameInput.attr('value', ${ui}.firstNameInput.attr('value')+' '+${ui}.lastNameInput.attr('value'));"><span>Auto</span></div></td></tr>
    <tr><td>Email</td><td><input dojoType="dijit.form.ValidationTextBox" name="email"/></td></tr>
    <tr><td>Telefono</td><td><input dojoType="dijit.form.TextBox" name="phone"/></td></tr>
    <tr><td>Estensione</td><td><input dojoType="dijit.form.TextBox" name="extension"/></td></tr>
    <tr><td>Titolo</td><td><input dojoType="dijit.form.TextBox" name="title"/></td></tr>
    <tr><td>Indirizzo</td><td><input dojoType="dijit.form.TextBox" name="address"/></td></tr>
    <tr><td>Citta</td><td><input dojoType="dijit.form.TextBox" name="city"/></td></tr>
    <tr><td>CAP</td><td><input dojoType="dijit.form.TextBox" name="zip"/></td></tr>
    <tr><td>Nazione</td><td><input dojoType="dijit.form.TextBox" name="country"/></td></tr>
    <tr><td>Codice fiscale</td><td><input dojoType="dijit.form.TextBox" name="code"/></td></tr>
    <tr><td>Nato il</td><td><input dojoType="dijit.form.DateTextBox" name="bornDate" constraints="{formatLength:'short'}"/></td></tr>
    <tr><td>Nato in</td><td><input dojoType="dijit.form.TextBox" name="bornIn"/></td></tr>
    <tr><td>Note</td><td><textarea dojoType="dijit.form.Textarea" name="info"></textarea></td></tr>
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
   [#if contactaSession.account??]
    <input jsId="ui.digitInput" dojoType="dijit.form.TextBox" value="" style="width:12em;height:26px;"/>
    <div dojoType="dijit.form.Button" iconClass="ico30" onclick="contacta.dial()"><span>${m.t("label.dial")}</span></div>
    <div dojoType="dijit.form.Button" iconClass="ico33" onclick="contacta.transfer()"><span>${m.t("label.transfer")}</span></div>
    <div dojoType="dijit.form.Button" iconClass="ico32" onclick="contacta.hold()"><span>${m.t("label.hold")}</span></div>
    <div dojoType="dijit.form.Button" iconClass="ico32" onclick="contacta.unhold()"><span>${m.t("label.unhold")}</span></div>
    <div dojoType="dijit.form.Button" iconClass="ico30" onclick="contacta.answer()"><span>${m.t("label.answer")}</span></div>
   [#else]
    <b><i>${m.t("short.phonebar.unavailable")}</i></b>
   [/#if]
  </div>
  <div dojoType="dijit.Toolbar" class="toolbarToolbar">
   <div dojoType="dijit.form.Button" iconClass="ico50" showLabel="false" onclick="${pad}.showCreate()"><span>${m.t("label.create")}</span></div>
   <div dojoType="dijit.form.Button" iconClass="ico51" showLabel="false" onclick="${pad}.showDeleteGridSelected()"><span>${m.t("label.delete")}</span></div>
   <div dojoType="dijit.form.Button" iconClass="icoEdit" showLabel="false" onclick="${pad}.showUpdateGridSelected()"><span>${m.t("label.edit")}</span></div>
   <div dojoType="dijit.form.Button" iconClass="icoRefresh" showLabel="false" onclick="${pad}.refresh()"><span>${m.t("label.reload")}</span></div>
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
  <table jsId="${ui}.grid" dojoType="dojox.grid.DataGrid" selectable="true">
  </table>
 </div>

 <div dojoType="dijit.layout.ContentPane" splitter="true" region="right" class="detailPane">
  <div jsId="${ui}.detailPane" dojoType="dijit.layout.ContentPane"></div>
 </div>
</div>

