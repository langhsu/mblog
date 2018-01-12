<#include "/admin/utils/ui.ftl"/>
<@layout>
<link rel='stylesheet' media='all' href='${base}/dist/css/plugins.css'/>
<script type="text/javascript" src="${base}/dist/js/plugins.js"></script>

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>文章编辑</h2>
                <div class="clearfix"></div>
            </div>
            <div class="x_content">
                <br>
				<#include "/admin/message.ftl">
                <form id="qForm" class="form-horizontal form-label-left" method="post" action="${base}/admin/posts/update">
                    <input type="hidden" name="type" value="${view.type}"/>
                    <input type="hidden" name="id" value="${view.id}"/>
                    <input type="hidden" name="author.id" value="${view.author.id}"/>
                    <div class="form-group">
                        <label class="col-sm-2 control-label no-padding-right">标题</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="title" value="${view.title}" maxlength="64" data-required >
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
                        <label for="desc" class="col-sm-2 control-label no-padding-right">内容:</label>
                        <div class="col-sm-8">
							<#include "/admin/editor/ueditor.ftl"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label no-padding-right">标签</label>
                        <div class="col-sm-8">
                            <input type="hidden" name="tags" id="fieldTags" value="${view.tags}">
                            <ul id="tags"></ul>
                            <p class="help-block" style="font-size: 12px;">添加相关标签，用逗号或空格分隔.</p>
                        </div>
                    </div>

                    <div class="ln_solid"></div>
                    <div class="form-group">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <button type="submit" class="btn btn-success">提交</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
$(function() {
	$('#tags').tagit({
		singleField: true,
		singleFieldNode: $('#fieldTags')
	});

	$('form').validate({
		onKeyup : true,
		onChange : true,
		eachValidField : function() {
			$(this).closest('div').removeClass('has-error').addClass('has-success');
		},
		eachInvalidField : function() {
			$(this).closest('div').removeClass('has-success').addClass('has-error');
		},
		conditional : {
			content : function() {
				return $(this).val().trim().length > 0;
			}
		},
		description : {
			content : {
				required : '<div class="alert alert-danger"><button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>写点内容吧</div>'
			}
		}
	});
});
</script>
</@layout>
