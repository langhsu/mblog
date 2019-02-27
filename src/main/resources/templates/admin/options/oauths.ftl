<form id="qForm" class="form-horizontal" method="post" action="update">
    <div class="form-group">
        <div class="col-md-12">
            <div class="help-block">未配置的三方登录平台不会在登录页显示</div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-12">
            <div class="help-block">QQ登录</div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">回调地址</label>
        <div class="col-sm-6">
            <input type="text" name="qq_callback" class="form-control" value="${options['qq_callback']}" placeholder="示例: http://{domain}/oauth/callback/qq">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">APP ID</label>
        <div class="col-sm-6">
            <input type="text" name="qq_app_id" class="form-control" value="${options['qq_app_id']}">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">APP KEY</label>
        <div class="col-sm-6">
            <input type="text" name="qq_app_key" class="form-control" value="${options['qq_app_key']}">
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-12">
            <div class="help-block">微博登录</div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">回调地址</label>
        <div class="col-sm-6">
            <input type="text" name="weibo_callback" class="form-control" value="${options['weibo_callback']}" placeholder="示例: http://{domain}/oauth/callback/weibo">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">CLIENT ID</label>
        <div class="col-sm-6">
            <input type="text" name="weibo_client_id" class="form-control" value="${options['weibo_client_id']}">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">CLIENT SERCRET</label>
        <div class="col-sm-6">
            <input type="text" name="weibo_client_sercret" class="form-control" value="${options['weibo_client_sercret']}">
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-12">
            <div class="help-block">Github登录</div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">回调地址</label>
        <div class="col-sm-6">
            <input type="text" name="github_callback" class="form-control" value="${options['github_callback']}" placeholder="示例: http://{domain}/oauth/callback/github">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">CLIENT ID</label>
        <div class="col-sm-6">
            <input type="text" name="github_client_id" class="form-control" value="${options['github_client_id']}">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">CLIENT SERCRET</label>
        <div class="col-sm-6">
            <input type="text" name="github_secret_key" class="form-control" value="${options['github_secret_key']}">
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary">提交</button>
        </div>
    </div>
</form>