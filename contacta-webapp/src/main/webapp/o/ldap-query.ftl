<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- $Revision: 532 $  :encoding=UTF-8:-->
<html>
<body>
<form action="ldap-query.action" method="POST">
<input name="firstName" value="${firstName}" size="5"/> <input name="lastName" value="${lastName}" size="5"/> <input type="submit" value="Cerca"/>
</form>
<#if personList??>
 <br/>Trovati ${personList.size()} contatti:<br/>
 <#list personList as p>
  <a href="tel://400">${p}</a><br/>
 </#list>
<#else>
 Non e' stato trovato nessun contatto, riprovare.
</#if>
</body>
</html>

