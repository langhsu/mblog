<#include "/admin/utils/ui.ftl"/>
<@layout>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>菜单管理</h2>
                <ul class="nav navbar-right panel_toolbox">
                    <@shiro.hasPermission name="authMenus:edit">
                        <li><a href="${base}/admin/authMenus/view">添加菜单</a>
                        </li>
                    </@shiro.hasPermission>
                </ul>
                <div class="clearfix"></div>
            </div>
            <div class="x_content">
                <table id="treeTable" class="table table-striped table-bordered b-t text-small">
                    <tr>
                        <th>名称</th>
                        <th>URL</th>
                        <th>权限</th>
                        <th>顺序</th>
                        <@shiro.hasPermission name="authMenus:edit">
                            <th width="300">操作</th>
                        </@shiro.hasPermission>
                    </tr>
                    <#list list as row>
                        <#if (row.parentId > 0)>
                        <tr id="${row.id}" pId="${row.parentId}">
                        <#else>
                        <tr id="${row.id}">
                        </#if>
                        <td>${row.name}</td>
                        <td>${row.url}</td>
                        <td>${row.permission}</td>
                        <td>${row.sort}</td>
                        <@shiro.hasPermission name="authMenus:edit">
                            <td class="text-center">
                                <#if (row.id > 1)>
                                <a href="${base}/admin/authMenus/view?id=${row.id}" class="btn btn-xs btn-primary">
                                    <i class="fa fa-check-square-o"></i> 修改
                                </a>
                                </#if>

                                <a href="${base}/admin/authMenus/view?parentId=${row.id}"
                                   class="btn btn-xs btn-primary">
                                    <i class="fa fa-check-square-o"></i> 添加下级菜单
                                </a>

                                <#if (row.id > 1)>
                                <a href="${base}/admin/authMenus/delete?id=${row.id}" class="btn btn-xs btn-default"><i
                                        class="fa fa-check-square-o"></i> 删除</a>
                                </#if>
                            </td>
                        </@shiro.hasPermission>
                    </tr>
                    </#list>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        var option = {
            expandLevel: 4,
        };
        $('#treeTable').treeTable(option);

    });
</script>
</@layout>