[#ftl]<!-- $Revision: 673 $  :encoding=UTF-8:-->
[#assign pad = "contacta.sip"/]
[#assign ui = "ui.sip"/]
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
    <tr><td>${m.t("label.fullName")}</td><td><input dojoType="dijit.form.TextBox" name="fullName"/></td></tr>
    <tr><td>${m.t("label.email")}</td><td><input dojoType="dijit.form.ValidationTextBox" name="email" required="true" invalidMessage="${m.t("message.email.invalid")}"/></td></tr>
    <tr><td colspan="2"><hr/></td></tr>
    <tr><td>${m.t("label.sip.login")}</td><td><input jsId="${ui}.loginInput" dojoType="dijit.form.ValidationTextBox" name="login" regExp="${reLogin}" promptMessage="${m.t("message.login.prompt")}" invalidMessage="${m.t("message.login.invalid")}" required="true"/></td></tr>
    <tr><td>${m.t("label.sip.password")}</td><td><input dojoType="dijit.form.TextBox" name="password" required="true"/></td></tr>
    <tr><td>${m.t("label.sip.profileName")}</td><td><select dojoType="dijit.form.ComboBox" name="profileName" autoComplete="true" required="true" onChange="${pad}.changeProfile(arguments[0])">[@profileOptions/]</select></td></tr>
    <tr><td>${m.t("label.sip.profileOptions")}</td><td><input jsId="ui.profileOptions" dojoType="dijit.form.ValidationTextBox" name="profileOptions"/></td></tr>
    <tr><td>${m.t("label.sip.context")}</td><td><select name="context" dojoType="dijit.form.ComboBox" autoComplete="true" required="true">[@contextOptions/]</select></td></tr>
    <tr><td>${m.t("label.sip.callgroup")}</td><td><input dojoType="dijit.form.ValidationTextBox" name="callgroup" regExp="\d{0,4}"/></td></tr>
    <tr><td>${m.t("label.sip.pickupgroup")}</td><td><input dojoType="dijit.form.ValidationTextBox" name="pickupgroup" regExp="\d{0,4}(,\d{1,4})*" promptMessage="${m.t("message.pickupgroup.prompt")}"/></td></tr>
    <tr><td>${m.t("label.sip.ringTimeout")}</td><td><input dojoType="dijit.form.NumberTextBox" name="ringTimeout" value="45" required="true" promptMessage="${m.t("message.ringTimeout.prompt")}" invalidMessage="${m.t("message.ringTimeout.invalid")}"/></td></tr>
    <tr><td colspan="2"><hr/></td></tr>
    <tr><td>${m.t("label.sip.voicemail")}</td><td><input jsId="ui.vmInput" dojoType="dijit.form.CheckBox" name="vmEnabled" value="true"/></td></tr>
    <tr><td>${m.t("label.vm.pin")}</td><td><input jsId="ui.vmPinInput" dojoType="dijit.form.ValidationTextBox" name="vmPin" regExp="\d{0,8}" promptMessage="${m.t("message.pin.prompt")}"/></td></tr>
    <tr><td>${m.t("label.vm.sendEmail")}</td><td><input dojoType="dijit.form.CheckBox" name="vmSendEmail" value="true"/></td></tr>
    [#if false]
    <tr><td>${m.t("label.sip.callwaiting")}</td><td><input dojoType="dijit.form.TextBox" name="callwaiting"/></td></tr>
    [/#if]
    <tr><td colspan="2"><hr/></td></tr>
    <tr><td>${m.t("label.coc.enabled")}</td><td><input jsId="ui.cocInput" dojoType="dijit.form.CheckBox" name="cocEnabled" value="true" onclick="watchCheckbox(ui.cocInput, 'cocLoginDescription', ui.cocLoginInput); watchCheckbox(ui.cocInput, 'cocPinDescription', ui.cocPinInput);"/></td></tr>
    <tr><td id="cocLoginDescription">${m.t("label.coc.login")}</td><td><input jsId="ui.cocLoginInput" dojoType="dijit.form.ValidationTextBox" required="false" name="cocLogin" regExp="\d{5}" promptMessage="${m.t("message.coc.login.prompt")}" invalidMessage="${m.t("message.coc.login.invalid")}"/></td></tr>
    <tr><td id="cocPinDescription">${m.t("label.coc.password")}</td><td><input jsId="ui.cocPinInput" dojoType="dijit.form.ValidationTextBox" required="false" name="cocPin" regExp="\d{4}" promptMessage="${m.t("message.coc.password.prompt")}" invalidMessage="${m.t("message.coc.password.invalid")}"/></td></tr>
   </table>
  </div>
  [#--@oko5/--]
  <div class="dijitDialogPaneActionBar">
   <button dojoType="dijit.form.Button" iconClass="icoApply" onclick="contacta.contactaService.notifyCheckCfg(${ui}.loginInput.get('value'));">Reboot</button>
   <button dojoType="dijit.form.Button" iconClass="icoApply"  type="submit">${m.t("label.ok")}</button>
   <button dojoType="dijit.form.Button" iconClass="icoCancel" onclick="dojo.hitch(${pad}, ${pad}._crudHide());return false;">${m.t("label.ko")}</button>
  </div>
 </form>
</div>


[#-- ====================================================================== --]
[#--                                                                        --]
[#-- ====================================================================== --]
<div dojoType="dijit.layout.BorderContainer"gutters="false" liveSplitters="false" class="gridpad">
 <div dojoType="dijit.layout.ContentPane" region="top" class="toolbarContainer">
  <div dojoType="dijit.Toolbar" class="toolbarToolbar">
   <div dojoType="dijit.form.Button" iconClass="icoNew" showLabel="false" onclick="${pad}.showCreate()"><span>${m.t("label.sip.create")}</span></div>
   <div dojoType="dijit.form.Button" iconClass="icoEdit" showLabel="false" onclick="${pad}.showUpdateGridSelected()"><span>${m.t("label.sip.edit")}</span></div>
   <div dojoType="dijit.form.Button" iconClass="icoRemove" showLabel="false" onclick="${pad}.showDeleteGridSelected()"><span>${m.t("label.sip.delete")}</span></div>
   <button dojoType="dijit.form.DropDownButton" iconClass="ico50" label="${m.t("label.sip.coverage")}">
    <div dojoType="dijit.Menu">
     <div dojoType="dijit.MenuItem" iconClass="ico50" label="${m.t("label.sip.coverage.add")}" onclick="${pad}.coverage.show()"></div>
     <div dojoType="dijit.MenuItem" iconClass="ico51" label="${m.t("label.sip.coverage.clear")}" onclick="${pad}.coverage.removeAll()"></div>
    </div>
   </button>
   <button dojoType="dijit.form.Button" iconClass="ico46" showLabel="false" onclick="${pad}.starDetail()"><span>${m.t("label.sip.asterisk.detail")}</span></button>
   <div dojoType="dijit.form.Button" iconClass="icoRefresh" showLabel="false" onclick="${pad}.refresh()"><span>${m.t("label.reload")}</span></div>
   <span class="dijit dijitReset dijitLeft dijitInline dijitButton" style="margin-top:0px; font-size:11px;">
    <label for="${ui}.quickSelect" style="font-size:9px;;">${m.t("label.quickSearch")}</label><br/>
    <select jsId="${ui}.quickSelect" dojoType="dijit.form.ComboBox" style="width:80px;">
      <option value="lastName" selected="true">${m.t("label.lastName")}</option>
      <option value="firstName">${m.t("label.firstName")}</option>
      <option value="login">${m.t("label.login")}</option>
    </select>
    <input jsId="${ui}.quickFilter" dojoType="dijit.form.TextBox" trim="true" intermediateChanges="true" style="width:100px;"/>
   </span>
   [#-- CONTACTA == END --]
  </div>
 </div>

 <div dojoType="dijit.layout.ContentPane" region="center" class="gridPane">
  <table jsId="${ui}.grid" dojoType="dojox.grid.DataGrid" selectable="true" rowsPerPage="100"></table>
 </div>

 <div dojoType="dijit.layout.ContentPane" splitter="true" region="right" class="detailPane">
  <div jsId="${ui}.detailPane" dojoType="dijit.layout.ContentPane"></div>
 </div>
</div>

