$(document).ready(function() {


	var Event = function(source,time){
		this.source=source;
		this.time=time;
	};

	$('#eventGreen').click(function(e){
		var ev=new Event("Green","New event");
		console.log(ev);
		$.ajax({
			type:"POST",
			url:"http://localhost:3000/event",
			data:ev
		});
	});
	$('#eventRed').click(function(e){
		var ev=new Event("Red","New event");
		console.log(ev);
		$.ajax({
			type:"POST",
			url:"http://localhost:3000/event",
			data:ev
		});
	});
	$('#eventYellow').click(function(e){
		var ev=new Event("Yellow","New event");
		console.log(ev);
		$.ajax({
			type:"POST",
			url:"http://localhost:3000/event",
			data:ev
		});
	});
	$('#eventBlue').click(function(e){
		var ev=new Event("Blue","New event");
		console.log(ev);
		$.ajax({
			type:"POST",
			url:"http://localhost:3000/event",
			data:ev
		});
	});

});