<#include "/admin/utils/ui.ftl"/>
<@layout>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <span>修改角色</span>
            </div>
            <div class="panel-body">
                <#include "/admin/message.ftl">
                <form id="qForm" class="form-horizontal form-label-left" method="post" action="update_role">
                    <input type="hidden" name="id" value="${view.id}" />

                    <div class="form-group">
                        <label class="col-lg-2 control-label">用户名</label>
                        <div class="col-lg-8">
                            <input class="form-control" type="text" value="${view.username}" disabled style="width:200px;">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-2 control-label">角色</label>
                        <div class="col-lg-8">
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
var J = jQuery;

$(function() {
})
</script>
</@layout>