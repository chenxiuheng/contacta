[#ftl]
<ThomsonPhoneMenu>
 [#list menuList as p]
 <MenuItem>
  <Name>${p.displayName}</Name>
  <URL>${p.phone!"n/a"}</URL>
 </MenuItem>
 [/#list]
</ThomsonPhoneMenu>

