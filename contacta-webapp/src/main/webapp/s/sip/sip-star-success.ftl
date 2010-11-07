<style>
.mic table.debug { border-collapse:collapse width:100% }
.mic .debug tr.yellow td { border:1px solid #FB7A31 background:#FFC text-align:left font-weight:bold }
.mic .debug th { padding:2px text-align:center background-color:red }
.mic .debug td { border:1px solid #cccccc padding:1px background-color:#eeeeee }
.mic .debug td:first-child { padding-left:0.5em padding-right:0.5em background-color:#dddddd text-align:right }
</style>
<div class="mic" style="width:720px;height:100%;overflow:scroll;">
<#assign s = sipAccount.sipUser/>
<#assign v = sipAccount.vmBox/>
<div style="float:left;">
<table class="debug" style="width:340px; margin:5px;">
<tr class="yellow"><td colspan="2">Utente SIP</td></tr>
<tr><td>id</td><td>${s.id?c}</td></tr>
<tr><td>name</td><td>${s.name!"N/A"}</td></tr>
<tr><td>host</td><td>${s.host!"N/A"}</td></tr>
<tr><td>nat</td><td>${s.nat!"N/A"}</td></tr>
<tr><td>type</td><td>${s.type!"N/A"}</td></tr>
<tr><td>accountcode</td><td>${s.accountcode!"N/A"}</td></tr>
<tr><td>amaflags</td><td>${s.amaflags!"N/A"}</td></tr>
<tr><td>callgroup</td><td>${s.callgroup!"N/A"}</td></tr>
<tr><td>callerid</td><td>${s.callerid!"N/A"}</td></tr>
<tr><td>cancallforward</td><td>${s.cancallforward!"N/A"}</td></tr>
<tr><td>canreinvite</td><td>${s.canreinvite!"N/A"}</td></tr>
<tr><td>context</td><td>${s.context!"N/A"}</td></tr>
<tr><td>defaultip</td><td>${s.defaultip!"N/A"}</td></tr>
<tr><td>dtmfmode</td><td>${s.dtmfmode!"N/A"}</td></tr>
<tr><td>fromuser</td><td>${s.fromuser!"N/A"}</td></tr>
<tr><td>fromdomain</td><td>${s.fromdomain!"N/A"}</td></tr>
<tr><td>insecure</td><td>${s.insecure!"N/A"}</td></tr>
<tr><td>language</td><td>${s.language!"N/A"}</td></tr>
<tr><td>mailbox</td><td>${s.mailbox!"N/A"}</td></tr>
<tr><td>md5secret</td><td>${s.md5secret!"N/A"}</td></tr>
<tr><td>deny</td><td>${s.deny!"N/A"}</td></tr>
<tr><td>permit</td><td>${s.permit!"N/A"}</td></tr>
<tr><td>mask</td><td>${s.mask!"N/A"}</td></tr>
<tr><td>musiconhold</td><td>${s.musiconhold!"N/A"}</td></tr>
<tr><td>pickupgroup</td><td>${s.pickupgroup!"N/A"}</td></tr>
<tr><td>qualify</td><td>${s.qualify!"N/A"}</td></tr>
<tr><td>regexten</td><td>${s.regexten!"N/A"}</td></tr>
<tr><td>restrictcid</td><td>${s.restrictcid!"N/A"}</td></tr>
<tr><td>rtptimeout</td><td>${s.rtptimeout!"N/A"}</td></tr>
<tr><td>rtpholdtimeout</td><td>${s.rtpholdtimeout!"N/A"}</td></tr>
<tr><td>secret</td><td>${s.secret!"N/A"}</td></tr>
<tr><td>setvar</td><td>${s.setvar!"N/A"}</td></tr>
<tr><td>disallow</td><td>${s.disallow!"N/A"}</td></tr>
<tr><td>allow</td><td>${s.allow!"N/A"}</td></tr>
<tr><td>fullcontact</td><td>${s.fullcontact!"N/A"}</td></tr>
<tr><td>ipaddr</td><td>${s.ipaddr!"N/A"}</td></tr>
<tr><td>port</td><td>${s.port!"N/A"}</td></tr>
<tr><td>regserver</td><td>${s.regserver!"N/A"}</td></tr>
<tr><td>regseconds</td><td>${s.regseconds!"N/A"}</td></tr>
<tr><td>username</td><td>${s.username!"N/A"}</td></tr>
<tr><td>callLimit</td><td>${s.callLimit!"N/A"}</td></tr>
</table>
</div>
<div style="float:left;">
<table class="debug" style="width:340px; margin:5px;">
<tr class="yellow"><td colspan="2">Voicemail</td></tr>
<tr><td>id</td><td>${v.id?c}</td></tr>
<tr><td>context</td><td>${v.context!"N/A"}</td></tr>
<tr><td>mailbox</td><td>${v.mailbox!"N/A"}</td></tr>
<tr><td>pin</td><td>${v.pin!"N/A"}</td></tr>
<tr><td>customer_id</td><td>${v.customer_id!"N/A"}</td></tr>
<tr><td>email</td><td>${v.email!"N/A"}</td></tr>
<tr><td>pager</td><td>${v.pager!"N/A"}</td></tr>
<tr><td>tz</td><td>${v.tz!"N/A"}</td></tr>
<tr><td>attach</td><td>${v.attach!"N/A"}</td></tr>
<tr><td>saycid</td><td>${v.saycid!"N/A"}</td></tr>
<tr><td>dialout</td><td>${v.dialout!"N/A"}</td></tr>
<tr><td>callback</td><td>${v.callback!"N/A"}</td></tr>
<tr><td>review</td><td>${v.review!"N/A"}</td></tr>
<tr><td>operator</td><td>${v.operator!"N/A"}</td></tr>
<tr><td>envelope</td><td>${v.envelope!"N/A"}</td></tr>
<tr><td>sayduration</td><td>${v.sayduration!"N/A"}</td></tr>
<tr><td>saydurationm</td><td>${v.saydurationm!"N/A"}</td></tr>
<tr><td>sendvoicemail</td><td>${v.sendvoicemail!"N/A"}</td></tr>
<tr><td>nextaftercmd</td><td>${v.nextaftercmd!"N/A"}</td></tr>
<tr><td>forcename</td><td>${v.forcename!"N/A"}</td></tr>
<tr><td>forcegreetings</td><td>${v.forcegreetings!"N/A"}</td></tr>
<tr><td>hidefromdir</td><td>${v.hidefromdir!"N/A"}</td></tr>
<tr><td>stamp</td><td>${v.stamp!"N/A"}</td></tr>
<tr><td>delete</td><td>${v.delete!"N/A"}</td></tr>
<tr><td>fullname</td><td>${v.fullname!"N/A"}</td></tr>
</table>
</div>
</div>

