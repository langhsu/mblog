<form id="qForm" class="form-horizontal" method="post" action="update">
    <div class="box-body">
        <div class="col-md-6">
            <div class="form-group">
                <label class="col-lg-3 control-label">SMTP地址</label>
                <div class="col-lg-9">
                    <input type="text" name="mail_smtp_host" class="form-control" value="${options['mail_smtp_host']}" placeholder="smtp.mtons.com">
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-3 control-label">邮箱账号</label>
                <div class="col-lg-9">
                    <input type="text" name="mail_smtp_username" class="form-control" value="${options['mail_smtp_username']}" placeholder="请输入邮箱账户名">
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-3 control-label">邮箱密码</label>
                <div class="col-lg-9">
                    <input type="text" name="mail_smtp_password" class="form-control" value="${options['mail_smtp_password']}" placeholder="请输入邮箱账户密码">
                </div>
            </div>
        </div>
    </div>
    <div class="box-footer">
        <button type="submit" class="btn btn-primary">提交</button>
    </div>
</form>