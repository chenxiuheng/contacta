<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- $Revision: 659 $  :encoding=UTF-8:-->
<html>
<head>
<title>Login</title>
<style>
input, select { background-color:#dedede; color:#000; border:1px solid #737373; padding:0px; font-size:13px; }
</style>
</head>
<body style="">
<div style="height:150px;"></div>
<div class="bg00" style="width:340px;">
 <div class="organicBg" style="height:28px; padding:1px 4px;">
  <div style="float:right; padding:2px 0px 1px 0px; color:white; font-size:9px;">${m.t("label.version")}</div>
  <div style="float:left; margin-right:80px; width:152px; height:25px; background:transparent url('${base}/r/static/contacta.png') no-repeat top left;"></div>
  <div style="white-space:nowrap; color:red;"></div>
 </div>

 <div class="" style="margin-left:auto; margin-right:auto; width:180px; height:160px;">
  <div style="padding-bottom:30px;">
   <div style="margin-left:auto; margin-right:auto; margin-top:.2em; margin-bottom:.2em; width:8em; font-weight:bold; color:#f89b33;"><#--${m.t("label.login")}--><br/></div>
   <@s.form name="login" method="POST" action="j_spring_security_check">
    <@s.textfield label="Login" labelposition="top" name="j_username" value="" cssStyle="width:160px;"/>
    <@s.password label="Password" labelposition="top" name="j_password" value="" cssStyle="width:160px; margin-bottom:0.2em;"/>
    <@s.checkbox label="Remember Me" labelposition="top" name="_spring_security_remember_me" value="aBoolean" fieldValue="true" cssStyle="width:100px; margin-bottom:2em;"/>
    <@s.submit value="Ok"/>
   </@s.form>
  </div>
 </div>
</div>
</body>
</html>

