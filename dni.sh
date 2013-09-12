#!/bin/bash

# This script checks the last person that has inserted its dni at the laboratory door,
# send the info to the GSN server at the given url and echoes the info via terminal.
# The raspberry pi and the dni machine must be known ssh hosts of each other.


var=$(ssh bifrost.gsi.dit.upm.es tail -n 2 /opt/doorcontrol/log/door.log)
IFS=' ' read -a array <<< "$var"
#array=$(echo $var | tr " " "\n")
stream="<stream-element timestamp='"
stream="$stream${array[1]}"
IFS=',' read -a subarray <<< "${array[2]}"
stream="$stream ${subarray[0]}"
stream="$stream CEST'>"
stream="$stream <field name='number' type='numeric'>"
dniNumber=${array[${#array[@]}-1]}
stream="$stream$dniNumber"
stream="$stream</field><field name='name' type='string'>"
stream="$stream${array[6]} ${array[7]}"
stream="$stream</field></stream-element>"
echo "$stream">xmlDni.xml

curl -v -s -X PUT -d "notification-id=1.3" --data-urlencode data@xmlDni.xml http://192.168.1.115:22001/streaming/


#echo $var
echo $stream

