
(function($){
	var mtons = window.mtons,
	Widget = mtons.Widget,
	J = jQuery;
	
	var Sidebox = Widget.extend({
        name : 'Sidebox',
        init : function (element, options) {
            Widget.fn.init.call(this, element, options);
        },
        defaults: {
        	recentUrl : null,
        	hotUrl : null,
        	maxResults :6,
            // callback
            onLoadRecent : function (i, data) {},
            onLoadHot : function (i, data) {}
        },
        bindEvents : function () {
        	$('#tab-title > span').on('click', function() {
        		if (!$(this).hasClass('selected')) {
        			$(this).addClass('selected').siblings().removeClass('selected');
        			$("#tab-content > div").eq($('#tab-title span').index(this)).show().siblings().hide();
        		}
        	});
        },
        
        onLoad : function () {
        	var opts = this.options;
        	// load recents
        	J.getJSON(opts.hotUrl, {maxResults : opts.maxResults}, function (ret) {
        		if(ret && ret.length > 0){
        			$('#hots').empty();
              		jQuery.each(ret, function(i, n) {
        				var item = opts.onLoadHot.call(this, i, n);
              			$('#hots').append(item);
              		});
              	} else {
              		$('#hots').append('<li class="cat-item cat-item-6"><span>沒有相关记录</span></li>');
              	}
        	});
        	
        	J.getJSON(opts.recentUrl, {maxResults : opts.maxResults}, function (ret) {
        		if(ret && ret.length > 0){
        			$('#recents').empty();
              		jQuery.each(ret, function(i, n) {
        				var item = opts.onLoadRecent.call(this, i, n);
              			$('#recents').append(item);
              		});
              	} else {
              		$('#recents').append('<li class="cat-item cat-item-6"><span>沒有相关记录</span></li>');
              	}
        	});
        },
        destroy : function () {
        }
    });
	
	this.Sidebox = Sidebox;
	
})(window.jQuery);