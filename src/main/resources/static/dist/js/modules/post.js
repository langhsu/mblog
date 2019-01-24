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

	var PostView = function () {};
	
	PostView.prototype = {
        name : 'PostView',
        init : function () {
        	this.bindEvents();
        },
        defaults: {
        	type : 'text',
        	defaultEditor: 'ueditor',
        	maxFiles : 6,
        },
        bindEvents : function () {
        	var that = this;

        	that.bindTagit();
        	that.bindValidate();
        	that.bindUpload();
        },
        
        bindTagit : function () {
        	$('#tags').tagit({
                singleField: true,
                singleFieldNode: $('#fieldTags'),
                tagLimit: 4
            });
        },
        
        bindUpload : function () {
            $('#upload_btn').change(function(){
                $(this).upload(app.base + '/post/upload?crop=1&width=360&height=200', function(data){
                    if (data.status == 200) {
                        var path = app.base + data.path;
                        $("#thumbnail_image").css("background", "url(" + path + ") no-repeat scroll center 0 rgba(0, 0, 0, 0)");
                        $("#thumbnail").val(data.path);
                    }
                });
            });
        },

        bindValidate: function () {
        	$('form').validate({
                onKeyup: true,
                onChange: true,
                eachValidField: function () {
                    $(this).closest('div').removeClass('has-error').addClass('has-success');
                },
                eachInvalidField: function () {
                    $(this).closest('div').removeClass('has-success').addClass('has-error');
                },
                conditional: {
                    content: function () {
                        return $(this).val().trim().length > 0;
                    }
                },
                description: {
                    content: {
                        required: '<div class="alert alert-danger"><button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>写点内容吧</div>'
                    }
                }
            });
        }
    };
	
	exports.init = function () {
		new PostView().init();
	}
	
});