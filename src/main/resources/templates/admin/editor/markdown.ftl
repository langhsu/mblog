<link href="${base}/dist/vendors/codemirror/lib/codemirror.css" rel="stylesheet">
<link href="${base}/dist/vendors/codemirror/theme/idea.css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="${base}/dist/vendors/codemirror/lib/codemirror.js"></script>
<script type="text/javascript" charset="utf-8" src="${base}/dist/vendors/codemirror/keymap/sublime.js"></script>

<textarea id="content" name="content" rows="5" class="form-control" required>${view.content?html}</textarea>

<script type="text/javascript">
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
</script>
