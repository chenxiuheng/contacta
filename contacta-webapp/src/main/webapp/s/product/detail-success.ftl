[#ftl]<!-- $Revision: 673 $  :encoding=UTF-8:-->
<div dojoType="dijit.layout.ContentPane" class="detailInner" title="Product">
<table class="detailTable">
<tr><td>${m.t("label.id")}:</td><td>${model.id?string}</td></tr>
<tr><td>${m.t("label.code")}:</td><td>${model.code?string}</td></tr>
<tr><td>${m.t("title.vendor")}:</td><td>${model.vendor}</td></tr>
<tr><td>${m.t("title.model")}:</td><td>${model.model}</td></tr>
<tr><td>${m.t("title.version")}:</td><td>${model.version}</td></tr>
<tr><td>${m.t("title.userAgent")}:</td><td>${model.userAgent}</td></tr>
</table>
<p>${m.t("title.resourceNameList")}:</p>
<ul>[#if model.resourceNameList??][#list model.resourceNameList as res]<li>${res}</li>[/#list][/#if]</ul>
</div>
