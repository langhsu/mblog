<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>栏目管理</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">首页</a></li>
        <li class="active">栏目管理</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">栏目列表</h3>
                    <div class="box-tools">
                        <a class="btn btn-default btn-sm" href="${base}/admin/channel/view">添加栏目</a>
                    </div>
                </div>
                <div class="box-body">
                    <div class="table-responsive">
                        <table id="dataGrid" class="table table-striped table-bordered">
                            <thead>
                            <tr>
                                <th width="80">#</th>
                                <th>名称</th>
                                <th>Key</th>
                                <th>状态</th>
                                <th width="140">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list list as row>
                                <tr>
                                    <td>${row.id}</td>
                                    <td>${row.name}</td>
                                    <td>${row.key}</td>
                                    <td>
                                        <#if (row.status == 0)>
                                            显示
                                        <#else>
                                            隐藏
                                        </#if>
                                    </td>
                                    <td>
                                        <a href="javascript:void(0);" class="btn btn-xs btn-default" data-id="${row.id}" data-action="weight">置顶</a>
                                        <a href="view?id=${row.id}" class="btn btn-xs btn-success">修改</a>
                                        <a href="javascript:void(0);" class="btn btn-xs btn-primary" data-id="${row.id}"
                                           data-action="delete">删除</a>
                                    </td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
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
            window.location.reload();
        } else {
            layer.msg(json.message, {icon: 2});
        }
    }

    function doUpdateWeight(id, weight) {
        J.getJSON('${base}/admin/channel/weight', J.param({'id': id, 'weight': weight}, true), ajaxReload);
    }

    $(function () {
        $('#dataGrid a[data-action="weight"]').bind('click', function(){
            var that = $(this);
            layer.confirm('确定将该项排序在第一位吗?', {
                btn: ['确定','取消'], //按钮
                shade: false //不显示遮罩
            }, function(){
                doUpdateWeight(that.attr('data-id'), 1);
            }, function(){
            });
            return false;
        });

        // 删除
        $('#dataGrid a[data-action="delete"]').bind('click', function () {
            var that = $(this);

            layer.confirm('确定删除此项吗?', {
                btn: ['确定', '取消'], //按钮
                shade: false //不显示遮罩
            }, function () {
                J.getJSON('${base}/admin/channel/delete', {id: that.attr('data-id')}, ajaxReload);
            }, function () {
            });
            return false;
        });

    })
</script>
</@layout>