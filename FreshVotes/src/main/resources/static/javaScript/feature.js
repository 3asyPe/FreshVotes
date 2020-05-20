textarea = document.querySelector("#textarea"); 
textarea.addEventListener('input', autoResize, false);
textarea.style.height = 'auto';
textarea.style.height = textarea.scrollHeight + 'px'
  
function autoResize() { 
    this.style.height = 'auto'; 
    this.style.height = this.scrollHeight + 'px'; 
} 