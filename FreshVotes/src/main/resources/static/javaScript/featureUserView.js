var likeBtn = document.getElementById("likeBtn");
var dislikeBtn = document.getElementById("dislikeBtn");

var likeNum = document.getElementById("likeNum");
var dislikeNum = document.getElementById("dislikeNum");

var likeLabel = document.getElementById("likeLabel");
var dislikeLabel = document.getElementById("dislikeLabel");

function checkStateLike(){
	if(likeBtn.checked){
		if(dislikeBtn.checked){
			dislikeNum.innerHTML = parseInt(dislikeNum.innerHTML, 10) - 1;
		}
		likeNum.innerHTML = parseInt(likeNum.innerHTML, 10) + 1;
		dislikeBtn.checked = false;
		doVote(true);
	}
	else{
		doVote(null);
		likeNum.innerHTML = parseInt(likeNum.innerHTML, 10) - 1;
	}
	consoleState();
	checkActiveClasses();
}

function checkStateDislike(){
	if(dislikeBtn.checked){
		if(likeBtn.checked){
			likeNum.innerHTML = parseInt(likeNum.innerHTML, 10) - 1;
		}
		dislikeNum.innerHTML = parseInt(dislikeNum.innerHTML, 10) + 1;
		likeBtn.checked = false;
		doVote(false);
	}
	else{
		doVote(null);
		dislikeNum.innerHTML = parseInt(dislikeNum.innerHTML, 10) - 1;
	}
	consoleState();
	checkActiveClasses();
}

function consoleState(){
	console.log("dislikeBtn " + dislikeBtn.checked);
	console.log("likeBtn " + likeBtn.checked);
}

function checkActiveClasses(){
	if(likeBtn.checked){
		likeLabel.classList.add("active-rate");
		dislikeLabel.classList.remove("active-rate");
	}
	else if(dislikeBtn.checked){
		dislikeLabel.classList.add("active-rate");
		likeLabel.classList.remove("active-rate");
	}
	else{
		dislikeLabel.classList.remove("active-rate");
		likeLabel.classList.remove("active-rate");
	}
}

function doVote(upvote){
	const postUrl = window.location.href + "/vote";
	const csrfToken = document.getElementById("csrfToken").value;
	
	var parameters={
            upvote: upvote
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
        url: postUrl,
        success:  function () {
           console.log("success");
        }, 
        error: function(){
        	console.log("hueccess");
        }
   });
}


likeBtn.onclick = checkStateLike;
dislikeBtn.onclick = checkStateDislike;
checkActiveClasses();