# [NodeEvented-DrEWE](https://github.com/carloscrespog/NodeEvented-DrEWE)


Node.js module in charge of generate events and process actions. Connected with Drools module and GSN module

## How to Install

```bash
npm install 
```


## How to use

```bash
node app.js 
```

This node module is able to:

  - deploy a conversational bot at `GET /bot`
  - post tweets to botgsi twitter account at `POST /post-tweet?query.tweet=`
  - send en email to a given set of directions at `POST /email?text=""&subject=""&to="... , ... , ..."

  - send light events to Drools via socket.io at `POST /light?body=`
  - send events to Drools by pressing buttons via socket.io at `GET /`
  - an old but pretty drools' rules composer at `GET /composer` that send them to the drools module via socket.io


## How to configure

Inside the config.js you can find six important parameters:

  - consumer_key: consumer key for twitter,
  - consumer_secret: consumer secret for twitter,
  - access_token_key: access token fot twitter for the user who is going to post tweets,
  - access_token_secret: access token secret, same as above,
  - email_user: email who will send the message,
  - email_password: password for the email


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