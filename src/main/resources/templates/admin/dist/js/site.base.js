jQuery.extend({
    format: function(source, params) {
        if ( arguments.length == 1 )
            return function() {
                var args = $.makeArray(arguments);
                args.unshift(source);
                return $.format.apply( this, args );
            };
        if ( arguments.length > 2 && params.constructor != Array  ) {
            params = $.makeArray(arguments).slice(1);
        }
        if ( params.constructor != Array ) {
            params = [ params ];
        }
        $.each(params, function(i, n) {
            source = source.replace(new RegExp('\\{' + i + '\\}', 'g'), n);
        });
        return source;
    }
});
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
        if (checked) {
            $(this).closest('table').find('input[type=checkbox]').attr('checked', 'checked');
        } else {
            $(this).closest('table').find('input[type=checkbox]').removeAttr('checked');
        }
    });
	
	$('.sidebar-menu a').each(function(){
        var $this = $(this);
        if($this[0].href === String(window.location)){
            $this.closest('li').addClass("active");
        }  
    });

    $('[data-toggle="tooltip"]').tooltip();
});
