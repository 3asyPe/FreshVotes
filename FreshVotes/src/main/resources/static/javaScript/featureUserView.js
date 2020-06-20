var likeBtn = document.getElementById("likeBtn");
var dislikeBtn = document.getElementById("dislikeBtn");
var btns = document.getElementsByClassName("btn");
	
for(var btn of btns){
	btn.onclick = function(){
		this.blur();
	};
}

var likeNum = document.getElementById("likeNum");
var dislikeNum = document.getElementById("dislikeNum");

var likeLabel = document.getElementById("likeLabel");
var dislikeLabel = document.getElementById("dislikeLabel");

function checkStateLike(){
	if(likeBtn.checked){
		if(dislikeBtn.checked){
			dislikeNum.innerHTML = parseInt(dislikeNum.innerHTML, 10) - 1;
			doVote(true, false);
		}
		else{
			doVote(true, null);
		}
		likeNum.innerHTML = parseInt(likeNum.innerHTML, 10) + 1;
		dislikeBtn.checked = false;
	}
	else{
		doVote(null, true);
		likeNum.innerHTML = parseInt(likeNum.innerHTML, 10) - 1;
	}
	consoleState();
	checkActiveClasses();
}

function checkStateDislike(){
	if(dislikeBtn.checked){
		if(likeBtn.checked){
			likeNum.innerHTML = parseInt(likeNum.innerHTML, 10) - 1;
			doVote(false, true);
		}
		else{
			doVote(false, null);
		}
		dislikeNum.innerHTML = parseInt(dislikeNum.innerHTML, 10) + 1;
		likeBtn.checked = false;
	}
	else{
		doVote(null, false);
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

function doVote(upvote, previousUpvote){
	const postUrl = window.location.href + "/vote";
	const csrfToken = document.getElementById("csrfToken").value;
	
	var parameters={
            upvote: upvote,
            previousUpvote: previousUpvote
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
        url: postUrl
   });
}


likeBtn.onclick = checkStateLike;
dislikeBtn.onclick = checkStateDislike;
checkActiveClasses();

var comments = document.getElementById("commentsDiv");
var textarea = document.getElementById("textarea-comment");

var sendButton=$("#send-button");
var replyButtons = document.getElementsByClassName("reply-button");
var closeButtons = document.getElementsByClassName("close-button");
var sendButtonsReply = document.getElementsByClassName("send-button-reply");

const postUrl = window.location.href + "/comments";
const csrfToken = document.getElementById("csrfToken").value

var name=$("#name").val();
var userImage = document.getElementById("userImage").src;

bindButtons();

function getNewCommentHTML(commentId, text){
	return  '<ul id="comment-div-' + commentId + '">' +
			    '<li class="media" id="comment-' + commentId + '">' +
			    	'<a th:href="@{/user/{userId}/profile(userId=${#authentication.getPrincipal().getId()})}">' +
                    	'<img src="' + userImage + '" class="user-image">' +
                    '</a>' +
			 		'<div class="media-body">' +
			 			'<div class="comment-header">' +
			 				'<div class="comment-title">' + name + '</div>' +
			 				'<button type="button" class="reply-button" id="reply-button-' + commentId + '">reply</button>' +
			 				'<div class="comment-date">Right now</div>' +
			 			'</div>' +
			 			'<div class="comment-text">' + text + '</div>' +
			 		'</div>' +
			 	'</li>' +
			 	'<div class="d-none" id="reply-textarea-div-' + commentId + '">' +
			 		'<ul>' +
			 			'<li class="media">' +
			 				'<img src="' + userImage + '" class="user-image">' +
			 				'<div class="media-body">' +
			 					'<textarea class="my-textarea" id="reply-textarea-' + commentId + '" rows="1"></textarea>' +
			 					'<div class="new-comment-buttons">' +
			 						'<button type="button" class="btn btn-info btn-feature close-button new-close-button" id="close-button-' + commentId + '">Close</button>' +
			 						'<button type="submit" class="btn btn-primary btn-feature send-button send-button-reply" id="send-button-' + commentId + '">Send</button>' +
			 					'</div>' +
			 				'</div>' +
			 			'</li>' +	
			 		'</ul>' +
			 	'</div>' +
			'</ul>';
}

sendButton.click(function( event ) {
	var text=$("#textarea-comment");
	
	if(text.val() == ""){
    	return;
    }
    var parameters={
        text: text.val()
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
        success:  function (data) {
            comments.innerHTML = getNewCommentHTML(data, text.val()) + comments.innerHTML;
            textarea.value = "";
            textarea.style.height = 'auto';
            textarea.style.height = textarea.scrollHeight + 'px';
            bindButtons();
            hideNoComments();
        }, error: function(data){
            comments.innerHTML = "Server Error.";
        }
   });
});

