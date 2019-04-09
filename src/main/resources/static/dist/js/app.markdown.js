var MdEditor = {
    editorId: null,
    format: {
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

        uploadImage: function(editor) {
            var input_f = $('<input type="file" name="file" accept="image/jpg,image/jpeg,image/png,image/gif">');
            input_f.on('change', function () {
                var file = input_f[0].files[0];
                if (!file) {
                    return false;
                }
                var form = new FormData();
                form.append("file", file);
                $.ajax({
                    url: _MTONS.BASE_PATH + "/post/upload",
                    data: form,
                    type: "POST",
                    cache: false, //上传文件无需缓存
                    processData: false, //用于对data参数进行序列化处理 这里必须false
                    contentType: false, //必须
                    success: function (result) {
                        if (result.status === 200) {
                            var image = `![` + result.name + `](` + _MTONS.BASE_PATH + result.path + `)`;
                            editor.replaceSelection(image);
                            var cursor = editor.getCursor();
                            editor.setCursor({
                                line: cursor.line,
                                ch: cursor.ch - 3
                            });
                            editor.focus();
                        } else {
                            layer.alert(result.message);
                        }
                    }
                });
            });

            input_f.click();
        },

        setPreMode: function setPreMode(element, mode, editor) {
			$('button[event=premode].active').removeClass('active');
			element.addClass('active');
			$('.editor-container').removeClass('liveMode editMode previewMode').addClass(mode);
        },
        
        fullscreen: function (editor) {
            var $btn = $('button[event=fullscreen]');
            $btn.toggleClass('active');
            $('.md-editor').toggleClass('fullscreen');
            var height = $(window).height() - 37;
            if ($btn.hasClass('active')) {
                editor.setSize('auto', height + 'px');
            } else {
                editor.setSize('auto', '450px');
            }
        }
    },

    initEditor: function () {
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

        editor.on('change', function (editor) {
            var $content = $('#content');
            $content.text(editor.getValue());
            $('.editor-preview').html(marked(editor.getValue()));
        });
		
		$('.editor-preview').html(marked($('#content').text()));

        // Toolbar click
        $('div.editor-toolbar').on('click', 'button[event]', function () {
            var that = $(this);
            var event = that.attr('event');
            switch (event) {
                case 'undo':
                    MdEditor.format.undo(editor);
                    break;
                case 'redo':
                    MdEditor.format.redo(editor);
                    break;
                case 'bold':
                    MdEditor.format.setBold(editor);
                    break;
                case 'italic':
                    MdEditor.format.setItalic(editor);
                    break;
                case 'blockquote':
                    MdEditor.format.setBlockQuote(editor);
                    break;
                case 'h1':
                    MdEditor.format.setHeader(editor, 1);
                    break;
                case 'h2':
                    MdEditor.format.setHeader(editor, 2);
                    break;
                case 'h3':
                    MdEditor.format.setHeader(editor, 3);
                    break;
                case 'h4':
                    MdEditor.format.setHeader(editor, 4);
                    break;
                case 'h5':
                    MdEditor.format.setHeader(editor, 5);
                    break;
                case 'link':
                    MdEditor.format.setLink(editor);
                    break;
                case 'image':
                    MdEditor.format.setImage(editor);
                    break;
                case 'inlinecode':
                    MdEditor.format.setInlineCode(editor);
                    break;
                case 'uploadimage':
                    MdEditor.format.uploadImage(editor);
                    break;
                case 'premode':
					var mode = that.data('value');
                    MdEditor.format.setPreMode(that, mode, editor);
                    break;
                case 'fullscreen':
                    MdEditor.format.fullscreen(editor);
                    break;
                default:
                    break;
            }
        });
    }
};
