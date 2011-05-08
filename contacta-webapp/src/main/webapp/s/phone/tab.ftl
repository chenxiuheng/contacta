[#ftl]<!-- $Revision: 673 $  :encoding=UTF-8:-->
[#assign pad = "contacta.phone"/]
[#assign ui = "ui.phone"/]
[#-- ====================================================================== --]
[#--                                                                        --]
[#-- ====================================================================== --]
<div jsId="${ui}.crudDialog" dojoType="dijit.Dialog">
 <form jsId="${ui}.crudForm" dojoType="dijit.form.Form">
  <div class="dijitDialogPaneContentArea">
   <div jsId="${ui}.crudMessage" dojoType="dijit.layout.ContentPane">
   </div>
   <input dojoType="dijit.form.TextBox" name="id" style="display:none;"/>
   <table>
    <tr><td>${m.t("label.phone.product")}</td><td><select jsId="${ui}.productSelect" name="product" dojoType="dijit.form.ComboBox" searchAttr="code"></select></td></tr>
    <tr><td>${m.t("label.mac")}</td><td><input dojoType="dijit.form.ValidationTextBox" name="code" value="00:00:00:00:00:00" regExp="[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}" lowercase="true" promptMessage="${m.t("message.mac.prompt")}" invalidMessage="${m.t("message.mac.invalid")}" required="true"/></td></tr>
    <tr><td>${m.t("label.phone.cfg")}</td><td><input dojoType="dijit.form.CheckBox" name="hasConfig" value="true"/></td></tr>
    <tr><td>${m.t("label.phone.location")}</td><td><input dojoType="dijit.form.TextBox" name="location"/></td></tr>
    <tr><td>${m.t("label.phone.sn")}</td><td><input dojoType="dijit.form.TextBox" name="serialNumber"/></td></tr>
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
   [@organicPadButtons pad ui/]
   <div dojoType="dijit.form.Button" iconClass="ico50" showLabel="true" onclick="${pad}.addAccountShow()"><span>${m.t("label.phone.addSip")}</span></div>
   <span class="dijit dijitReset dijitLeft dijitInline dijitButton" style="margin-top:0px; font-size:11px;">
    <label for="ui.phonePagination" style="font-size:9px;">${m.t("label.callerid")}</label><br/>
    <select jsId="ui.phonePagination" dojoType="dijit.form.ComboBox" autoComplete="true" style="width:80px;" onchange="${pad}.paging()">
      <option value="${m.t("label.na")}">${m.t("label.unassigned")}</option>
      <option value="*" selected="true">*</option>
      <option value="-">-</option>
    </select>
   </span>
   <span class="dijit dijitReset dijitLeft dijitInline dijitButton" style="margin-top:0px; font-size:11px;">
    <label for="${ui}.quickFilterSelect" style="font-size:9px;">${m.t("label.quickSearch")}</label><br/>
    <select jsId="${ui}.quickSelect" dojoType="dijit.form.ComboBox" autoComplete="true" style="width:80px;">
     <option value="accounts">${m.t("label.callerid")}</option>
     <option value="macAddress">${m.t("label.mac")}</option>
    </select>
    <input jsId="${ui}.quickFilter" dojoType="dijit.form.TextBox" trim="true" intermediateChanges="true" style="width:100px;"/>
   </span>
  </div>
 </div>

 <div dojoType="dijit.layout.ContentPane" region="center" class="gridPane">
  [#--<table jsId="${ui}.grid" dojoType="dojox.grid.DataGrid" sortInfo="1" noDataMessage="${m.t("label.detailPaneEmpty")}" rowsPerPage="20"></table>--]
  [#--<table dojoType="dojox.grid.DataGrid" noDataMessage="${m.t("label.detailPaneEmpty")}"
  var grid = new dojox.grid.EnhancedGrid({id: "grid", store: "store1", plugins: {nestedSorting: true}, ...}, dojo.byId("gridDiv"));
         plugins="{menus:{ headerMenu:'${ui}.headerMenu', rowMenu:'${ui}.rowMenu', cellMenu:'${ui}.cellMenu', selectedRegionMenu:'${ui}.selectedRegionMenu'}}"
         nestedSorting:true, dnd:true, indirectSelection:true,
  --]
  <table jsId="${ui}.grid" dojoType="dojox.grid.DataGrid" sortInfo="1">

   [#--
   <script type="dojo/method" event="onStyleRow" args="row">
    //The row object has 4 parameters, and you can set two others to provide your own styling
    //These parameters are :
    // -- index : the row index
    // -- selected: wether the row is selected
    // -- over : wether the mouse is over this row
    // -- odd : wether this row index is odd.
    //console.log('xxx', arguments);
    var grid = ${ui}.grid;
    grid.styleRowState(row);
    return;

    var item = grid.getItem(row.index);
    if (item)
    {
      var type = grid.store.getValue(item, "id", null);
      if (type == "50")
      {
        row.customStyles += "color:red;";
      }
    }
    grid.focus.styleRow(row);
    grid.edit.styleRow(row);
   </script>

   <div dojoType="dijit.Menu" id="${ui}.headerMenu"  style="display: none;">
    <div dojoType="dijit.MenuItem">Header Menu Item 1</div>
   </div>
   <div dojoType="dijit.Menu" id="${ui}.rowMenu"  style="display: none;">
    <div dojoType="dijit.MenuItem">Row Menu Item 1</div>
   </div>
   <div dojoType="dijit.Menu" id="${ui}.cellMenu"  style="display: none;">
    <div dojoType="dijit.MenuItem">Cell Menu Item 1</div>
   </div>
   <div dojoType="dijit.Menu" id="${ui}.selectedRegionMenu"  style="display: none;">
    <div dojoType="dijit.MenuItem">Action 1 for Selected Region</div>
   </div>
   --]
  </table>
 </div>

 <div dojoType="dijit.layout.ContentPane" splitter="true" region="right" class="detailPane">
  <div jsId="${ui}.detailPane" dojoType="dijit.layout.ContentPane" preventCache="true" class="detailPaneInner"></div>
 </div>
 [#--<div dojoType="dijit.layout.ContentPane" splitter="true" region="right" class="detailPane">
  <div jsId="${ui}.detailStack" dojoType="dijit.layout.StackContainer" region="center" class="detailPaneIn">
   <div jsId="${ui}.detailPane" dojoType="dijit.layout.ContentPane" preventCache="true" class="detailPaneInner"></div>
   <div jsId="${ui}.detailPane" dojoType="dijit.layout.ContentPane"></div>
  </div>
 </div>--]
</div>

