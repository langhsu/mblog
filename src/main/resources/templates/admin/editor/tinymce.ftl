<script type="text/javascript" charset="utf-8" src="${base}/dist/js/jquery.form.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${base}/dist/vendors/tinymce/tinymce.min.js"></script>

<textarea id="content" name="content" rows="5" required>${view.content?html}</textarea>

<script type="text/javascript">
$(function () {
    tinymce.init({
        selector: "#content",
        theme: 'modern',
        language: "zh_CN",
        upload_image_url: "${base}/post/upload", //配置的上传图片的路由
        height: 400,
        plugins: [
            'advlist autolink autosave lists link image print anchor codesample',
            'searchreplace visualblocks code fullscreen textcolor colorpicker textpattern uploadimage',
            'contextmenu paste'
        ],
        toolbar: "undo redo | formatselect bold underline blockquote alignleft aligncenter alignright " +
        "forecolor bullist numlist link unlink uploadimage codesample removeformat fullscreen ",
        menubar: false,
        statusbar : false,
        paste_data_images: true,
        convert_urls: false,
        body_class: 'markdown-body',
        codesample_dialog_width: '600',
        codesample_dialog_height: '400',
        block_formats: 'Paragraph=p;标题1=h4;标题2=h5;标题3=h6;',
        codesample_languages: [
            {text: 'HTML/XML', value: 'html'},
            {text: 'JavaScript', value: 'javascript'},
            {text: 'CSS', value: 'css'},
            {text: 'PHP', value: 'php'},
            {text: 'Ruby', value: 'ruby'},
            {text: 'Python', value: 'python'},
            {text: 'Java', value: 'java'},
            {text: 'C', value: 'c'},
            {text: 'C#', value: 'csharp'},
            {text: 'C++', value: 'cpp'}
        ],
        content_css: [
            '${base}/dist/vendors/bootstrap/css/bootstrap.min.css',
            '${base}/dist/css/editor.css',
        ],
        setup: function(editor) {
            editor.on('change', function(e) {
                tinymce.triggerSave();
                $("#" + editor.id).valid();
            });
        }
        //参考网站 https://www.tinymce.com/
    });
})
</script>
