var emailEl = document.getElementById("email");
var form = document.getElementById("form-email");
var submit = document.getElementById("submit-form");

emailEl.onkeyup = emailEl.setCustomValidity("Not correct email address");
submit.onclick = verifyEmail;

function verifyEmail(){
	const emailPattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	console.log("verifyEmail");
	if(!emailEl.value.match(emailPattern)){
		emailEl.setCustomValidity("Not correct email address");
		form.reportValidity();
	}
	else{
		emailEl.setCustomValidity("");
		
		const url = "http://localhost:8080/email/verifyEmail";
		const csrfToken = document.getElementById("csrfToken").value;
		
		var parameters={
			email: emailEl.value
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
	        	if(data == true){        		
	        		window.location.href = "/login?activate";
	        	}
	        	else{
	        		window.location.href = "/login?notactivated";
	        	}
	        },
	        error: function(){
	        	console.log("error");
	        }
	   });
	}
}