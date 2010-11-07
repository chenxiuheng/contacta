[#ftl]<!-- $Revision: 666 $  :encoding=UTF-8:-->
[#-- tmp here, see also main css --]
<style>
table.detailTable { border-collapse:collapse; width:100%; }
.detailTable tr.yellow td { border:1px solid #FB7A31; background:#FFC; text-align:left; font-weight:bold; }
.detailTable th { padding:2px; text-align:center; background-color:red; }
.detailTable td { border:1px solid #cccccc; padding:1px; background-color:#eeeeee; }
.detailTable td:first-child { padding-left:0.5em; padding-right:0.5em; background-color:#dddddd; text-align:right; }
.detailTable ul { margin:0px; padding:0px; list-style:none; }
.detailTable li {  }
.detailTable li:hover {  }
</style>
<div dojoType="dijit.TitlePane" title="Telefono">
 <table class="detailTable">
  <tr><td>Product</td><td>${model.product.code!"N/A"}</td></tr>
  <tr><td>Vendor</td><td>${model.product.vendor!"N/A"}</td></tr>
  <tr><td>MAC address</td><td>${model.macAddress}</td></tr>
  <tr><td>Cfg locale</td><td>${model.hasConfig?string}</td></tr>
  <tr><td>Rubrica locale</td><td>${model.hasDirectory?string}</td></tr>
  <tr><td>Stanza</td><td>${model.location!"N/A"}</td></tr>
  <tr><td>Numero seriale</td><td>${model.serialNumber!"N/A"}</td></tr>
  <tr><td>last boot</td><td>${model.lastBoot!"N/A"}</td></tr>
  <tr><td>ipAddress</td><td>${model.ipAddress!"N/A"}</td></tr>
 </table>
</div>

[#if model.sipAccountList.size() != 0]
 <div class="spacer"></div>
 <div dojoType="dijit.TitlePane" title="Linee SIP">
  <table class="detailTable">
   [#list model.sipAccountList as ac]
    <tr><td>${ac.login}</td><td>${ac.displayName}</td></tr>
    [#if ac.hasCoverage]
    [#list ac.coverageList as cvg]
     <tr><td>&nbsp;</td><td>&nbsp;&nbsp;${cvg.type} (${cvg.ringTimeout} sec) -&gt; ${cvg.toSip.login}</td></tr>
    [/#list]
    [/#if]
   [/#list]
  </table>
 </div>
[/#if]
<div class="spacer"></div>
[#if model.product.code?starts_with("SPIP")]
<div dojoType="dijit.TitlePane" title="Provisioning" tooltip="I'm the tooltip for Provisioning's title bar">
<!--div class="contacta" style="margin:6px; border:1px dotted #aaaaaa; padding:6px; "-->
 [#assign mac = model.macAddress?replace(":","")/]
 <ul class="detailTable">
  <li><a target="_blank" href="${base}/p/${mac}-phone.cfg">MAC-phone.cfg</a></li>
  <li><a target="_blank" href="${base}/p/${mac}-directory.xml">MAC-directory.xml</a></li>
  <li><a target="_blank" href="${base}/p/${mac}-license.xml">MAC-license.xml</a></li>
  <li><a target="_blank" href="${base}/p/${mac}.cfg">MAC.cfg</a></li>
  <li><a target="_blank" href="${base}/p/local-settings.cfg">local-settings.cfg</a></li>
  <li><a target="_blank" href="${base}/p/sip.cfg">sip.cfg</a></li>
  <li><a target="_blank" href="${base}/p/phone1.cfg">phone1.cfg</a></li>
 </ul>
</div>
[/#if]
