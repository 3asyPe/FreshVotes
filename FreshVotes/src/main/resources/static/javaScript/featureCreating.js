function checkLength(){
	var title = document.getElementById("title");
	var description = document.getElementById("textarea");
	
	function checkTitle(){
		console.log(title.value.length)
		if(title.value.length > 35){
			title.setCustomValidity("Max length of title is 35 symbols");
		}
		else{
			title.setCustomValidity("");
		}
	}
	
	function checkDescription(){
		console.log(description.value.length)
		if(description.value.length > 5000){
			description.setCustomValidity("Max length of description is 5000 symbols");
		}
		else{
			description.setCustomValidity("");
		}
	}
	
	title.onchange = checkTitle;
	description.onchange = checkDescription;
}

checkLength();