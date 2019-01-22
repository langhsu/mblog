<div class="panel panel-default corner-radius panel-hot-topics">
	<div class="panel-heading">
		<h3 class="panel-title"><i class="fa fa-area-chart"></i> 热门文章</h3>
	</div>
	<div class="panel-body">
		<ul class="list" id="hots">
            <img src="${base}/dist/images/spinner.gif">
		</ul>
	</div>
</div>

<div class="panel panel-default corner-radius panel-hot-topics">
	<div class="panel-heading">
		<h3 class="panel-title"><i class="fa fa-bars"></i> 最新发布</h3>
	</div>
	<div class="panel-body">
		<ul class="list" id="latests">
			<img src="${base}/dist/images/spinner.gif">
		</ul>
	</div>
</div>

<div class="panel panel-default corner-radius panel-hot-topics">
    <div class="panel-heading">
        <h3 class="panel-title"><i class="fa fa-comment-o"></i> 最新评论</h3>
    </div>
    <div class="panel-body">
        <ul class="list" id="comments">
            <img src="${base}/dist/images/spinner.gif">
        </ul>
    </div>
</div>

<script type="text/javascript">

var li_template = '<li>{0}. <a href="${base}/view/{1}">{2}</a></li>';

var comment_li_template = '<li><a href="${base}/view/{0}">{1}</a></li>'

seajs.use('sidebox', function (sidebox) {
	sidebox.init({
        latestUrl : '${base}/api/latests',
    	hotUrl : '${base}/api/hots',
		hotTagUrl : '${base}/api/hot_tags',
		latestCommentUrl:'${base}/api/latest_comments',

        size :10,
        // callback
        onLoadHot : function (i, data) {
      		return jQuery.format(li_template, i + 1, data.id, data.title);
        },
        onLoadLatest : function (i, data) {
      		return jQuery.format(li_template, i + 1, data.id, data.title);
        },
        onLoadLatestComment : function (i, data) {
      		return jQuery.format(comment_li_template, data.toId, data.content);
        }
	});
});
</script>