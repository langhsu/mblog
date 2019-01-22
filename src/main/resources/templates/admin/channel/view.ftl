<#include "/admin/utils/ui.ftl"/>
<@layout>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <span>修改栏目</span>
            </div>
            <div class="panel-body">
                <#include "/admin/message.ftl">
                <form id="qForm" class="form-horizontal form-label-left" method="post" action="update">
                    <#if view??>
                        <input type="hidden" name="id" value="${view.id}" />
                    </#if>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">名称：</label>
                        <div class="col-lg-4">
                            <input type="text" name="name" class="form-control" value="${view.name}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">唯一标识：</label>
                        <div class="col-lg-4">
                            <input type="text" name="key" class="form-control" value="${view.key}">
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