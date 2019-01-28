<#include "/admin/utils/ui.ftl"/>
<@layout>
<section class="content-header">
    <h1>修改栏目</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">首页</a></li>
        <li><a href="${base}/admin/channel/list">栏目管理</a></li>
        <li class="active">修改栏目</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <form id="qForm" class="form-horizontal form-label-left" method="post" action="update">
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">修改栏目</h3>
                    </div>
                    <div class="box-body">
                        <#include "/admin/message.ftl">
                        <#if view??>
                            <input type="hidden" name="id" value="${view.id}" />
                        </#if>

                        <div class="form-group">
                            <label class="col-lg-3 control-label">名称：</label>
                            <div class="col-lg-4">
                                <input type="text" name="name" class="form-control" value="${view.name}" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">唯一标识：</label>
                            <div class="col-lg-4">
                                <input type="text" name="key" class="form-control" value="${view.key}" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">导航栏状态：</label>
                            <div class="col-lg-4">
                                <select name="status" class="form-control" data-select="${view.status}">
                                    <option value="0">显示</option>
                                    <option value="1">隐藏</option>
                                </select>
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