
function hidePwd(hide_pwd_id, show_pwd_id, pwd_id) {
	document.getElementById(hide_pwd_id).style.display = "none";
	document.getElementById(show_pwd_id).style.display = "block";

	document.getElementById(pwd_id).setAttribute("type", "password");
}

function showPwd(hide_pwd_id, show_pwd_id, pwd_id) {
	document.getElementById(hide_pwd_id).style.display = "block";
	document.getElementById(show_pwd_id).style.display = "none";

	document.getElementById(pwd_id).setAttribute("type", "text");
}

// check pass
function check_pass() {
	if (document.getElementById('password').value == document.getElementById('password-retype').value) {
			document.getElementById('submit').disabled = false;
			document.getElementById('submit').style.cursor = 'pointer';
			document.getElementById('error-mesg').innerHTML = '';
	} else {
			document.getElementById('submit').disabled = true;
			document.getElementById('submit').style.cursor = 'not-allowed';
			document.getElementById('error-mesg').innerHTML = 'Mật khẩu nhập lại không khớp!';
	}
	
	console.log(document.getElementById('password').value);
	console.log(document.getElementById('password-retype').value);
}