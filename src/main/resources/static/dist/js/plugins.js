/*! jQuery UI - v1.9.0 - 2012-10-05
* http://jqueryui.com
* Includes: jquery.ui.core.js
* Copyright 2012 jQuery Foundation and other contributors; Licensed MIT */
(function(e,t){function i(t,n){var r,i,o,u=t.nodeName.toLowerCase();return"area"===u?(r=t.parentNode,i=r.name,!t.href||!i||r.nodeName.toLowerCase()!=="map"?!1:(o=e("img[usemap=#"+i+"]")[0],!!o&&s(o))):(/input|select|textarea|button|object/.test(u)?!t.disabled:"a"===u?t.href||n:n)&&s(t)}function s(t){return!e(t).parents().andSelf().filter(function(){return e.css(this,"visibility")==="hidden"||e.expr.filters.hidden(this)}).length}var n=0,r=/^ui-id-\d+$/;e.ui=e.ui||{};if(e.ui.version)return;e.extend(e.ui,{version:"1.9.0",keyCode:{BACKSPACE:8,COMMA:188,DELETE:46,DOWN:40,END:35,ENTER:13,ESCAPE:27,HOME:36,LEFT:37,NUMPAD_ADD:107,NUMPAD_DECIMAL:110,NUMPAD_DIVIDE:111,NUMPAD_ENTER:108,NUMPAD_MULTIPLY:106,NUMPAD_SUBTRACT:109,PAGE_DOWN:34,PAGE_UP:33,PERIOD:190,RIGHT:39,SPACE:32,TAB:9,UP:38}}),e.fn.extend({_focus:e.fn.focus,focus:function(t,n){return typeof t=="number"?this.each(function(){var r=this;setTimeout(function(){e(r).focus(),n&&n.call(r)},t)}):this._focus.apply(this,arguments)},scrollParent:function(){var t;return e.browser.msie&&/(static|relative)/.test(this.css("position"))||/absolute/.test(this.css("position"))?t=this.parents().filter(function(){return/(relative|absolute|fixed)/.test(e.css(this,"position"))&&/(auto|scroll)/.test(e.css(this,"overflow")+e.css(this,"overflow-y")+e.css(this,"overflow-x"))}).eq(0):t=this.parents().filter(function(){return/(auto|scroll)/.test(e.css(this,"overflow")+e.css(this,"overflow-y")+e.css(this,"overflow-x"))}).eq(0),/fixed/.test(this.css("position"))||!t.length?e(document):t},zIndex:function(n){if(n!==t)return this.css("zIndex",n);if(this.length){var r=e(this[0]),i,s;while(r.length&&r[0]!==document){i=r.css("position");if(i==="absolute"||i==="relative"||i==="fixed"){s=parseInt(r.css("zIndex"),10);if(!isNaN(s)&&s!==0)return s}r=r.parent()}}return 0},uniqueId:function(){return this.each(function(){this.id||(this.id="ui-id-"+ ++n)})},removeUniqueId:function(){return this.each(function(){r.test(this.id)&&e(this).removeAttr("id")})}}),e("<a>").outerWidth(1).jquery||e.each(["Width","Height"],function(n,r){function u(t,n,r,s){return e.each(i,function(){n-=parseFloat(e.css(t,"padding"+this))||0,r&&(n-=parseFloat(e.css(t,"border"+this+"Width"))||0),s&&(n-=parseFloat(e.css(t,"margin"+this))||0)}),n}var i=r==="Width"?["Left","Right"]:["Top","Bottom"],s=r.toLowerCase(),o={innerWidth:e.fn.innerWidth,innerHeight:e.fn.innerHeight,outerWidth:e.fn.outerWidth,outerHeight:e.fn.outerHeight};e.fn["inner"+r]=function(n){return n===t?o["inner"+r].call(this):this.each(function(){e(this).css(s,u(this,n)+"px")})},e.fn["outer"+r]=function(t,n){return typeof t!="number"?o["outer"+r].call(this,t):this.each(function(){e(this).css(s,u(this,t,!0,n)+"px")})}}),e.extend(e.expr[":"],{data:e.expr.createPseudo?e.expr.createPseudo(function(t){return function(n){return!!e.data(n,t)}}):function(t,n,r){return!!e.data(t,r[3])},focusable:function(t){return i(t,!isNaN(e.attr(t,"tabindex")))},tabbable:function(t){var n=e.attr(t,"tabindex"),r=isNaN(n);return(r||n>=0)&&i(t,!r)}}),e(function(){var t=document.body,n=t.appendChild(n=document.createElement("div"));n.offsetHeight,e.extend(n.style,{minHeight:"100px",height:"auto",padding:0,borderWidth:0}),e.support.minHeight=n.offsetHeight===100,e.support.selectstart="onselectstart"in n,t.removeChild(n).style.display="none"}),e.fn.extend({disableSelection:function(){return this.bind((e.support.selectstart?"selectstart":"mousedown")+".ui-disableSelection",function(e){e.preventDefault()})},enableSelection:function(){return this.unbind(".ui-disableSelection")}}),e.extend(e.ui,{plugin:{add:function(t,n,r){var i,s=e.ui[t].prototype;for(i in r)s.plugins[i]=s.plugins[i]||[],s.plugins[i].push([n,r[i]])},call:function(e,t,n){var r,i=e.plugins[t];if(!i||!e.element[0].parentNode||e.element[0].parentNode.nodeType===11)return;for(r=0;r<i.length;r++)e.options[i[r][0]]&&i[r][1].apply(e.element,n)}},contains:e.contains,hasScroll:function(t,n){if(e(t).css("overflow")==="hidden")return!1;var r=n&&n==="left"?"scrollLeft":"scrollTop",i=!1;return t[r]>0?!0:(t[r]=1,i=t[r]>0,t[r]=0,i)},isOverAxis:function(e,t,n){return e>t&&e<t+n},isOver:function(t,n,r,i,s,o){return e.ui.isOverAxis(t,r,s)&&e.ui.isOverAxis(n,i,o)}})})(jQuery);

