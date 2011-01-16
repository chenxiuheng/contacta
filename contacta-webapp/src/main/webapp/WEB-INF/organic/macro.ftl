[#ftl][#-- $Revision: 674 $  :encoding=UTF-8:--]

[#include "./macro-common.ftl"/]

[#-- ====================================================================== --]
[#--      local macros                                                      --]
[#-- ====================================================================== --]
[#--#assign reLogin = "\\d{2,4}"/--]
[#assign reLogin = ".+"/]

[#macro profileOptions]
[#list profileList as opt]<option value="${opt.code}">${opt.code}</option>[/#list]
[/#macro]

[#macro coverageOptions]
[#list contactaConfiguration.coverageCombo.options as opt]<option value="${opt}">${opt}</option>[/#list]
[/#macro]

[#macro contextOptions]
[#list contextList as opt]<option value="${opt.code}">${opt.code}</option>[/#list]
[/#macro]


[#-- Ok/Cancel buttons --]
[#macro oko2 ok ko]
<div dojoType="dijit.layout.ContentPane" style="width:100%; height:34; padding:2px 0px 2px 0px; margin-top:10px;">
 <div dojoType="dijit.form.Button" iconClass="ico24" onclick="${ko}"><span>${m.t("label.ko")}</span></div>
 <div dojoType="dijit.form.Button" iconClass="ico23" onclick="${ok}"><span>${m.t("label.ok")}</span></div>
</div>
[/#macro]


[#-- ////////////////////////////////// --]
[#--           COMMON WIDGETS           --]
[#-- ////////////////////////////////// --]
<div dojoType="dijit.Dialog" jsId="ui.dojoAlert" title="${m.t("label.attention")}" duration="200" style="display:none;">
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
