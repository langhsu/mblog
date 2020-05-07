<textarea id="content" name="content" rows="5" class="form-control" required>${view.content?html}</textarea>

<script type="text/javascript">
seajs.use('editor', function(editor) {
	editor.init(function () {
		$('#content').removeClass('form-control');
	});
});
</script>
