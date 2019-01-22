<form id="qForm" class="form-horizontal" method="post" action="update">
    <#include "/admin/message.ftl">

    <div class="form-group">
        <label class="col-lg-2 control-label">站点名称</label>
        <div class="col-lg-3">
            <input type="text" name="site_name" class="form-control" value="${configs['site_name'].value}">
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">域名</label>
        <div class="col-lg-8">
            <input type="text" name="site_domain" class="form-control" value="${configs['site_domain'].value}">
            <p class="help-block">示例: http://mtons.com</p>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">站点关键字</label>
        <div class="col-lg-7">
            <input type="text" name="site_keywords" class="form-control" value="${configs['site_keywords'].value}">
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">站点描述</label>
        <div class="col-lg-8">
            <textarea rows="3" class="form-control" name="site_description" class="form-control" placeholder="请输入meta标签">${configs['site_description'].value}</textarea>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">扩展metas</label>
        <div class="col-lg-8">
            <textarea rows="3" class="form-control" name="site_metas" placeholder="请输入meta标签">${configs['site_metas'].value}</textarea>
        </div>
    </div>
	<div class="form-group">
        <label class="col-lg-2 control-label">copyright</label>
        <div class="col-lg-5">
            <input type="text" name="site_copyright" class="form-control" value="${configs['site_copyright'].value}">
            <p class="help-block">示例: Copyright © Mtons</p>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">备案号</label>
        <div class="col-lg-5">
            <input type="text" name="site_icp" class="form-control" value="${configs['site_icp'].value}">
            <p class="help-block">示例: 京ICP备12345678号</p>
        </div>
    </div>
    <div class="form-group">
        <div class="col-lg-9 col-lg-offset-3">
            <button type="submit" class="btn btn-primary btn-small">提交</button>
        </div>
    </div>
</form>