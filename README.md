[DrEWE](https://github.com/carloscrespog/DrEWE)
=====
![DrEWE Logo](https://dl.dropboxusercontent.com/u/25002167/DrEWE.png)
An intelligent platform for task automation

## Introduction

This project aims to be a task automation platform, in a similar way to [IFTTT](www.ifttt.com), [Zapier](www.zapier.com) or [CouldWork](www.couldwork.com). It uses a new ontology or standarized schema for managing the evented web named [EWE](http://www.gsi.dit.upm.es/ontologies/ewe/), the [Drools Expert](https://www.jboss.org/drools/) rule engine to process low level events and create high level events, [SPIN](http://spinrdf.org/) SPARQL Syntax to process this high level events and order actions, [GSN](http://sourceforge.net/projects/gsn/) middleware as an Event Network and a huge variety of scripts and applications that generates events and proccess actions, such as Raspberry Pi scripts or Node.js applications.


DrEWE consist in four main modules or projects that can be found under the subtrees of this project.

### [Berries](https://github.com/carloscrespog/DrEWE/tree/master/Berries)

Raspberry Pi software 

### [Drools](https://github.com/carloscrespog/DrEWE/tree/master/Drools)

Module in charge of processing events and ordering actions. It consists in two different rule engines that work together: the Drools-based engine and the SPIN-based engine

### [GSN](https://github.com/carloscrespog/DrEWE/tree/master/GSN)

GSN is a middleware (extendible software infrastructure) for rapid deployment and integration of heterogeneous wireless sensor networks. In this project, we have implemented it as an Event Network and under this subtree we only have commited the parts that differ from the main project and the instructions to make it work.

### [NodeEvented](https://github.com/carloscrespog/DrEWE/tree/master/NodeEvented)

Under this subtree lays the module in charge of taking care of the actions such as twitter connection, email connection, web bot...

### [GCalendar-DrEWE](https://github.com/carloscrespog/GCalendar-DrEWE)

A Node.js module that simplifies the use of RESTful Google Calendar API without any interaction with the user, retrieve all events on a given calendar and send them to a GSN server. Before sending the events, it checks if it has already been added.

##Arquitecture

Under this diagram, you can see the DrEWE arquitecture at a glance

![DrEWE full](https://dl.dropboxusercontent.com/u/25002167/DrEWE%20full.png)

For a detailed explanation of each module, visit the subprojects repos.

## License

```
Copyright 2012 UPM-GSI: Group of Intelligent Systems - Universidad Politécnica de Madrid (UPM)

Licensed under the Apache License, Version 2.0 (the "License"); 
You may not use this file except in compliance with the License. 
You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by 
applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific 
language governing permissions and limitations under the License.
```
![GSI Logo](http://gsi.dit.upm.es/templates/jgsi/images/logo.png)

This project has been developed as the master thesis of [Carlos Crespo](https://github.com/carloscrespog) under the tutelage of [Miguel Coronado](https://github.com/miguelcb84) and the supervision of [Carlos A. Iglesias](https://github.com/cif2cif) at [gsi-upm](https://github.com/gsi-upm)