/*! jQuery UI - v1.9.0 - 2012-10-05
* http://jqueryui.com
* Includes: jquery.ui.widget.js
* Copyright 2012 jQuery Foundation and other contributors; Licensed MIT */
(function(e,t){var n=0,r=Array.prototype.slice,i=e.cleanData;e.cleanData=function(t){for(var n=0,r;(r=t[n])!=null;n++)try{e(r).triggerHandler("remove")}catch(s){}i(t)},e.widget=function(t,n,r){var i,s,o,u,a=t.split(".")[0];t=t.split(".")[1],i=a+"-"+t,r||(r=n,n=e.Widget),e.expr[":"][i.toLowerCase()]=function(t){return!!e.data(t,i)},e[a]=e[a]||{},s=e[a][t],o=e[a][t]=function(e,t){if(!this._createWidget)return new o(e,t);arguments.length&&this._createWidget(e,t)},e.extend(o,s,{version:r.version,_proto:e.extend({},r),_childConstructors:[]}),u=new n,u.options=e.widget.extend({},u.options),e.each(r,function(t,i){e.isFunction(i)&&(r[t]=function(){var e=function(){return n.prototype[t].apply(this,arguments)},r=function(e){return n.prototype[t].apply(this,e)};return function(){var t=this._super,n=this._superApply,s;return this._super=e,this._superApply=r,s=i.apply(this,arguments),this._super=t,this._superApply=n,s}}())}),o.prototype=e.widget.extend(u,{widgetEventPrefix:t},r,{constructor:o,namespace:a,widgetName:t,widgetBaseClass:i,widgetFullName:i}),s?(e.each(s._childConstructors,function(t,n){var r=n.prototype;e.widget(r.namespace+"."+r.widgetName,o,n._proto)}),delete s._childConstructors):n._childConstructors.push(o),e.widget.bridge(t,o)},e.widget.extend=function(n){var i=r.call(arguments,1),s=0,o=i.length,u,a;for(;s<o;s++)for(u in i[s])a=i[s][u],i[s].hasOwnProperty(u)&&a!==t&&(n[u]=e.isPlainObject(a)?e.widget.extend({},n[u],a):a);return n},e.widget.bridge=function(n,i){var s=i.prototype.widgetFullName;e.fn[n]=function(o){var u=typeof o=="string",a=r.call(arguments,1),f=this;return o=!u&&a.length?e.widget.extend.apply(null,[o].concat(a)):o,u?this.each(function(){var r,i=e.data(this,s);if(!i)return e.error("cannot call methods on "+n+" prior to initialization; "+"attempted to call method '"+o+"'");if(!e.isFunction(i[o])||o.charAt(0)==="_")return e.error("no such method '"+o+"' for "+n+" widget instance");r=i[o].apply(i,a);if(r!==i&&r!==t)return f=r&&r.jquery?f.pushStack(r.get()):r,!1}):this.each(function(){var t=e.data(this,s);t?t.option(o||{})._init():new i(o,this)}),f}},e.Widget=function(e,t){},e.Widget._childConstructors=[],e.Widget.prototype={widgetName:"widget",widgetEventPrefix:"",defaultElement:"<div>",options:{disabled:!1,create:null},_createWidget:function(t,r){r=e(r||this.defaultElement||this)[0],this.element=e(r),this.uuid=n++,this.eventNamespace="."+this.widgetName+this.uuid,this.options=e.widget.extend({},this.options,this._getCreateOptions(),t),this.bindings=e(),this.hoverable=e(),this.focusable=e(),r!==this&&(e.data(r,this.widgetName,this),e.data(r,this.widgetFullName,this),this._on({remove:"destroy"}),this.document=e(r.style?r.ownerDocument:r.document||r),this.window=e(this.document[0].defaultView||this.document[0].parentWindow)),this._create(),this._trigger("create",null,this._getCreateEventData()),this._init()},_getCreateOptions:e.noop,_getCreateEventData:e.noop,_create:e.noop,_init:e.noop,destroy:function(){this._destroy(),this.element.unbind(this.eventNamespace).removeData(this.widgetName).removeData(this.widgetFullName).removeData(e.camelCase(this.widgetFullName)),this.widget().unbind(this.eventNamespace).removeAttr("aria-disabled").removeClass(this.widgetFullName+"-disabled "+"ui-state-disabled"),this.bindings.unbind(this.eventNamespace),this.hoverable.removeClass("ui-state-hover"),this.focusable.removeClass("ui-state-focus")},_destroy:e.noop,widget:function(){return this.element},option:function(n,r){var i=n,s,o,u;if(arguments.length===0)return e.widget.extend({},this.options);if(typeof n=="string"){i={},s=n.split("."),n=s.shift();if(s.length){o=i[n]=e.widget.extend({},this.options[n]);for(u=0;u<s.length-1;u++)o[s[u]]=o[s[u]]||{},o=o[s[u]];n=s.pop();if(r===t)return o[n]===t?null:o[n];o[n]=r}else{if(r===t)return this.options[n]===t?null:this.options[n];i[n]=r}}return this._setOptions(i),this},_setOptions:function(e){var t;for(t in e)this._setOption(t,e[t]);return this},_setOption:function(e,t){return this.options[e]=t,e==="disabled"&&(this.widget().toggleClass(this.widgetFullName+"-disabled ui-state-disabled",!!t).attr("aria-disabled",t),this.hoverable.removeClass("ui-state-hover"),this.focusable.removeClass("ui-state-focus")),this},enable:function(){return this._setOption("disabled",!1)},disable:function(){return this._setOption("disabled",!0)},_on:function(t,n){n?(t=e(t),this.bindings=this.bindings.add(t)):(n=t,t=this.element);var r=this;e.each(n,function(n,i){function s(){if(r.options.disabled===!0||e(this).hasClass("ui-state-disabled"))return;return(typeof i=="string"?r[i]:i).apply(r,arguments)}typeof i!="string"&&(s.guid=i.guid=i.guid||s.guid||e.guid++);var o=n.match(/^(\w+)\s*(.*)$/),u=o[1]+r.eventNamespace,a=o[2];a?r.widget().delegate(a,u,s):t.bind(u,s)})},_off:function(e,t){t=(t||"").split(" ").join(this.eventNamespace+" ")+this.eventNamespace,e.unbind(t).undelegate(t)},_delay:function(e,t){function n(){return(typeof e=="string"?r[e]:e).apply(r,arguments)}var r=this;return setTimeout(n,t||0)},_hoverable:function(t){this.hoverable=this.hoverable.add(t),this._on(t,{mouseenter:function(t){e(t.currentTarget).addClass("ui-state-hover")},mouseleave:function(t){e(t.currentTarget).removeClass("ui-state-hover")}})},_focusable:function(t){this.focusable=this.focusable.add(t),this._on(t,{focusin:function(t){e(t.currentTarget).addClass("ui-state-focus")},focusout:function(t){e(t.currentTarget).removeClass("ui-state-focus")}})},_trigger:function(t,n,r){var i,s,o=this.options[t];r=r||{},n=e.Event(n),n.type=(t===this.widgetEventPrefix?t:this.widgetEventPrefix+t).toLowerCase(),n.target=this.element[0],s=n.originalEvent;if(s)for(i in s)i in n||(n[i]=s[i]);return this.element.trigger(n,r),!(e.isFunction(o)&&o.apply(this.element[0],[n].concat(r))===!1||n.isDefaultPrevented())}},e.each({show:"fadeIn",hide:"fadeOut"},function(t,n){e.Widget.prototype["_"+t]=function(r,i,s){typeof i=="string"&&(i={effect:i});var o,u=i?i===!0||typeof i=="number"?n:i.effect||n:t;i=i||{},typeof i=="number"&&(i={duration:i}),o=!e.isEmptyObject(i),i.complete=s,i.delay&&r.delay(i.delay),o&&e.effects&&(e.effects.effect[u]||e.uiBackCompat!==!1&&e.effects[u])?r[t](i):u!==t&&r[u]?r[u](i.duration,i.easing,s):r.queue(function(n){e(this)[t](),s&&s.call(r[0]),n()})}}),e.uiBackCompat!==!1&&(e.Widget.prototype._getCreateOptions=function(){return e.metadata&&e.metadata.get(this.element[0])[this.widgetName]})})(jQuery);

