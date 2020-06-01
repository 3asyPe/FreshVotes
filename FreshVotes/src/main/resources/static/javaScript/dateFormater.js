var dates = document.getElementsByClassName("comment-date");

for(var date of dates){
	date.innerHTML = formatDate(date.innerHTML);
}
	
function formatDate(date){
	date = new Date(date);
	return moment(date).fromNow();
}

