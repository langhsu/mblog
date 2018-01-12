/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/

/**
 * 侧边栏
 */
define(function(require, exports, module) {
	J = jQuery;
	require('plugins');
	
	var Sidebox = {
        init : function (options) {
        	this.options = $.extend({}, this.defaults, options);
        },
        defaults: {
			latestUrl : '',
        	hotUrl : '',
			hotTagUrl : '',
			hotUserUrl:'',
        	size :6,
            // callback
            onLoadLatest : function (i, data) {},
            onLoadHot : function (i, data) {}
        },
        
        onLoad : function () {
        	var opts = this.options;
			var that = this;

			// 手机端跳过加载侧边栏
			if(mblog && (mblog.browser.ios || mblog.browser.android)) {
				return false;
			}
        	// load hots
			if (opts.hotUrl) {

				J.ajax( {
					url: opts.hotUrl,
					data: {size : opts.size},
					cache : true,
					success: function (ret) {
						$('#hots').empty();
						if(ret && ret.length > 0){
							jQuery.each(ret, function(i, n) {
								var item = opts.onLoadHot.call(that, i, n);
								$('#hots').append(item);
							});
						} else {
							$('#hots').append('<li class="cat-item cat-item-6"><span>沒有相关记录</span></li>');
						}
					}
				} );
			}

			if (opts.latestUrl) {
				J.ajax( {
					url: opts.latestUrl,
					data: {size : opts.size},
					cache : true,
					success: function (ret) {
						$('#latests').empty();
						if (ret && ret.length > 0) {
							jQuery.each(ret, function (i, n) {
								var item = opts.onLoadLatest.call(that, i, n);
								$('#latests').append(item);
							});
						} else {
							$('#latests').append('<li class="cat-item cat-item-6"><span>沒有相关记录</span></li>');
						}
					}
				} );
			}

			if (opts.hotUserUrl) {
				J.ajax( {
					url: opts.hotUserUrl,
					data: {size : opts.size},
					cache : true,
					success: function (ret) {
						$('#hotuser').empty();
						if (ret && ret.length > 0) {
							jQuery.each(ret, function (i, n) {
								var item = opts.onLoadHotUser.call(that, i, n);
								$('#hotuser').append(item);
							});
						} else {
							$('#hotuser').append('<li class="cat-item cat-item-6"><span>沒有相关记录</span></li>');
						}
					}
				} );
			}
			
        }
    };
	
	exports.init = function (opts) {
		Sidebox.init(opts);
		Sidebox.onLoad();
	}
	
});