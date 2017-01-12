var destinationAlertMsg = '/user/alert';
var destinationSurprise = '/user/surprise';

/**
 * Init operations
 */
$(document).ready(function() {
	setFootBar();

	createWebSocketConnection();
});

function setFootBar() {
	var domH = $(window).height();
	$('.row').css('height', domH - 150);
	$(window).resize(function() {
		var domH = $(window).height();
		$('.row').css('height', domH - 150);
	});
}

function createWebSocketConnection() {
	// Create and init the SockJS object
	var socket = new SockJS('/ws');
	var stompClient = Stomp.over(socket);

	// Subscribe the '/alert' and '/surprise' channel
	stompClient.connect({}, function(frame) {
		 
		stompClient.subscribe(destinationAlertMsg, function(notification) {
			// Call the notify function when receive a notification
			gotMessageFromAlertmsg(JSON.parse(notification.body).message);
		});
		
		stompClient.subscribe(destinationSurprise, function(notification) {
			// Call the notify function when receive a notification
			gotMessageFromSurprise(JSON.parse(notification.body).message);
		});
	});
}

function gotMessageFromAlertmsg(message) {
	alert('Got message: <' + message + '> from <' + destinationAlertMsg + '>');
}

function gotMessageFromSurprise(message) {
	alert('Got message: <' + message + '> from <' + destinationSurprise + '>');
}
