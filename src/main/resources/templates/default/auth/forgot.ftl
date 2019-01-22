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
                <form method="POST" action="${base}/forgot" accept-charset="UTF-8">
                    <div class="form-group">
                        <label class="control-label" for="email">邮箱地址</label>
                        <div class="input-group">
                            <input type="text" class="form-control" name="email" maxlength="64" data-required data-conditional="email" data-description="email" data-describedby="message">
                            <span class="input-group-btn">
                                <button class="btn btn-primary" type="button" id="sendCode">获取验证码</button>
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label">验证码</label>
                        <input type="text" class="form-control" name="code" maxlength="6" data-required>
                    </div>
                    <div class="form-group ">
                        <label class="control-label" for="username">密码</label>
                        <input class="form-control" name="password" id="password" type="password" maxlength="18" placeholder="新密码" data-required>
                    </div>
                    <div class="form-group ">
                        <label class="control-label" for="username">确认密码</label>
                        <input class="form-control" name="password2" type="password" maxlength="18" placeholder="请再输入一次密码" data-required data-conditional="confirm" data-describedby="message" data-description="confirm">
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
        $('form').validate({
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
                },
                confirm : function() {
                    return $(this).val() == $('#password').val();
                }
            },
            description : {
                email : {
                    conditional : '<div class="alert alert-danger">邮箱格式不合法</div>'
                },
                confirm : {
                    conditional : '<div class="alert alert-danger">两次输入的密码不一致</div>'
                }
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