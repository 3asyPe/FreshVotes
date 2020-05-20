textarea1 = document.querySelector("#textarea");
textarea2 = document.querySelector("#textarea-comment");
for(textarea : [textarea1, textarea2]){
	textarea.addEventListener('input', autoResize, false);
	textarea.style.height = 'auto';
	textarea.style.height = textarea.scrollHeight + 'px'
	  
	function autoResize() { 
	    this.style.height = 'auto'; 
	    this.style.height = this.scrollHeight + 'px'; 
	} 
}