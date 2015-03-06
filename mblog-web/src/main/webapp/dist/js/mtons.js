/**
 * MTONS UI (Javascript Extension Tools) 
 *
 * @version  1.0.1
 * @author   langhsu(<a href="mailto:langhsu@gmail.com">langhsu@gmail.com</a>)   
 * 
 */

/**
 * @description
 * Package: Mtons
 *
 * Dependencies:
 * jQuery.
 * 
 */

(function ($) {
    // Define
    var mtons = window.mtons || {},
    uuid = 0;

    // J = jQuery;
    mtons.version = '1.0.1';
    
    var type = function (obj) { return typeof obj;};
    var isFunction = function (obj) { return type(obj) == "function";};
    var isObject = function (obj) { return type(obj) == "object";};
    var isArray = function(o) {
        return o && (o.constructor === Array || Object.prototype.toString.call(o) === "[object Array]");
    };
    
    mtons = {
        type: type,
        
        isFunction: isFunction,
        
        isObject: isObject,
        
        isArray: isArray,
        
        noop : function () {
            //I've got nothing to do
        },
        
        /**
         * 格式化字符串
         
         * @param {String} 含占位符({index}/{property}})的字符串模板
         * @param {Object|...} arg {argument index} / {propertyName}
         * @example
         *         1. mtons.format('<div class="{0}">{1}</div>', 'box', 'text');
         *         2. mtons.format('<div class="{cls}">{t}</div>', {cls: 'box', t: 'text'});
         *         //output of both: <div class="box">text</div>
         */
        format: function (str, args) {
            if (!isObject(args)) {
                args = mtons.slice(arguments, 1);
            }
            return str.replace(/(^|[^\\])\{(\w+)\}/g, function (m, p, index) {
                var x = args[index];
                return (p || '') + (x !== undefined ? x : '');
            });
        },
        
        /**
         * 从已有的数组中返回选定的元素
         * 
         * Same as Array.slice
         * 
         * @param {Array} array
         * @param {Number} from 起始位置
         * @param {Number} end  结束位置
         * @return {Array} new array
         */
        slice: function (array, from, end) {
            var len = array.length, i, arr;
            from = from || 0;
            end = end || len;
            try {
                return Array.prototype.slice.call(array, from, end);
            } catch (e) {
                if (from < 0) {
                    from += len;
                }
                
                if (end < 0) {
                    end += len;
                }
                
                for (i = from, len = array.length, arr = []; i < end && i < len; i += 1) {
                    arr.push(array[i]);
                }
                return arr;
            }
        },
        
        /**
         * 遍历对象、数组,执行回调方法
         *
         * @param {Object|Array} obj
         * @param {Function} callback(index, object)
         */
        each: function (obj, callback) {
            if (callback) {
                var i = 0;
                if (isArray(obj)) {
                    for ( ; i < length; i++ ) {
                        var val = callback.call( obj[ i ], i, obj[ i ] );

                        if ( val === false ) {
                            break;
                        }
                    }
                } else { 
                    for ( i in obj ) {
                        var val = callback.call( obj[ i ], i, obj[ i ] );

                        if ( val === false ) {
                            break;
                        }
                    }
                }
            }
        },
        
        /**
         * 去空格
         * 
         * @param  {String} str 字符串
         * @return {String}
         * @example
         * .trim('    test    ');
         * return test
         */
        trim :function(str){
            return str.replace(/^\s+/, '').replace(/\s+$/, '');
        }
    };
        
    /**
     * 扩展目标对象
     * 
     * 从源对象复制属性到目标对象
     * 
     * @param {Mixed} dest 被扩展的对象或数组
     * @param {Mixed} source {Object|...} 用来参照扩展的对象或数组
     * @return {Mixed} 返回被扩展后的对象或数组
     * 
     * @example
     *         // 用 B ...  扩展 A 对象;
     *        mtons.extend(A, B ...);
     */
    var extend = function(dest) {
        var i = 1, source;
        var args = mtons.slice(arguments, i);
        
        if (!dest || !args) return dest;
        
        for ( ; i < arguments.length; i++) {
            if ( (source = arguments[ i ]) ) {
                for (var idx in source) {
                    var field = source[idx];
                    
                    if (dest[idx] === field) continue;
                    
                    if (isObject(idx) && !isArray(idx) && field !== null) {
                        extend(dest[idx], field);
                        
                    } else if (field !== undefined) {
                        dest[idx] = field;
                    }
                }
            }
        }
        return dest;
    };
    
    var Class = function () {};
    
    /**
     * 给Class添加扩展方法
     * 
     * @param {Mixed} proto
     * @returns {Function}
     * 
     * @example
     *         var Smile = mtons.Class.extend({
     *            init: function() {
     *                this.name = 'super';
     *            },
     *            smile : function() {
     *                alert(this.name);
     *            }
     *        });
     *        
     *        var s = new Smile();
     */
    Class.extend = function(proto) {
        var that = this,
            klass = function() {},
            fn, // super
            subclass = proto && proto.init ? proto.init : function () {
                that.apply(this, arguments);
            };
        klass.prototype = that.prototype;
        fn = subclass.fn = subclass.prototype = new klass();
        
        extend(fn, proto);
        
        // 重新指定构造函数
        fn.constructor = subclass;
        
        // 保证每个子类都含有extend方法
        subclass.extend = that.extend;
        return subclass;
    };
    
    Class.prototype = {
        defaults : {},
        /**
         * 初始化 options
         * @param {Array} options
         */
        _initOptions : function(options) {
            var opts = options || {};
            this.options = $.extend({}, this.defaults, opts);
        },
        /**
         * 延迟执行
         * 
         * @param {Object} context 上下文
         * @param {Number} defer 毫秒
         * @param {Mixed} args 参数（可空）
         *
         * @returns {Function}
         *
         * @example
         * function test(n){
         *     console.log("defer out" + n);
         * }
         * 
         * new mtons.Class().defer(test, test, 1000)(1000);
         */
        defer : function (context, fn, defer) {
            var timerID = null;
            return function(args) {
                clearTimeout(timerID); // 互斥执行
                timerID = setTimeout(function () { fn.apply(context, [args] || []); }, defer);
            };
        }
    };
    
    /**
     * UI 管理器, 提供对实例化的组件进行管理
     */
    mtons.Register = {
        register : {},
        get : function (type, id) {
            var widgets = this.register[type] || [];
            return widgets ? widgets[id] : null;
        },
        add : function (type, widget) {
            var widgets = this.register[type] || [];
            widgets[widget.id] = widget;
            this.register[type] = widgets;
        },
        size : function (type) {
            if (!type) {
                return this.register.length;
            } else {
                return this.register[type] ? this.register[type].length : 0;
            }
        },
        remove : function(type, id) {
            if (this.register[type]) {
                var _widget = this.register[type][id];
                if (_widget) {
                    _widget.destroy && _widget.destroy();
                }
                delete this.register[type][id];
            }
        }
    };
    
    var Event = function () {};
    
    /**
     * 提供基本的事件支持, 简化事件操作流程
     * 且针对子类 加入了对 options.disabled 的嗅探
     *
     * @tips 当 options 中包含 'disabled', 则不会对该操作进行响应
     */
    Event = Class.extend({
        _events : {},
        /**
         * 绑定一个事件，并且返回一个对象用于解绑该事件
         *
         * e = event.bind('test',callback);
         * e.unbind();
         *
         * @param {String} eventType 事件的名称
         * @param {Function} handler 该事件的回调函数
         * @param {Object} context 上下文
         */
        bind: function (eventType, handler, context) {
            /*
             * 支持数组形式批量绑定
             * event.bind({
             * click : {handler : function() {}, context : this}
             * });
             */ 
            if (eventType.constructor === Array) {
                for (var idx in eventType) {
                    var evt = eventType[idx];
                    this.bind(idx, evt.handler, evt.context);
                }
                return;
            }
            if (typeof handler != 'function') return false;
                
            var name = eventType.toLowerCase(),
                event = this._events[name] || [],
                state = context || this;
            
            event.push({handler : handler, context: state});
            this._events[name] = event;
        },

        /**
         * 解除事件绑定
         *
         * e = event.bind('test',callback);
         * e.unbind('test',callback)
         *
         * @param  {String} eventType  事件的名称
         * @param  {Function} handle 该事件的回调函数
         * @return {Boolean} 解绑成功返回true
         */
        unbind: function (eventType, handler) {
            // 不传参数清除所有事件
            if (!eventType) {
                this._events = {};
                return;
            }
            var eventQueue = [], name = eventType.toLowerCase();

            if (this._events[name]) eventQueue = this._events[name];
                
            // 如果没有handler清除所有此类型的事件
            if (!handler) {
                delete this._events[name];
            } else {
                for (var i = 0, l = eventQueue.length; i < l; i++) {
                    if (eventQueue[i].handler == handler) {
                        eventQueue.splice(i, 1);
                        break;
                    }
                }
            }
        },
        /**
         * 触发一个事件，可以使用该事件的命名空间来触发一组事件
         *
         * e = event.bind('test',callback);
         * e.trigger('test');
         *
         * @param  {String} eventType  事件的名称
         * @param  {Object} args 传入该事件回调的参数
         * @return {Boolean} 返回true
         */
        trigger: function (eventType, args) {
            if (!eventType) {
                return;
            }
            
            var eventQueue = [], name = eventType.toLowerCase();

            if (args && args.options.disabled && args.options.disabled == true) {
                return false;
            }
            
            if (this._events[name]) eventQueue = this._events[name];
                
            for (var i = 0; i < eventQueue.length; i++) {
                var evt = eventQueue[i];
                if (evt.handler.apply(evt.context, args) == false) {
                    return false;
                }
            }
        },
        hasEvent: function (eventType) {
            var event = this._events[eventType.toLowerCase()];
            if (event && event.length) return true;
            return false;
        }
    });
    
    var Widget = function () {};
    
    /**
     * UI组件基类
     * 
     * 拥有'init','_create','destroy'生命周期; 继承 Event
     * 
     * @tips Widget 被定义为用于继承的基类, 通常不会被直接实例化
     */
    Widget = Event.extend({
        /**
         * Id, 如果没有指定则自动生成(格式: widget-uuid)
         * 该Id也为实例化后可以根据此ID获取实例对象
         */
        id : '',
        
        name : 'widget',
        
        /**
         * 存储实例化的插件实例对象,可以通过插件的名字获取
         */
        _plugins: [],
        
        /**
         * 初始化widget
         * @param element 触发该widget操作的element
         * @param options 
         */
        init: function (element, options) {
            this._initOptions(options);
            
            var opts = this.options;
            
            //指定该实例的Id
            this.id = opts.id ? opts.id : 'widget-' + (uuid++);
            
            if (element) {
                this.element = (element instanceof $ ? element : $(element));
            }
            
            this._create();
            
            // 缓存该对象以便获取
            mtons.Register.add(this.name, this);
        },
        
        _create: function () {
            if (!this._rendered) {
                this.renderUI();
                this.bindEvents();
                this._rendered = true;
            }
        },
        
        /**
         * 通过该方法判断该组件是否已渲染
         * @return true/false
         */
        isRender: function () {
            return this._rendered;
        },
        
        /**
         * 渲染UI, 用于组件重写
         */
        renderUI: function() {},
        
        /**
         * 事件绑定
         */
        bindEvents: function() {},
        
        addPlugin : function (plugin) {
            this._plugins.push(plugin);
        },
        /**
         * 销毁该组件
         */
        destroy: function () {
            this.container = null;
            
            if (this.element) {
                this.element.undelegate();
            }
            this.unbind();
            if (this._plugins) {
                $.each(this._plugins, function(plugin){
                    plugin.destroy && plugin.destroy();
                });
            }
            mtons.Register.remove(this.name, this.id);
        }
    });
    
    /* -------------------------------------------
     * ----  Extension jQuery : extend plugin ----
     * -------------------------------------------
     */
    $.extend($.fn, {
        /**
         * 设置 box model 宽度
         */
        _outerWidth : function(width){
            if (typeof(width) == 'undefined'){
                if (this[0] == window){
                    return this.width() || document.body.clientWidth;
                }
                return this.outerWidth()||0;
            }
            return this.each(function(){
                if ($._boxModel){
                    $(this).width(width - ($(this).outerWidth() - $(this).width()));
                } else {
                    $(this).width(width);
                }
            });
        },
        
        /**
         * 设置 box model 高度
         */
        _outerHeight : function(height){
            if (typeof(height) == 'undefined'){
                if (this[0] == window){
                    return this.height() || document.body.clientHeight;
                }
                return this.outerHeight()||0;
            }
            return this.each(function(){
                if ($._boxModel){
                    $(this).height(height - ($(this).outerHeight() - $(this).height()));
                } else {
                    $(this).height(height);
                }
            });
        },
        
        /**
         * 设置元素中滚动条的水平偏移
         */
        _scrollLeft : function(left){
            if (typeof(left) == 'undefined'){
                return this.scrollLeft();
            } else {
                return this.each(function () {
                    $(this).scrollLeft(left);
                });
            }
        }
    });
    
    $(function(){
        // 判断浏览器盒模型
        var d = $('<div style="position:absolute;top:-1000px;width:100px;height:100px;padding:5px"></div>').appendTo('body');
        d.width(100);
        $._boxModel = parseInt(d.width()) == 100;
        d.remove();
    });
    
    extend(mtons, {
        extend : extend,
        Class : Class,
        Event : Event,
        Widget : Widget,
        Register : mtons.Register,

        /**
         * 将mtons下的插件桥接到jQuery
         * @param {String} name 插件名称
         * @param {Function} widget 对应的插件Class
         */
        bridgeTojQuery : function (name, widget) {
            $.fn[name] = function (options) {
                return this.each(function () {
                    var _widget = $.data(this, "widget-" + name);
                    
                    if (options == "destroy") {
                        if (_widget) {
                            _widget.destroy();
                            mtons.Register.remove(_widget.name, _widget.id);
                            $(this).removeData("widget-" + name);
                            return true;
                        }
                        return false;
                    }
                    options = options || {};
                    //不要重复绑定
                    if (!_widget) {
                        _widget = new widget(this, options);
                        $.data(this, "widget-" + name, _widget);
                    }
                    
                });
            };
        },
        out : function(message){
            msg = String(message);
            if (console) {
                if (console.log) {
                    console.log(message);
                } else {
                    alert(msg);
                }
            }
            return msg;
        }
    });
    
    this.mtons = mtons;
    
})(jQuery);