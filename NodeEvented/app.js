
/**
 * Module dependencies.
 */

 var express = require('express')
 , routes = require('./routes')
 , user = require('./routes/user')
 , http = require('http')
 , path = require('path')
 ,OAuth = require('oauth').OAuth
 ,Twitter = require('twitter')
 ,nodemailer = require("nodemailer")
 ,config=require("./config");

 var smtpTransport = nodemailer.createTransport("SMTP",{
  service: "Gmail",
  auth: {
    user: config.email_user,
    pass: config.email_password
  }
});

 var app = express();



 app.configure(function(){
  app.set('port', process.env.PORT || 3000);
  app.set('views', __dirname + '/views');
  app.set('view engine', 'jade');
  app.use(express.favicon());
  app.use(express.logger('dev'));
  app.use(express.bodyParser());
  app.use(express.methodOverride());
  app.use(app.router);
  app.use(express.static(path.join(__dirname, 'public')));
});

 app.configure('development', function(){
  app.use(express.errorHandler());

});

 app.get('/', routes.index);
 app.get('/composer', routes.composer);
 app.get('/bot', routes.bot);
 app.get('/users', user.list);

 var server=http.createServer(app).listen(app.get('port'), function(){
  console.log("Express server listening on port " + app.get('port'));
});







 app.get('/post-tweet', function (req, res, next) {

  var oauth, twitter;

  twitter = new Twitter({
    consumer_key: config.consumer_key,
    consumer_secret: config.consumer_secret,
    access_token_key: config.access_token_key,
    access_token_secret: config.access_token_secret
  });

  twitter.post('https://api.twitter.com/1.1/statuses/update.json',
  {
    status: req.query.tweet,
    trim_user: 'true'
  },
  function (response) {
    console.log(response);
    if (response.id) {
      res.send(response, 200);
    } else {
      res.send('There was a problem.', 400);
    }
  });



});


 var io = require('socket.io').listen(server);
 var extSocket;
 io.on('connection', function (socket) {
  extSocket=socket;
  //socket.emit('news', { hello: 0 });
  socket.on('hola', function (data) {
    
  //   var options = {
  //     hotname:'http://192.168.1.132',
  //     port: 8080,
  //     path: '/0/action/snapshot',
  //     method: 'GET',

  //   };


  //   var req = http.request(options, function(res) {
  //     console.log('STATUS: ' + res.statusCode);
  //     console.log('Item sent to GSN');
  //     res.setEncoding('utf8');

  //   });
  //   req.on('error', function(e) {
  //     console.log('problem with request: ' + e.message);
  //   });
  //   console.log('******************');
  //   req.end();
  });

});
 app.post('/event',function(req,res){
  //console.log(req.body.source);
  extSocket.emit(req.body.source, { data: 1 });
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.send("success",200);
});

 app.post('/light',function(req,res){
  extSocket.emit('light', { data: req.body.value });
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.send("success",200);
});

 app.post('/bot',function(req,res){
  console.log(req.body);
  console.log('----------');
  extSocket.emit('bot', req.body.text);
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.send("success",200);
});

 app.post('/email',function(req,res){

  var mailOptions = {
    from: "Bot gsi ✔ <botgsi@gmail.com>", // sender address
    to: req.body.to, // list of receivers
    subject: req.body.subject, // Subject line
    text: req.body.text, // plaintext body
  }

  smtpTransport.sendMail(mailOptions, function(error, response){
    if(error){
      console.log(error);
    }else{
      console.log("Message sent: " + response.message);
    }

  });
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.send("success",200);
});

 app.post('/drools',function(req,res){
  extSocket.emit('Rule', req.body.rule);
  //console.log(req.body);
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.send("success",200);
});

 app.get('/example',function(req,res){
  res.send('8008');
});

// var everyone = require("now").initialize(server);

// everyone.now.logStuff = function(msg){
//     console.log(msg);
// };
