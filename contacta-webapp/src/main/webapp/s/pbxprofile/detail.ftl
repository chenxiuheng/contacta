[#ftl]<!-- $Revision: 673 $  :encoding=UTF-8:-->
<div dojoType="dijit.layout.ContentPane" class="detailInner" title="PBX Profile">
<table class="detailTable">
<tr><td>${m.t("label.id")}:</td><td>${model.id?string}</td></tr>
<tr><td>${m.t("label.code")}:</td><td>${model.code?string}</td></tr>
<tr><td>${m.t("label.label")}:</td><td>${model.label}</td></tr>
<tr><td>${m.t("label.command")}:</td><td>${model.command}</td></tr>
<tr><td colspan="2">${m.t("label.macro")}:</td></tr>
<tr><td colspan="2"><pre>${model.macro}</pre></td></tr>
</table>
</div>

