$(document).ready(function() {
	$('#registerBtn').on('click', handleSubmitForm);
});

var handleSubmitForm = function() {
	var checkResult = checkInput();
	
	if (checkResult) {
		sendAjax();
	}
}

function checkInput() {
	if (email.value == '') {
		showErrorMessage('請輸入 Email');
		return false;
	}
	if (mobileNo.value == '') {
		showErrorMessage('請輸入手機號碼');
		return false;
	}
	if (password.value == '') {
		showErrorMessage('請輸入登入密碼');
		return false;
	}
	if (confirmPassword.value == '') {
		showErrorMessage('請確認密碼');
		return false;
	}
	if (password.value != confirmPassword.value) {
		showErrorMessage('兩次密碼輸入不同, 請再次確認');
		return false;
	}
	return true;
}

function showErrorMessage(errorMessage) {
	var successDiv = document.getElementById('successMessage');
	var errorDiv = document.getElementById('errorMessage');
	
	successDiv.style.display = 'none';
	
	errorDiv.style.display = 'block';
	errorDiv.innerHTML = errorMessage;
}

function showSuccessMessage(successMessage) {
	var successDiv = document.getElementById('successMessage');
	var errorDiv = document.getElementById('errorMessage');
	
	errorDiv.style.display = 'none';
	
	successDiv.style.display = 'block';
	successDiv.innerHTML = successMessage;
}

function sendAjax() {
	$.ajax({
		url: '/user/do_register',
		type: 'POST',
		contentType: 'application/json',
		dataType: 'json',
		data: JSON.stringify({
			'email': $('#email').val(),
			'password': $('#password').val()
		}),
		success:function(response) {
			showSuccessMessage('註冊成功');
		},
		error:function(response) {
			var json = JSON.parse(response.responseText);
			showErrorMessage(json.data);				
		}
	});
}