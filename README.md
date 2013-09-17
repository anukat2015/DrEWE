[DrEWE](https://github.com/carloscrespog/DrEWE)
=====
![DrEWE Logo](https://dl.dropboxusercontent.com/u/25002167/DrEWE.png)
An intelligent platform for task automation

## Introduction
A quick overview of this project:

![DrEWE intro](https://dl.dropboxusercontent.com/u/25002167/EWE%20repo/DrEWE%20full%20-%20DrEWE%20intro.png)

DrEWE is a task automation platform, in a similar way to [IFTTT](www.ifttt.com), [Zapier](www.zapier.com) or [CouldWork](www.couldwork.com) but with a main big difference, the semantic approach. It uses a new ontology or standarized schema for managing the evented web named [EWE](http://www.gsi.dit.upm.es/ontologies/ewe/), the [Drools Expert](https://www.jboss.org/drools/) rule engine to process low level events and create high level events, [SPIN](http://spinrdf.org/) SPARQL Syntax to process this high level events and order actions, [GSN](http://sourceforge.net/projects/gsn/) middleware as an Event Network and a huge variety of scripts and applications that generates events and proccess actions, such as Raspberry Pi scripts or Node.js applications.

Another main feature that makes DrEWE different is the complex event processing. This means that rules are no longer *if this then that*, instead, they provide powerful features like after, during, before and coincides examples. For example, *if there is a programmed meeting for subjects A and B, and ten minutes before the meeting they both go into the laboratory: trigger the event 'meeting started' and do all the task that you needed like tweet about it or take a photo to prove that the meeting is going on.*

###The semantic point

As we all know, a number of prominent web sites mobile and desktop applications feature rule-based task automation. Typically, these services provide users the ability to define which action should be executed when some event is triggered. Some examples of this simple task automation could be “When I am mentioned in Twitter, send me an email”, “When I reach 500 meters of this place, check in in Foursquare”, or “Turn Bluetooth on when I leave work”. We call them Task Automation Service (TAS). Some TASs allow users to share the rules they have developed, so that other users can reuse these tools and adapt them to their own preferences.

Task Automation is a rising area, recently lots different web services and mobile-apps focus their business on this topic. Although the concept is not new, several changes on the state of technology supports the success of these services and applications. Among them the massive publishing of third-party APIs on the Cloud, providing access to their services is a key factor that unchained this mushrooming.

So, if we could standarize all of these task automation services under the EWE ontology, we will have interoperability between each platform and we will find really interesting features like compatibility for task rules despite the source and a huge semantic database that will provide all the advantages that big data is bringing to our lifes.

## Submodules
DrEWE consist in five main modules or projects that can be found under the subtrees of this project.

### [GCalendar-DrEWE](https://github.com/carloscrespog/GCalendar-DrEWE)

A Node.js module that simplifies the use of RESTful Google Calendar API without any interaction with the user, retrieve all events on a given calendar and send them to a GSN server. Before sending the events, it checks if it has already been added. 

So this module only needs you to configure your Google Calendar credentials and once started, it will periodically make calls to google calendar api, get the events and proccess them to insert in our events' network: GSN.

### [Berries-DrEWE](https://github.com/carloscrespog/DrEWE/tree/master/Berries)

Raspberry's scripts and modules that communicate with GSN and/or SPIN to produce events, make actions and handle requests. Under this subtree we'll find the software needed to perform the following actions:
	
- Generate events when somebody inserts the dni at the entrance of the laboratory, retrieving all the info and inserting it to GSN
- Take photos with a raspberry camera board periodically or under request
- Serve this photos to the network via HTTP
- Generate periodically events with the current light value, so you are able to know whether the light is on inside the laboratory.

### [GSN-DrEWE](https://github.com/carloscrespog/DrEWE/tree/master/GSN)

GSN is a middleware (extendible software infrastructure) for rapid deployment and integration of heterogeneous wireless sensor networks. In this project, we have implemented it as an Event Network and under this subtree we only have commited the parts that differ from the main project and the instructions to make it work.

In DrEWE project, we use GSN as an event network that is a little bit different than the sensor network GSN use to be. Some features that an event network may have:

- **Accesible entry points:** for that purpose, we use the GSN remote push wrapper. This wrapper deploys an entry point via HTTP request PUT, so the modules in charge of generate events simply have to make this type of request and the events will be pushed into our event network.

- **Accesible exit points:** one of the main features of GSN, is that all the data can be retrieved, by default, via HTTP request from any point of the network by simple HTTP GET request that are detailed under the [GSN](https://github.com/carloscrespog/DrEWE/tree/master/GSN) subtree. 

- **Timed database:** as we are talking about events, one feature of them is that they are timed. By default, GSN provides a timestamp column for each type of data that it receives. This timestamp is used by the next modules (the rule engines) for complex event reasoning in order to provide extreme potential.

- **Directionable access:** this means that one application at the network is able to subscribe to one or more channels and retrieve only the information that it needs. For example, one application that only wants to know who enters the laboratory and doesn't care about the light level. Furthermore, this represents an abstraction layer for the next step: inserting the events into a CEP rule engine.

### [Drools-DrEWE](https://github.com/carloscrespog/DrEWE/tree/master/Drools)

Module in charge of processing events and ordering actions. It consists in two different rule engines that work together: the Drools-based engine and the SPIN-based engine. Each of them has a purpose and a reason to be here in DrEWE:

The Drools engine is a well known rule engine that provides Complex Event Processing thanks to its Fusion module. Although, once deployed this module comes with a software that automatically retrieve the data inside GSN and push it into Drools as timed events, enabling us to use all the powerful CEP features.

The SPIN engine is a bit tricky because the SPIN API itself only comes whith an SPARQL inference module. That means we have to run SPARQL inferences constantly over a semantic model and put that inferenced triples into a new model, check that model and act consequently. All of this is implemented in this module and also represents the semantic approach of DrEWE task automation service making a proficient use of the rdf Jena Apache software.

### [NodeEvented-DrEWE](https://github.com/carloscrespog/DrEWE/tree/master/NodeEvented)

Node.js module in charge of generate events and process actions. Connected with Drools module and GSN module. It provides several features that are detailed under this subtree.

It is written in node and uses the main advantage of this modern programming language: the low latency. So once a rule is triggered under the Drools or SPIN engine, it only will take fractions of a second to perform high level actions such as post a tweet, order the camera to take a photo or make the bot talk something. 



##Architecture

Under this diagram, you can see the DrEWE arquitecture at a glance

![DrEWE full](https://dl.dropboxusercontent.com/u/25002167/EWE%20repo/DrEWE%20full%20-%20DrEWE%20repo.png)

For a detailed explanation of each module, you can visit each subproject's repository.

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