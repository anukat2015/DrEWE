#!/bin/bash

# Simple script that execute dni.sh periodically.
# This script is necessary due to the nature of the dni server, that changes its logs 
# in an unpleasant way

while :
do
	echo "Press [CTRL+C] to stop.."
	./dni.sh
	sleep 3
done
