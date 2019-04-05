<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>文章管理</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">首页</a></li>
        <li class="active">文章管理</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">文章列表</h3>
                    <div class="box-tools">
                        <a class="btn btn-default btn-sm" href="${base}/admin/post/view">新建</a>
                        <a class="btn btn-default btn-sm" href="javascrit:;" data-action="batch_del">批量删除</a>
                    </div>
                </div>
                <div class="box-body">
                    <form id="qForm" class="form-inline search-row">
                        <input type="hidden" name="pageNo" value="${page.number + 1}"/>
                        <div class="form-group">
                            <select class="form-control" name="channelId" data-select="${channelId}">
                                <option value="0">查询所有栏目</option>
                                <#list channels as row>
                                    <option value="${row.id}">${row.name}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="form-group">
                            <input type="text" name="title" class="form-control" value="${title}" placeholder="请输入标题关键字">
                        </div>
                        <button type="submit" class="btn btn-default">查询</button>
                    </form>
                    <div class="table-responsive">
                        <table id="dataGrid" class="table table-striped table-bordered">
                            <thead>
                            <tr>
                                <th width="30"><input type="checkbox" class="checkall"></th>
                                <th width="80">#</th>
                                <th>文章标题</th>
                                <th width="120">作者</th>
                                <th width="100">发表日期</th>
                                <th width="60">访问数</th>
                                <th width="80">状态</th>
                                <th width="80">发布</th>
                                <th width="180">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list page.content as row>
                                <tr>
                                    <td>
                                        <input type="checkbox" name="id" value="${row.id}">
                                    </td>
                                    <td>
                                        <img src="<@resource src=row.thumbnail/>" style="width: 80px;">
                                    </td>
                                    <td>
                                        <a href="${base}/post/${row.id}" target="_blank">${row.title}</a>
                                    </td>
                                    <td>${row.author.username}</td>
                                    <td>${row.created?string('yyyy-MM-dd')}</td>
                                    <td><span class="label label-default">${row.views}</span></td>
                                    <td>
                                        <#if (row.featured > 0)>
                                            <span class="label label-danger">推荐</span>
                                        </#if>
                                        <#if (row.weight > 0)>
                                            <span class="label label-warning">置顶</span>
                                        </#if>
                                    </td>
                                    <td>
                                        <#if (row.status = 0)>
                                            <span class="label label-default">已发布</span>
                                        </#if>
                                        <#if (row.status = 1)>
                                            <span class="label label-warning">草稿</span>
                                        </#if>
                                    </td>
                                    <td>
                                        <#if (row.featured == 0)>
                                            <a href="javascript:void(0);" class="btn btn-xs btn-default" data-id="${row.id}" rel="featured">推荐</a>
                                        <#else>
                                            <a href="javascript:void(0);" class="btn btn-xs btn-danger" data-id="${row.id}" rel="unfeatured">消荐</a>
                                        </#if>

                                        <#if (row.weight == 0)>
                                            <a href="javascript:void(0);" class="btn btn-xs btn-default" data-id="${row.id}" rel="weight">置顶</a>
                                        <#else>
                                            <a href="javascript:void(0);" class="btn btn-xs btn-warning" data-id="${row.id}" rel="unweight">消顶</a>
                                        </#if>

                                        <a href="${base}/admin/post/view?id=${row.id}" class="btn btn-xs btn-info">修改</a>
                                        <a href="javascript:void(0);" class="btn btn-xs btn-primary" data-id="${row.id}" rel="delete">删除</a>
                                    </td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="box-footer">
                    <@pager "list" page 5 />
                </div>
            </div>
        </div>
    </div>
</section>
<script type="text/javascript">
var J = jQuery;

function ajaxReload(json){
    if(json.code >= 0){
        if(json.message != null && json.message != ''){
			layer.msg(json.message, {icon: 1});
        }
        $('#qForm').submit();
    }else{
		layer.msg(json.message, {icon: 2});
    }
}

function doDelete(ids) {
	J.getJSON('${base}/admin/post/delete', J.param({'id': ids}, true), ajaxReload);
}

function doUpdateFeatured(id, featured) {
    J.getJSON('${base}/admin/post/featured', J.param({'id': id, 'featured': featured}, true), ajaxReload);
}

function doUpdateWeight(id, weight) {
    J.getJSON('${base}/admin/post/weight', J.param({'id': id, 'weight': weight}, true), ajaxReload);
}

$(function() {
	// 删除
    $('#dataGrid a[rel="delete"]').bind('click', function(){
        var that = $(this);
		layer.confirm('确定删除此项吗?', {
            btn: ['确定','取消'], //按钮
            shade: false //不显示遮罩
        }, function(){
			doDelete(that.attr('data-id'));
        }, function(){
        });
        return false;
    });

    // 推荐/加精
    $('#dataGrid a[rel="featured"]').bind('click', function(){
        var that = $(this);
        layer.confirm('确定推荐吗?', {
            btn: ['确定','取消'], //按钮
            shade: false //不显示遮罩
        }, function(){
            doUpdateFeatured(that.attr('data-id'), 1);
        }, function(){
        });
        return false;
    });

    // 撤销
    $('#dataGrid a[rel="unfeatured"]').bind('click', function(){
        var that = $(this);
        layer.confirm('确定撤销吗?', {
            btn: ['确定','取消'], //按钮
            shade: false //不显示遮罩
        }, function(){
            doUpdateFeatured(that.attr('data-id'), 0);
        }, function(){
        });
        return false;
    });

    // 推荐/加精
    $('#dataGrid a[rel="weight"]').bind('click', function(){
        var that = $(this);
        layer.confirm('确定置顶该项吗', {
            btn: ['确定','取消'], //按钮
            shade: false //不显示遮罩
        }, function(){
            doUpdateWeight(that.attr('data-id'), 1);
        }, function(){
        });
        return false;
    });

    // 撤销
    $('#dataGrid a[rel="unweight"]').bind('click', function(){
        var that = $(this);
        layer.confirm('确定撤销吗?', {
            btn: ['确定','取消'], //按钮
            shade: false //不显示遮罩
        }, function(){
            doUpdateWeight(that.attr('data-id'), 0);
        }, function(){
        });
        return false;
    });
    
    $('a[data-action="batch_del"]').click(function () {
		var check_length=$("input[type=checkbox][name=id]:checked").length;
		
		if (check_length == 0) {
			layer.msg("请至少选择一项", {icon: 2});
			return false;
		}
		
		var ids = [];
		$("input[type=checkbox][name=id]:checked").each(function(){
			ids.push($(this).val());
		});
		
		layer.confirm('确定删除此项吗?', {
            btn: ['确定','取消'], //按钮
            shade: false //不显示遮罩
        }, function(){
			doDelete(ids);
        }, function(){
        });
    });
})
</script>
</@layout>
