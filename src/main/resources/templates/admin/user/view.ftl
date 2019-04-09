<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>修改角色</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">首页</a></li>
        <li><a href="${base}/admin/user/list">用户管理</a></li>
        <li class="active">修改角色</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <form id="qForm" class="form-horizontal form-label-left" method="post" action="update_role">
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">修改角色</h3>
                    </div>
                    <div class="box-body">
                        <#include "/admin/message.ftl">
                        <input type="hidden" name="id" value="${view.id}" />

                        <div class="form-group">
                            <label class="col-sm-2 control-label">用户名</label>
                            <div class="col-sm-8">
                                <input class="form-control" type="text" value="${view.username}" disabled style="width:200px;">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">角色</label>
                            <div class="col-sm-8">
                            <#--遍历所有角色和用户当前拥有的角色，如果用户当前拥有某个角色，则把checkbox设置为选中-->
                                <#list roles as role>
                                    <#assign hasRole ="false">
                                    <label class="checkbox-inline">
                                        <#list view.roles as userRole>
                                            <#if userRole.id == role.id>
                                                <#assign hasRole ="true">
                                                <#break>
                                            </#if>
                                        </#list>
                                        <#if hasRole == "true">
                                            <input type="checkbox" name="roleIds" value="${role.id}" checked="checked"> ${role.name}
                                        <#else>
                                            <input type="checkbox" name="roleIds" value="${role.id}"> ${role.name}
                                        </#if>
                                    </label>
                                </#list>
                            </div>
                        </div>
                    </div>
                    <div class="box-footer">
                        <button type="submit" class="btn btn-primary">提交</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</section>
<script type="text/javascript">
var J = jQuery;

$(function() {
})
</script>
</@layout>