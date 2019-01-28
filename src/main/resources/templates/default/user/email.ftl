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
			<form id="submitForm" action="email" method="post" class="form-horizontal">
				<div class="form-group">
					<label class="control-label col-lg-3" for="email">邮箱地址</label>
					<div class="col-lg-4">
						<div class="input-group">
							<input type="text" class="form-control" name="email" value="${profile.email}" maxlength="64" required>
                            <span class="input-group-btn">
								<button class="btn btn-primary" type="button" id="sendCode">获取验证码</button>
						  	</span>
						</div>
					</div>
				</div>
                <div class="form-group">
                    <label class="control-label col-lg-3">验证码</label>
                    <div class="col-lg-4">
                        <input type="text" class="form-control" name="code" maxlength="6" required>
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
    $("#submitForm").validate({
        rules: {
            email: {
                required: true,
                email: true
            },
            code: {
                required: true
            }
        },
        errorElement: "em",
        errorPlacement: function (error, element) {
            error.addClass("help-block");
            if ( element.prop( "name" ) === "email" ) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        },
        highlight: function (element, errorClass, validClass) {
            $(element).closest("div").addClass("has-error").removeClass("has-success");
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).closest("div").addClass("has-success").removeClass("has-error");
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