/*
* jQuery UI Tag-it!
*
* @version v2.0 (06/2011)
*
* Copyright 2011, Levy Carneiro Jr.
* Released under the MIT license.
* http://aehlke.github.com/tag-it/LICENSE
*
* Homepage:
*   http://aehlke.github.com/tag-it/
*
* Authors:
*   Levy Carneiro Jr.
*   Martin Rehfeld
*   Tobias Schmidt
*   Skylar Challand
*   Alex Ehlke
*
* Maintainer:
*   Alex Ehlke - Twitter: @aehlke
*
* Dependencies:
*   jQuery v1.4+
*   jQuery UI v1.8+
*/
(function($){$.widget("ui.tagit",{options:{allowDuplicates:false,caseSensitive:true,fieldName:"tags",placeholderText:null,readOnly:false,removeConfirmation:false,tagLimit:null,allowSpaces:false,singleField:false,singleFieldDelimiter:",",singleFieldNode:null,animate:true,tabIndex:null,beforeTagAdded:null,afterTagAdded:null,beforeTagRemoved:null,afterTagRemoved:null,onTagClicked:null,onTagLimitExceeded:null,onTagAdded:null,onTagRemoved:null},_create:function(){var that=this;if(this.element.is("input")){this.tagList=$("<ul></ul>").insertAfter(this.element);this.options.singleField=true;this.options.singleFieldNode=this.element;this.element.css("display","none")}else{this.tagList=this.element.find("ul, ol").andSelf().last()}this.tagInput=$('<input type="text" />').addClass("ui-widget-content");if(this.options.readOnly){this.tagInput.attr("disabled","disabled")}if(this.options.tabIndex){this.tagInput.attr("tabindex",this.options.tabIndex)}if(this.options.placeholderText){this.tagInput.attr("placeholder",this.options.placeholderText)}this.tagList.addClass("tagit").addClass("ui-widget ui-widget-content ui-corner-all").append($('<li class="tagit-new"></li>').append(this.tagInput)).click(function(e){var target=$(e.target);if(target.hasClass("tagit-label")){var tag=target.closest(".tagit-choice");if(!tag.hasClass("removed")){that._trigger("onTagClicked",e,{tag:tag,tagLabel:that.tagLabel(tag)})}}else{that.tagInput.focus()}});var addedExistingFromSingleFieldNode=false;if(this.options.singleField){if(this.options.singleFieldNode){var node=$(this.options.singleFieldNode);var tags=node.val().split(this.options.singleFieldDelimiter);node.val("");$.each(tags,function(index,tag){that.createTag(tag,null,true);addedExistingFromSingleFieldNode=true})}else{this.options.singleFieldNode=$('<input type="hidden" style="display:none;" value="" name="'+this.options.fieldName+'" />');this.tagList.after(this.options.singleFieldNode)}}if(!addedExistingFromSingleFieldNode){this.tagList.children("li").each(function(){if(!$(this).hasClass("tagit-new")){that.createTag($(this).text(),$(this).attr("class"),true);$(this).remove()}})}this.tagInput.keydown(function(event){if(event.which==$.ui.keyCode.BACKSPACE&&that.tagInput.val()===""){var tag=that._lastTag();if(!that.options.removeConfirmation||tag.hasClass("remove")){that.removeTag(tag)}else{if(that.options.removeConfirmation){tag.addClass("remove ui-state-highlight")}}}else{if(that.options.removeConfirmation){that._lastTag().removeClass("remove ui-state-highlight")}}if(event.which===$.ui.keyCode.COMMA||event.which===$.ui.keyCode.ENTER||(event.which==$.ui.keyCode.TAB&&that.tagInput.val()!=="")||(event.which==$.ui.keyCode.SPACE&&that.options.allowSpaces!==true&&($.trim(that.tagInput.val()).replace(/^s*/,"").charAt(0)!='"'||($.trim(that.tagInput.val()).charAt(0)=='"'&&$.trim(that.tagInput.val()).charAt($.trim(that.tagInput.val()).length-1)=='"'&&$.trim(that.tagInput.val()).length-1!==0)))){if(!(event.which===$.ui.keyCode.ENTER&&that.tagInput.val()==="")){event.preventDefault()}that.createTag(that._cleanedInput())}}).blur(function(e){that.createTag(that._cleanedInput())})},_cleanedInput:function(){return $.trim(this.tagInput.val().replace(/^"(.*)"$/,"$1"))},_lastTag:function(){return this.tagList.find(".tagit-choice:last:not(.removed)")},_tags:function(){return this.tagList.find(".tagit-choice:not(.removed)")},assignedTags:function(){var that=this;var tags=[];if(this.options.singleField){tags=$(this.options.singleFieldNode).val().split(this.options.singleFieldDelimiter);if(tags[0]===""){tags=[]}}else{this._tags().each(function(){tags.push(that.tagLabel(this))})}return tags},_updateSingleTagsField:function(tags){$(this.options.singleFieldNode).val(tags.join(this.options.singleFieldDelimiter)).trigger("change")},_subtractArray:function(a1,a2){var result=[];for(var i=0;i<a1.length;i++){if($.inArray(a1[i],a2)==-1){result.push(a1[i])}}return result},tagLabel:function(tag){if(this.options.singleField){return $(tag).find(".tagit-label:first").text()}else{return $(tag).find("input:first").val()}},_findTagByLabel:function(name){var that=this;var tag=null;this._tags().each(function(i){if(that._formatStr(name)==that._formatStr(that.tagLabel(this))){tag=$(this);return false}});return tag},_isNew:function(name){return !this._findTagByLabel(name)},_formatStr:function(str){if(this.options.caseSensitive){return str}return $.trim(str.toLowerCase())},_effectExists:function(name){return Boolean($.effects&&($.effects[name]||($.effects.effect&&$.effects.effect[name])))},createTag:function(value,additionalClass,duringInitialization){var that=this;value=$.trim(value);if(this.options.preprocessTag){value=this.options.preprocessTag(value)}if(value===""){return false}if(!this.options.allowDuplicates&&!this._isNew(value)){var existingTag=this._findTagByLabel(value);if(this._trigger("onTagExists",null,{existingTag:existingTag,duringInitialization:duringInitialization})!==false){if(this._effectExists("highlight")){existingTag.effect("highlight")
}}return false}if(this.options.tagLimit&&this._tags().length>=this.options.tagLimit){this._trigger("onTagLimitExceeded",null,{duringInitialization:duringInitialization});return false}var label=$(this.options.onTagClicked?'<a class="tagit-label"></a>':'<span class="tagit-label"></span>').text(value);var tag=$("<li></li>").addClass("tagit-choice ui-widget-content ui-state-default ui-corner-all").addClass(additionalClass).append(label);if(this.options.readOnly){tag.addClass("tagit-choice-read-only")}else{tag.addClass("tagit-choice-editable");var removeTagIcon=$("<span></span>").addClass("ui-icon ui-icon-close");var removeTag=$('<a><span class="text-icon">\xd7</span></a>').addClass("tagit-close").append(removeTagIcon).click(function(e){that.removeTag(tag)});tag.append(removeTag)}if(!this.options.singleField){var escapedValue=label.html();tag.append('<input type="hidden" style="display:none;" value="'+escapedValue+'" name="'+this.options.fieldName+'" />')}if(this._trigger("beforeTagAdded",null,{tag:tag,tagLabel:this.tagLabel(tag),duringInitialization:duringInitialization})===false){return}if(this.options.singleField){var tags=this.assignedTags();tags.push(value);this._updateSingleTagsField(tags)}this._trigger("onTagAdded",null,tag);this.tagInput.val("");this.tagInput.parent().before(tag);this._trigger("afterTagAdded",null,{tag:tag,tagLabel:this.tagLabel(tag),duringInitialization:duringInitialization})},removeTag:function(tag,animate){animate=typeof animate==="undefined"?this.options.animate:animate;tag=$(tag);this._trigger("onTagRemoved",null,tag);if(this._trigger("beforeTagRemoved",null,{tag:tag,tagLabel:this.tagLabel(tag)})===false){return}if(this.options.singleField){var tags=this.assignedTags();var removedTagLabel=this.tagLabel(tag);tags=$.grep(tags,function(el){return el!=removedTagLabel});this._updateSingleTagsField(tags)}if(animate){tag.addClass("removed");var hide_args=this._effectExists("blind")?["blind",{direction:"horizontal"},"fast"]:["fast"];var thisTag=this;hide_args.push(function(){tag.remove();thisTag._trigger("afterTagRemoved",null,{tag:tag,tagLabel:thisTag.tagLabel(tag)})});tag.fadeOut("fast").hide.apply(tag,hide_args).dequeue()}else{tag.remove();this._trigger("afterTagRemoved",null,{tag:tag,tagLabel:this.tagLabel(tag)})}},removeTagByLabel:function(tagLabel,animate){var toRemove=this._findTagByLabel(tagLabel);if(!toRemove){throw"No such tag exists with the name '"+tagLabel+"'"}this.removeTag(toRemove,animate)},removeAll:function(){var that=this;this._tags().each(function(index,tag){that.removeTag(tag,false)})}})})(jQuery);

