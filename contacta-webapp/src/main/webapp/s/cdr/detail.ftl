[#ftl]<!-- $Revision: 673 $  :encoding=UTF-8:-->
<div dojoType="dijit.layout.ContentPane" class="detailInner" title="CDR">
<table class="detailTable">
[#--
<tr><td>${m.t("label.id")}:</td><td>${model.id}</td></tr>
<tr><td>${m.t("label.hasbillsec")}:</td><td>${model.hasbillsec}</td></tr>
<tr><td>${m.t("label.hasduration")}:</td><td>${model.anhasduration}</td></tr>
<tr><td>${m.t("label.hasamaflags")}:</td><td>${model.hasamaflags}</td></tr>
--]
<tr><td>${m.t("label.calldate")}:</td><td>${model.calldate}</td></tr>
<tr><td>${m.t("label.clid")}:</td><td>${model.clid}</td></tr>
<tr><td>${m.t("label.src")}:</td><td>${model.src}</td></tr>
<tr><td>${m.t("label.dst")}:</td><td>${model.dst}</td></tr>
<tr><td>${m.t("label.dcontext")}:</td><td>${model.dcontext}</td></tr>
<tr><td>${m.t("label.channel")}:</td><td>${model.channel}</td></tr>
<tr><td>${m.t("label.dstchannel")}:</td><td>${model.dstchannel!"N/A"}</td></tr>
<tr><td>${m.t("label.lastapp")}:</td><td>${model.lastapp}</td></tr>
<tr><td>${m.t("label.lastdata")}:</td><td>${model.lastdata!"N/A"}</td></tr>
<tr><td>${m.t("label.duration")}:</td><td>${model.duration}</td></tr>
<tr><td>${m.t("label.billsec")}:</td><td>${model.billsec}</td></tr>
<tr><td>${m.t("label.disposition")}:</td><td>${model.disposition}</td></tr>
<tr><td>${m.t("label.amaflags")}:</td><td>${model.amaflags}</td></tr>
<tr><td>${m.t("label.accountcode")}:</td><td>${model.accountcode!"N/A"}</td></tr>
<tr><td>${m.t("label.uniqueid")}:</td><td>${model.uniqueid}</td></tr>
<tr><td>${m.t("label.userfield")}:</td><td>${model.userfield!"N/A"}</td></tr>
</table>
</div>

