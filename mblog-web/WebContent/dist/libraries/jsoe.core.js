/*
---
Jsoe

description:
- Javascript Object-Oriented Extend

authors:
- langhsu

requires:
  - 
  
exmple:
var Hello = function(){};
Hello.prototype = {
	init: function(){
	},
	sayHello: function(){
		alert("hello");
	}
};

Hello = Jooe.extend(Hello.prototype);


var Hello = Hello.extend({
	init: function(){
	},
	sayHello: function(){
		this._super.sayHello();
	}
});
...
*/
(function($) {
	var Jsoe = function() {};
	Jsoe.version = "1.0.0";
	
	var extend = function(){		
		var options, name = null, src, copy, copyIsArray = null, clone,
		target = arguments[0] || {},
		i = 1,
		length = arguments.length,
		deep = false;

		// Handle a deep copy situation
		if ( typeof target === "boolean" ) {
			deep = target;
			target = arguments[1] || {};
			// skip the boolean and the target
			i = 2;
		}
	
		// Handle case when target is a string or something (possible in deep copy)
		if ( typeof target !== "object" && !jQuery.isFunction(target) ) {
			target = {};
		}
	
		// extend jQuery itself if only one argument is passed
		if ( length === i ) {
			target = this;
			--i;
		}
	
		for ( ; i < length; i++ ) {
			// Only deal with non-null/undefined values
			if ( (options = arguments[ i ]) != null ) {
				// Extend the base object
				for ( name in options ) {
					src = target[ name ];
					copy = options[ name ];
	
					// Prevent never-ending loop
					if ( target === copy ) {
						continue;
					}
	
					// Recurse if we're merging plain objects or arrays
					if ( deep && copy && ( jQuery.isPlainObject(copy) || (copyIsArray = jQuery.isArray(copy)) ) ) {
						if ( copyIsArray ) {
							copyIsArray = false;
							clone = src && jQuery.isArray(src) ? src : [];
	
						} else {
							clone = src && jQuery.isPlainObject(src) ? src : {};
						}
	
						// Never move original objects, clone them
						target[ name ] = jQuery.extend( deep, clone, copy );
	
					// Don't bring in undefined values
					} else if ( copy !== undefined ) {
						target[ name ] = copy;
					}
				}
			}
		}
	
		// Return the modified object
		return target;
	};
	
	Jsoe.extend = function(proto) {
        var base = function() {},
            that = this,
            subclass = proto && proto.init ? proto.init : function () {
                that.apply(this, arguments);
            },
            fn;

        base.prototype = that.prototype;
        fn = subclass.fn = subclass.prototype = new base();

        for (var member in proto) {
            if (typeof proto[member] === "object" && !(proto[member] instanceof Array) && proto[member] !== null) {
                // Merge object members
                fn[member] = extend(true, {}, base.prototype[member], proto[member]);
            } else {
                fn[member] = proto[member];
            }
        }

        fn.constructor = subclass; // 重新指定构造函数，确保this.init方法被调用
        subclass.extend = that.extend; // 保证每个子类都含有extend方法
        return subclass;
    };
    
    window.Jsoe = Jsoe;
    
})(window.jQuery);