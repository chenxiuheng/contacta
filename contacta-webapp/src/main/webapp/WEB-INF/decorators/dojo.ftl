[#ftl]<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
[#-- $Revision: 674 $ --]
[#-- :encoding=UTF-8: --]

<html>
[#-- ====================================================================== --]
[#--    head                                                                --]
[#-- ====================================================================== --]
<head>
<title>Contacta: ${title}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="icon" type="image/png" href="${base}/r/static/favicon.png"/>
<link rel="stylesheet" type="text/css" href="${base}/res/css_page.action" media="all"/>
<style>
</style>

<script type="text/javascript">
var djConfig = {
 isDebug:${debug?string},
 usePlainJson:true,
 parseOnLoad:true
 /*[#--
 extraLocale: ['it-it', 'en-us'],
 baseUrl: '${base}${dojoBase}/dojo',
 dojoBlankHtmlUrl: '${base}${dojoBase}/dojo/resources/blank.html',
 dojoIframeHistoryUrl: '${base}${dojoBase}/resources/iframe_history.html',
 preventBackButtonFix: false,
 bindEncoding: '$ {encoding}',
 dojoRichTextFrameUrl: "../../../src/widget/templates/richtextframe.html" //for xdomain
 debugAtAllCosts: ${debug?string}, // not needed, but allows the Venkman debugger to work with the includes
 --] */
};
</script>
<script type="text/javascript" src="${base}${dojoBase}/dojo/dojo.js[#if debug == true].uncompressed.js[/#if]"></script>
<script type="text/javascript" src="${base}${dojoBase}/dojo/micdojo.js[#if debug == true].uncompressed.js[/#if]"></script>
<script type="text/javascript" src="${base}${dojoBase}/dojo/back.js"></script>
<script type="text/javascript">
dojo.require("dojo.i18n");
dojo.require('dijit.dijit');
dojo.require('dijit.Dialog');
dojo.require('dijit.TitlePane');
dojo.require("dijit.Tooltip");
dojo.require('dijit.Menu');
dojo.require('dijit.Editor');
dojo.require('dijit.layout.ContentPane');
dojo.require('dijit.layout.TabContainer');
dojo.require('dijit.layout.StackContainer');
dojo.require('dijit.layout.BorderContainer');
dojo.require('dijit.form.Form');
dojo.require('dijit.form.Button');
dojo.require('dijit.form.TextBox');
dojo.require('dijit.form.ComboBox');
dojo.require('dijit.form.FilteringSelect');
dojo.require('dijit.form.CheckBox');
dojo.require('dijit.form.NumberTextBox');
dojo.require("dijit.form.ValidationTextBox");
dojo.require("dijit.form.MultiSelect");
/*
dojo.require('dijit.form.CurrencyTextBox');
dojo.require("dojox.form.RangeSlider");
dojo.require('dojox.data.AndOrWriteStore');
dojo.require('dojox.layout.ExpandoPane');
dojo.require('dojox.layout.ResizeHandle');
dojo.require("dojox.gfx");
dojo.require("dojox.gfx.move");
*/

dojo.require('dojox.grid.DataGrid');
dojo.require('dojox.grid.cells.dijit');
/*
dojo.require("dojox.grid.EnhancedGrid");
dojo.require("dojox.grid.enhanced.plugins.DnD");
dojo.require("dojox.grid.enhanced.plugins.Menu");
dojo.require("dojox.grid.enhanced.plugins.NestedSorting");
dojo.require("dojox.grid.enhanced.plugins.IndirectSelection");
*/
dojo.require('dojo.parser');
dojo.require("dojo.date.locale");
dojo.require("dojo.date");
dojo.require("dojo.date.stamp");
dojo.require('dojo.data.ItemFileReadStore');
dojo.require('dojo.data.ItemFileWriteStore');
//dojo.require("dojo.io.iframe");
dojo.require("dojo.rpc.RpcService");
dojo.require("dojo.rpc.JsonService");
//dojo.require("dojo.rpc.JsonpService");
dojo.require("dojo.back");

//dojo.require("openinnovation.organic.Organic");
</script>

${head}

</head>
[#-- ====================================================================== --]
[#--    body                                                                --]
[#-- ====================================================================== --]
<body class="${dojoTheme} organic">
<script type="text/javascript">dojo.back.init();</script>

[#-- ====================================================================== --]
[#--    common widgets                                                      --]
[#-- ====================================================================== --]
<div id="organicLoadingPanel">
 <div style="width:420px;height:70px;margin:auto;margin-top:20%;border:1px solid silver;padding-top:5px;">
  <div style="margin:auto;">
   <table cellspacing="10" style="margin:auto;"><tr><td><div class="ico02"></div></td><td><div>${m.t("label.wait")}</div></td></tr></table>
  </div>
  <div style="margin:auto;margin-top:3px;width:400px" id="ui.progressBar" jsId="ui.progressBar" dojoType="dijit.ProgressBar" onChange="organic.util.checkProgress()"></div>
 </div>
</div>


${body}


[#-- ====================================================================== --]
[#--   dialogs                                                              --]
[#-- ====================================================================== --]
<div id="dialogs" style="display:none;">
 [#include "../../util/macro.ftl"/]
 [#include "../../util/dialogs.ftl"/]

 <div jsId="ui.waitDialog" dojoType="dijit.Dialog" title="${m.t("title.wait")}">
  <div class="dijitContentPaneLoading" style="width:20em;">${m.t("short.wait")}</div>
  <div jsId="ui.waitMessagePane" dojoType="dijit.layout.ContentPane"></div>
 </div>

 <div jsId="ui.organic.deleteDialog" dojoType="dijit.Dialog" title="${m.t("title.phone.delete")}">
  <div class="dijitDialogPaneContentArea">
   <div jsId="ui.organic.deleteMessage" dojoType="dijit.layout.ContentPane">
   </div>
  </div>
  <div class="dijitDialogPaneActionBar">
   <form jsId="ui.organic.deleteForm" dojoType="dijit.form.Form">
    <button dojoType="dijit.form.Button" iconClass="icoApply"  type="submit">${m.t("label.ok")}</button>
    <button dojoType="dijit.form.Button" iconClass="icoCancel" onclick="ui.organic.deleteDialog.hide();return false;">${m.t("label.ko")}</button>
   </form>
  </div>
 </div>

 [#-- organicErrorDialog --]
 <div jsId="ui.organicErrorDialog" dojoType="dijit.Dialog" title="Organic Error Message">
  <div dojoType="dijit.layout.BorderContainer" style="width:800px; height:800px;">
   <div dojoType="dijit.layout.ContentPane" region="top">
    <div jsId="ui.organicErrorForm" dojoType="dijit.form.Form">
     <input dojoType="dijit.form.TextBox" name="fileName" readOnly="false" style="width:300px;"/>fileName<br/>
     <input dojoType="dijit.form.TextBox" name="lineNumber" readOnly="false" style="width:300px;"/>lineNumber<br/>
     <input dojoType="dijit.form.TextBox" name="message" readOnly="false" style="width:300px;"/>message<br/>
     [#--<textarea dojoType="dijit.form.SimpleTextarea" name="stack" rows="30" cols="100" readOnly="false" style=""></textarea>--]
    </div>
   </div>
   <div jsId="ui.organicErrorStack" dojoType="dijit.layout.ContentPane" region="center" style="font-size:10px;">
   </div>
  </div>
 </div>

 [#-- ui.messageDialog --]
 <div jsId="ui.organic.messageDialog" dojoType="dijit.Dialog" title="Organic Info Message">
  <div jsId="ui.organic.messagePane" dojoType="dijit.layout.ContentPane"></div>
 </div>
 [#-- alert duration="200" --]
 <div jsId="ui.dojoAlert" dojoType="dijit.Dialog" title="${m.t("attention")}">
  <div dojoType="dijit.layout.ContentPane" style="padding:5px 5px 5px 5px;">
   <div dojoType="dijit.layout.ContentPane" style="margin-bottom:20px;">
    <table>
     <tr>
      <td align="left"><div class="errorImgPane" dojoType="dijit.layout.ContentPane" style="margin-right:10px;"></div></td>
      <td align="right" style="width:300px;"><div dojoType="dijit.layout.ContentPane" jsId="ui.dojoAlertText" style="text-align:left;"></div></td>
     </tr>
    </table>
   </div>
   <center><div dojoType="dijit.form.Button" onclick="organic.util.dojoAlertHide();" style="margin:auto;"><span>${m.t("label.ok")}</span></div></center>
  </div>
 </div>
</div>

</body>
</html>

