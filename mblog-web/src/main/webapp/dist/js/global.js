// 插件
(function($){
	var uuid = 0;
	
	var App = function() {};
	App.browser = function() {
       var u = navigator.userAgent;
       return {//移动终端浏览器版本信息 
            trident: u.indexOf('Trident') > -1, //IE内核
            presto: u.indexOf('Presto') > -1, //opera内核
            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
            mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //是否为移动终端
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
            iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
            iPad: u.indexOf('iPad') > -1, //是否iPad
            webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
        };
    }();
	
	this.App = App;
	
	// 注册上传插件
	jQuery.fn.extend({
		upload: function(url, callback){
			var self = this;
			var frameId = "ajax-pload-" + (++uuid);
			var $frame = $("<iframe id='" + frameId + "' name='" + frameId + "' style='position:absolute; top:-9999px;display:none;'/>").appendTo("body");
			var $form = $("<form action='" + url + "' target='" + frameId + "' method='post' enctype='multipart/form-data' style='display:none;'/>");
			
			var nfile = self.clone(true);
			self.attr("id", "uf-" + uuid);
			self.before(nfile);
			self.detach().appendTo($form);
			
			$form.appendTo("body");
			
			$form.submit(function() {
				$frame.load(function() {
					var contents = $frame.contents().get(0);
					var data = $(contents).find('body').html();
					data = (data ? $.parseJSON(data) : {});
					$form.remove();
					setTimeout( function() {
						$frame.remove();
						if (callback) {
							callback.call(self, data);
						}
					}, 1000);
				});
				return true;
			}).submit();
			return this;
		}
	});
	
	jQuery.extend({
		/**
	     * 使用参数格式化字符串
	     * source：字符串模式 abcdef{0}-{1}，
	     * params：参数数组，参数序号对应模式中的下标
	     */
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
	    },
	});
	
})(window.jQuery);

// 插件代码/结束

var wpexLocalize = {
	"mobileMenuOpen" : "Click here to navigate",
	"mobileMenuClosed" : "Close navigation",
	"isOriginLeft" : "1"
};


jQuery( function($) {
	
	$(document).ready(function(){
		
		// Vars
		var $window = $(window);
		
		// 图片懒加载
		$("img").lazyload({
	   		 placeholder : "$base/images/spinner.gif",
	   		 effect      : "fadeIn"
	   	});
		
		// Lightbox
		$('.wpex-lightbox').magnificPopup({ type: 'image' });
		$('.wpex-gallery-lightbox').each(function() {
			$(this).magnificPopup({
				delegate: 'a',
				type: 'image',
				gallery: {
					enabled:true
				}
			});
		});

		// Back to top link
		$scrollTopLink = $( 'a.site-scroll-top' );
		$window.scroll(function () {
			if ($(this).scrollTop() > 100) {
				$scrollTopLink.fadeIn();
			} else {
				$scrollTopLink.fadeOut();
			}
		});		
		$scrollTopLink.on('click', function() {
			$( 'html, body' ).animate({scrollTop:0}, 400);
			return false;
		} );
		
		// Masonry
		var $container = $('.masonry-grid');
		$container.imagesLoaded(function(){
			// FlexSlider run after images are loaded
			$container.masonry({
				itemSelector: '.masonry-entry',
				transitionDuration: '0.3s',
				isOriginLeft: wpexLocalize.isOriginLeft
			});
		});

		// Infinite scroll
		var $container = $('.masonry-grid');
		$container.infinitescroll( {
			loading: {
				msg: null,
				finishedMsg : null,
				msgText : null,
				msgText: '<div class="infinite-scroll-loader"><i class="fa fa-spinner fa-spin"></i></div>',
			},
				navSelector  : 'div.page-jump',
				nextSelector : 'div.page-jump div.older-posts a',
				itemSelector : '.masonry-entry',
			},
			// trigger Masonry as a callback
			function( newElements ) {
				// hide new items while they are loading
				var $newElems = $( newElements ).css({ opacity: 0 });
				// ensure that images load before adding to masonry layout
				$newElems.imagesLoaded(function(){
					// show elems now they're ready
					$newElems.animate({ opacity: 1 });
					$container.masonry( 'appended', $newElems, true );
					// Self hosted audio and video
//					jQuery(newElements).find('audio,video').mediaelementplayer();
					
					// Lightbox
					$('.wpex-gallery-lightbox').each(function() {
						$(this).magnificPopup({
							delegate: 'a',
							type: 'image',
							gallery: {
								enabled:true
							}
						});
					});
			});
		});

	}); // End doc ready
	
});