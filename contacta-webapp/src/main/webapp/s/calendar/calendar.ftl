<!-- $Id: calendar.ftl 660 2010-07-17 23:14:21Z michele.bianchi $ -->
<div class="organicTabContent" dojoType="dijit.layout.BorderContainer" gutters="false" style="">

 <div dojoType="dijit.layout.ContentPane" style="padding:1em" region="top">
  <div dojoType="dijit.form.Form" jsId="ui.confBookForm" style="border:1px dotted #cccccc; padding:1em">
   <div style="padding-bottom:1em; font-weight:normal;">
    <#if organicSession.account??>
     <b>${m.t("label.firstName")}:</b> ${organicSession.account.label!"N/A"}, <b>${m.t("label.email")}:</b> ${organicSession.mail!"N/A"}, <b>${m.t("label.exten")}:</b> ${organicSession.account.login!"N/A"}
    <#else/>
     <b>${m.t("label.email")}:</b> ${organicSession.account.email!"N/A"}
    </#if>
   </div>
   <table>
    <tr>
     <td>${m.t("label.day")} <input id="oc2" type="hidden"/></td>
     <td>
      <!-- value="2009-06-10" constraints="{min:'2009-06-01',max:'2020-12-31',formatLength:'long'}" -->
      <div jsId="ui.confDayInput" dojoType="dijit.form.DateTextBox" name="day" trim="true" required="true"
           constraints="{formatLength:'short'}"
           onChange="dojo.byId('oc2').value=arguments[0]"
           invalidMessage="${m.t("message.date.invalid")}"
           >
       <!--onMouseLeave,onKeyDown, ... script type="dojo/connect" event="onMouseEnter">eventHandler.apply(this, arguments)</script-->
      </div>
     </td>
    </tr>
    <tr><td>${m.t("label.from")}</td><td><input jsId="ui.confBeginInput" dojoType="dijit.form.TimeTextBox" name="begin" value="T14:00" required="true"
                                 constraints="{timePattern:'HH:mm', clickableIncrement:'T00:30:00',visibleRange:'T08:00:00'}"
                                 invalidMessage="${m.t("message.time.invalid")}"/></td></tr>
    <tr><td>${m.t("label.to")}</td><td><input jsId="ui.confEndInput" dojoType="dijit.form.TimeTextBox" name="end" value="T15:00" required="true"
                                constraints="{timePattern:'HH:mm', clickableIncrement:'T00:30:00',visibleRange:'T08:00:00'}"
                                invalidMessage="${m.t("message.time.invalid")}"/></td></tr>
    <#if false>
    <tr>
     <td>${m.t("label.attendees")}</td>
     <td>
      <!-- ??? value="$-setvaluetest-CA" queryExpr="*${0}*" -->
      <input dojoType="dijit.form.ComboBox"
             jsId="ui.attendeeInput"
             store="ui.personStore"
             searchAttr="mail"
             autoComplete="false"
             hasDownArrow="false"
             highlightMatch="all"
             />
      <div dojoType="dijit.form.Button" showLabel="true" onclick="contacta.booking.addAttendee()"><span>${m.t("label.add")}</span></div>
     </td>
    </tr>
    <tr><td></td><td><textarea dojoType="dijit.form.Textarea" jsId="ui.attendeeListInput" name="attendeeList" style="width:40em; min-height:4em;"></textarea></td></tr>
    </#if>
   </table>
   <div dojoType="dijit.form.Button" iconClass="ico24" showLabel="true" onclick="contacta.booking.reset()"><span>${m.t("label.reset")}</span></div>
   <div dojoType="dijit.form.Button" iconClass="ico23" showLabel="true" onclick="contacta.booking.submit()"><span>${m.t("label.book")}</span></div>
  </div>
 </div>

 <div dojoType="dijit.layout.ContentPane" style="padding:0em 1em;" region="center">
  <div jsId="ui.calendarPane" dojoType="dijit.layout.ContentPane" href="${base}/s/booking-calendar.action"
       refreshOnShow="false" preventCache="true" preload="true"
       style="border:1px dotted #cccccc; padding:1em; overflow:scroll;"
       >
   ${m.t("label.booking.detail")}
  </div>
 </div>

 <div dojoType="dijit.layout.ContentPane" style="padding:1em" region="bottom">
  <div dojoType="dijit.form.Button" iconClass="ico53" showLabel="true" onclick="contacta.booking.calendar(0)"><span>${m.t("label.booking.reload")}</span></div>
  <div dojoType="dijit.form.Button" iconClass="ico43" showLabel="true" onclick="contacta.booking.calendar(-1)"><span>${m.t("label.prev")}</span></div>
  <div dojoType="dijit.form.Button" iconClass="ico42" showLabel="true" onclick="contacta.booking.calendar(+1)"><span>${m.t("label.next")}</span></div>
 </div>
</div>

