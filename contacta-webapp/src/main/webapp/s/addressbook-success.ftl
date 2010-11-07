<!-- $Revision: 666 $  :encoding=UTF-8:-->
<div dojoType="dijit.TitlePane" title="Contatto">
<style>
table.addressbook { width:100%; }
table.addressbook th { border:1px dotted navy; padding:1px 8px; font-weight:bold; background-color:#FAF0E6; }
table.addressbook td { border-bottom:1px dotted #aaaaaa; padding:2px 10px;; }
</style>

<table class="addressbook">
 <thead>
  <tr>
   <th>Nome completo</th>
   <th>Interno</th>
   <th>Telefono</th>
   <th>Email</th>
  </tr>
 </thead>
 <tbody>
  <#list personList as l>
   <tr class="busy">
    <td>${l.fullName}</td>
    <td><#if l.extension??><a class="" onclick="contacta.dial('${l.extension}')">${l.extension}<#else>N/A</#if></td>
    <td><#if l.phone??><a class="" onclick="contacta.dial('${l.phone}')">${l.phone}<#else>N/A</#if></td>
    <td>${l.email!"N/A"}</td>
   </tr>
  </#list>
 </tbody>
</table>
</div>

