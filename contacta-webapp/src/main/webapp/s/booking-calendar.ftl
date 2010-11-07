<!-- $Id: booking-calendar.ftl 579 2009-08-09 13:24:50Z michele.bianchi $ -->
<style>
</style>
<div style="padding-bottom:1em;"><b>${m.t("label.week")} ${weekN},  ${weekStart?date?string}</b></div>
<table cellspacing="0" class="cal">
 <tr>
  <th><div class="cal" style="border:1px dotted #ccc;">Ore</div></th>
  <#list labelDay as d>
   <th><div class="cal">${d}</div></th>
  </#list>
 </tr>

 <#assign i = 8/>
 <#assign odd=true/>
 <#list week as d>
  <tr>
    <#if odd == true>
     <td rowspan="2"><div class="hour">${i}:00</div></td>
    </#if>
   <#list d as h>
    <td>
     <div class="cal">
     <#if h gt 0>
      <div class="cal" style="width:${h}%; background-color:#12c260;"></div>
     </#if>
     </div>
    </td>
   </#list>
  </tr>
  <#assign i = i+0.5/>
  <#assign odd = !odd/>
 </#list>
</table>

