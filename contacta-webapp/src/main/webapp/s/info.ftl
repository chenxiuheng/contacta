<style>
.mic table.debug { border-collapse:collapse; margin:1em;  width:600px; }
.mic .debug tr.yellow th { border:1px solid #FB7A31; background:#FFC; }
.mic .debug th { padding:2px; text-align:center; background-color:red; }
.mic .debug td { border:1px solid #bbbbbb; padding:1px; background-color:#eeeeee; }
.mic .debug td:first-child { padding-left:0.5em; padding-right:0.5em; background-color:#dddddd; text-align:right; }
</style>
<table class="debug">
 <thead>
  <tr class="yellow"><th colspan="2">${m.t("title.status.jvm")}</th></tr>
 </thead>
 <tbody>
  <tr><td>OS</td><td>${systemProperties["os.name"]} ${systemProperties["os.arch"]} ${systemProperties["os.version"]}</td></tr>
  <tr><td>JVM</td><td>${systemProperties["java.vm.vendor"]} ${systemProperties["java.vm.name"]} ${systemProperties["java.vm.version"]}</td></tr>
  <tr><td>Runtime</td><td> ${systemProperties["java.runtime.name"]} ${systemProperties["java.runtime.version"]} (${systemProperties["java.home"]})</td></tr>
  <tr><td>Encoding</td><td>${systemProperties["file.encoding"]}</td></tr>
  <tr><td>Processors</td><td>${runtime.availableProcessors()}</td></tr>
  <tr><td>Memory</td><td>${runtime.freeMemory()/1048576}mb/${runtime.totalMemory()/1048576}mb (free/total)</td></tr>
  <tr><td>User</td><td>${systemProperties["user.name"]} ${systemProperties["user.language"]}_${systemProperties["user.country"]} ${systemProperties["user.home"]}</td></tr>
  <tr><td>Dir</td><td>cd:${systemProperties["user.dir"]} - tmp:${systemProperties["java.io.tmpdir"]}</td></tr>
 </tbody>
</table>

<table class="debug">
 <thead>
  <tr class="yellow"><th colspan="2">${m.t("title.configuration")}</th></tr>
 </thead>
 <tbody>
 <tr><td>version</td><td>${configuration.version}</td></tr>
 <tr><td>revision</td><td>${configuration.revision}</td></tr>
 <tr><td>proxyHost</td><td>${configuration.proxyHost!"n/a"}</td></tr>
 <tr><td>proxyPort</td><td><#if configuration.proxyPort??>${configuration.proxyPort?c}<#else>n/a</#if></td></tr>
 <tr><td>managerHost</td><td>${configuration.managerHost!"n/a"}</td></tr>
</tbody>
</table>

