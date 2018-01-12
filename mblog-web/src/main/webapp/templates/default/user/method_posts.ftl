<#include "/default/utils/ui.ftl"/>

<@layout "我的文章">
<div class="row users-show">
    <div class="col-xs-12 col-md-3 side-left">
		<#include "/default/user/right.ftl"/>
    </div>
    <div class="col-xs-12 col-md-9 side-right">
        <div class="panel panel-default">
            <div class="panel-heading">
                我的文章
            </div>

            <div class="panel-body">
                <ul class="list-group">
					<#list page.content as row>
                        <li class="list-group-item" el="loop-${row.id}">
							<a href="${base}/view/${row.id}" class="remove-padding-left">${row.title}</a>
                            <span class="meta">
								${row.favors} 点赞
								<span> ⋅ </span>
								${row.comments} 回复
								<span> ⋅ </span>
								<span class="timeago">${timeAgo(row.created)}</span>
      						</span>

                            <div class="pull-right hidden-xs">
                                <a class="act_edit" href="javascript:void(0);" data-evt="edit" data-id="${row.id}" data-toggle="tooltip" title="编辑文章">
                                    <i class="icon icon-note"></i>
								</a>
                                <a class="act_delete" href="javascript:void(0);" data-evt="trash" data-id="${row.id}" data-toggle="tooltip" title="删除文章">
                                    <i class="icon icon-close"></i>
								</a>
                            </div>
                        </li>
					</#list>

					<#if page.content?size == 0>
                        <li class="list-group-item ">
                            <div class="infos">
                                <div class="media-heading">该目录下还没有内容!</div>
                            </div>
                        </li>
					</#if>
                </ul>
            </div>
            <div class="panel-footer">
				<@pager request.requestURI!"", page, 5/>
            </div>
        </div>
    </div>
</div>
<!-- /end -->

<script type="text/javascript">
$(function() {
	// delete
	$('a[data-evt=trash]').click(function () {
		var id = $(this).attr('data-id');

		layer.confirm('确定删除此项吗?', {
            btn: ['确定','取消'], //按钮
            shade: false //不显示遮罩
        }, function(){
			jQuery.getJSON('${base}/post/delete/' + id, function (ret) {
				layer.msg(ret.message, {icon: 1});
				if (ret.code >=0) {
					$('#loop-' + id).fadeOut();
					$('#loop-' + id).remove();
				}
			});

        }, function(){

        });
	});
	
	// edit
	$('a[data-evt=edit]').click(function () {
		var id = $(this).attr('data-id');
		window.location.href='${base}/post/to_update/' + id;
	});
})
</script>
</@layout>