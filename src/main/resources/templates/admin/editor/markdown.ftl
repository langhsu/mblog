<link href="${base}/dist/vendors/codemirror/lib/codemirror.css" rel="stylesheet">
<link href="${base}/dist/vendors/codemirror/theme/idea.css" rel="stylesheet">
<link href="${base}/dist/css/editor.css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="${base}/dist/vendors/codemirror/lib/codemirror.js"></script>
<script type="text/javascript" charset="utf-8" src="${base}/dist/vendors/codemirror/mode/markdown/markdown.js"></script>
<script type="text/javascript" charset="utf-8" src="${base}/dist/vendors/codemirror/keymap/sublime.js"></script>
<script type="text/javascript" charset="utf-8" src="${base}/dist/vendors/marked/marked.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${base}/dist/js/app.markdown.js"></script>
<div class="md-editor">
    <div class="editor-toolbar">
        <button type="button" event="undo">
            <i class="icon fa fa-rotate-left"></i>
        </button>
        <button type="button" event="redo">
            <i class="icon fa fa-rotate-right"></i>
        </button>
        <i class="separator">|</i>
        <button type="button" event="bold">
            <i class="icon fa fa-bold"></i>
        </button>
        <button type="button" event="italic">
            <i class="icon fa fa-italic"></i>
        </button>
        <button type="button" event="h2">
            <i class="icon fa fa-header"></i>
        </button>
        <button type="button" event="blockquote">
            <i class="icon fa fa-quote-left"></i>
        </button>
        <button type="button" event="link">
            <i class="icon fa fa-link"></i>
        </button>
        <button type="button" event="image">
            <i class="icon fa fa-image"></i>
        </button>
        <button type="button" event="uploadimage">
            <i class="icon fa fa-file-image-o"></i>
        </button>
        <button type="button" event="inlinecode">
            <i class="icon fa fa-code"></i>
        </button>
        <i class="separator">|</i>
        <button type="button" class="active" event="premode" data-value="editMode">
            <i class="icon fa fa-tablet"></i>
        </button>
        <button type="button" event="premode" data-value="liveMode">
            <i class="icon fa fa-columns"></i>
        </button>
        <button type="button" event="premode" data-value="previewMode">
            <i class="icon fa fa-desktop"></i>
        </button>
        <i class="separator">|</i>
        <button type="button" event="fullscreen">
            <i class="icon fa fa-arrows-alt"></i>
        </button>
    </div>
    <div class="editor-container editMode">
        <div class="editor-body">
            <textarea id="content" name="content" rows="5" class="form-control" required>${view.content?html}</textarea>
        </div>
        <div class="editor-preview markdown-body">
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        MdEditor.initEditor();
    });
</script>
