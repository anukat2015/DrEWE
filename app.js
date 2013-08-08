
/**
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')
  , user = require('./routes/user')
  , http = require('http')
  , path = require('path')
  ,OAuth = require('oauth').OAuth
  ,Twitter = require('twitter');


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
      consumer_key: 'SDfRc6NHXJnO9Z3AHCt7bg',
      consumer_secret: 'KLYo1vPcmlo1YhxZY9fbOhmwqu1jATLsWMOcqVXzM',
      access_token_key: '1461415566-O9IkGQKqOawrIobnoz71TfPhjUyWmzZCuF4L4eE',
      access_token_secret: 'bLrhaQDJ7hjNJLKPeTjzXl1TjaN4sNvdvxR1a0QBRE'
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
    console.log(data);
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
