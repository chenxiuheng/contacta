[#ftl]<!-- $Revision: 663 $  :encoding=UTF-8:-->
<div dojoType="dijit.layout.ContentPane" class="detailInner" title="Utente">
 <table class="detailTable">
  <tr><td>Nome Completo</td><td>${sipAccount.displayName!"N/A"}</td></tr>
  <tr><td>Email</td><td><a href="mailto:${sipAccount.email}" title="${sipAccount.email}">${sipAccount.email}[#--?substring(0,sipAccount.email?index_of("@"))--]</a></td></tr>
  <tr class="yellow"><td colspan="2">Linea SIP</td></tr>
  <tr><td>SIP Login</td><td>${sipAccount.login}</td></tr>
  <tr><td>SIP Password</td><td>${sipAccount.password}</td></tr>
  <tr><td>Presence</td><td>${sipAccount.presence!"N/A"}</td></tr>
  <tr><td>Profilo</td><td>${sipAccount.profileName!"N/A"}</td></tr>
  <tr><td>Opzioni</td><td>${sipAccount.profileOptions!"N/A"}</td></tr>
  <tr><td>Contesto</td><td>[#if sipAccount.context??]${sipAccount.context.code}[/#if]</td></tr>
  <tr><td>Callgroup</td><td>${sipAccount.callgroup!"N/A"}</td></tr>
  <tr><td>Pickupgroup</td><td>${sipAccount.pickupgroup!"N/A"}</td></tr>
  <tr><td>Ring Timeout</td><td>${sipAccount.ringTimeout}</td></tr>
  [#if sipAccount.vmEnabled]
  <tr class="yellow"><td colspan="2">Segreteria</td></tr>
  <tr><td>PIN</td><td>[#if sipAccount.vmBox??]${sipAccount.vmBox.pin}[/#if]</td></tr>
  <tr><td>Spedisci email</td><td>${sipAccount.vmSendEmail?string}</td></tr>
  [/#if]
  [#if !sipAccount.vmEnabled]
  <tr class="yellow"><td colspan="2">Segreteria (disabilitata)</td></tr>
  <tr><td>PIN</td><td>[#if sipAccount.vmBox??]${sipAccount.vmBox.pin}[/#if]</td></tr>
  [/#if]
  [#if sipAccount.coc??]
  <tr class="yellow"><td colspan="2">Cambio contesto</td></tr>
  <tr><td id="cocLoginDescription">Login</td><td>${sipAccount.coc.login}</td></tr>
  <tr><td id="cocPinDescription">PIN</td><td>${sipAccount.coc.pin}</td></tr>
  [/#if]
  [#if sipAccount.hasCoverage]
  <tr class="yellow"><td colspan="2">Coverage</td></tr>
  [#list sipAccount.coverageList as cvg]
  <tr><td>${cvg.type} (${cvg.ringTimeout} sec)</td><td>${cvg.toSip.login}</td></tr>
  [/#list]
  [/#if]
 </table>
</div>
[#if sipAccount.phone??]
[#assign phone = sipAccount.phone/]
<div class="spacer"></div>
<div dojoType="dijit.layout.ContentPane" class="detailInner" title="Telefono">
 <table class="detailTable">
  <tr><td>Vendor</td><td>${phone.product.vendor!"N/A"}</select></td></tr>
  <tr><td>Modello</td><td>${phone.product.code!"N/A"}</td></tr>
  <tr><td>MAC address</td><td>${phone.macAddress}</td></tr>
  <tr><td>Cfg locale</td><td>${phone.hasConfig?string}</td></tr>
  <tr><td>Rubrica locale</td><td>${phone.hasDirectory?string}</td></tr>
  <tr><td>Stanza</td><td>${phone.location!"N/A"}</td></tr>
  [#if false]
  <tr><td>Numero seriale</td><td>serialNumber</td></tr>
  [/#if]

  [#if phone.sipAccountList.size() != 0]
  <tr class="yellow"><td colspan="2">Linee SIP</td></tr>
  [#list phone.sipAccountList as ac]
  <tr><td>${ac.login}</td><td>${ac.displayName}</td></tr>
  [/#list]
  [/#if]
 </table>
</div>
<div dojoType="dijit.form.Button" iconClass="ico50" showLabel="true" onclick="contacta.phone.addAccountShow(${phone.id?c})"><span>Aggiungi linea</span></div>
[/#if]
