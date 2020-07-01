var title = document.getElementById("title");
var description = document.getElementById("textarea");

title.onkeyup = function(){title.setCustomValidity("");}
description.onkeyup = function(){description.setCustomValidity("");}

function checkLength(){
	if(title.value.length > 35){
		title.setCustomValidity("Max length of title is 35 symbols");
	}
	else{
		title.setCustomValidity("");
	}
	
	if(description.value.length > 5000){
		description.setCustomValidity("Max length of description is 5000 symbols");
	}
	else{
		description.setCustomValidity("");
	}
}

var formSubmit = document.getElementById("form-submit");
formSubmit.onclick = checkLength;