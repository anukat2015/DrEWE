#!/bin/bash
#sleep 3s
sudo raspistill -o camera.jpg
scp camera.jpg carloscrespog@192.168.1.128:/Volumes/Job/GSI/PFC/workspacePFC/NodeEvented/public/img

