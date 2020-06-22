var password = document.getElementById("password");
var confirm_password = document.getElementById("confirmPassword");
var usernameEl = document.getElementById("username");

password.onkeyup = function(){password.setCustomValidity("");}
confirm_password.onkeyup = function(){confirm_password.setCustomValidity("");}
usernameEl.onkeyup = function(){usernameEl.setCustomValidity("");}

function validatingPasswords(){
	var passPattern=  /^[A-Za-z]\w{5,14}$/;
	
	if(!password.value.match(passPattern)){
		password.setCustomValidity("Password has to consist 6-14 letters, numbers or _")
	}
	else{
		password.setCustomValidity("");
	}
	
	if (confirm_password.value == ""){
		confirm_password.setCustomValidity("Please fill out this field.")
	}
	else if(password.value != confirm_password.value) {
	    confirm_password.setCustomValidity("Passwords Don't Match");
	} 
	else {
	    confirm_password.setCustomValidity("");
	}
}

function matchingTheUsernames(){
	const url = "http://localhost:8080/api/user/username/match";
	const csrfToken = document.getElementById("csrfToken").value;
	
	var response = fetch(url, {
		method: "POST",
		
		body: JSON.stringify({
			username: usernameEl.value
		}),
		
		headers: { 
	        "Content-type": "application/json; charset=UTF-8",
	        'X-CSRF-TOKEN': csrfToken
	    } 
	})
	.then(response => response.json())
	.then(function(json){
		if(json === false){
			usernameEl.setCustomValidity("This username is already taken");
		}
		else{
			usernameEl.setCustomValidity("");
		}
	});
}

function createAccountValidating(){
	validatingPasswords();
	matchingTheUsernames();
}

var createAccountBtn = document.getElementById("createAccount");
createAccount.onclick = createAccountValidating;