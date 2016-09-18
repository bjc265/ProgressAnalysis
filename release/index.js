$(document).ready(function () {
    $('.depth').each(function () {
        var $this = $(this);
        var shift = $this.data('depth') * 20;
        $this.css('transform', 'translate(' + shift + 'px)');
    })
});