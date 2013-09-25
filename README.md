[DrEWE](https://github.com/carloscrespog/DrEWE)
=====
![DrEWE Logo](https://dl.dropboxusercontent.com/u/25002167/DrEWE.png)
An intelligent platform for task automation

## Introduction



DrEWE is a task automation platform. It works in a similar way to [IFTTT](http://www.ifttt.com), [Zapier](http://www.zapier.com) or [CouldWork](http://www.couldwork.com) but with a main big difference, the semantic approach. It uses a new ontology or standardized schema for managing the evented web named [EWE](http://www.gsi.dit.upm.es/ontologies/ewe/); the [Drools Expert](https://www.jboss.org/drools/) rule engine to process low level events and create high level events; [SPIN](http://spinrdf.org/) SPARQL Syntax to process this high level events and order actions; [GSN](http://sourceforge.net/projects/gsn/) middleware as an Event Network and a huge variety of scripts and applications that generates events and proccesses actions, such as Raspberry Pi scripts or Node.js applications.

![DrEWE intro](https://dl.dropboxusercontent.com/u/25002167/EWE%20repo/DrEWE%20full%20-%20DrEWE%20intro.png)

Another main feature that makes DrEWE different is the complex event processing. This means that rules are no longer *if this then that*, instead, DrEWE enables powerful features like after, during, before and coincides statements. For example, *if there is a programmed meeting for subjects A and B, and ten minutes before the meeting they both go into the laboratory: trigger the event 'meeting started' and perform all the necessary tasks, like tweet about it or take a photo to prove that the meeting is going on.*

###The semantic point

As we all know, a number of prominent web sites, mobile and desktop applications feature rule-based task automation. Typically, these services provide users the ability to define which action should be executed when some event is triggered. Some examples of this simple task automation could be “When I am mentioned in Twitter, send me an email”, “When I am within 500 meters from this place, check-in in Foursquare”, or “Turn Bluetooth on when I leave work”. We call them Task Automation Service (TAS). Some TASs allow users to share the rules they have developed, so that other users can reuse these tools and tailor them to their own preferences.

Task Automation is a rising area: recently lots of different web services and mobile-apps focus their business on this topic. Although the concept is not new, several changes on the state of technology support the success of these services and applications. Among them, the massive publishing of third-party APIs on the Cloud, providing access to their services is a key factor that unchained this mushrooming.

So, if all these task automation services could be standardized under the EWE ontology, we would have interoperability between each platform and we would find really interesting features, such as compatibility for task rules no matter the source, or a huge semantic database that will provide all the advantages that big data is bringing to our lives.

## Submodules
DrEWE consist in five main modules or projects that can be found under the subtrees of this project.

### [GCalendar-DrEWE](https://github.com/carloscrespog/GCalendar-DrEWE)

A Node.js module that simplifies the use of RESTful Google Calendar API without any interaction with the user, it retrieves all events on a given calendar and sends them to a GSN server. Before sending the events, it checks if it has already been added to the server. 

This module only needs the Google Calendar credentials to be configured and once started, it will periodically make calls to the Google calendar API, collect the events and process them to insert in our events' network: GSN.


### [Berries-DrEWE](https://github.com/carloscrespog/DrEWE/tree/master/Berries)

This subtree contains Raspberry scripts and modules that communicate with GSN and/or SPIN to produce events, make actions and handle requests. Under this subtree we'll find the software needed to perform the following actions:
	
- Generate events when somebody inserts the Id card at the entrance of the laboratory, retrieving all the info and submitting it to GSN
- Take photos with a Raspberry camera board periodically or under request
- Serve these photos to the network via HTTP
- Generate periodically events with the current light value, so it is possible to know whether the light is on inside the laboratory.

### [GSN-DrEWE](https://github.com/carloscrespog/DrEWE/tree/master/GSN)

GSN is a middleware (extensible software infrastructure) for rapid deployment and integration of heterogeneous wireless sensor networks. In this project, we have implemented it as an Event Network. Under this subtree we only have committed the parts that differ from the main project and the instructions to make it work.

In DrEWE project, we use GSN as an event network that is a little bit different than the sensor network GSN used to be. Some features that an event network may have:

- **Accessible entry points:** for that purpose, we use the GSN remote push wrapper. This wrapper deploys an entry point via HTTP request PUT, so the modules in charge of generate events simply have to make this type of request and the events will be pushed into our event network.

- **Accessible exit points:** one of the main features of GSN, is that all the data can be retrieved, by default, via HTTP request from any point of the network by simple HTTP GET request that are detailed under the [GSN](https://github.com/carloscrespog/DrEWE/tree/master/GSN) subtree. 

- **Timed database:** one of the features of events is that they are timed. By default, GSN provides a timestamp column for each type of data that it receives. This timestamp is used by the following modules (the rule engines) for complex event reasoning in order to provide extreme potential.

- **Directionable access:** this means that one application in the network is able to subscribe to one or more channels and retrieve only the information that it needs. For example, one application that only wants to know who enters the laboratory and doesn't care about the light level. Furthermore, this represents an abstraction layer for the next step: inserting the events into a CEP rule engine.

### [Drools-DrEWE](https://github.com/carloscrespog/DrEWE/tree/master/Drools)

This is the module in charge of processing events and ordering actions. It consists in two different rule engines that work together: the Drools-based engine and the SPIN-based engine. Each of them has a purpose and a reason to be here in DrEWE:

The Drools engine is a well known rule engine that provides Complex Event Processing thanks to its Fusion module. Although, once deployed this module comes with a software that automatically retrieve the data inside GSN and push it into Drools as timed events, enabling us to use all the powerful CEP features.

The SPIN engine is a bit tricky because the SPIN API itself only comes with an SPARQL inference module. That means that SPARQL inferences need to be run constantly over a semantic model and put that inferenced triples into a new model, check that model and act consequently. All this is implemented in this module and also represents the semantic approach of DrEWE task automation service, making a proficient use of the rdf [Apache Jena software](http://jena.apache.org/).

### [NodeEvented-DrEWE](https://github.com/carloscrespog/DrEWE/tree/master/NodeEvented)

This Node.js module is in charge of generating events and processing actions. It is connected with the Drools module and the GSN module. It provides several features that are detailed under this subtree.

It is written in Node and uses the main advantage of this modern programming language: the low latency. So once a rule is triggered under the Drools or SPIN engine, it only will take fractions of a second to perform high level actions such as posting a tweet, commanding the camera to take a picture or making the bot say something. 




##Architecture

Under this diagram, you can see the DrEWE arquitecture at a glance

![DrEWE full](https://dl.dropboxusercontent.com/u/25002167/EWE%20repo/DrEWE%20full%20-%20DrEWE%20repo.png)

For a detailed explanation of each module, you can visit each subproject's repository by clicking in each folder of this repo or by clicking in the headings of the Submodules section.

##Use case example

*"When a meeting is scheduled, if the corresponding attendees enter their Id cards at the entrance of the laboratory during ten minutes before the start time: generate the event meeting started."*

![Use case when](https://dl.dropboxusercontent.com/u/25002167/EWE%20repo/DrEWE%20full%20-%20Use%20case%20when.png)

*"When a meeting is started, send an email to each attendee, show a photo via bot and post a tweet mentioning each of them and a photo to prove that the meeting has occurred."*

![Use case then](https://dl.dropboxusercontent.com/u/25002167/EWE%20repo/DrEWE%20full%20-%20Use%20case%20then.png)
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