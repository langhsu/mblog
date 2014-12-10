jQuery( function($) {
	
	$(document).ready(function(){

		// Vars
		var $window = $(window);

		// Main menu superfish
		$('ul.sf-menu').superfish({
			delay: 200,
			animation: {opacity:'show', height:'show'},
			speed: 'fast',
			cssArrows: false,
			disableHI: true
		});

		// Prepend Mobile menu
		$('.site-main-wrap').prepend('<nav class="wpex-mobile-nav"></nav>');
		// Grab all content from menu and add into wpex-mobile-nav element
		var mobileMenuContents = $('.main-nav').html();
		$('.wpex-mobile-nav').html('<ul class="wpex-mobile-nav-ul">' + mobileMenuContents + '</ul>');
		// Remove all classes inside prepended nav
		$('.wpex-mobile-nav-ul, .wpex-mobile-nav-ul *').children().each(function() {
			var attributes = this.attributes;
			$(this).removeAttr("style");
		});
		// Add classes where needed
		$('.wpex-mobile-nav-ul').addClass('container');
		$('.wpex-mobile-nav-ul .menu-item-has-children').children('a').prepend('<i class="fa fa-plus wpex-mobile-dropdown-icon"></i>');
		// Dropdown clicks
		var clickHandler = ('ontouchstart' in document.documentElement ? "touchstart" : "click");
		$('.wpex-mobile-dropdown-icon').bind(clickHandler, function(e) {
			//e.preventDefault();
			if ( $(this).hasClass("wpex-clicked")) {
				$(this).addClass('fa-plus');
				$(this).removeClass('fa-minus');
				$(this).parent().next('ul').hide();
			} else {
				$(this).removeClass('fa-plus');
				$(this).addClass('fa-minus');
				$(this).parent().next('ul').show();
			}
			$(this).toggleClass("wpex-clicked");
			return false; // Better support for theme customizer
		});
		// Main toggle
		$('.navigation-toggle').click( function(e) {
			//e.preventDefault();
			var txt = $(".wpex-mobile-nav").is(':visible') ? wpexLocalize.mobileMenuOpen : wpexLocalize.mobileMenuClosed;
			$(this).children('.navigation-toggle-text').text(txt);
			$(this).children('.navigation-toggle-icon').toggleClass('fa-bars fa-times');
			$('.wpex-mobile-nav').toggle();
			return false; // Better support for theme customizer
		});
		// Close on orientation change
		$( window ).on( "orientationchange", function( event ) {
			$('.wpex-mobile-nav').hide();
			$('.navigation-toggle-icon').removeClass('fa-times');
			$('.navigation-toggle-icon').addClass('fa-bars');
			$('.navigation-toggle-text').text(wpexLocalize.mobileMenuOpen);
		});

		// Sticky Nav
		$(".sticky-nav").sticky({topSpacing:0});

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

		// Gallery slider
		function wpex_gallery_slider() {
			$('div.post-gallery .flexslider').flexslider( {
				slideshow : true,
				animation : 'fade',
				pauseOnHover: true,
				animationSpeed : 400,
				smoothHeight : true,
				directionNav: true,
				controlNav: false,
				prevText : '<span class="fa fa-caret-left"></span>',
				nextText : '<span class="fa fa-caret-right"></span>'
			});
		}

		// Masonry
		var $container = $('.masonry-grid');
		$container.imagesLoaded(function(){
			// FlexSlider run after images are loaded
			wpex_gallery_slider();
			$container.masonry({
				itemSelector: '.loop-entry',
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
				itemSelector : '.loop-entry',
			},
			// trigger Masonry as a callback
			function( newElements ) {
				// hide new items while they are loading
				var $newElems = $( newElements ).css({ opacity: 0 });
				// ensure that images load before adding to masonry layout
				$newElems.imagesLoaded(function(){
					// Slider
					wpex_gallery_slider();
					// show elems now they're ready
					$newElems.animate({ opacity: 1 });
					$container.masonry( 'appended', $newElems, true );
					// Self hosted audio and video
//					jQuery(newElements).find('audio,video').mediaelementplayer();
					
					jQuery(newElements).find('img').lazyload({
			    		 placeholder : "../images/spinner.gif",
			    		 effect      : "fadeIn"
			    	});
					
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

		// Fluid Videos
		fluidvids.init({
			selector: '.entry iframe', // runs querySelectorAll()
			players: ['www.youtube.com', 'player.vimeo.com'] // players to support
		});
		
	}); // End doc ready
	
});