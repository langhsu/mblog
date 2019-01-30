$(function() {
	/**
	 * 记住下拉框选中值
	 */
	$('select[data-select]').each(function() {
		var id = $(this).attr('data-select');
		if (typeof(id) != 'undefined' && id.length > 0) {
			$(this).val(id);
		}
	});
	
	$('.checkall').on('click', function(event) {
		var checked = $(this).prop('checked');
        $(this).closest('table').find('input[type=checkbox]').prop('checked', checked);
    });
	
	$('.sidebar-menu a').each(function(){
        var $this = $(this);
        if($this[0].href === String(window.location)){
            $this.closest('li').addClass("active");
        }  
    });

    $('[data-toggle="tooltip"]').tooltip();
});
