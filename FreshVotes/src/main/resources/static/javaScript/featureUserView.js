var likeBtn = document.getElementById("likeBtn");
var dislikeBtn = document.getElementById("dislikeBtn");

var likeNum = document.getElementById("likeNum");
var dislikeNum = document.getElementById("dislikeNum");

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
}

function checkStateDislike(){
	if(dislikeBtn.checked){
		if(likeBtn.checked){
			likeNum.innerHTML = parseInt(likeNum.innerHTML, 10) - 1;
		}
		likeBtn.checked = false;
		doVote(false);
		dislikeNum.innerHTML = parseInt(dislikeNum.innerHTML, 10) + 1;
	}
	else{
		doVote(null);
		dislikeNum.innerHTML = parseInt(dislikeNum.innerHTML, 10) - 1;
	}
	consoleState();
}

function consoleState(){
	console.log("dislikeBtn " + dislikeBtn.checked);
	console.log("likeBtn " + likeBtn.checked);
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