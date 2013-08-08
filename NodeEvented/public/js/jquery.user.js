$(document).ready(function() {

  var canvas = document.getElementById("composer");
  //console.log(canvas);
  var canvasW=0;
  var canvasH=0;
  if(canvas!==null){
    canvas.width = document.width;
    canvas.height = document.height;
    canvasW = canvas.width;
    canvasH = canvas.height;
  }


  var colorRed="#a72e23";
  var colorBlue="#205ec5";
  var colorGreen="#61af45";
  var colorYellow="#ed9f1f";

  var Event = function(source,time){
    this.source=source;
    this.time=time;
  };

  var Payload=function(rule){
    this.rule=rule;
  };

  var stage = new Kinetic.Stage({
    container: 'composer',
    width: canvasW,
    height: canvasH
  });
  var layer = new Kinetic.Layer();

  var circles=new Array();



  $('#redCircle').click(function(e){
    var layer = new Kinetic.Layer();
    var group = new Kinetic.Group({
      draggable: true
    });
    
    var image=new Image();
    image.onload=function(){
      console.log(image);
    //$('h1').html(image);
    var img = new Kinetic.Image({
      x: (stage.getWidth() / 2)-30,
      y: (stage.getHeight() / 2)-30,
      image:image
    });
    var circle = new Kinetic.RegularPolygon({
      x: stage.getWidth() / 2,
      y: stage.getHeight() / 2,
      sides:6,
      radius: 70,
      
      stroke: 'white',
      strokeWidth: 4,
      //draggable: true,
      shadowColor: 'black',
      shadowBlur: 10,
      shadowOffset: 5,
      shadowOpacity: 0.45,
      //fillPatternImage:image,
      //fillPatternRepeat:'no-repeat',
      fill: colorRed
    });

    circle.on('mouseover',function(){
      document.body.style.cursor = '-webkit-grab';
    });

    circle.on('mouseout',function(){
      document.body.style.cursor = 'default';
    });
    circles.push(circle);
    group.add(circle);
    group.add(img);
    layer.add(group);
    layer.draw();
    //layer.add(circle);
    stage.add(layer);
  };
  image.src='img/gmail2.png';

});

$('#blueCircle').click(function(e){
  var layer = new Kinetic.Layer();
  var group = new Kinetic.Group({
    draggable: true
  });

  var image=new Image();
  image.onload=function(){
    console.log(image);
    //$('h1').html(image);
    var img = new Kinetic.Image({
      x: (stage.getWidth() / 2)-30,
      y: (stage.getHeight() / 2)-30,
      image:image
    });
    var circle = new Kinetic.RegularPolygon({

      x: stage.getWidth() / 2,
      y: stage.getHeight() / 2,
      sides:6,
      radius: 70,
      fill: colorBlue,
      stroke: 'white',
      strokeWidth: 4,
    //draggable: true,
    shadowColor: 'black',
    shadowBlur: 10,
    shadowOffset: 5,
    shadowOpacity: 0.45
  });
    circle.on('mouseover',function(){
      document.body.style.cursor = '-webkit-grab';
    });

    circle.on('mouseout',function(){
      document.body.style.cursor = 'default';
    });
    circles.push(circle);
    group.add(circle);
    group.add(img);
    layer.add(group);
    layer.draw();
    //layer.add(circle);
    stage.add(layer);
  };
  image.src='img/twitter.png';
});


$('#greenCircle').click(function(e){
  var layer = new Kinetic.Layer();
  var group = new Kinetic.Group({
    draggable: true
  });
  
  var image=new Image();
  image.onload=function(){
    console.log(image);
    //$('h1').html(image);
    var img = new Kinetic.Image({
      x: (stage.getWidth() / 2)-30,
      y: (stage.getHeight() / 2)-30,
      image:image
    });
    var circle = new Kinetic.RegularPolygon({
      x: stage.getWidth() / 2,
      y: stage.getHeight() / 2,
      sides:6,
      radius: 70,
      fill: colorGreen,
      stroke: 'white',
      strokeWidth: 4,
    //draggable: true,
    shadowColor: 'black',
    shadowBlur: 10,
    shadowOffset: 5,
    shadowOpacity: 0.45
  });
    circle.on('mouseover',function(){
      document.body.style.cursor = '-webkit-grab';
    });

    circle.on('mouseout',function(){
      document.body.style.cursor = 'default';
    });
    circles.push(circle);
    group.add(circle);
    group.add(img);
    layer.add(group);
    layer.draw();
    //layer.add(circle);
    stage.add(layer);
  };
  image.src='img/evernote.png';
});
$('#yellowCircle').click(function(e){
  var layer = new Kinetic.Layer();
  var group = new Kinetic.Group({
    draggable: true
  });
  
  var image=new Image();
  image.onload=function(){
    console.log(image);
    //$('h1').html(image);
    var img = new Kinetic.Image({
      x: (stage.getWidth() / 2)-30,
      y: (stage.getHeight() / 2)-30,
      image:image
    });
    var circle = new Kinetic.RegularPolygon({
      x: stage.getWidth() / 2,
      y: stage.getHeight() / 2,
      sides:6,
      radius: 70,
      fill: colorYellow,
      stroke: 'white',
      strokeWidth: 4,
      //draggable: true,
      shadowColor: 'black',
      shadowBlur: 10,
      shadowOffset: 5,
      shadowOpacity: 0.45
    });
    circle.on('mouseover',function(){
      document.body.style.cursor = '-webkit-grab';
    });

    circle.on('mouseout',function(){
      document.body.style.cursor = 'default';
    });
    circles.push(circle);
    group.add(circle);
    group.add(img);
    layer.add(group);
    layer.draw();
    //layer.add(circle);
    stage.add(layer);
  };
  image.src='img/feed.png';
});

$('#composerButton').click(function(e){
  var jsonCanvas=$.parseJSON(stage.toJSON());
  var composerEvents=new Array();
  for (var i in jsonCanvas.children){
    console.log(jsonCanvas.children[i].children[0].children[0]);
    var color=jsonCanvas.children[i].children[0].children[0].attrs.fill;
    if(color===colorRed){
      color="Red";
    } else if(color===colorBlue){
      color='Blue';
    } else if(color===colorGreen){
      color='Green';
    } else if(color===colorYellow){
      color='Yellow';
    }
    var time=jsonCanvas.children[i].children[0].children[0].attrs.y;
    var ev=new Event(color,time);
    composerEvents.push(ev);
      //console.log(composerEvents);
    }
    //funcion inutil:
    var orderedCompEvents=composerEvents.sort(sortfunction);
    var droolified=droolify(orderedCompEvents);
    console.log(droolified);
    sendToDrools(droolified);
  });
function sortfunction(a, b){
    return (a.time - b.time); //causes an array to be sorted numerically and ascending
  }

  function droolify(events){
    var eventCount=0;
    var output="";
    output+="import com.sample.Event; ";
    output+="declare Event ";
    output+="@role(event) ";
    output+="end ";
    output+="rule \"Example\" ";
    output+="when ";
    console.log(events);
    for (var n in events){
      console.log(events[n].source);
      if(n==0){

        output+="$newEvent"+n+": Event(source==\""+events[n].source+"\") from entry-point entrada ";
      }else{

       output+="$newEvent"+n+": Event(source==\""+events[n].source+"\", this after $newEvent"+(n-1)+") from entry-point entrada ";
     }
   }
   output+="then ";
   output+="System.out.println(\"Rule triggered !!!!! \"); ";
   output+="end";

   return output;
 }
 function sendToDrools(droolified){
    //sin esto:lo parte al primer = que se encuentra
    var body=new Payload(droolified);
    $.ajax({
      type:"POST",
      url:"http://localhost:3000/drools",
      data:body
    });
  }

          // add the shape to the layer


      // add the layer to the stage
      //stage.add(layer)
    // var context = canvas.getContext('2d');
    //      context.beginPath();
    //  context.moveTo(100, 150);
    //   context.lineTo(450, 250);
    //   context.lineTo(550, 100);
    //   context.stroke();

  });