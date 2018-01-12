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
	var Authc = require('authc');
	
	ContentConstants = {
		id : 0,
		type: 1,
		url: function(p){
			return app.base + p;
		}
	};

	ContentPost= {
		// 定义表情
		phizs : {"微笑":"smile","撇嘴":"curl","色":"color","发呆":"trance","得意":"proud","流泪":"tears","害羞":"shy","闭嘴":"shut","睡":"sleep","大哭":"crying","尴尬":"embarrass","发怒":"torment","调皮":"naughty","龇牙":"growl","惊讶":"surprise",
			"难过":"sad","酷":"cool","冷汗":"cold","抓狂":"crazy","吐":"spit","偷笑":"titter","可爱":"lovely","白眼":"whiteeye","傲慢":"arrogant","饥饿":"hunger","困":"sleepy","惊恐":"panic","流汗":"sweating","憨笑":"mirth","大兵":"soldier",
			"奋斗":"fight","咒骂":"curse","疑问":"doubt","嘘…":"hiss","晕":"Halo","折磨":"torture","衰":"wane","骷髅":"skeleton","敲打":"beating","再见":"goodbye","擦汗":"wipe","抠鼻":"pullnose","鼓掌":"applause","糗大了":"humiliating","坏笑":"grin",
			"左哼哼":"lefthum","右哼哼":"righthum","哈欠":"yawn","鄙视":"despise","委屈":"aggrieved","快哭了":"gonnacry","阴险":"sinister","亲亲":"kiss","吓":"scared","可怜":"poor","菜刀":"cookknife","西瓜":"watermelon","啤酒":"beer","篮球":"basketball","兵乓":"pingpang",
			"咖啡":"coffee","饭":"rice","猪头":"pig","玫瑰":"rose","凋谢":"withered","示爱":"affection","爱心":"heart","心碎":"brokenheart","蛋糕":"cake","闪电":"lighting","炸弹":"bomb","刀":"knife","足球":"soccer","瓢虫":"ladybug","便便":"shit",
			"月亮":"moon","太阳":"sun","礼物":"gift","拥抱":"hug","强":"strong","弱":"weak","握手":"hands","胜利":"victory","抱拳":"holdfist","勾引":"seduce","拳头":"fist","差劲":"bad","爱你":"loveu","NO":"NO","OK":"OK"
		}
	};

	ContentRender = {
		// 分解内容的正则(表情)
		CONT_EXP: /(\[[…\u00FF-\uFFFF]{1,3}\]|.+?)/g,
		CONT_BR: /\r\n/g,
		CONT_NBSP: /[ ]/g,
		// 替换内容中的特殊内容
		_wrap: function(){
			// 匹配串
			var ms = arguments[0];
			if(ms.length == 1){
				switch(ms.charAt(0)){
					case '<':
						return "&lt;";
					case '>':
						return "&gt;";
					default:
						return ms;
				}
			}else{
				switch(ms.charAt(0)){
					case '#':
						return "<a href='/t/" + ms.slice(1, -1) + "'>" + ms + "</a>";
					case '/':
						var k = ms.slice(3, -1);
						return "//<a href='/k/u/name/" + k + "'>@" + k + "</a>:";
					case '[':
						var k = ms.slice(1, -1);
						return "<img src='"+ContentConstants.url("/dist/images/phiz/" + ContentPost.phizs[k] + ".gif")+"' alt='" + k + "' title='" + k + "'/>";
					default:
						return ms;
				}
			}
		},
		// 包装消息内容
		wrapItem: function(content){
			return content.replace(ContentRender.CONT_EXP, ContentRender._wrap);
		},
		template: function(template){
			return jQuery.format(template);
		}
	};
	
	var Comment = {
        name : 'Comment',
		contentRender: ContentRender,
        init : function (options) {
        	this.options = $.extend({}, this.defaults, options);
        	this.bindEvents();
        },
        defaults: {
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
        },
        
        onLoad : function () {
        	this.pageCallback(1);
        },
        
        pageCallback: function (pn) {
        	var opts = this.options;
        	var that = this;
        	
        	var $list = $("#chat_container");
        	var html = '';

        	J.getJSON(opts.load_url, {pageSize : opts.pageSize, pn: pn}, function (ret) {
        		
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