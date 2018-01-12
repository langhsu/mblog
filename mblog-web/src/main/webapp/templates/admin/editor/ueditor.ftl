<link href="${base}/dist/vendors/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">

<script type="text/javascript" charset="utf-8" src="${base}/dist/vendors/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${base}/dist/vendors/ueditor/ueditor.all.min.js"></script>

<textarea id="view_content" name="content" rows="5" data-required data-describedby="message" data-description="content">${view.content}</textarea>

<script type="text/javascript">
function setContent(content) {
	if (content != null && content.length > 0) {
    	if (mblog.browser.ios || mblog.browser.android) {
    		$('#view_content').text(content);
    	} else {
    		UE.getEditor('view_content').setContent(content);
    	}
	}
}

$(function () {
	var ueditor = UE.getEditor('view_content', {
		fastUpload: "${base}/aj_um_upload",
		fastFileName: 'upfile',
		fastUrlPrefix: '${base}',
		imageAllowFiles: [".png", ".jpg", ".jpeg", ".gif", ".bmp"], /* 上传图片格式显示 */

		wordCount: true,
		maximumWords: 2000,
		initialFrameWidth: '100%',
		initialFrameHeight: 300
	});
})
</script>
