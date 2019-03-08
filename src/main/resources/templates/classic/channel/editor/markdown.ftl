<div class="mk-editor">
    <div class="editor-toolbar">
        <ul class="editor-toolbar-menu">
            <li class="item" event="undo">
                <span class="icon icon-action-undo"></span>
            </li>
            <li class="item" event="redo">
                <span class="icon icon-action-redo"></span>
            </li>
            <li class="item" event="link">
                <span class="icon icon-link"></span>
            </li>
            <li class="item" event="image">
                <span class="icon icon-picture"></span>
            </li>
            <!--
            <li class="item" event="premode">
                <span class="icon icon-screen-desktop"></span>
            </li>
            -->
        </ul>
    </div>
    <div class="editor-container">
        <textarea id="content" name="content" rows="5" class="form-control" required>${view.content?html}</textarea>
    </div>
</div>
<script type="text/javascript">
    seajs.use('markdown');
</script>
