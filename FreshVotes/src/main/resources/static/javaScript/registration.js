var password = document.getElementById("password");
var confirm_password = document.getElementById("confirmPassword");
var usernameEl = document.getElementById("username");

password.onkeyup = function(){password.setCustomValidity("");}
confirm_password.onkeyup = function(){confirm_password.setCustomValidity("");}
usernameEl.onkeyup = function(){usernameEl.setCustomValidity("");}

function validatingPasswords(){
	var passPattern=  /^[A-Za-z]\w{5,14}$/;
	
	if(!password.value.match(passPattern)){
		password.setCustomValidity("Password has to consist 6-14 letters, numbers or _");
	}
	else{
		password.setCustomValidity("");
	}
	
	if (confirm_password.value == ""){
		confirm_password.setCustomValidity("Please fill out this field.");
	}
	else if(password.value != confirm_password.value) {
	    confirm_password.setCustomValidity("Passwords Don't Match");
	} 
	else {
	    confirm_password.setCustomValidity("");
	}
}

function matchingTheUsernames(){
	const usernamePattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	if(!usernameEl.value.match(usernamePattern)){
		usernameEl.setCustomValidity("Not correct email address");
		return;
	}
	else{
		usernameEl.setCustomValidity("");
	}
	
	const url = "http://localhost:8080/api/user/username/match";
	const csrfToken = document.getElementById("csrfToken").value;
	
	var parameters={
		username: usernameEl.value
    };
	
    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader('X-CSRF-TOKEN', csrfToken);
        }
    });
    
    $.ajax({
        type:  'POST',
        contentType: "application/json; charset=utf-8",
        data:  JSON.stringify(parameters), 
        dataType: "json",
        url: url,
        async : false,
        success : function(data){
        	console.log(data);
        	if(data == false){        		
        		usernameEl.setCustomValidity("This username is already taken");
        	}
        	else{
        		usernameEl.setCustomValidity("");
        	}
        },
        error: function(){
        	console.log("error");
        }
   });
}

function createAccountValidating(){
	console.log("createAccountValidating")
	validatingPasswords();
	matchingTheUsernames();
}

var createAccountBtn = document.getElementById("createAccount");
createAccount.onclick = createAccountValidating;