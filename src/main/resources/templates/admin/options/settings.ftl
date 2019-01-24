<form id="qForm" method="post" action="update">
    <div class="box-body">
        <div class="col-md-4">
            <p class="lead">QQ登录</p>
            <div class="form-group">
                <label>回调地址</label>
                <input type="text" name="qq_callback" class="form-control" value="${values['qq_callback'].value}" placeholder="示例: http://{domain}/oauth/callback/qq">
            </div>
            <div class="form-group">
                <label>APP_ID</label>
                <input type="text" name="qq_app_id" class="form-control" value="${values['qq_app_id'].value}">
            </div>
            <div class="form-group">
                <label>APP_KEY</label>
                <input type="text" name="qq_app_key" class="form-control" value="${values['qq_app_key'].value}">
            </div>
        </div>

        <div class="col-md-4">
            <p class="lead">微博登录</p>
            <div class="form-group">
                <label>回调地址</label>
                <input type="text" name="weibo_callback" class="form-control" value="${values['weibo_callback'].value}" placeholder="示例: http://{domain}/oauth/callback/weibo">
            </div>
            <div class="form-group">
                <label>CLIENT_ID</label>
                <input type="text" name="weibo_client_id" class="form-control" value="${values['weibo_client_id'].value}">
            </div>
            <div class="form-group">
                <label>CLIENT_SERCRET</label>
                <input type="text" name="weibo_client_sercret" class="form-control" value="${values['weibo_client_sercret'].value}">
            </div>
        </div>

        <div class="col-md-4">
            <p class="lead">Github登录</p>
            <div class="form-group">
                <label>回调地址</label>
                <input type="text" name="github_callback" class="form-control" value="${values['github_callback'].value}" placeholder="示例: http://{domain}/oauth/callback/github">
            </div>
            <div class="form-group">
                <label>CLIENT_ID</label>
                <input type="text" name="github_client_id" class="form-control" value="${values['github_client_id'].value}">
            </div>
            <div class="form-group">
                <label>CLIENT_SERCRET</label>
                <input type="text" name="github_secret_key" class="form-control" value="${values['github_secret_key'].value}">
            </div>
        </div>
    </div>
    <div class="box-footer">
        <button type="submit" class="btn btn-primary">提交</button>
    </div>
</form>