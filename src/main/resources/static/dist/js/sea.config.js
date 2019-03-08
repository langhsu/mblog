seajs.config({
    base: _MTONS.BASE_PATH,
    alias: {
        'plugins': 'dist/js/plugins',

        /* modules */
        'main': 'dist/js/modules/main',
        'authc': 'dist/js/modules/authc',
        'sidebox': 'dist/js/modules/sidebox',
        'post': 'dist/js/modules/post',
        'comment': 'dist/js/modules/comment',
        'avatar': 'dist/js/modules/avatar',
        'editor': 'dist/js/modules/editor',
        'view': 'dist/js/modules/view',
        'validate': 'dist/js/modules/validate',
        'markdown': 'dist/js/modules/markdown',

        /* vendors */
        'pace': 'vendors/pace/pace.min',
        'pjax': 'vendors/pjax/jquery.pjax',
        'lazyload': 'vendors/lazyload/jquery.lazyload',

        'share': 'vendors/share.js/js/social-share.min.js',
        'share-css': 'vendors/share.js/css/share.min.css',

        'tagsinput': 'vendors/bootstrap-tagsinput/bootstrap-tagsinput',

        'highlight':'vendors/highlight/highlight.pack.js',
        'highlight-css': 'vendors/highlight/styles/github.css',

        'validation': 'vendors/jquery-validation/jquery.validate.min.js',
        'validation-additional': 'vendors/jquery-validation/additional-methods.js',
        'validation-localization': 'vendors/jquery-validation/localization/messages_zh.min.js',

        'tinymce': 'vendors/tinymce/tinymce.min',
        'form': 'dist/js/jquery.form.min',

        'owo': 'vendors/owo/OwO.min.js',
        'owo-css': 'vendors/owo/OwO.min.css',

        'codemirror': 'vendors/codemirror/lib/codemirror.js',
        'codemirror-markdown': 'vendors/codemirror/mode/markdown/markdown',
        'codemirror-css': 'vendors/codemirror/lib/codemirror.css',
        'codemirror-theme': 'vendors/codemirror/theme/idea.css',
        'codemirror-keymap': 'vendors/codemirror/keymap/sublime',

        'marked': 'vendors/marked/marked.min',
        'app.markdown': 'dist/js/app.markdown'
    },

    // 路径配置
    paths: {
        'vendors': _MTONS.BASE_PATH + '/dist/vendors',
        'dist': _MTONS.BASE_PATH + '/dist'
    },

    // 变量配置
    vars: {
        'locale': 'zh-cn'
    },

    charset: 'utf-8',

    debug: false
});

// var __SEAJS_FILE_VERSION = '?v=1.3';
//
// seajs.on('fetch', function(data) {
// 	if (!data.uri) {
// 		return ;
// 	}
//
// 	if (data.uri.indexOf(app.mainScript) > 0) {
// 		return ;
// 	}
//
//    if (/\:\/\/.*?\/assets\/libs\/[^(common)]/.test(data.uri)) {
//        return ;
//    }
//
//    data.requestUri = data.uri + __SEAJS_FILE_VERSION;
//
// });
//
// seajs.on('define', function(data) {
// 	if (data.uri.lastIndexOf(__SEAJS_FILE_VERSION) > 0) {
// 	    data.uri = data.uri.replace(__SEAJS_FILE_VERSION, '');
// 	}
// });