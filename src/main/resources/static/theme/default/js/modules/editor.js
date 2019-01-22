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

    var initEditor = function (callback) {
        require.async(['form', 'tinymce'], function () {
            var options = {
                selector: "#content",
                theme: 'modern',
                upload_image_url: window.app.base + "/post/upload", //配置的上传图片的路由
                height: 400,
                plugins: [
                    'fullpage advlist autolink autosave charmap lists link image print anchor codesample',
                    'searchreplace visualblocks code fullscreen textcolor colorpicker textpattern uploadimage',
                    'contextmenu paste'
                ],
                toolbar: "undo redo | formatselect | bold underline blockquote | alignleft aligncenter alignright | " +
                "forecolor bullist numlist | link unlink | uploadimage codesample removeformat | fullscreen ",
                menubar: false,
                language: "zh_CN",
                statusbar : false,
                body_class: 'markdown-body',
                codesample_dialog_width: '600',
                codesample_dialog_height: '400',
                block_formats: 'Paragraph=p;标题1=h4;标题2=h5;标题3=h6;Preformatted=pre',
                paste_data_images: true,
                entity_encoding: 'raw',
                content_css: [
                    window.app.base + '/dist/vendors/bootstrap/css/bootstrap.min.css',
                    window.app.base + '/theme/default/css/editor.css',
                ]
                //参考网站 https://www.tinymce.com/
            };

            if ($(window).width() < 900) {
                options.theme = 'mobile';
                options.toolbar = "undo redo | bold underline blockquote | alignleft aligncenter alignright | uploadimage"
            }
            tinymce.init(options);

            callback.call(this);
        });

    };

	exports.init = function (callback) {
        initEditor(callback);
    };
});