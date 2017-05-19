var stompClient = null;

function setConnected(connected) {
	$('#connect').prop('disabled', connected);
	$('#disconnect').prop('disabled', !connected);
	if(connected) {
		$('#conversation').show();
	} else {
		$('#conversation').hide();
	}
	$('#greetings').html('');
}

function connect() {
	var socket = new SockJS('/websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		setConnected(true);
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/greetings', function(greeting) {
			showGreeting(JSON.parse(greeting.body).content);
		});
	});
}

function disconnect() {
	if(stompClient != null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log('Disconnected');
}

function sendName() {
	var name = $('#name').val();
	var message = JSON.stringify({'name': name });
	stompClient.send('/app/hello', {}, message);
}

function showGreeting(message) {
	$('#greetings').append("<tr><td>" + message + "</td></tr>")
}

$(function() {
	$(document).ready(function() {
		$('form').on('submit', function(e) {
			e.preventDefault();
		});
		
		$('#connect').click(connect);
		$('#disconnect').click(disconnect);
		$('#send').click(sendName);
	});
});