<textarea id="content" name="content" class="form-control">${view.content?html}</textarea>

<script type="text/javascript">
seajs.use('editor', function(editor) {
	editor.init(function () {
		$('#content').removeClass('form-control');
	});
});
</script>
