<#include "/default/utils/ui.ftl"/>

<@layout "注册">

<div class="row">
    <div class="col-md-4 col-md-offset-4 floating-box">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">离成功只差一步</h3>
            </div>
            <div class="panel-body">
                <div id="message"><#include "/default/inc/action_message.ftl"/></div>
                <form method="POST" action="${base}/oauth/callback/bind_oauth" accept-charset="UTF-8">
                    <input type="hidden" name="oauthType" value="${open.oauthType}"/>

                    <input type="hidden" name="code" value="${open.oauthCode}"/>
                    <input type="hidden" name="oauthUserId" value="${open.oauthUserId}"/>
                    <input type="hidden" name="accessToken" value="${open.accessToken}"/>
                    <input type="hidden" name="expireIn" value="${open.expireIn}"/>
                    <input type="hidden" name="refreshToken" value="${open.refreshToken}"/>
                    <input type="hidden" name="avatar" value="${open.avatar}"/>

                    <div class="form-group ">
                        <label class="control-label" for="username">用户名</label>
                        <input class="form-control" name="username" type="text" value="${open.username}" data-required data-conditional="username" data-description="username" data-describedby="message">
                    </div>
                    <div class="form-group ">
                        <label class="control-label" for="password">昵称</label>
                        <input class="form-control" name="nickname" type="text" value="${open.nickname}" data-required>
                    </div>

                    <button type="submit" class="btn btn-lg btn-success btn-block">
                        提  交
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
                username : function() {
                    return /^[a-z][a-z_0-9]{4,18}$/i.test($(this).val());
                }
                /*,
                email : function() {
                    return /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($(this).val());
                }*/
            },
            description : {
                username : {
                    conditional : '<div class="alert alert-danger">只能是字母/字母+数字的组合</div>'
                }
                /*,
                email : {
                    conditional : '<div class="alert alert-danger">邮箱格式不合法</div>'
                }
                */
            }
        });
    })
</script>
</@layout>