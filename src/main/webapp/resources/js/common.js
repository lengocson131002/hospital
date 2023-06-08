document.addEventListener("DOMContentLoaded", function(){
    window.addEventListener('scroll', function() {
        let navbar_height = document.querySelector('.navbar').offsetHeight;
        if (window.scrollY > navbar_height) {
            document.getElementById('navbar_top').classList.add('fixed-top');
            // add padding top to show content behind navbar
            document.body.style.paddingTop = (navbar_height + 10) + 'px';
        } else {
            document.getElementById('navbar_top').classList.remove('fixed-top');
            // remove padding top from body
            document.body.style.paddingTop = '0';
        }
    });
});

$('.alert .btn-close').on('click', function(e) {
    $(this).parent().hide();
});

$(function() {
    $('ul.nav li a[href$="' + window.location.pathname+ '"]').parent().addClass("active");
});
