<#include "/default/utils/ui.ftl"/>

<@layout "密码找回">

<div class="row">
    <div class="col-md-4 col-md-offset-4 floating-box">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">通过邮箱找回密码</h3>
            </div>
            <div class="panel-body">
                <div id="message">
                    <#include "/default/inc/action_message.ftl"/>
                </div>
                <form method="POST" action="apply" accept-charset="UTF-8">
                    <div class="form-group ">
                        <input maxlength="32" class="form-control border" name="username" placeholder="用户名" type="text" required>
                    </div>
                    <button type="submit" class="btn btn-lg btn-success btn-block btn-sm">
                        提 交
                    </button>

                </form>
            </div>
        </div>
    </div>
</div>

</@layout>