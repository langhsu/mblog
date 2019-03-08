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

    var Format = {
        undo: function undo(editor) {
            editor.undo();
        },

        redo: function redo(editor) {
            editor.redo();
        },

        setBold: function setBold(editor) {
            editor.replaceSelection(`**${editor.getSelection()}**`);
            var cursor = editor.getCursor();
            editor.setCursor({
                line: cursor.line,
                ch: cursor.ch - 2
            });
            editor.focus();
        },

        setItalic: function setItalic(editor) {
            editor.replaceSelection(`*${editor.getSelection()}*`);
            var cursor = editor.getCursor();
            editor.setCursor({
                line: cursor.line,
                ch: cursor.ch - 1
            });
            editor.focus();
        },

        setBlockQuote: function setBlockQuote(editor) {
            var cursorLine = editor.getCursor().line;
            editor.replaceRange('> ', {
                line: cursorLine,
                ch: 0
            }, {
                line: cursorLine,
                ch: 0
            });
            editor.focus();
        },

        setHeader: function setHeader(editor, level) {
            var cursorLine = editor.getCursor().line;
            switch (level) {
                case 1:
                    editor.replaceRange('# ', {
                        line: cursorLine,
                        ch: 0
                    }, {
                        line: cursorLine,
                        ch: 0
                    });
                    break;
                case 2:
                    editor.replaceRange('## ', {
                        line: cursorLine,
                        ch: 0
                    }, {
                        line: cursorLine,
                        ch: 0
                    });
                    break;
                case 3:
                    editor.replaceRange('### ', {
                        line: cursorLine,
                        ch: 0
                    }, {
                        line: cursorLine,
                        ch: 0
                    });
                    break;
                case 4:
                    editor.replaceRange('#### ', {
                        line: cursorLine,
                        ch: 0
                    }, {
                        line: cursorLine,
                        ch: 0
                    });
                    break;
                case 5:
                    editor.replaceRange('##### ', {
                        line: cursorLine,
                        ch: 0
                    }, {
                        line: cursorLine,
                        ch: 0
                    });
                    break;
                default:
                    break;
            }
            editor.focus();
        },
        setLink: function setLink(editor) {
            editor.replaceSelection(`[](https://)`);
            var cursor = editor.getCursor();
            editor.setCursor({
                line: cursor.line,
                ch: cursor.ch - 11
            });
            editor.focus();
        },

        setImage: function setImage(editor) {
            editor.replaceSelection(`![]()`);
            var cursor = editor.getCursor();
            editor.setCursor({
                line: cursor.line,
                ch: cursor.ch - 3
            });
            editor.focus();
        },

        setInlineCode: function setInlineCode(editor) {
            editor.replaceSelection(`\`${editor.getSelection()}\``);
            var cursor = editor.getCursor();
            editor.setCursor({
                line: cursor.line,
                ch: cursor.ch - 1
            });
            editor.focus();
        },

        setPreMode: function setPreMode() {
            $('div#markdown-container').removeClass('markdown-container-mdmode');
            $('div#preview-container').removeClass('preview-container-mdmode');
            $('div#markdown-container').toggleClass('markdown-container-premode');
            $('div#preview-container').toggleClass('preview-container-premode');
        }
    };

    require.async(['codemirror-markdown', 'codemirror-keymap'], function () {
        var editor = CodeMirror.fromTextArea(document.getElementById("content"), {
            mode: 'markdown',     // Markdown
            lineNumbers: true,     // 显示行数
            indentUnit: 4,         // 缩进4格
            tabSize: 4,
            autoCloseBrackets: true,
            matchBrackets: true,   // 括号匹配
            lineWrapping: true,    // 自动换行
            highlightFormatting: true,
            theme: 'idea',
            keyMap: 'sublime',
            extraKeys: {"Enter": "newlineAndIndentContinueMarkdownList"}
        });
        editor.setSize('auto', '450px');

        editor.on('change', function (cm, co) {
            $('#content').text(cm.getValue());
        });

        // Toolbar click

        $('div.editor-toolbar').on('click', 'li', function () {

            var event = $(this).attr('event');
            switch (event) {
                case 'undo':
                    Format.undo(editor);
                    break;
                case 'redo':
                    Format.redo(editor);
                    break;
                case 'bold':
                    Format.setBold(editor);
                    break;
                case 'italic':
                    Format.setItalic(editor);
                    break;
                case 'blockquote':
                    Format.setBlockQuote(editor);
                    break;
                case 'h1':
                    Format.setHeader(editor, 1);
                    break;
                case 'h2':
                    Format.setHeader(editor, 2);
                    break;
                case 'h3':
                    Format.setHeader(editor, 3);
                    break;
                case 'h4':
                    Format.setHeader(editor, 4);
                    break;
                case 'h5':
                    Format.setHeader(editor, 5);
                    break;
                case 'link':
                    Format.setLink(editor);
                    break;
                case 'image':
                    Format.setImage(editor);
                    break;
                case 'inlinecode':
                    Format.setInlineCode(editor);
                    break;
                case 'premode':
                    Format.setPreMode();
                    editor.focus();
                    break;
                default:
                    break;
            }
        });
    });
});