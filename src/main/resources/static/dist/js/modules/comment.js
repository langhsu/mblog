/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/

define(function(require, exports, module) {
	J = jQuery;
	require('plugins');
    require('owo-css');
    require('owo');

	var Authc = require('authc');
	
	var Comment = {
        name : 'Comment',
        init : function (options) {
        	this.options = $.extend({}, this.defaults, options);

        	if (!this.options.load) {
        		return false;
			}
        	this.bindEvents();
        },
        defaults: {
        	load: true,
        	load_url : null,
        	post_url : null,
        	toId : 0,
			pageSize :6,
            // callback
            onLoad : function (i, data) {}
        },
        bindEvents : function () {
        	var that = this;
        	that.pageCallback(1);
        	$('#btn-chat').click(function () {
        		var text = $('#chat_text').val();
        		var pid = $('#chat_pid').val();
        		that.post(that.options.toId, pid, text);
        	});

            new OwO({
                logo: '<i class="fa fa-smile-o fa-2"></i>',
                container: document.getElementById('face-btn'),
                target: document.getElementById('chat_text'),
                api: _MTONS.BASE_PATH + '/dist/vendors/owo/OwO.json',
                position: 'down',
                width: '600px',
                maxHeight: '250px'
            });
        },
        
        onLoad : function () {
        	this.pageCallback(1);
        },
        
        pageCallback: function (pn) {
        	var opts = this.options;
        	var that = this;
        	
        	var $list = $("#chat_container");
        	var html = '';

        	J.getJSON(opts.load_url, {pageSize : opts.pageSize, pageNo: pn}, function (ret) {
        		
        		$('#chat_count').html(ret.totalElements);
        		
          		jQuery.each(ret.content, function(i, n) {
    				var item = opts.onLoad.call(this, i, n);

    				html += item;
          		});
        	
	        	$list.empty().append(html);
	        	
	    		if (ret.size < 1) {
	    			$list.append('<li><p>还没有评论, 快来占沙发吧!</p></li>');
	    		}
	    		if (ret.totalPages > 1) {
	    			$("#pager").page(ret, J.proxy(that, 'pageCallback'));
	    		}
        	});
        },
        
        post: function (toId, pid, text) {
        	var opts = this.options;
        	var that = this;

			if (!Authc.isAuthced()) {
				Authc.showLogin();
				return false;
			}

        	if (text.length == 0) {
        		layer.msg('请输入内容再提交!', {icon: 2});
        		return false;
        	}
        	if (text.length > 255) {
        		layer.msg('内容过长，请输入140以内个字符', {icon: 2});
        		return false;
        	}
        	
        	jQuery.ajax({
        		url: opts.post_url, 
        		data: {'toId': toId,'pid': pid, 'text': text},
        		dataType: "json",
        		type :  "POST",
        		cache : false,
        		async: false,
        		error : function(i, g, h) {
        			layer.msg('发送错误', {icon: 2});
        		},
        		success: function(ret){
        			if(ret){
        				if (ret.code >= 0) {
        					layer.msg(ret.message, {icon: 1});
        					$('#chat_text').val('');
        					$('#chat_reply').hide();
        					$('#chat_pid').val('0');
        					//window.location.reload();
        					that.pageCallback(1);
        				} else {
        					layer.msg(ret.message, {icon: 5});
        				}
        			}
              	}
        	});
        }
    };
	
	exports.init = function (opts) {
		Comment.init(opts);
	}
	
});