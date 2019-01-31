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
	require('tagsinput');
    require('validation');
    require('validation-additional');
    require('validation-localization');

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
            $('#tags').tagsinput({
                maxTags: 4,
                trimValue: true
            });
        },
        
        bindUpload : function () {
            $('#upload_btn').change(function(){
                $(this).upload(_MTONS.BASE_PATH + '/post/upload?crop=1&width=360&height=240', function(data){
                    if (data.status == 200) {
                        var path = data.path;
                        $("#thumbnail_image").css("background", "url(" + path + ") no-repeat scroll center 0 rgba(0, 0, 0, 0)");
                        $("#thumbnail").val(path);
                    }
                });
            });
        },

        bindValidate: function () {
            $("#submitForm").submit(function () {
                tinyMCE.triggerSave();
            }).validate({
                ignore: "",
                rules: {
                    title: 'required',
                    channelId: 'required',
                    content: {
                        required: true,
                        check_editor: true
                    }
                },
                errorElement: "em",
                errorPlacement: function (error, element) {
                    error.addClass("help-block");
                    if (element.prop("type") === "checkbox") {
                        error.insertAfter(element.parent("label"));
                    } else if (element.is("textarea")) {
                        error.insertAfter(element.next());
                    } else {
                        error.insertAfter(element);
                    }
                },
                highlight: function (element, errorClass, validClass) {
                    $(element).closest("div").addClass("has-error").removeClass("has-success");
                },
                unhighlight: function (element, errorClass, validClass) {
                    $(element).closest("div").addClass("has-success").removeClass("has-error");
                }
            });
        }
    };
	
	exports.init = function () {
		new PostView().init();
	}
	
});