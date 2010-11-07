<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- $Revision: 584 $  :encoding=UTF-8:-->
<html>
<head>
<title>Channels</title>
<style>
input, select { background-color:#dedede; color:#000; border:1px solid #737373; padding:0px; font-size:13px; }
</style>
</head>
<body style="contacta">
<style>
.mic table.debug { border-collapse:collapse; width:100%; }
.mic .debug tr.busy td { background:#FFC; color:red; }
.mic .debug th { padding:2px; text-align:center; background-color:white; border:1px solid black; }
.mic .debug td { border:1px solid #cccccc; padding:2px; background-color:#eeeeee;  text-align:center; }
.mic .debug td:first-child { padding-left:0.5em; padding-right:0.5em; background-color:#dddddd; text-align:right; }
</style>
<div class="bg00" style="">
<div class="mic" style="">
 <div style="">
  <div style="margin:1em; font-weight:bold; color:navy;">Stato linee telefoniche <#--a href="" style="color:red;">(ricarica)</a--></div>
  <div>
   <table class="debug">
    <tr>
     <th>Linea</th>
     <th>Stato</th>
     <th>Durata</th>
     <th>Chiamante</th>
     <th>Chiamato</th>
    </tr>
    <#list lineList as l>
     <#if l.call??>
      <#assign f = l.call.from/>
      <#assign t = l.call.to/>
      <tr class="busy">
       <td>${l.callerId}</td><td>${f.state}</td><td>${f.duration}</td>
       <td>${f.callerId}</td><td>${t.callerId}</td>
      </tr>
     <#else>
      <tr>
       <td>${l.callerId}</td><td>Libero</td><td colspan="3"></td>
      </tr>
     </#if>
    </#list>
   </table>
  </div>
  <#--a href="" style="color:red;">(ricarica)</a--></div>
  <#if false>
  <div>
   <table>
    <tr>
     <th>Chiamante</th>
     <th>Chiamato</th>
     <th>Stato</th>
     <th>Durata</th>
     <th>context</th>
     <th>application</th>
     <th>data</th>
     <th>bridged</th>
    </tr>
    <#list callList as call>
     <#assign f = call.from/>
     <#assign t = call.to/>
     <tr>
      <td>${f.callerId}</td><td>${t.callerId}</td><td>${f.state}</td><td>${f.duration}</td>
      <td>${f.context}</td><td>${f.application}</td><td>${f.data}</td><td>${f.bridged}</td>
     </tr>
    </#list>
   </table>
  </div>
  </#if>
 </div>
</div>
</div>
</body>
</html>

