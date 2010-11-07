
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- $Revision: 579 $ -->
<!-- :encoding=UTF-8: -->
<html>
<head>
 <title>${m.t("title.error")}</title>
 <style>
 </style>
 <script type="text/javascript">
 </script>
</head>
<body class="contacta">
<div>
 <div class="ico00" style="float:right;"></div>
 <div style="clear:both;"></div>
 <div>
  ${m.t("short.error")}:<br/>
  <#if errorList??><#list errorList as msg><br/>${msg}</#list></#if>
 </div>
</div>
</body>
</html>

