<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- $Revision: 597 $  :encoding=UTF-8:-->
<html>
<head>
<title>Login</title>
<style>
input, select { background-color:#dedede; color:#000; border:1px solid #737373; padding:0px; font-size:13px; }
</style>
</head>
<body style="">
<!--  -->
<div class="bg00">
 <div class="ico00" style="margin-left:2px; margin-bottom:2em;"><!--</div>--><div style="margin-left:220px; margin-bottom:2em;">
 <div style="margin-left:auto; margin-right:auto; margin-top:0.2em; margin-bottom:.2em; width:8em; font-weight:bold; color:#8a8f9c;">Contacta login</div>
 <@s.form name="login" method="GET" action="j_spring_security_check">
  <@s.textfield label="Interno" labelposition="top" name="j_username" value="" cssStyle="width:160px;"/>
  <@s.textfield name="j_password" value="password" cssStyle="display:none; width:160px; margin-bottom:0.2em;"/>
  <@s.submit value="Entra"/>
 </@s.form>
</div>
</div>
</body>
</html>

