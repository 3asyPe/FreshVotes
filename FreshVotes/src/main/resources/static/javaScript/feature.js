textarea1 = document.querySelector("#textarea");
textarea2 = document.querySelector("#textarea-comment");

textarea1.addEventListener('input', autoResize, false);
textarea2.addEventListener('input', autoResize, false);

textarea1.style.height = 'auto';
textarea1.style.height = textarea1.scrollHeight + 'px'

textarea2.style.height = 'auto';
textarea2.style.height = textarea2.scrollHeight + 'px'
  
function autoResize() { 
    this.style.height = 'auto'; 
    this.style.height = this.scrollHeight + 'px'; 
} 

window.onload=function() {
    var comments = document.getElementById("commentsDiv");
    var commentsHTML= comments.innerHTML;
    var name=$("#name");
    var text=$("#textarea-comment");
    var sendButton=$("#send-button");
    var postUrl = window.location.href + "/comments";
    const csrfToken = document.getElementById("csrfToken").value;
    var textarea = document.getElementById("textarea-comment");

    sendButton.click(function( event ) {
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
                comments.innerHTML = '<ul>' +
			                		      '<li class="media">' +
				        			          '<img src="..." class="mr-3" alt="...">' +
				        			  	      '<div class="media-body">' +
				        			  		      '<h5 class="mt-0">' + name.val() + '</h5>' +
				        			  		      '<div class="comment-text">' + text.val() + '</div>' +
				        			  	      '</div>' +
				        			      '</li>' +
				        			  '</ul>' +
				        			  comments.innerHTML;
                textarea.value = "";
                textarea.style.height = 'auto';
                textarea.style.height = textarea.scrollHeight + 'px'
            }, error: function(data){
                comments.html("Server Error.");
            }
       });
	});
}