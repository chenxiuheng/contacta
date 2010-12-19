<!-- $Id: conference.ftl 607 2010-03-08 21:55:42Z michele.bianchi $ -->
<div class="organicTabContent" dojoType="dijit.layout.BorderContainer" gutters="false" style="">

 <div dojoType="dijit.layout.ContentPane" style="padding:1em" region="top">
  <div dojoType="dijit.form.Button" iconClass="ico53" showLabel="true" onclick="contacta.booking.my()"><span>${m.t("label.booking.reload")}</span></div>
 </div>

 <div dojoType="dijit.layout.ContentPane" style="padding:0em 1em;" region="center">
  <div jsId="ui.bookingSchedule" dojoType="dijit.layout.ContentPane" href="${base}/s/booking-my.action"
       refreshOnShow="false" preventCache="true" preload="true"
       style="border:1px dotted #cccccc; padding:1em; overflow:scroll;"
       >
   ${m.t("label.booking.detail")}
  </div>
 </div>
</div>

