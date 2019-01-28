<#include "/default/utils/ui.ftl"/>

<@layout "重置密码">

<div class="row">
    <div class="col-md-4 col-md-offset-4 floating-box">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">找回密码</h3>
            </div>
            <div class="panel-body">
                <div id="message">
                    <#include "/default/inc/action_message.ftl"/>
                </div>
                <form id="submitForm" method="POST" action="${base}/forgot" accept-charset="UTF-8">
                    <div class="form-group">
                        <label class="control-label" for="email">邮箱地址</label>
                        <div class="input-group">
                            <input type="text" class="form-control" name="email" maxlength="64" required>
                            <span class="input-group-btn">
                                <button class="btn btn-primary" type="button" id="sendCode">获取验证码</button>
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label">验证码</label>
                        <input type="text" class="form-control" name="code" maxlength="6" required>
                    </div>
                    <div class="form-group ">
                        <label class="control-label" for="username">密码</label>
                        <input class="form-control" name="password" id="password" type="password" maxlength="18" placeholder="新密码" required>
                    </div>
                    <div class="form-group ">
                        <label class="control-label" for="username">确认密码</label>
                        <input class="form-control" name="password2" type="password" maxlength="18" placeholder="请再输入一次密码">
                    </div>
                    <button type="submit" class="btn btn-success btn-block">
                        提 交
                    </button>

                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function(){
        $("#submitForm").validate({
            rules: {
                email: {
                    required: true,
                    email: true
                },
                password: 'required',
                code: 'required',
                password2: {
                    required: true,
                    equalTo: "#password"
                }
            },
            messages: {
                password2: {
                    required: '请输入确认密码',
                    equalTo: '两次输入的密码不一致'
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
            $.getJSON('${base}/email/send_code', {'email': email, 'type': 2}, function (data) {
                if (data.code == 0) {
                    btn.text('重新发送');
                    $('#message').html('<div class="alert alert-success">' + data.message + '</div>');
                } else {
                    $('#message').html('<div class="alert alert-danger">' + data.message + '</div>');
                }

                btn.button('reset');
            });
        })
    })
</script>
</@layout>