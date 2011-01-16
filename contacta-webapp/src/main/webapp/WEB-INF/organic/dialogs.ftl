[#ftl][#-- $Id$ --]

[#include "./dialogs-common.ftl"/]

[#-- ====================================================================== --]
[#--   sip dialogs                                                          --]
[#-- ====================================================================== --]

[#-- ui.starDetailDialog --]
<div dojoType="dijit.Dialog" jsId="ui.starDetailDialog" title="${m.t("title.asterisk.details")}" refreshOnShow="false" preventCache="true" preload="false">
</div>

[#-- ui.coverageDialog --]
<div dojoType="dijit.Dialog" jsId="ui.coverageDialog" title="${m.t("title.coverage.add")}">
 <div dojoType="dijit.form.Form" jsId="ui.coverageForm">
  <table>
   <tr><td>${m.t("label.exten")}</td><td><input dojoType="dijit.form.TextBox" name="exten"/></td></tr>
   <tr><td>${m.t("label.coverage.type")}</td><td><select dojoType="dijit.form.ComboBox" name="type" required="true">[@coverageOptions/]</select></td></tr>
   <tr><td>${m.t("label.coverage.options")}</td><td><input dojoType="dijit.form.TextBox" name="options"/></td></tr>
   <tr><td>${m.t("label.coverage.ringTimeout")}</td><td><input dojoType="dijit.form.NumberTextBox" name="ringTimeout" value="15" promptMessage="${m.t("message.ringTimeout.prompt")}" invalidMessage="${m.t("message.ringTimeout.invalid")}"/></td></tr>
  </table>
 </div>
 [@oko2 ok="contacta.sip.coverage.submit" ko="contacta.sip.coverage.cancel"/]
</div>

[#-- ====================================================================== --]
[#--   stores                                                               --]
[#-- ====================================================================== --]
<div id="globalStores" style="display:none;">
 <div jsId="globalStore.vendorStore" dojoType="dojo.data.ItemFileReadStore" url="${base}/s/datastore.action?method=processList" urlPreventCache="false"></div>
</div>


[#-- ====================================================================== --]
[#--   phone dialogs                                                        --]
[#-- ====================================================================== --]

[#-- ui.phoneAddAccountDialog --]
<div dojoType="dijit.Dialog" id="ui.phoneAddAccountDialog" jsId="ui.phoneAddAccountDialog" title="${m.t("title.phone.addSip")}">
 <div dojoType="dijit.form.Form" jsId="ui.phoneAddAccountForm">
  <table>
   <tr><td>${m.t("label.exten")}</td><td><input dojoType="dijit.form.TextBox" name="login"/></td></tr>
  </table>
 </div>
 [@oko2 ok="contacta.phone.addAccountSubmit()" ko="contacta.phone.addAccountCancel()"/]
[#if false][#--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX --]
 <div dojoType="dijit.layout.ContentPane" id="phoneAddAccountPane" jsId="phoneAddAccountPane">
  <div dojoType="dijit.layout.ContentPane" style="margin-bottom:10px;">${m.t("label.phone.addSip")}</div>
  <div style="height:400px;">
  <div id="ui.phoneAddAccountGrid" jsId="ui.phoneAddAccountGrid" dojoType="dojox.grid.DataGrid" autoWidth="true"
      noDataMessage="${m.t("label.detailPaneEmpty")}" structure="contacta.sip.structure" selectionMode="single"
      >
  </div>
  </div>
 </div>
 [@oko2 ok="phoneAddAccountSubmit" ko="phoneAddAccountCancel"/]
[/#if]
</div>

[#-- ui.wizardPhonePersonDialog value="00:00:00:00:00:00" --]
<div dojoType="dijit.Dialog" jsId="ui.wizardPhonePersonDialog" title="Crea un nuovo...">
 <div dojoType="dijit.form.Form" jsId="ui.wizardPhonePersonForm" style="padding:5px 5px 5px 5px;">
  <table>
   <tr><td>${m.t("label.firstName")}</td><td><input jsId="firstNameInputB" dojoType="dijit.form.TextBox" name="account.firstName"/></td></tr>
   <tr><td>${m.t("label.lastName")}</td><td><input jsId="lastNameInputB" dojoType="dijit.form.TextBox" name="account.lastName"/></td></tr>
   <tr><td>${m.t("label.fullName")}</td><td><input jsId="fullNameInputB" dojoType="dijit.form.TextBox" name="account.fullName"/><div dojoType="dijit.form.Button" onclick="fullNameInputB.attr('value', firstNameInputB.attr('value')+' '+lastNameInputB.attr('value'));"><span>${m.t("label.sip.auto")}</span></div></td></tr>
   <tr><td colspan="2"><hr/></td></tr>
   <tr><td>${m.t("label.sip.login")}</td><td><input dojoType="dijit.form.ValidationTextBox" name="account.login" regExp="\d{4}" promptMessage="${m.t("message.login.prompt")}" invalidMessage="${m.t("message.login.invalid")}" required="true"/></td></tr>
   <tr><td>${m.t("label.sip.password")}</td><td><input dojoType="dijit.form.TextBox" name="account.password" required="true"/></td></tr>
   <tr><td>${m.t("label.sip.profileName")}</td><td><select dojoType="dijit.form.ComboBox" name="account.profileName" autoComplete="true" required="true" onChange="contacta.sip.changeProfile(arguments[0])">[@profileOptions/]</select></td></tr>
   <tr><td>${m.t("label.sip.profileOptions")}</td><td><input jsId="ui.profileOptions" dojoType="dijit.form.ValidationTextBox" name="account.profileOptions"/></td></tr>
   <tr><td>${m.t("label.sip.context")}</td><td><select name="account.context" dojoType="dijit.form.ComboBox" autoComplete="true" required="true">[@contextOptions/]</select></td></tr>
   <tr><td>${m.t("label.sip.ringTimeout")}</td><td><input dojoType="dijit.form.NumberTextBox" name="account.ringTimeout" value="15" promptMessage="${m.t("message.ringTimeout.prompt")}" invalidMessage="${m.t("message.ringTimeout.invalid")}"/></td></tr>
   <tr><td>${m.t("label.sip.voicemail")}</td><td><input jsId="ui.vmInputWizard" dojoType="dijit.form.CheckBox" name="account.vmEnabled" value="true"/></td></tr>
   <tr><td id="vmDescWizard">${m.t("label.vm.pin")}</td><td><input jsId="ui.vmPinInputWizard" dojoType="dijit.form.ValidationTextBox" name="account.voicemailPin" regExp="\d{0,8}" promptMessage="${m.t("message.pin.prompt")}"/></td></tr>
   <tr><td>${m.t("label.sip.callgroup")}</td><td><input dojoType="dijit.form.ValidationTextBox" name="account.callgroup" regExp="\d{0,4}"/></td></tr>
   <tr><td>${m.t("label.sip.pickupgroup")}</td><td><input dojoType="dijit.form.ValidationTextBox" name="account.pickupgroup" regExp="\d{0,4}(,\d{1,4})*" promptMessage="${m.t("message.pickupgroup.prompt")}"/></td></tr>
   [#if false]
   <tr><td>${m.t("label.sip.callwaiting")}</td><td><input dojoType="dijit.form.TextBox" name="callwaiting"/></td></tr>
   [/#if]
   <tr><td>${m.t("label.coc.enabled")}</td><td><input jsId="ui.cocInputWizard" dojoType="dijit.form.CheckBox" name="account.cocEnabled" value="true" onclick="watchCheckbox(ui.cocInputWizard, 'cocLoginDescriptionWizard', ui.cocLoginInputWizard); watchCheckbox(ui.cocInputWizard, 'cocPinDescriptionWizard', ui.cocPinInputWizard);"/></td></tr>
   <tr><td id="cocLoginDescriptionWizard">${m.t("label.coc.login")}</td><td><input jsId="ui.cocLoginInputWizard" dojoType="dijit.form.ValidationTextBox" required="false" name="account.cocLogin" regExp="\d{5}" promptMessage="${m.t("message.coc.login.prompt")}" invalidMessage="${m.t("message.coc.login.invalid")}"/></td></tr>
   <tr><td id="cocPinDescriptionWizard">${m.t("label.coc.password")}</td><td><input jsId="ui.cocPinInputWizard" dojoType="dijit.form.ValidationTextBox" required="false" name="account.cocPin" regExp="\d{4}" promptMessage="${m.t("message.coc.password.prompt")}" invalidMessage="${m.t("message.coc.password.invalid")}"/></td></tr>

   <tr><td colspan="2"><hr/></td></tr>
   <tr><td>${m.t("label.phone.vendor")}</td><td><select name="phone.vendor" dojoType="dijit.form.ComboBox" autoComplete="false"><option value="POLYCOM">POLYCOM</option></select></td></tr>
   <tr><td>${m.t("label.phone.model")}</td><td><select name="phone.model" dojoType="dijit.form.ComboBox" autoComplete="false"><option value="SPIP_330">SPIP_330</option><option value="SPIP_450">SPIP_450</option><option value="SPIP_670">SPIP_670</option></select></td></tr>
   <tr><td>${m.t("label.mac")}</td><td><input dojoType="dijit.form.ValidationTextBox" name="phone.macAddress" regExp="[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}" lowercase="true" promptMessage="${m.t("message.mac.prompt")}" invalidMessage="${m.t("message.mac.invalid")}" required="true"/></td></tr>
   <tr><td>${m.t("label.phone.location")}</td><td><input dojoType="dijit.form.TextBox" name="phone.location"/></td></tr>
  </table>
 </div>
 [@oko2 "contacta.phone.wizardPhonePersonSubmit()" "ui.wizardPhonePersonDialog.hide()"/]
</div>

[#-- ui.wizardAssignPhoneDialog  --]
<div dojoType="dijit.Dialog" jsId="ui.wizardAssignPhoneDialog" title="${m.t("label.phone.create")}">
 <div dojoType="dijit.form.Form" jsId="ui.wizardAssignPhoneForm" style="padding:5px 5px 5px 5px;">
  [#--  labelFunc="ciao" labelAttr="login" labelType="html"  store="stateStore" onChange="setVal2" value="boh"  --]
  <div style="padding:0.5em;">
   <label for="ui.wizardPhoneSelect" style="padding-bottom:0.5em;"><b>${m.t("label.phone.phone")}</b></label><br/><br/>
   <input jsId="ui.wizardPhoneInput" dojoType="dijit.form.TextBox" name="phone.id" style="display:none"/>
   <input jsId="ui.wizardPhoneSelect" dojoType="dijit.form.FilteringSelect" name="phone.macAddress" onChange="contacta.phone.setVal1"
          searchAttr="macAddress" labelAttr="macAddress"
          />
  </div>
  <div style="padding:0.5em;">
   <label for="ui.wizardAccountSelect" style="padding-bottom:0.5em;"><b>${m.t("label.sip.sip")}</b></label><br/><br/>
   <input jsId="ui.wizardAccountInput" dojoType="dijit.form.TextBox" name="account.id" style="display:none"/>
   <input jsId="ui.wizardAccountSelect" dojoType="dijit.form.FilteringSelect" name="account.login" onChange="contacta.phone.setVal2"
          searchAttr="login" labelAttr="login" autoComplete="true"
          promptMessage="${m.t("message.wizard.assign.exten.prompt")}" invalidMessage="${m.t("message.wizard.assign.exten.invalid")}"
          />
  </div>
 </div>
 [@oko2 "contacta.phone.wizardAssignPhoneSubmit()" "ui.wizardAssignPhoneDialog.hide()"/]
</div>

