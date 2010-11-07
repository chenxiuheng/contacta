 <#assign c = a.conference>
 <#if c.code?starts_with("_")>
  <#assign exten = c.code?substring(c.code?index_of("#")+1)/>
 <#else>
  <#assign exten = c.code/>
 </#if>
 Prenotazione effettuata da ${a.mail}:

 Numero telefonico: ${exten}
 PIN: ${c.pin}

 Giorno: ${a.startTime?date?string}
 Orario inizio: ${a.startTime?time?string}
 Orario fine: ${a.endTime?time?string}
 Invitati: ${a.attendees}

