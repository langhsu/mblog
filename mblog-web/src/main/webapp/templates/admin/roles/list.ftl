<#include "/admin/utils/ui.ftl"/>
<@layout>

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>角色管理</h2>
                <ul class="nav navbar-right panel_toolbox">
                    <@shiro.hasPermission name="roles:edit">
                        <li><a href="${base}/admin/roles/view">添加角色</a>
                        </li>
                    </@shiro.hasPermission>
                </ul>
                <div class="clearfix"></div>
            </div>
            <div class="x_content">
                <form id="qForm" class="form-inline">
                    <input type="hidden" name="pn" value="${page.pageNo}"/>
                    <div class="form-group">
                        <input type="text" name="key" class="form-control" value="${key}" placeholder="请输入关键字">
                    </div>
                    <button type="submit" class="btn btn-default">查询</button>
                </form>
            </div>
            <div class="x_content">
                <table id="dataGrid" class="table table-striped table-bordered b-t text-small">
                    <thead>
                    <tr>
                        <th width="80" style="text-align: center">#</th>
                        <th>用户名</th>
                        <th width="300" style="text-align: center">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list page.content as row>
                        <tr>
                            <td class="text-center">${row.id}</td>
                            <td>${row.name}</td>
                            <td class="text-center">
                                <@shiro.hasPermission name="roles:edit">
                                    <a href="${base}/admin/roles/view?id=${row.id}" class="btn btn-xs btn-primary">
                                        <i class="fa fa-check-square-o"></i> 修改
                                    </a>
                                    <a href="${base}/admin/roles/delete?id=${row.id}" class="btn btn-xs btn-default"><i
                                            class="fa fa-check-square-o"></i> 删除</a>
                                </@shiro.hasPermission>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <@pager "list" page 5 />
            </div>
        </div>
    </div>
</div>

</@layout>