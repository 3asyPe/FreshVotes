var filterAll = document.getElementById("filter-all");
var filterAccepted = document.getElementById("filter-accepted");
var filterReview = document.getElementById("filter-review");
var filterPending = document.getElementById("filter-pending");
var filterRejected = document.getElementById("filter-rejected");

const activatedClass = "activatedFilter";

filterAll.addEventListener("click", radioboxClickAction, false);
filterAccepted.addEventListener("click", checkboxClickAction, false);
filterReview.addEventListener("click", checkboxClickAction, false);
filterPending.addEventListener("click", checkboxClickAction, false);
filterRejected.addEventListener("click", checkboxClickAction, false);

function checkboxClickAction(){
	console.log(this);
	console.log(this.classList);
	if(this.classList.contains(activatedClass)){
		this.classList.remove(activatedClass);
	}
	else{		
		this.classList.add(activatedClass);
		deactivateFilterAll();
	}
	
	sendRequest();
}

function radioboxClickAction(){
	if(this.classList.contains(activatedClass)){
		this.classList.remove(activatedClass);
	}
	else{		
		this.classList.add(activatedClass);
		deactivateAllButFilterAll();
	}
	
	sendRequest();
}

function deactivateFilterAll(){
	filterAll.classList.remove(activatedClass);
}

function deactivateAllButFilterAll(){
	filterAccepted.classList.remove(activatedClass);
	filterReview.classList.remove(activatedClass);
	filterPending.classList.remove(activatedClass);
	filterRejected.classList.remove(activatedClass);
}

function sendRequest(){
	console.log("send request");
	urlParams = new URL(window.location.href).searchParams;
	params = [];
	
	if(filterAll.classList.contains(activatedClass)){
		params.push("all");
	}
	else{
		if(filterAccepted.classList.contains(activatedClass)){
			params.push('Accepted');
		}
		if(filterReview.classList.contains(activatedClass)){
			params.push("Review");
		}
		if(filterPending.classList.contains(activatedClass)){
			params.push("Pending review");
		}
		if(filterRejected.classList.contains(activatedClass)){
			params.push("Rejected");
		}
	}
	
	urlParams.set("filters", params);
	window.location.href = window.location.pathname + "?" + urlParams.toString();
}


