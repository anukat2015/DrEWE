# [Drools-DrEWE](https://github.com/carloscrespog/Drools-DrEWE)


Drools module for DrEWE project. It launches the drools environment, the SPIN module and the GSNToExpert module. It also is in charge of loading both drools and SPIN rules. 

![Drools Arq](https://dl.dropboxusercontent.com/u/25002167/EWE%20repo/DrEWE%20full%20-%20Drools.png)

###GSN To Expert
This module is in charge of retrieving events from GSN and inserting them into the drools rule engine. All the data in GSN is accesible via HTTP, so it is retrieved via GET requests and inserted in the Drools engine via an 'entry point', which is is one of the features of Drools Fusion, the CEP (complex event processing) module of the Drools suite.
###Drools module
Once the events has been inserted into the drools engine, the rules can be triggered at real time. This rules can produce other events, direct actions that are sent to the next module or SPIN events that will be handle by the SPIN module.

###SPIN module
[SPIN](http://spinrdf.org/) is a W3C Member Submission that has become the de-facto industry standard to represent SPARQL rules and constraints on Semantic Web models. SPIN also provides meta-modeling capabilities that allow users to define their own SPARQL functions and query templates.

The SPIN notation is supported by the [EWE ontology](http://www.gsi.dit.upm.es/ontologies/ewe/), which is used by DrEWE to represent events, actions and rules. 

So it SPIN itself represents an inference engine that may be used as a rule engine. For this purpose, we put a periodic inferencing task and check for the inferenced triples. A SPIN rule in SPARQL example:

	CONSTRUCT{
		ewe:Action_1 dcterms:title "bot" .
		ewe:Action_1 dcterms:description ?description .
	}
	WHERE {
		?event dcterms:title "calendar" .
		?event dcterms:description ?description .
	}

When handled by our SPIN module, this will execute a bot action (send something to the bot) when a calendar event is inserted (i.e.: meeting started)

##Installation

This project can be installed as a tipical eclipse project, but there is some points that require special attention: Drools environment and SPIN API

###Drools
Firstly, you need to set up the drools environment in your machine. A detailed set of instructions can be found [here](http://docs.jboss.org/drools/release/5.2.0.Final/droolsjbpm-introduction-docs/html/ch03.html)

###SPIN API

The SPIN api by [Topbraid](http://topbraid.org/spin/api/) can be easily spicified and integrated as a Maven Dependency. So if your eclipse has the m2e plugin, you just have to mark this project as a 'maven project' in order to retrieve the dependencies at the pom.xml file.

##Execution

Under the 'es.upm.dit.gsi.DrEwe.Main' packet we can find two Init classes.

- DroolsInit.java: will launch this module that needs GSN to be running at the default but configurable port
- DroolsInitTest.java: will launch the module without the need of GSN to be running. Furthermore, I've developed a suite of test for the drools' fusion rules that comes in form of a sequence of insertion of events and mangement of the drools' pseudoclock

##Test

We've developed a suite of JUnit tests that checks the correct performance of the SPIN module. They can be found under the src/main/test folder.

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