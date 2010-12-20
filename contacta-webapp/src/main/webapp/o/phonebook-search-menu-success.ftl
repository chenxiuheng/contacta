[#ftl]
<ThomsonPhoneMenu>
 [#list menuList as p]
 <MenuItem>
  <Name>${p.name!""}</Name>
  <URL>${p.url!""}</URL>
 </MenuItem>
 [/#list]
</ThomsonPhoneMenu>

