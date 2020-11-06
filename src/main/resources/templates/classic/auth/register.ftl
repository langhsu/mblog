<@layout.extends name="/inc/layout.ftl">
    <@layout.put block="title">
        <title>注册</title>
    </@layout.put>
    <@layout.put block="contents">
        <div class="row">
            <div class="col-md-4 col-md-offset-4 floating-box">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">注册</h3>
                    </div>
                    <div class="panel-body">
                        <@layout.extends name="/inc/action_message.ftl" />
                        <div id="message">
                        </div>
                        <form id="submitForm" method="POST" action="register" accept-charset="UTF-8">
                            <div class="form-group ">
                                <label class="control-label" for="username">用户名</label>
                                <input class="form-control" id="username" name="username" type="text" placeholder="字母和数字的组合, 不少于5位" required>
                            </div>
                            <@controls name="register_email_validate">
                                <div class="form-group">
                                    <label class="control-label" for="username">邮箱</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="email" maxlength="64" placeholder="请输入邮箱地址" required>
                                        <span class="input-group-btn">
                                            <button class="btn btn-primary" type="button" id="sendCode">获取验证码</button>
                                        </span>
                                    </div>
                                </div>
                                <div class="form-group ">
                                    <label class="control-label" for="code">验证码</label>
                                    <input class="form-control" id="code" name="code" type="text" placeholder="请输入邮箱验证码" maxlength="6" required>
                                </div>
                            </@controls>
                            <div class="form-group ">
                                <label class="control-label" for="username">密码</label>
                                <input class="form-control" id="password" name="password" type="password" maxlength="18" placeholder="请输入密码" required>
                            </div>
                            <div class="form-group ">
                                <label class="control-label" for="username">确认密码</label>
                                <input class="form-control" id="password2" name="password2" type="password" placeholder="请再一次输入密码" maxlength="18">
                            </div>
                            <button type="submit" class="btn btn-primary btn-block">
                                提交
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript">
            seajs.use('validate', function (validate) {
                validate.register('#submitForm', '#sendCode');
            });
        </script>
    </@layout.put>
</@layout.extends>