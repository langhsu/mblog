<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>重置密码</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">首页</a></li>
        <li><a href="${base}/admin/user/list">用户管理</a></li>
        <li class="active">重置密码</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <form id="qForm" class="form-horizontal form-label-left" method="post">
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">重置密码</h3>
                    </div>
                    <div class="box-body">
                        <#include "/admin/message.ftl">
                        <div class="form-group">
                            <label class="col-lg-3 control-label">${view.username} 的新密码：</label>
                            <div class="col-lg-4">
                                <input type="text" class="input-small form-control" required name="newPassword" placeholder="新密码">
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