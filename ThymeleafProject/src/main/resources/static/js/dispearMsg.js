// Hide all elements with class 'dispearMsg' after a custom time (default 2000ms)
document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.dispearMsg').forEach(function(msg) {
        var time = parseInt(msg.getAttribute('data-dispear-time')) || 2000;
        setTimeout(function() {
            msg.style.display = 'none';
        }, time);
    });
});
