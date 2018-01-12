<!-- Login dialog BEGIN -->
<div id="login_alert" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document" style="width: 450px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">请登录</h4>
            </div>
            <div class="modal-body">
                <form method="POST" action="${base}/login" accept-charset="UTF-8">
                    <div class="form-group ">
                        <label class="control-label" for="username">账号</label>
                        <input class="form-control" id="ajax_login_username" name="username" type="text" required>
                    </div>
                    <div class="form-group ">
                        <label class="control-label" for="password">密码</label>
                        <input class="form-control" id="ajax_login_password" name="password" type="password" required>
                    </div>
                    <button id="ajax_login_submit" class="btn btn-lg btn-success btn-block btn-sm" type="button">
                        登录 Use it
                    </button>
                    <div id="ajax_login_message" class="text-danger"></div>
                    <hr>
                    <fieldset class="form-group">
                        <div class="alert alert-info">
                            使用以下方法注册或者登录（<a class="forget-password" href="${base}/forgot/apply">忘记密码？</a>）
                        </div>
                        <a class="btn btn-default btn-block" href="${base}/oauth/callback/call_weibo">
                            <i class="fa fa-weibo"></i> 微博帐号登录
                        </a>
                        <a class="btn btn-default btn-block" href="${base}/oauth/callback/call_qq">
                            <i class="fa fa-qq"></i> QQ帐号登录
                        </a>
                    </fieldset>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- Login dialog END -->

<!-- Fixed navbar -->
<header class="site-header headroom">
    <!--[if lt IE 9]>
	<div class="alert alert-danger alert-dismissible fade in" role="alert" style="margin-bottom:0">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		<strong>您正在使用低版本浏览器，</strong> 在本页面的显示效果可能有差异。
		建议您升级到
		<a href="http://www.google.cn/intl/zh-CN/chrome/" target="_blank">Chrome</a>
		或以下浏览器：
		<a href="www.mozilla.org/en-US/firefox/‎" target="_blank">Firefox</a> /
		<a href="http://www.apple.com.cn/safari/" target="_blank">Safari</a> /
		<a href="http://www.opera.com/" target="_blank">Opera</a> /
		<a href="http://windows.microsoft.com/en-us/internet-explorer/download-ie" target="_blank">Internet Explorer 9+</a>
	</div>
    <![endif]-->

    <div class="container">
        <nav class="navbar" role="navigation">
            <div class="navbar-header">
                <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${base}/">
                    <img src="${base}/static/default/images/logo.png"/>
                </a>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
					<#if profile??>
						<li data="user">
							<a href="${base}/user" nav="user">我的主页</a>
						</li>
					</#if>
					<#list channels as row>
						<li>
							<a href="${base}/channel/${row.id}" nav="${row.name}">${row.name}</a>
						</li>
					</#list>
                </ul>
                <ul class="navbar-button list-inline" id="header_user">
                    <li view="search" class="hidden-xs hidden-sm">
                        <form method="GET" action="${base}/search" accept-charset="UTF-8" class="navbar-form navbar-left">
                            <div class="form-group">
                                <input class="form-control search-input mac-style" placeholder="搜索" name="kw" type="text" value="${kw}">
                                <button class="search-btn" type="submit"><i class="fa fa-search"></i></button>
                            </div>
                        </form>
                    </li>

				<#if profile??>
                    <li><a href="${base}/post/new" class="btn btn-default btn-sm"><i class="icon icon-note"></i> 写文章</a></li>
                    <li class="dropdown">
                        <a href="#" class="user dropdown-toggle" data-toggle="dropdown">
                            <img class="img-circle" src="${base}${profile.avatar}">
                            <span>${profile.name}</span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li>
                                <a href="${base}/user/profile">编辑资料</a>
                            </li>
                            <li class="divider"></li>
                            <@shiro.hasPermission name="admin">
                                <li><a href="${base}/admin">后台管理</a></li>
                            </@shiro.hasPermission>

                            <li><a href="${base}/logout">退出</a></li>
                        </ul>
                    </li>
				<#else>
                    <li><a href="${base}/login" class="btn btn-default btn-sm signup">登录</a></li>

                    <li><a href="${base}/register" class="btn btn-primary btn-sm signup">注册</a></li>
				</#if>

                </ul>
            </div>
        </nav>
    </div>
</header>

<script type="text/javascript">
$(function () {
	$('a[nav]').each(function(){  
        $this = $(this);
        if($this[0].href == String(window.location)){  
            $this.closest('li').addClass("active");  
        }  
    });
});
</script>
<!-- Header END -->