<#include "/admin/utils/ui.ftl"/>
<@layout>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>用户管理</h2>
                <div class="clearfix"></div>
            </div>
            <div class="x_content">
            </div>
        </div>
        <div class="x_content">
            <form id="qForm" class="form-inline">
                <input type="hidden" name="pn" value="${page.pageNo}" />
                <div class="form-group">
                    <input type="text" name="key" class="form-control" value="${key}" placeholder="请输入关键字">
                </div>
                <button type="submit" class="btn btn-default">查询</button>
            </form>
        </div>
		<div class="x_content">
            <table id="dataGrid" class="table table-striped table-bordered b-t">
                <thead>
                <tr>
                    <th width="80">#</th>
                    <th>用户名</th>
                    <th>昵称</th>
                    <th>邮箱</th>
                    <th>角色</th>
                    <th>状态</th>
					<@shiro.hasPermission name="users:edit">
                    <th width="300"></th>
					</@shiro.hasPermission>
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
                    <@shiro.hasPermission name="users:edit">
                    <td class="text-center">
                        <#if row.id != 1>
                            <#if row.status == 0>
                            <a href="javascript:void(0);" class="btn btn-xs btn-default" data-id="${row.id}" data-action="close">
                                <i class="fa fa-close"></i> 关闭
                            </a>
                            <#else>
                            <a href="javascript:void(0);" class="btn btn-xs btn-success"" data-id="${row.id}" data-action="open">
                            <i class="fa fa-check"></i> 激活
                            </a>
                            </#if>
                            <a href="${base}/admin/users/pwd?id=${row.id}" class="btn btn-xs btn-white">
                                <i class="fa fa-unlock-alt"></i> 修改密码
                            </a>

                            <a href="${base}/admin/users/view?id=${row.id}" class="btn btn-xs btn-primary">
                                <i class="fa fa-check-square-o"></i> 修改角色
                            </a>
                        <#else>
                        <a href="javascript:void(0);" class="btn btn-xs disabled"><i class="fa fa-check-square-o"></i> 不可编辑</a>
                        </#if>
                    </td>
                    </@shiro.hasPermission>
                </tr>
                </#list>
                </tbody>
            </table>
		</div>
		<div class="x_content">
			<@pager "list" page 5 />
		</div>
    </div>
</div>

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
			 J.getJSON('${base}/admin/users/close', {id: that.attr('data-id'), active: false}, ajaxReload);
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
			 J.getJSON('${base}/admin/users/open', {id: that.attr('data-id'), active: true}, ajaxReload);
        }, function(){
        });
        return false;
    });
})
</script>
</@layout>