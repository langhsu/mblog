$(document).ready(function() {

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
        if (checked) {
            $(this).closest('table').find('input[type=checkbox]').attr('checked', 'checked');
        } else {
            $(this).closest('table').find('input[type=checkbox]').removeAttr('checked');
        }
    });
	
	$('a[nav]').each(function(){  
        var $this = $(this);
        if($this[0].href == String(window.location)){  
            $this.closest('li').addClass("active-menu");
        }  
    });

    $('[data-toggle="tooltip"]').tooltip();
});
