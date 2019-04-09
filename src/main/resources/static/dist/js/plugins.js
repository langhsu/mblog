(function($){
	var uuid = 0;

	function pageLink(curNo, pageNo){
		if(curNo === -1){
			return '<li class="pass"><span>...</span></li>';
		}
		if(pageNo === curNo){
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
			if(pc === 0){
				this.html('');
				return this;
			}

			var	c = 3,				// 左右各需要显示的分页数量
				tc = c * 2 + 3, 	// 显示的分页总数
				cp = parseInt(p.number) + 1,		//当前页号
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