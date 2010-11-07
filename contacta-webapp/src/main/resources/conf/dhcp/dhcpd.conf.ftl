# $Id: dhcpd.conf.ftl 616 2010-04-03 21:07:58Z michele.bianchi $

authoritative;
ddns-update-style none;
log-facility local7;
default-lease-time 600;
max-lease-time 7200;

# option definitions common to all supported networks...
option domain-name "${contacta.dhcpd.domainNameServers}";
option domain-name-servers ${contacta.dhcpd.domainName};


#######################################################################
# subnet
#######################################################################
subnet ${contacta.dhcpd.subnet} netmask ${contacta.dhcpd.netmask}
{
  range ${contacta.dhcpd.range};

  option domain-name "${contacta.dhcpd.domainName}";
  option domain-name-servers ${contacta.dhcpd.domainNameServers};
  option ntp-servers ${contacta.dhcpd.ntpServers};
  option routers ${contacta.dhcpd.routers};
  option tftp-server-name "${tftpServerName}";
  option time-offset ${contacta.dhcpd.timeOffset}; # TZ=+1
}

#######################################################################
# phone list
#######################################################################
<#list phoneList as ph>
host phone-${ph.id}
{
  hardware ethernet ${ph.macAddress};
  <#if ph.ipAddress??>fixed-address ${ph.ipAddress};</#if>
  option tftp-server-name "${tftpServerName}/${ph.macAddress}";
}
</#list>
