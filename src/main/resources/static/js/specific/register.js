$(document).ready(function() {
	$("#registerBtn").on("click", handleSubmitForm);
});

var handleSubmitForm = function() {
	$.ajax({
		url: "/user/do_register",
		type: "POST",
		contentType: "application/json",
		dataType: "json",
		data: JSON.stringify({
			"email": $("#email").val(),
			"password": $("#password").val()
		}),
		success:function(JR) {
			alert('success');
		},
		error:function(JR) {
			alert('error');				
		}
	});
}