/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/

define(function(require, exports, module) {
	J = jQuery;
	require('plugins');

	var upload_url = _MTONS.BASE_PATH + '/settings/avatar';

	$('#upload_btn').change(function(){
		$(this).upload(upload_url, function(data){
            if (data.status == 200) {
				window.location.reload();
			} else {
                layer.msg(data.message, {icon: 5});
			}
		});
	});
	
});