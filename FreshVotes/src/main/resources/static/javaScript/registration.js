var password = document.getElementById("password");
var confirm_password = document.getElementById("confirmPassword");

function validatePassword(){
	if (confirm_password.value == ""){
		confirm_password.setCustomValidity("Please fill out this field.")
	}
	else if(password.value != confirm_password.value) {
	    confirm_password.setCustomValidity("Passwords Don't Match");
	} 
	else {
	    confirm_password.setCustomValidity('');
	}
}

password.onchange = validatePassword;
confirm_password.onkeyup = validatePassword;