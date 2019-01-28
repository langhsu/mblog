<#include "/admin/utils/ui.ftl"/>
<@layout>

<link href="${base}/dist/vendors/treetable/css/jquery.treetable.css" rel="stylesheet" type="text/css"/>
<link href="${base}/dist/vendors/treetable/css/jquery.treetable.theme.default.css" rel="stylesheet" type="text/css"/>
<script src="${base}/dist/vendors/treetable/jquery.treetable.js"></script>

<#macro treeIterator nodes>
<#-- 循环节点-->
    <#list nodes as row>
    <tr data-tt-id="${row.id}" <#if (row.parentId??) > data-tt-parent-id="${row.parentId}" </#if>>
        <td>
            <input type="checkbox" name="perms" id="${row.name}-${row.id}" value="${row.id}"> ${row.description}
        </td>
    </tr>

    <#-- 判断是否有子集 -->
        <#if row.items??>
            <@treeIterator nodes=row.items />
        </#if>
    </#list>
</#macro>

<section class="content-header">
    <h1>编辑角色</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">首页</a></li>
        <li><a href="${base}/admin/role/list">角色管理</a></li>
        <li class="active">编辑角色</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <form id="qForm" class="form-horizontal form-label-left" method="post" action="update">
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">编辑角色</h3>
                    </div>
                    <div class="box-body">
                        <#include "/admin/message.ftl">
                        <#if view?? && (view.id > 0)>
                            <input type="hidden" name="id" value="${view.id}"/>
                        </#if>

                        <div class="form-group">
                            <label for="name" class="col-lg-2 control-label">角色名称：</label>
                            <div class="col-lg-3">
                                <input type="text" class="form-control" placeholder="请输入角色名称" name="name"
                                       value="${view.name}" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="item" class="col-lg-2 control-label">分配菜单：</label>
                            <div class="col-lg-6" id="perms">
                                <table id="dataGrid" class="table table-border table-bordered table-bg">
                                    <caption>
                                        <a href="#" onclick="jQuery('#dataGrid').treetable('expandAll'); return false;">展开所有</a>
                                        <a href="#" onclick="jQuery('#dataGrid').treetable('collapseAll'); return false;">收起所有</a>
                                    </caption>
                                    <tbody>
                                        <@treeIterator nodes=permissions />
                                    </tbody>
                                </table>
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
    var perm = [];
        <#list view.permissions as p>
        perm.push('${p.id}');
            <#if p.children??>
                <#list p.children as c>
                perm.push('${c.id}');
                    <#if c.children??>
                        <#list c.children as a>
                        perm.push('${a.id}');
                        </#list>
                    </#if>

                </#list>
            </#if>
        </#list>

    $(function () {
        $("#dataGrid").treetable({expandable: true});

        $('input:checkbox', '#dataGrid ').click(function () {
            if ($(this).prop("checked")) {
                var tr = $(this).closest("tr");
                var parent = tr.attr("data-tt-parent-id");

                if (typeof(parent) != 'undefined') {
                    var parentArray = parent.split('.');

                    var temp = '';
                    for (var i = 0; i < parentArray.length; i++) {
                        if (i > 0) {
                            temp += '.' + parentArray[i];
                        } else {
                            temp += parentArray[i];
                        }

                        $('tr[data-tt-id="' + temp + '"]>td>input:checkbox').prop("checked", $(this).prop("checked"));
                    }
                }
            }
        })

        if (perm.length > 0) {
            $('#perms :checkbox').each(function () {
                this.checked = (jQuery.inArray(this.value, perm) != -1);
            });
        }
    });
</script>
</@layout>