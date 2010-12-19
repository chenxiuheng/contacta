[#ftl]
<ThomsonPhoneBook>
 [#list personList as p]
 <DirectoryEntry>
  <Name>${p.displayName}</Name>
  <Telephone>${p.phone!"n/a"}</Telephone>
 </DirectoryEntry>
 [/#list]
</ThomsonPhoneBook>

