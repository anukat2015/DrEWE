$(document).ready(function() {
		console.log(window.location.hostname);

	var socket = io.connect(window.location.hostname);
	console.log(socket);
	socket.on('bot',function(msg){
		//console.log("info received");
		//console.log(msg);
		msgReceived(msg);
	});

	$(document.body).keypress(function(event){
		if ( event.which == 32 ) {
			event.preventDefault();
			console.log("success");
			clearMessages();
		}
	});
	function clearMessages(){
		$("#bot-text").empty();
	}
	function msgReceived(msg){
		//console.log(msg);
		$("#bot-text").prepend($('<blockquote  class="example-twitter"><p>'+msg+'</p></div>').hide().fadeIn(2000));
	}
	//$("#bot-text").append($('<blockquote  class="example-twitter"><p>ola k dise</p></div>').hide().fadeIn(2000));
	

});