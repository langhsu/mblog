<form id="qForm" class="form-horizontal" method="post" action="update">
    <#include "/admin/message.ftl">
    <div class="channel-left-border">
        <div class="channel-title">QQ登录</div>
        <div class="form-group">
            <label class="col-lg-2 control-label">回调地址</label>
            <div class="col-lg-6">
                <input type="text" name="site_oauth_qq" class="form-control" value="${configs['site_oauth_qq'].value}">
                <p class="help-block">示例: http://{domain}/oauth/callback/qq</p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2 control-label">app_ID</label>
            <div class="col-lg-6">
                <input type="text" name="qq_app_id" class="form-control" value="${configs['qq_app_id'].value}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2 control-label">app_KEY</label>
            <div class="col-lg-6">
                <input type="text" name="qq_app_key" class="form-control" value="${configs['qq_app_key'].value}">
            </div>
        </div>
    </div>

    <div class="channel-left-border">
        <div class="channel-title">微博登录</div>
        <div class="form-group">
            <label class="col-lg-2 control-label">回调地址</label>
            <div class="col-lg-6">
                <input type="text" name="site_oauth_weibo" class="form-control" value="${configs['site_oauth_weibo'].value}">
                <p class="help-block">示例: http://{domain}/oauth/callback/weibo</p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2 control-label">client_ID</label>
            <div class="col-lg-6">
                <input type="text" name="weibo_client_id" class="form-control" value="${configs['weibo_client_id'].value}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2 control-label">client_SERCRET</label>
            <div class="col-lg-6">
                <input type="text" name="weibo_client_sercret" class="form-control" value="${configs['weibo_client_sercret'].value}">
            </div>
        </div>
    </div>

    <div class="channel-left-border">
        <div class="channel-title">豆瓣登录</div>
        <div class="form-group">
            <label class="col-lg-2 control-label">回调地址</label>
            <div class="col-lg-6">
                <input type="text" name="site_oauth_douban" class="form-control" value="${configs['site_oauth_douban'].value}">
                <p class="help-block">示例: http://{domain}/oauth/callback/douban</p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2 control-label">api_key</label>
            <div class="col-lg-6">
                <input type="text" name="douban_api_key" class="form-control" value="${configs['douban_api_key'].value}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2 control-label">secret_key</label>
            <div class="col-lg-6">
                <input type="text" name="douban_secret_key" class="form-control" value="${configs['douban_secret_key'].value}">
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="col-lg-9 col-lg-offset-3">
            <button type="submit" class="btn btn-primary btn-small">提交</button>
        </div>
    </div>
</form>