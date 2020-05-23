var textareas = document.getElementsByClassName("my-textarea");

for (var textarea of textareas){
	textarea.addEventListener('input', autoResize, false);
	textarea.style.height = 'auto';
	textarea.style.height = textarea.scrollHeight + 'px';
};

  
function autoResize() { 
    this.style.height = 'auto'; 
    this.style.height = this.scrollHeight + 'px';
} 

window.onload=function() {
    var comments = document.getElementById("commentsDiv");
    var commentsHTML= comments.innerHTML;
    var textarea = document.getElementById("textarea-comment");
    
    var name=$("#name");
    var text=$("#textarea-comment");
    
    var sendButton=$("#send-button");
    var replyButtons = document.getElementsByClassName("reply-button");
    var closeButtons = document.getElementsByClassName("close-button");
    var sendButtonsReply = document.getElementsByClassName("send-button-reply");
    
    var postUrl = window.location.href + "/comments";
    const csrfToken = document.getElementById("csrfToken").value;

    sendButton.click(function( event ) {
    	if(text.val() == ""){
        	return;
        }
        var parameters={
            name: name.val(),
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
                comments.innerHTML = '<ul id="comment-div-' + data + '">' +
			                		      '<li class="media" id="comment-' + data + '">' +
				        			          '<img src="..." class="mr-3" alt="...">' +
				        			  	      '<div class="media-body">' +
				        			  	      	  '<div class="row">' +
				        			  	      	  	  '<h5 class="mt-0">' + name.val() + '</h5>' +
												      '<button type="button" class="btn btn-sm btn-info reply-button" id="reply-button-' + data + '">reply</button>' +
											      '</div>' +
				        			  		      '<div class="comment-text">' + text.val() + '</div>' +
				        			  	      '</div>' +
				        			      '</li>' +
				        			      '<div class="d-none" id="reply-textarea-div-' + data + '">' +
											'<ul>' +
												'<li class="media">' +
												  '<img src="..." class="mr-3" alt="...">' +
												  '<div class="media-body">' +
													'<textarea class="form-control my-textarea" id="reply-textarea-' + data + '" rows="1"></textarea>' +
												  '</div>' +
												  '<button type="submit" class="btn btn-primary send-button-reply" id="send-button-' + data + '">Send</button>' +
												  '<button type="button" class="btn btn-info close-button" id="close-button-' + data + '">Close</button>' +
												'</li>' +	
											'</ul>' +
										  '</div>' +
				        			  '</ul>' +
				        			  comments.innerHTML;
                textarea.value = "";
                textarea.style.height = 'auto';
                textarea.style.height = textarea.scrollHeight + 'px';
                bindButtons();
            }, error: function(data){
                comments.innerHTML = "Server Error.";
            }
       });
	});
    
    bindButtons();
    
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
    
    function sendReplyComment(){
    	commentIdVal = this.getAttribute("id").split("-")[2];
    	var text=$("#reply-textarea-" + commentIdVal);
    	console.log(text);
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
                comment.innerHTML = comment.innerHTML +
                					  '<ul id="comment-div-' + data + '">' +
			                		      '<li class="media" id="comment-' + data + '">' +
				        			          '<img src="..." class="mr-3" alt="...">' +
				        			  	      '<div class="media-body">' +
				        			  	      	  '<div class="row">' +
				        			  	      	  	  '<h5 class="mt-0">' + name.val() + '</h5>' +
												      '<button type="button" class="btn btn-sm btn-info reply-button" id="reply-button-' + data + '">reply</button>' +
											      '</div>' +
				        			  		      '<div class="comment-text">' + text.val() + '</div>' +
				        			  	      '</div>' +
				        			      '</li>' +
				        			      '<div class="d-none" id="reply-textarea-div-' + data + '">' +
											'<ul>' +
												'<li class="media">' +
												  '<img src="..." class="mr-3" alt="...">' +
												  '<div class="media-body">' +
													'<textarea class="form-control my-textarea" id="reply-textarea-' + data + '" rows="1"></textarea>' +
												  '</div>' +
												  '<button type="submit" class="btn btn-primary send-button-reply" id="send-button-' + data + '">Send</button>' +
												  '<button type="button" class="btn btn-info close-button" id="close-button-' + data + '">Close</button>' +
												'</li>' +	
											'</ul>' +
										  '</div>' +
				        			  '</ul>';
                bindButtons();
                closeCommentTextarea(commentIdVal);
            }, error: function(data){
                comment.innerHTML = "Server Error.";
            }
       });
    }
    
    
    
    function bindClose(){
    	for(var closeButton of closeButtons){
    		closeButton.addEventListener('click', bindCloseCommentTextarea, false);
    		function bindCloseCommentTextarea(){
    			let commentId = this.getAttribute("id").split("-")[2];
    			console.log(commentId);
    			
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
		    	console.log(commentId);
		    	
		    	commentTextareaDiv = document.getElementById("reply-textarea-div-" + commentId);
		    	commentTextareaDiv.classList.remove("d-none");
		    	
		    	commentTextarea = document.getElementById("reply-textarea-" + commentId);
		    	commentTextarea.focus();
		    	commentTextarea.addEventListener('input', autoResize, false)
		    };
	    }
    }
}