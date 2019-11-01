---
# Front matter comment to ensure Jekyll properly reads file.
---

document.addEventListener('DOMContentLoaded', function(){ 
    var isOpen = false;
    var menubutton = document.getElementById('js-menubutton');
    var menu = document.getElementById('js-menu');
    var openClass = "navigation-is-open"; 

    function trigger() {
        if (isOpen) {
            document.body.classList.remove(openClass);
            this.setAttribute('aria-expanded', 'false');
            menu.hidden = true;
        } else {
            document.body.classList.add(openClass);
            this.setAttribute('aria-expanded', 'true');
            menu.hidden = false;
        }
        isOpen = !isOpen;
    }

    menubutton.addEventListener('click', trigger, false);
});