function sendReplyComment(){
	commentIdVal = this.getAttribute("id").split("-")[2];
	text=$("#reply-textarea-" + commentIdVal)
	if(text.val() == ""){
		return;
	}
	
	var parameters={
			text: text.val(),
			commentId: commentIdVal
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
		success:  function (data) {
			var comment = document.getElementById("comment-div-" + commentIdVal)
			comment.innerHTML = comment.innerHTML + getNewCommentHTML(data, text.val());
			bindButtons();
			closeCommentTextarea(commentIdVal);
		}, error: function(data){
			comment.innerHTML = "Server Error.";
		}
	});
}

function bindButtons(){
	bindSendButtons();
	bindClose();
	bindReply();
}

function bindSendButtons(){
	for(var sendButton of sendButtonsReply){
		sendButton.addEventListener('click', sendReplyComment, false);
	}
}

function bindClose(){
	for(var closeButton of closeButtons){
		closeButton.addEventListener('click', bindCloseCommentTextarea, false);
		function bindCloseCommentTextarea(){
			let commentId = this.getAttribute("id").split("-")[2];	
			closeCommentTextarea(commentId);
		}
	}
}

function closeCommentTextarea(commentId){
	commentTextareaDiv = document.getElementById("reply-textarea-div-" + commentId);
	commentTextareaDiv.classList.add("d-none");
}

function bindReply(){
    for(var replyButton of replyButtons){
	    replyButton.addEventListener('click', replyComment, false);
	    function replyComment(){
	    	let commentId = this.getAttribute("id").split("-")[2];	
	    	commentTextareaDiv = document.getElementById("reply-textarea-div-" + commentId);
	    	commentTextareaDiv.classList.remove("d-none");
	    	
	    	commentTextarea = document.getElementById("reply-textarea-" + commentId);
	    	commentTextarea.focus();
	    	commentTextarea.addEventListener('input', autoResize, false)
	    };
    }
}

function hideNoComments(){
	noComments = document.getElementsByClassName("no-comments");
	for(var noComment of noComments){
		noComment.classList.add("d-none");
	}
}

function autoResize() { 
    this.style.height = 'auto'; 
    this.style.height = this.scrollHeight + 'px';
} 

var statusAccept = document.getElementById("status-accept");
var statusReview = document.getElementById("status-review");
var statusPendingReview = document.getElementById("status-pending-review");
var statusReject = document.getElementById("status-reject");
var statusSave = document.getElementById("status-save");

var statusBadge = document.getElementById("status-badge")
var cur_status = statusBadge.innerHTML

statusAccept.addEventListener("click", function(){tryChangeCurStatus("Accepted")}, false);
statusReview.addEventListener("click", function(){tryChangeCurStatus("Review")}, false);
statusPendingReview.addEventListener("click", function(){tryChangeCurStatus("Pending review")}, false);
statusReject.addEventListener("click", function(){tryChangeCurStatus("Rejected")}, false);

statusSave.addEventListener("click", saveStatusBadge, false);

function tryChangeCurStatus(text){
	cur_status = text; 
}

function saveStatusBadge(){
	const url = window.location.href + "/status";
	const csrfToken = document.getElementById("csrfToken").value;
		
	var parameters={
	    status: cur_status
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
	    success:  function (data) {
			console.log("success");
			window.location.href = window.location.href;
		}, error: function(data){
			console.log("error");
			window.location.href = window.location.href;
		}
	});
}




