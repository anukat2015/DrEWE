#[GSN-DrEWE](https://github.com/carloscrespog/GSN-DrEWE)


DrEWE files related to GSN. In this repo we only provide the virual sensor's files that we use in the project [DrEWE](https://github.com/carloscrespog/DrEWE)
##Implemented virtual sensors

###Calendar Virtual Sensor

Fields:

	- title: calendar event title
	- attendees: variable number of attendees separated by a #
	- start: start date


###Dni Virtual Sensor
Fields:

	- number: dni number of the person that enters the laboratory
	- name: dni display name of the person that enters the laboratory


###Remote Light Virtual Sensor
Fields:

	- value: room's light level

Note: each of these virtual sensor has a "predicate key" (immutable value) `eventType` that is used by the Drools module to create different java objects depending on that variable.

##How it works

###Direct Remote Push
This three virtual sensors use the [Direct Remote Push Wrapper](http://sourceforge.net/apps/trac/gsn/wiki/RemoteDirectPush). This wrapper passively listen for data pushed from the remote sensor. Typically it can be used to retrieve data from devices that are not always connected or that may change their IP address often. Once deployed, we can push data to GSN via HTTP, depending on the notification-id param:

```
	PUT http://localhost:22001/streaming?notification-id=* & data=$GSN_STREAM
	{'Content-Type','application/x-www-form-urlencoded'}
```
The GSN stream in the `data` field must be exactly as the virtual sensor specification. 

The `notification-id` field is in charge of decide which virtual sensor is receiving the data and is configurable via xml (one notification-id per virtual sensor that uses remote push wrapper)

For example, the application [GCalendar-DrEWE](https://github.com/carloscrespog/GCalendar-DrEWE) should send data in this way:
```
	GSN_STREAM:
		<stream-element timestamp="2013-9-9 0:38:49 CEST">
			<field name="title" type="string">Meeting</field>
			<field name="attendees" type="string">attendee@gmail.com#otherattendee@gmail.com</field>
			<field name="start" type="string">2013-09-02 17:30:00+02:00</field>
		</stream-element>

	PUT http://localhost:22001/streaming?notification-id=1.4 & data=$GSN_STREAM
	{'Content-Type','application/x-www-form-urlencoded'}
```
###Usage
Once deployed, GSN data is accesible from any point of the network. By default, GSN will be deployed at port 22001 so, for example, to access data from the calendar virtual sensor:
```
	GET http://localhost:22001/gsn?REQUEST=114&name=calendarvs&window=40
```
- `REQUEST` param is for the type of request, 114 type returns a stream of GSN data limited by the window param
- `name` param is for the virtual sensor, and it is defined inside each virtual sensor file

	Summary:

	- We push data to GSN depending on the `notification-id` param in a PUT request
	- We retrieve data from GSN depending on the `name` param ina GET request

##Virtual sensors

The key abstraction in GSN is the virtual sensor. Virtual sensors abstract from the implemen- tation details of the data aource to sensor data and correspond either to a data stream received directly from sensors or to a data stream derived from other virtual sensors. A virtual sensor can be any kind of data producer, for example, a real sensor, a wireless camera, a desktop com- puter, or any combination of virtual sensors. 

A virtual sensor may have any number of input data streams and produces exactly one output data stream (with predefined format) based on the input data streams and arbitrary local processing. The specification of a virtual sensor provides all necessary information required for deploying and using it, including:

- metadata used for identification and discovery
- the details of the data streams which the virtual sensor consumes and produces 
- an SQL-based specification of the stream processing (filtering and integration) performed in a virtual sensor, 
- the processing class which performs the more advanced and complex data processing (if needed) on the output stream before releasing it 
- functional properties related to persistency, error handling, life-cycle, management, and physical deployment.


##Installing to Run and debug GSN in Eclipse

######Here they are the installing steps to get GSN up and running. You can find a more detailed documentation [here](http://sourceforge.net/apps/trac/gsn/wiki/install-gsn)

- Download and install Eclipse SDK.
- Start Eclipse.
- Download and install the Subclipse (http://subclipse.tigris.org/install.html)
- File -> Import -> Other -> Checkout Projects from SVN.
- Check “Create a new repository location”.
- Paste the repository location http://gsn.svn.sourceforge.net/svnroot/gsn
- Select "trunk" and click "Next".
- Select "Check out project configured using the New Projects Wizard" and click "Finish".
- In the New Projects Wizard select "Java Project" and click "Next".
- In the New Java Projects Wizard.
- Enter the project name, select "Create new project in workspace".
- Select "Create separate folders . . . " and click on "Configure default".
- If the project doesn’t contains a "src" directory (depends on the Eclipse version you are using), assure to create one by clicking on the "Create new s ource folder".
- In Build Path preferences select "Folders", enter "build/classes" as the output folder name and click "OK".
- Click "Finish".
- Click "Ok" to confirm overwrite of non standard resources.
- Wait for the files to be downloaded from the repository.

- To add library files to the build path.
	- Project -> Properties.
	- In the Properties dialog select "Java Build Path".
	- In the Java Build Path dialog, select the "Libraries" tab.
	- On the Libraries tab, click on "Add jars . . . ".
	- Add only the .jar files in the lib directory and its sub-folders ; do not add any LICENSE text files
- check whether there are any build errors, which prevent the java compiler from building GSN
	- note: some errors are safe to ignore, but need compiler configuration changes (namely "Access restriction" errors)
		- in the main menu select "Window" > "Preferences". a configuration popup should appear
		- in the menu of the popup select "Java -> Compiler -> Errors/Warnings". you should see a list of possible warning/error levels for the java compiler
		- in the list go to "Deprecated and restricted API -> Forbidden reference (access rules)" and change it to "Warning"

`GSN is now ready to Run.

####Configuring eclipse to run/debug GSN through Eclipse

#####Step 1: Setting the Ant Home:

- Download the Ant binaries (apache-ant-1.7.x-bin.zip) from http://ant.apache.org/
- Extract the folder apache-ant-1.7.x to a suitable location on your hard drive
- Open Eclipse and do the following steps to set the Ant Home
- Go to Window Menu and select Preferences, on the left side, click on ANT and then select Runtime.
- In the Classpath tab (opened on the center of the window) select Ant Home Entries, click on the Ant Home button and browse toward the directory that contains the files from the jakarta-ant archive (\apache-ant-1.7.x\bin).
- Click on OK in the Browse window and again to exit the Preferences dialog.

#####Step 2: Setting an Ant Build System for your GSN project.

- Select the GSN project and on the Project menu click on properties tab.
- On the Properties sheet for the project, select Builders on the left side and click on "New" and select "Ant Builder".
- A "Builder Properties for . . . " window pops up which has several tabs.
- Main Tab.
	- For build file, click on Browse Workspace and select the build.xml file.
	- For base directory, click on the Browse Workspace and click on the project name which contains the gsn source code.
	- Leave the "Set an Input Handler" selected.
- Targets Tab.
	- For After a Clean, click on Set Targets and select both Build and Bind.
	- Click on Ok.
	- Back in the Builders page.
	- Select the new Ant Builder.
	- De-select the existing Java Builder and click OK in the confirmation panel which will appear.
	- Move the new Ant Builder to the top of the list.
	- Click on OK.

#####Trying it out

- Build the project.
- Set the gsn-controller-port parameter:
	- Open build.xml, locate the gsn-controller-port value and copy it to the clipboard.
	- Open the Run dialog (Run -> Open Run Dialog . . . ).
	- In the target Run Configuration (eg. Main)
		- Go to the "Arguments" tab
		- Paste or type the gsn-controller-port value from build.xml
		- Click "Close"
	- Click on the Run button on the toolbar
	- The GNS application should display "GSN Starting . . . " in the console
	- Open a web browser and browse to http://localhost:22001 and verify that the GSN server is working.
	- Stop the running GSN using the ant Stop task
	- Insert a breakpoint in the first line of the Main class
	- Start the application from the "Debug" button on the Eclipse toolbar
	- GSN should start in the Eclipse Debug perspective and pause at the breakpoint

Now you can debug your virtual sensors in eclipse, try it by setting a break point on a line in the main file.

###Direct remote push error fix

- After installing a fresh and clean version of GSN, any virtual sensor that uses the remote direct push wrapper won't work. Nevertheless, this problem has a quick solution: 

	- Go to GSN project classpath at Project -> Properties -> Java Build Path 
	- Look for 'xstream-1.3.1'
	- Select it and click on remove
	- Download the latest xstream jar at its [homepage](http://xstream.codehaus.org/) 
	- Copy this jar under the lib/core/ folder at your GSN project
	- Right-click on it and select Build Path -> add to build path
	- Re-build the project


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