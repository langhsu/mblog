<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>角色管理</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">首页</a></li>
        <li class="active">角色管理</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">角色列表</h3>
                    <div class="box-tools">
                        <a class="btn btn-default btn-sm" href="${base}/admin/role/view">添加角色</a>
                    </div>
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
                <div class="box-footer">
                    <@pager "list" page 5 />
                </div>
            </div>
        </div>
    </div>
</section>
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