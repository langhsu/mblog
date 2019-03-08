$(function () {
    var page = String(window.location);

    /**
     * 记住下拉框选中值
     */
    $('select[data-select]').each(function () {
        var id = $(this).attr('data-select');
        if (typeof(id) != 'undefined' && id.length > 0) {
            $(this).val(id);
        }
    });

    $('.checkall').on('click', function (event) {
        var checked = $(this).prop('checked');
        $(this).closest('table').find('input[type=checkbox]').prop('checked', checked);
    });

    $('.sidebar-menu a').each(function () {
        var $this = $(this);
        var href = $this[0].href;
        if (href === page) {
            $this.closest('li').addClass("active");
        } else if (href.substring(0, href.lastIndexOf('/')) === page.substring(0, page.lastIndexOf('/'))) {
            $this.closest('li').addClass("active");
        }
    });

    $('[data-toggle="tooltip"]').tooltip();
});
