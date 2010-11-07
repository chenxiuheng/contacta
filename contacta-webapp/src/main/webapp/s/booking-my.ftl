<!-- $Id: booking-today.ftl 476 2009-06-10 12:51:35Z michele.bianchi $ -->
<!--
 int id;
 String code;
 SipAccountModel account;
 String mail;
 String attendees;
 String subject;
 String location;
 String description;
 Date startTime;
 Date endTime;
 boolean allDay;
 Repeat repeat;
 Reminder reminder;
 ConferenceModel conference;
 String room;
-->
<style>
</style>
<#assign odd=true/>
<#list appointmentList as a>
 <#assign odd=!odd/>
 <#assign c = a.conference>
 <#if c.code?starts_with("_")>
  <#assign exten = c.code?substring(c.code?index_of("#")+1)/>
 <#else>
  <#assign exten = c.code/>
 </#if>
 <div class="booking booking_${odd?string}">
  ${m.t("short.booking.of", [a.mail!"N/A"])}:
  <ul>
  <li>${m.t("label.phone.number")}: ${exten}</li>
  <li>${m.t("label.pin")}: ${c.pin}</li>
  <li>${m.t("label.date")}: ${a.startTime?date?string}</li>
  <li>${m.t("label.schedule")}: ${a.startTime?time?string} - ${a.endTime?time?string}</li>
  <!--li>Invitati: ${a.attendees}</li-->
  </ul>
  <div>
   <div dojoType="dijit.form.Button" iconClass="ico24" showLabel="true" onclick="contacta.booking.cancel(${a.id?c})"><span>${m.t("label.delete")}</span></div>
   <div dojoType="dijit.form.Button" iconClass="ico23" showLabel="true" onclick="contacta.booking.remail(${a.id?c})"><span>${m.t("label.mail.repeat")}</span></div>
  </div>
 </div>
</#list>

<#if appointmentList.size() == 0>
<div>${m.t("short.booking.none", [contactaSession.account.email])}</div>
</#if>
