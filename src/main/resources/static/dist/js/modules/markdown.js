/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/

define('markdown', [
    'codemirror-css',
    'codemirror-theme',
    'codemirror'
    ], function(require, exports, module) {

    require.async(['codemirror-keymap'], function () {
        var editor = CodeMirror.fromTextArea(document.getElementById("content"), {
            mode: 'markdown',     // Markdown
            lineNumbers: true,     // 显示行数
            indentUnit: 4,         // 缩进4格
            tabSize: 4,
            autoCloseBrackets: true,
            matchBrackets: true,   // 括号匹配
            lineWrapping: true,    // 自动换行
            theme: 'idea',
            keyMap: 'sublime',
            extraKeys: {"Enter": "newlineAndIndentContinueMarkdownList"}
        });
        editor.setSize('auto', '450px');

        editor.on('change', function (cm, co) {
            $('#content').text(cm.getValue());
        });
    });
});