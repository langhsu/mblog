<#include "/default/utils/ui.ftl"/>
<@layout "修改邮箱">

<div class="panel panel-default stacked">
	<div class="panel-heading">
		<ul class="nav nav-pills account-tab">
			<li><a href="profile">基本信息</a></li>
            <li class="active"><a href="email">修改邮箱</a></li>
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
					<label class="control-label col-lg-3" for="email">邮箱地址</label>
					<div class="col-lg-4">
						<div class="input-group">
							<input type="text" class="form-control" name="email" value="${profile.email}" maxlength="64" data-required data-conditional="email" data-description="email" data-describedby="message">
                            <span class="input-group-btn">
								<button class="btn btn-primary" type="button" id="sendCode">获取验证码</button>
						  	</span>
						</div>
					</div>
				</div>
                <div class="form-group">
                    <label class="control-label col-lg-3">验证码</label>
                    <div class="col-lg-4">
                        <input type="text" class="form-control" name="code" maxlength="6" data-required>
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

	$('#sendCode').click(function () {
		var btn = $(this).button('sending');
		var email = $('input[name=email]').val();
		$.getJSON('${base}/email/send_code', {'email': email, 'type': 1}, function (data) {
			if (data.code == 0) {
			    btn.text('重新发送');
                $('#message').html('<div class="alert alert-success">' + data.message + '</div>');
			} else {
			    $('#message').html('<div class="alert alert-danger">' + data.message + '</div>');
			}

            btn.button('reset');
        });
    })
});
</script>
</@layout>