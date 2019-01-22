<#include "/admin/utils/ui.ftl"/>
<@layout>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <span>重置密码</span>
            </div>
            <div class="panel-body">
                <#include "/admin/message.ftl">
                <form id="qForm" class="form-horizontal form-label-left" method="post">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">${view.username} 的新密码：</label>
                        <div class="col-lg-4">
                            <input type="text" class="input-small form-control" data-required="true" name="newPassword" placeholder="新密码">
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