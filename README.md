# [Berries-DrEWE](https://github.com/carloscrespog/Berries-DrEWE)


Raspberry's scripts and modules that communicate with GSN and/or SPIN to produce events, make actions and handle requests.
![Berries](https://dl.dropboxusercontent.com/u/25002167/EWE%20repo/DrEWE%20full%20-%20Berries.png)
## Retrieving the dni log

The script in charge of this task is dni.sh. This script checks the last person that has inserted its dni at the laboratory door,
sends the info to the GSN server at the given url and echoes the info via terminal.

The raspberry pi and the dni machine must be known ssh hosts of each other. In order to make this task iterative we execute it by dniLoop.sh. This script is necessary due to the nature of the dni server, that changes its logs in an unpleasant way.

### Execution

```bash
./dniLoop.sh
```

## Retrieving the light level

This Python script sends the light level to a GSN server. The data acquisition is made via a RC circuit attached to a given pin, because of the lack of analog inputs in the raspberry, this script sets a given entry as low and counts the loop's cycles that it spends discharging. 

###The circuit

The trick is to time how long it takes a point in the circuit the reach a voltage that is great enough to register as a “High” on a GPIO pin.

![Rasp circuit](http://www.raspberrypi-spy.co.uk/wp-content/uploads/2012/08/Light-Sensor.png)

A detailed explanation can be found [here.](http://www.raspberrypi-spy.co.uk/2012/08/reading-analogue-sensors-with-one-gpio-pin/)

###Execution
```bash
sudo ./python light.py &
```

## Motion

In order to handle the camera we use a modified version of the [motion packet](http://www.lavrsen.dk/foswiki/bin/view/Motion/WebHome) that can be found here: [motion-mmal](https://github.com/dozencrows/motion/tree/mmal-test). This packet automatically sets up a
http server that controls the camera and several other features.

###Installation

```bash
$ sudo apt-get install -y libjpeg62 libjpeg62-dev libavformat53 libavformat-dev libavcodec53 libavcodec-dev libavutil51 libavutil-dev libc6-dev zlib1g-dev libmysqlclient18 libmysqlclient-dev libpq5 libpq-dev
$ wget https://www.dropbox.com/s/xdfcxm5hu71s97d/motion-mmal.tar.gz
$ tar zxvf motion-mmal.tar.gz
```
###Configuration

Replace motion-mmalcam.conf with the one given at this repository. With the given configuration, it will deploy an http server at port 8080 that allows us to control the camera remotely, will take snapshots periodically and place them in the imageServer.py directory, in order to serve the latest snapshot via http (accesible from any point of the network).

###Control

Motion provides several methods to control the camera, all of them documented at the [Motion http API](http://www.lavrsen.dk/foswiki/bin/view/Motion/MotionHttpAPI)

For example, if you want to force a raspberry snapshot from any other machine in the network (assume that raspberry's ip is 192.168.1.132)

```
GET 192.168.1.132:8080/0/action/snapshot
```

###Execution
```bash
$ sudo ./motion -n -c motion-mmalcam.conf &
```


## Image Server

Motion provides several amazing features to control the camera, one of them is the video straming. However we only needed motion to take pictures, nothing to do with video. 

imageServer.py is a simple script that takes the last picture taken by motion and serves it to the world (in our case, just to the network) via http, creating a http server with python module BaseHTTPServer.

###Execution
```
python imageServer.py &
```
###Usage
How to get the last picture (by default, port is 8088 and we assume the raspberry's ip is the same as above)
```
GET http://192.168.1.132:8080/
```
## Another way to send pictures

Before we took the decision of using motion, we simplified it: take and picture and send it via ssh protocol. This is the reason why Picture.sh is still in this repo, in order to have a secondary way to handle the camera action.

###Execution

```bash
./Picture.sh
```



## License

```
Copyright 2013 UPM-GSI: Group of Intelligent Systems - Universidad Politécnica de Madrid (UPM)

Licensed under the Apache License, Version 2.0 (the "License"); 
You may not use this file except in compliance with the License. 
You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by 
applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific 
language governing permissions and limitations under the License.
```
![GSI Logo](http://gsi.dit.upm.es/templates/jgsi/images/logo.png)

This project has been developed as the master thesis of [Carlos Crespo](https://github.com/carloscrespog) under the tutelage of [Miguel Coronado](https://github.com/miguelcb84) and the supervision of [Carlos A. Iglesias](https://github.com/cif2cif) at [gsi-upm](https://github.com/gsi-upm)

























