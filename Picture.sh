#!/bin/bash

# Simple script that takes a phto using the raspberry
# camera board and send it to a given machine, currently not used.
# We've installed motion in order to make things easier and take advantages
# of its features

sudo raspistill -o camera.jpg

scp camera.jpg carloscrespog@192.168.1.128:/Volumes/Job/GSI/PFC/workspacePFC/NodeEvented/public/img

