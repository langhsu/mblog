<#include "/default/utils/ui.ftl"/>
<@layout "编辑文章">

<div class="panel panel-default">
	<div class="panel-heading">
		<i class="fa fa-pencil-square"></i> 编辑文章
	</div>
	<div class="panel-body">
		<div id="message"></div>
		<form class="form-horizontal" action="${base}/post/update" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${view.id}"/>
            <input type="hidden" name="authorId" value="${view.authorId}"/>
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right">标题</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="title" maxlength="32" data-required value="${view.title}">
				</div>
			</div>
            <div class="form-group">
                <label class="col-sm-2 control-label no-padding-right">发布到</label>
                <div class="col-sm-3">
                    <select class="form-control" name="channelId">
						<#list channels as row>
                            <option value="${row.id}" <#if (view.channelId == row.id)> selected </#if>>${row.name}</option>
						</#list>
                    </select>
                </div>
            </div>
			<div class="form-group">
				<label for="desc" class="col-sm-2 control-label no-padding-right">内容</label>
				<input type="hidden" name="editor" value="${site_editor}"/>
				<div class="col-sm-8">
					<#include "/default/channel/editor/ueditor.ftl"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right">标签</label>
				<div class="col-sm-8">
					<input type="hidden" name="tags" id="fieldTags" value="${view.tags}">
					<ul id="tags"></ul>
					<p class="help-block" style="font-size: 12px;">添加相关标签，用逗号或空格分隔 (最多4个).</p>
				</div>
			</div>
			<div class="row">
				<div class="form-group">
					<div class="text-center">
						<button type="submit" class="btn btn-primary">提交</button>
					</div>
				</div>
			</div>
		</form>
		<!-- /form-actions -->
	</div>
</div>

<script type="text/javascript">
seajs.use('post', function (post) {
	post.init();
});
</script>
</@layout>