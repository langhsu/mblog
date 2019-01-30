<#include "/classic/utils/ui.ftl"/>

<@layout "注册">
<div class="row">
    <div class="col-md-4 col-md-offset-4 floating-box">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">注册</h3>
            </div>
            <div class="panel-body">
                <#include "/classic/inc/action_message.ftl"/>
                <div id="message">
                </div>
                <form id="submitForm" method="POST" action="register" accept-charset="UTF-8">
                    <div class="form-group ">
                        <label class="control-label" for="username">用户名</label>
                        <input class="form-control" id="username" name="username" type="text" placeholder="字母和数字的组合, 不少于5位" required>
                    </div>
                    <div class="form-group ">
                        <label class="control-label" for="username">密码</label>
                        <input class="form-control" id="password" name="password" type="password" maxlength="18" placeholder="请输入密码" required>
                    </div>
                    <div class="form-group ">
                        <label class="control-label" for="username">确认密码</label>
                        <input class="form-control" id="password2" name="password2" type="password" placeholder="请再一次输入密码" maxlength="18">
                    </div>
                    <button type="submit" class="btn btn-success btn-block">
                        提交
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    seajs.use('validate', function (validate) {
        validate.register('#submitForm');
    });
</script>

</@layout>