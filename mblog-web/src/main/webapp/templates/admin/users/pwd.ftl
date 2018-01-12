<#include "/admin/utils/ui.ftl"/>
<@layout>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>重置密码</h2>
                <div class="clearfix"></div>
            </div>
            <div class="x_content">
                <br>
				<#include "/admin/message.ftl">
                <form id="qForm" class="form-horizontal form-label-left" method="post">
                    <div class="form-group">
                        <p>设置  ${view.username} 的新密码:</p>
                        <div style="width: 260px;">
                            <input type="text" class="input-small form-control" data-required="true" name="newPassword" placeholder="新密码">
                        </div>
                    </div>
                    <div class="actions m-t">
                        <button type="submit" class="btn btn-primary btn-small">提交</button>
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