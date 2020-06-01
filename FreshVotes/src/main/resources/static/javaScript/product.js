textarea = document.querySelector("#textarea"); 
textarea.addEventListener('input', autoResize, false);


function autoResize() { 
    this.style.height = 'auto'; 
    this.style.height = this.scrollHeight + 'px'; 
} 

function matchingTheUsernames(){
	var nameEl = document.getElementById("name");
	const url = "http://localhost:8080/api/product/name/match";
	const csrfToken = document.getElementById("csrfToken").value;
	
	function matchName(){
		var response = fetch(url, {
			method: "POST",
			
			body: JSON.stringify({
				name: nameEl.value
			}),
			
			headers: { 
		        "Content-type": "application/json; charset=UTF-8",
		        'X-CSRF-TOKEN': csrfToken
		    } 
		})
		.then(response => response.json())
		.then(function(json){
			if(json === false){
				nameEl.setCustomValidity("This name is already taken");
			}
			else{
				nameEl.setCustomValidity("");
			}
		});

	}
	
	nameEl.onchange = matchName;
}

matchingTheUsernames();