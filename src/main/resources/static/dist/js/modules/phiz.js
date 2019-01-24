/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/

/*
 * 表情输入
 * date: 2015-7-14
 * version: 1.0
 */
define(function(require, exports, module) {
	J = jQuery;
	require('plugins');
	
	$.fn.jphiz = function(options){
		$.jphiz(this, options);
	};
	
	$.jphiz = function(box, options) {
		var self, opts, phizItem;
		
		self = $(box);
		opts = $.extend($.jphiz.options, options);
		
		phizItem = $.format("<span><a href='javascript:;' title='{0}'><img src='{1}'/></a></span>");

		// 点击表情图标
		self.bind("click", function(){
			if (Phiz.isHidden()) {
				// 显示表情窗口
				Phiz.showBox();
				// 显示表情
				if($("span", Phiz.box()).length > 0){
					return false;
				}
				$.each(Phiz.phizs, function(k, v){
					$("[view='c-phizs']", Phiz.box()).append( phizItem(k, opts.base + "/images/phiz/" + v + ".gif'"));
				});

				// 点击某个表情
				$("[view='c-phizs']>span>a").bind("click", function(){
					var t = $('#' + opts.textId);
					var content = t.val() + "[" + $(this).attr("title") + "]";
					t.val(content);
				});
			} else {
				Phiz.hideBox();
			}
			return false;
		});

		//点击页面，隐藏表情窗口

		$("body").click(function(){
			if(!Phiz.isHidden()){
				Phiz.hideBox();
			}
		});

	};
	
	$.jphiz.options = {
		base: '.',
		textId: 'ipt-text',
		lnkBoxId: 'btn-lnk',
		phizBoxId: 'v-phizTab'
	};
	
	var jphizOpts = $.jphiz.options;
	
	Phiz = {
		// 定义表情 
		phizs : {
			"微笑":"smile","撇嘴":"curl","色":"color","发呆":"trance","得意":"proud",
			"流泪":"tears","害羞":"shy","闭嘴":"shut","睡":"sleep","大哭":"crying",
			"尴尬":"embarrass","发怒":"torment","调皮":"naughty","龇牙":"growl","惊讶":"surprise",
			"难过":"sad","酷":"cool","冷汗":"cold","抓狂":"crazy","吐":"spit",
			"偷笑":"titter","可爱":"lovely","白眼":"whiteeye","傲慢":"arrogant","饥饿":"hunger",
			"困":"sleepy","惊恐":"panic","流汗":"sweating","憨笑":"mirth","大兵":"soldier",
		    "奋斗":"fight","咒骂":"curse","疑问":"doubt","嘘…":"hiss","晕":"Halo",
		    "折磨":"torture","衰":"wane","骷髅":"skeleton","敲打":"beating","再见":"goodbye",
		    "擦汗":"wipe","抠鼻":"pullnose","鼓掌":"applause","糗大了":"humiliating","坏笑":"grin",
		    "左哼哼":"lefthum","右哼哼":"righthum","哈欠":"yawn","鄙视":"despise","委屈":"aggrieved",
		    "快哭了":"gonnacry","阴险":"sinister","亲亲":"kiss","吓":"scared","可怜":"poor",
		    "菜刀":"cookknife","西瓜":"watermelon","啤酒":"beer","篮球":"basketball","兵乓":"pingpang",
		    "咖啡":"coffee","饭":"rice","猪头":"pig","玫瑰":"rose","凋谢":"withered",
		    "示爱":"affection","爱心":"heart","心碎":"brokenheart","蛋糕":"cake","闪电":"lighting",
		    "炸弹":"bomb","刀":"knife","足球":"soccer","瓢虫":"ladybug","便便":"shit",
		    "月亮":"moon","太阳":"sun","礼物":"gift","拥抱":"hug","强":"strong",
		    "弱":"weak","握手":"hands","胜利":"victory","抱拳":"holdfist","勾引":"seduce",
		    "拳头":"fist","差劲":"bad","爱你":"loveu","NO":"NO","OK":"OK"
		},
		
		box: function() {
			return $('#' + jphizOpts.phizBoxId);
		},
		
		lnk: function() {
			return $('#' + jphizOpts.lnkBoxId);
		},
		
		isHidden: function(){
			return this.box().css("display") == "none" ? true : false;
		},
		
		showBox: function(offset) {
			this.box().slideDown("300");
			this.lnk().hide();
		},
		
		hideBox: function() {
			this.box().slideUp("300");
			this.lnk().show();
		}
	};
});