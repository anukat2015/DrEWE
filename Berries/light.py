#!/usr/local/bin/python

# This Python script sends the light level to a GSN server.
# The data acquisition is made via a RC circuit attached to a given pin,
# because of the lack of analog inputs in the raspberry, this script sets 
# a given entry as low and counts the loop's cycles that it spends discharging.

import RPi.GPIO as GPIO, time
import urllib2
import urllib
import datetime
# Tell the GPIO library to use
# Broadcom GPIO references
GPIO.setmode(GPIO.BCM)

# Define function to measure charge time
def RCtime (PiPin):
  measurement = 0
  # Discharge capacitor
  GPIO.setup(PiPin, GPIO.OUT)
  GPIO.output(PiPin, GPIO.LOW)
  time.sleep(0.1)

  GPIO.setup(PiPin, GPIO.IN)
  # Count loops until voltage across
  # capacitor reads high on GPIO
  while (GPIO.input(PiPin) == GPIO.HIGH):
	time.sleep(0.1)
  while (GPIO.input(PiPin) == GPIO.LOW):
    measurement += 1

  return measurement

# Main program loop
while True:
  # gsn server 
  gsnserver='http://192.168.1.115:22001/streaming'
  print RCtime(17) # Measure timing using GPIO17
  stream=''
  stream+='<stream-element timestamp=\''
  now=datetime.datetime.now()
  dateNow=now.date()
  y = dateNow.year
  m = dateNow.month
  d = dateNow.day
  timeNow=now.time()
  H = timeNow.hour
  M = timeNow.minute
  S = timeNow.second
  stream+=str(y)
  stream+='-'
  stream+=str(m)
  stream+='-'
  stream+=str(d)
  stream+=' '
  stream+=str(H)
  stream+=':'
  stream+=str(M)
  stream+=':'
  stream+=str(S)
  stream+=' UTC\'>'
  stream+='<field name=\'value\' type=\'numeric\'>'
  stream+= str(RCtime(17))
  stream+='</field></stream-element>'
  print stream
  params = {}
  opener = urllib2.build_opener(urllib2.HTTPHandler)

  params['notification-id']=1.2
  params['data'] = stream
  params = urllib.urlencode(params) 
  request = urllib2.Request(gsnserver, data=params)
  request.add_header('Content-Type','application/x-www-form-urlencoded')
  opener = urllib2.build_opener(urllib2.HTTPHandler)
  request.get_method = lambda: 'PUT'
  url = opener.open(request)

  time.sleep(1)
  