(function($){
	var uuid = 0;

	function pageLink(curNo, pageNo){
		if(curNo == -1){
			return '<li class="pass"><span>......</span></li>';
		}
		if(pageNo == curNo){
			return $.format('<li class="active"><a href="javascript:void(0)" pn="{0}">{0}</a></li>', pageNo);
		}
		return $.format('<li><a href="javascript:void(0);" pn="{0}">{0}</a></li>', pageNo);
	}

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
					var data = $(contents).find('body').text();
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
		},

		/**
		 * 分页函数
		 * p: paging
		 * callback: 点击分页时调用的函数，参数为pageNo
		 * mini: 是否为迷你型，如果true，则只有"上一页"和"下一页"的按钮
		 */
		page: function(p, callback, mini){
			var pc = parseInt(p.totalPages); 	// 总页数
			if(pc == 0){
				this.html('');
				return this;
			}

			var	c = 3,				// 左右各需要显示的分页数量
				tc = c * 2 + 3, 	// 显示的分页总数
				cp = parseInt(p.number),		//当前页号
				h = [];
			h.push('<ul class="pagination pagination-sm">');
			// 上一页
			if(cp > 1){
				h.push($.format('<li class="previous"><a href="javascript:void(0);" pn="{0}"><span class="arrow" title="上一页">&lt;</span></a></li>', cp - 1));
			}

			// 分页
			if(!mini){
				if(pc > tc){
					if(cp <= c + 2){
						for(var i = 1; i < tc - 1; i++){
							h.push(pageLink(cp, i));
						}
						h.push(pageLink(-1, 0));
						h.push(pageLink(cp, pc));
					}else{
						h.push(pageLink(cp, 1));
						h.push(pageLink(-1, 0));

						if(cp > (pc - (c + 2))){
							for(var i = pc - tc + 2; i <= pc; i++){
								h.push(pageLink(cp, i));
							}
						}else{
							for(var i = cp - c; i < cp + c; i++){
								h.push(pageLink(cp, i));
							}
							h.push(pageLink(-1, 0));
							h.push(pageLink(cp, pc));
						}
					}
				}else{
					for(var i = 1; i <= pc; i++){
						h.push(pageLink(cp, i));
					}
				}
			}

			// 下一页
			if(cp < pc){
				h.push($.format('<li class="next"><a href="javascript:void(0);" pn="{0}"><span class="arrow" title="下一页">&gt;</span></a></li>', cp + 1));
			}

			h.push('</ul>');

			// 添加事件
			this.html(h.join(''));
			this.find('a').bind('click', function(){
				var pn = $(this).attr('pn');
				if(0 < pn && pn <= pc){
					callback(pn);
				}
				return false;
			});

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
	    }

	});

})(window.jQuery);