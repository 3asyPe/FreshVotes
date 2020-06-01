window.onload=function() {
	var textareas = document.getElementsByClassName("my-textarea");
	
	for (var textarea of textareas){
		textarea.addEventListener('input', autoResize, false);
	}
    
    function autoResize() { 
	    this.style.height = 'auto'; 
	    this.style.height = this.scrollHeight + 'px';
	} 
}