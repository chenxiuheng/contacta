[#ftl]
<ThomsonPhoneBook>
 [#list personList as p]
 <DirectoryEntry>
  <Name>${p.displayName}</Name>
  <Telephone>${p.phone!"n/a"}</Telephone>
 </DirectoryEntry>
 [/#list]
 [#--
 <MenuItem>
  <Name>Arrh Foulard -> ZZZ</Name>
  <URL>http://www.server.com/get32results_7.php</URL>
 </MenuItem>
 --]
</ThomsonPhoneBook>

