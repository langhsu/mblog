<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>用户管理</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">首页</a></li>
        <li class="active">用户管理</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">用户列表</h3>
                </div>
                <div class="box-body">
                    <form id="qForm" class="form-inline search-row">
                        <input type="hidden" name="pageNo" value="${page.number + 1}"/>
                        <div class="form-group">
                            <input type="text" name="name" class="form-control" value="${name}" placeholder="请输入关键字">
                        </div>
                        <button type="submit" class="btn btn-default">查询</button>
                    </form>
                    <div class="table-responsive">
                        <table id="dataGrid" class="table table-striped table-bordered">
                            <thead>
                            <tr>
                                <th width="80">#</th>
                                <th>用户名</th>
                                <th>昵称</th>
                                <th>邮箱</th>
                                <th>角色</th>
                                <th>状态</th>
                                <th width="300"></th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list page.content as row>
                                <tr>
                                    <td class="text-center">${row.id}</td>
                                    <td>${row.username}</td>
                                    <td>${row.name}</td>
                                    <td>${row.email}</td>
                                    <td>
                                        <#list row.roles as role>
                                ${role.name}
                                </#list>
                                    </td>
                                    <td>
                                        <#if (row.status == 0)>
                                            <span class="label label-success">启用</span>
                                        <#else>
                                            <span class="label label-default">禁用</span>
                                        </#if>
                                    </td>
                                    <td class="text-center">
                                        <#if row.id != 1>
                                            <#if row.status == 0>
                                                <a href="javascript:void(0);" class="btn btn-xs btn-default" data-id="${row.id}" data-action="close">关闭</a>
                                            <#else>
                                                <a href="javascript:void(0);" class="btn btn-xs btn-success" data-id="${row.id}" data-action="open">激活</a>
                                            </#if>
                                            <a href="${base}/admin/user/pwd?id=${row.id}" class="btn btn-xs btn-success">修改密码</a>

                                            <a href="${base}/admin/user/view?id=${row.id}" class="btn btn-xs btn-primary">修改角色</a>
                                        <#else>
                                            <a href="javascript:void(0);" class="btn btn-xs disabled"><i class="fa fa-check-square-o"></i> 不可编辑</a>
                                        </#if>
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

$(function() {
	// 停用
    $('#dataGrid a[data-action="close"]').bind('click', function(){
		var that = $(this);
		layer.confirm('该账号停用后，将不能登录系统，确定要停用?', {
            btn: ['确定','取消'], //按钮
            shade: false //不显示遮罩
        }, function(){
			 J.getJSON('${base}/admin/user/close', {id: that.attr('data-id'), active: false}, ajaxReload);
        }, function(){
        });
        return false;
    });
	
    // 激活
    $('#dataGrid a[data-action="open"]').bind('click', function(){
		var that = $(this);
		layer.confirm('该账号激活后，将可访问系统中的已授权功能，确定要激活?', {
            btn: ['确定','取消'], //按钮
            shade: false //不显示遮罩
        }, function(){
			 J.getJSON('${base}/admin/user/open', {id: that.attr('data-id'), active: true}, ajaxReload);
        }, function(){
        });
        return false;
    });
})
</script>
</@layout>