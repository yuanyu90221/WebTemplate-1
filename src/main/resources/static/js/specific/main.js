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

	// Subscribe the '/notify' channel
	stompClient.connect({}, function(frame) {
		stompClient.subscribe('/user/queue/notify', function(notification) {
			// Call the notify function when receive a notification
			gotNotify(JSON.parse(notification.body).message);
		});
		
		stompClient.subscribe('/user/surprise', function(notification) {
			// Call the notify function when receive a notification
			gotSurprise(JSON.parse(notification.body).message);
		});
	});
}

function gotNotify(message) {
	alert('Got message: <' + message + '> from /user/queue/notify');
}

function gotSurprise(message) {
	alert('Got message: <' + message + '> from /user/surprise');
}
