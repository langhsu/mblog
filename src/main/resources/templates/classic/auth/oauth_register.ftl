<#include "/classic/utils/ui.ftl"/>

<@layout "注册">

<div class="row">
    <div class="col-md-4 col-md-offset-4 floating-box">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">离成功只差一步</h3>
            </div>
            <div class="panel-body">
                <div id="message"><#include "/classic/inc/action_message.ftl"/></div>
                <form id="submitForm" method="POST" action="${base}/oauth/callback/bind_oauth" accept-charset="UTF-8">
                    <input type="hidden" name="oauthType" value="${open.oauthType}"/>

                    <input type="hidden" name="code" value="${open.oauthCode}"/>
                    <input type="hidden" name="oauthUserId" value="${open.oauthUserId}"/>
                    <input type="hidden" name="accessToken" value="${open.accessToken}"/>
                    <input type="hidden" name="expireIn" value="${open.expireIn}"/>
                    <input type="hidden" name="refreshToken" value="${open.refreshToken}"/>
                    <input type="hidden" name="avatar" value="${open.avatar}"/>

                    <div class="form-group ">
                        <label class="control-label" for="username">用户名</label>
                        <input class="form-control" id="username" name="username" type="text" value="${open.username}" required>
                    </div>
                    <button type="submit" class="btn btn-success btn-block">
                        提  交
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
                username: {
                    required: true,
                    check_username: true
                }
            },
            messages: {
                username: {
                    required: '请输入用户名'
                }
            },
            errorElement: "em",
            errorPlacement: function (error, element) {
                error.addClass("help-block");
                error.insertAfter(element);
            },
            highlight: function (element, errorClass, validClass) {
                $(element).closest("div").addClass("has-error").removeClass("has-success");
            },
            unhighlight: function (element, errorClass, validClass) {
                $(element).closest("div").addClass("has-success").removeClass("has-error");
            }
        });
    })
</script>
</@layout>