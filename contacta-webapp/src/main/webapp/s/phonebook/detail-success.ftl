[#ftl]<!-- $Revision: 673 $  :encoding=UTF-8:-->
[#-- tmp here, see also main css --]
<style>
</style>
<div dojoType="dijit.layout.ContentPane" class="detailInner" title="Contatto">
<table class="detailTable">
<tr><td>${m.t("label.displayName")}:</td><td><div>${model.displayName!""}</div></td></tr>
<tr><td>${m.t("label.firstName")}:</td><td><div>${model.firstName!""}</div></td></tr>
<tr><td>${m.t("label.lastName")}:</td><td><div>${model.lastName!""}</div></td></tr>
<tr><td>${m.t("label.company")}:</td><td><div>${model.company!""}</div></td></tr>
<tr><td>${m.t("label.email")}:</td><td><div>${model.email!""}</div></td></tr>
<tr><td>${m.t("label.phone")}:</td><td><div>${model.phone!""}</div></td></tr>
<tr><td>${m.t("label.extension")}:</td><td><div>${model.extension!""}</div></td></tr>
<tr><td>${m.t("label.code")}:</td><td><div>${model.code!""}</div></td></tr>
<tr><td>${m.t("label.bornDate")}:</td><td><div>${model.bornDate!""}</div></td></tr>
<tr><td>${m.t("label.bornIn")}:</td><td><div>${model.bornIn!""}</div></td></tr>
<tr><td>${m.t("label.address")}:</td><td><div>${model.address!""}</div></td></tr>
<tr><td>${m.t("label.city")}:</td><td><div>${model.city!""}</div></td></tr>
<tr><td>${m.t("label.zip")}:</td><td><div>${model.zip!""}</div></td></tr>
<tr><td>${m.t("label.country")}:</td><td><div>${model.country!""}</div></td></tr>
[#----]
<tr><td>${m.t("label.info")}:</td></tr>
<tr><td colspan="2"><pre>${model.info!""}</pre></td></tr>

</table>
</div>

