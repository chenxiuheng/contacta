[#ftl]<!-- $Revision: 673 $  :encoding=UTF-8:-->
<div dojoType="dijit.TitlePane" title="Contatto">
<table class="detailTable">
<tr><td>${m.t("label.id")}:</td><td>${model.id?string}</td></tr>
<tr><td>${m.t("label.firstName")}:</td><td>${model.firstName!"N/A"}</td></tr>
<tr><td>${m.t("label.lastName")}:</td><td>${model.lastName!"N/A"}</td></tr>
<tr><td>${m.t("label.displayName")}:</td><td>${model.displayName!"N/A"}</td></tr>
<tr><td>${m.t("label.company")}:</td><td>${model.company!"N/A"}</td></tr>
<tr><td>${m.t("label.email")}:</td><td>${model.email!"N/A"}</td></tr>
<tr><td>${m.t("label.phone")}:</td><td>${model.phone!"N/A"}</td></tr>
<tr><td>${m.t("label.extension")}:</td><td>${model.extension!"N/A"}</td></tr>
<tr><td>${m.t("label.code")}:</td><td>${model.code!"N/A"}</td></tr>
<tr><td>${m.t("label.bornDate")}:</td><td>${model.bornDate!"N/A"}</td></tr>
<tr><td>${m.t("label.bornIn")}:</td><td>${model.bornIn!"N/A"}</td></tr>
<tr><td>${m.t("label.address")}:</td><td>${model.address!"N/A"}</td></tr>
<tr><td>${m.t("label.city")}:</td><td>${model.city!"N/A"}</td></tr>
<tr><td>${m.t("label.zip")}:</td><td>${model.zip!"N/A"}</td></tr>
<tr><td>${m.t("label.country")}:</td><td>${model.country!"N/A"}</td></tr>
<tr><td colspan="2">${m.t("label.info")}:</td></tr>
<tr><td colspan="2"><pre>${model.info!"N/A"}</pre></td></tr>
</table>
</div>

