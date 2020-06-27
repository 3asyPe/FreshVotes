var loginBtn = document.getElementById("login");
		
loginBtn.addEventListener("click", function(){
	
	const url = "http://localhost:8080/user/loggedin";
	const csrfToken = document.getElementById("csrfToken").value;
	const usernameEl = document.getElementById("username");
	
	console.log("request");
	
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
			window.location.href="/login";
		}
		else{
			window.location.href="/dashboard";
		}
	});
});