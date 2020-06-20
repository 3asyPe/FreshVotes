function validatingPasswords(){
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
}

function matchingTheUsernames(){
	var usernameEl = document.getElementById("username");
	const url = "http://localhost:8080/api/user/username/match";
	const csrfToken = document.getElementById("csrfToken").value;
	
	function matchUsername(){
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
}

validatingPasswords();
matchingTheUsernames();