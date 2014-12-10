(function(jQuery) {  
  
    jQuery.fn.lazyload = function(options) {  
        var settings = {  
            threshold    : 0,  
            failurelimit : 0,  
            event        : "scroll",  
            effect       : "show",  
            container    : window  
        };  
                  
        if(options) {  
            jQuery.extend(settings, options);  
        }  
  
        /* Fire one scroll event per scroll. Not one scroll event per image. */  
        var elements = this;  
        if ("scroll" == settings.event) {  
            jQuery(settings.container).bind("scroll", function(event) {  
                  
                var counter = 0;  
                elements.each(function() {  
                    if (jQuery.abovethetop(this, settings)) {  
                            /* Nothing. */  
                    } else if (!jQuery.belowthefold(this, settings)) {  
                            jQuery(this).trigger("appear");  
                    } else {  
                        if (counter++ > settings.failurelimit) {  
                            return false;  
                        }  
                    }  
                });  
                /* Remove image from array so it is not looped next time. */  
                var temp = jQuery.grep(elements, function(element) {  
                    return !element.loaded;  
                });  
                elements = jQuery(temp);  
            });  
        }  
          
        this.each(function() {  
            var self = this;  
              
            /* Save original only if it is not defined in HTML. */  
            if (undefined != jQuery(self).attr("original")){  
                self.loaded = false;  
  
                /* When appear is triggered load original image. */  
                jQuery(self).one("appear", function() {  
                    if (!this.loaded) {  
                        jQuery("<img />")  
                            .bind("load", function() {  
                                jQuery(self)  
                                    .hide()  
                                    .removeAttr("height")  
                                    .attr("src", jQuery(self).attr("original"))  
                                    [settings.effect](settings.effectspeed);  
                                self.loaded = true;  
                            })  
                            .attr("src", jQuery(self).attr("original"));  
                    };  
                });  
            }  
        });  
          
        /* Force initial check if images should appear. */  
        jQuery(settings.container).trigger(settings.event);  
          
        return this;  
    };  
  
    /* Convenience methods in jQuery namespace.           */  
    /* Use as  jQuery.belowthefold(element, {threshold : 100, container : window}) */  
      
    function checkshow(){  
          
      
    }  
      
    jQuery.belowthefold = function(element, settings) {  
        if (settings.container === undefined || settings.container === window) {  
            var fold = jQuery(window).height()*2 + jQuery(window).scrollTop();  
        } else {  
            var fold = jQuery(settings.container).offset().top + jQuery(settings.container).height()*2;  
        }         
        return fold <= jQuery(element).offset().top - settings.threshold;  
  
    };  
      
    jQuery.abovethetop = function(element, settings) {  
        if (settings.container === undefined || settings.container === window) {  
            var fold = jQuery(window).scrollTop();  
        } else {  
            var fold = jQuery(settings.container).offset().top;  
        }  
        return fold >= jQuery(element).offset().top + settings.threshold  + jQuery(element).height();  
    };  
    /* Custom selectors for your convenience.   */  
    /* Use as jQuery("img:below-the-fold").something() */  
  
    jQuery.extend(jQuery.expr[':'], {  
        "below-the-fold" : "jQuery.belowthefold(a, {threshold : 0, container: window})",  
        "above-the-fold" : "!jQuery.belowthefold(a, {threshold : 0, container: window})"  
    });  
      
})(jQuery);