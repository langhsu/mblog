<#include "/admin/utils/ui.ftl"/>
<@layout>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <span>角色管理</span>
                <ul class="nav nav-pills navbar-right panel-toolbox">
                    <li>
                    <li><a href="${base}/admin/role/view">添加角色</a></li>
                    </li>
                </ul>
            </div>
            <div class="panel-body">
                <form id="qForm" class="form-inline panel-form">
                    <input type="hidden" name="pn" value="${page.pageNo}"/>
                    <div class="form-group">
                        <input type="text" name="key" class="form-control" value="${key}" placeholder="请输入关键字">
                    </div>
                    <button type="submit" class="btn btn-default">查询</button>
                </form>
                <div class="table-responsive">
                    <table id="dataGrid" class="table table-striped table-bordered text-small">
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
                                    <#if row.id != 1>
                                        <a href="${base}/admin/role/view?id=${row.id}" class="btn btn-xs btn-success">
                                            <i class="fa fa-check-square-o"></i> 修改
                                        </a>
                                        <a href="javascript:void(0);" class="btn btn-xs btn-primary" data-id="${row.id}"
                                           data-action="delete">
                                            <i class="fa fa-bitbucket"></i> 删除
                                        </a>
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
            <div class="panel-footer">
                <@pager "list" page 5 />
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var J = jQuery;

    function ajaxReload(json) {
        if (json.code >= 0) {
            if (json.message != null && json.message != '') {
                layer.msg(json.message, {icon: 1});
            }
            $('#qForm').submit();
        } else {
            layer.msg(json.message, {icon: 2});
        }
    }

    function doDelete(ids) {
        J.getJSON('${base}/admin/role/delete', J.param({'id': ids}, true), ajaxReload);
    }

    $(function () {
        // 删除
        $('#dataGrid a[data-action="delete"]').bind('click', function () {
            var that = $(this);
            layer.confirm('确定删除此项吗?', {
                btn: ['确定', '取消'], //按钮
                shade: false //不显示遮罩
            }, function () {
                doDelete(that.attr('data-id'));
            }, function () {
            });
            return false;
        });
    })
</script>
</@layout>