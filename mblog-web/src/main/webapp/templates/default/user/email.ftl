<#include "/default/utils/ui.ftl"/>
<@layout "修改用户信息">

<div class="panel panel-default stacked">
	<div class="panel-heading">
		<ul class="nav nav-pills account-tab">
			<li class="active"><a href="profile">基本信息</a></li>
			<li><a href="avatar">修改头像</a></li>
			<li><a href="password">修改密码</a></li>
		</ul>
	</div>
	<div class="panel-body">
		<div id="message">
			<#include "/default/inc/action_message.ftl"/>
		</div>
		<div class="tab-pane active" id="profile">
			<form id="pf" action="email" method="post" class="form-horizontal">
				<div class="form-group">
					<label class="control-label col-lg-3" for="email">修改邮箱地址</label>
					<div class="col-lg-4">
						<input type="text" class="form-control" name="email" value="${profile.email}" maxlength="64" data-required data-conditional="email" data-description="email" data-describedby="message">
						<p class="help-block">修改后将会重新发送验证邮件.</p>
					</div>
				</div>
				<div class="form-group">
					<div class="text-center">
						<button type="submit" class="btn btn-primary">提交</button>
					</div>
				</div><!-- /form-actions -->
			</form>
		</div>
	</div><!-- /panel-content -->
</div><!-- /panel -->

<script type="text/javascript">
$(function () {
	$('#pf').validate({
        onKeyup : true,
        onChange : true,
        eachValidField : function() {
            $(this).closest('div').removeClass('has-error').addClass('has-success');
        },
        eachInvalidField : function() {
            $(this).closest('div').removeClass('has-success').addClass('has-error');
        },
        conditional : {
            email : function() {
                return /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($(this).val());
            }
        },
        description : {
            email : {
                conditional : '<div class="alert alert-danger">邮箱格式不合法</div>'
            }
        }
	});
});
</script>
</@layout>