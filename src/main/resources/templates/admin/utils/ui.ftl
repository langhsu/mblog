<#-- layout -->
<#macro layout>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>后台管理 - ${options['site_name']}</title>

    <!-- Favicons -->
    <link rel="apple-touch-icon-precomposed" href="http://mtons.com/dist/images/logo.png"/>
    <link rel="shortcut icon" href="http://mtons.com/dist/images/logo.png"/>

    <!-- Bootstrap -->
    <link href="${base}/dist/vendors/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${base}/dist/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">

    <!-- Theme Style -->
    <link href="${base}/theme/admin/dist/css/site.css" rel="stylesheet">
    <link href="${base}/theme/admin/dist/css/site.addons.css" rel="stylesheet">
    <link href="${base}/theme/admin/dist/css/skins/skin-blue.css" rel="stylesheet">

    <script type="text/javascript">
        var _MTONS = _MTONS || {};
        _MTONS.BASE_PATH = '${base}';
        _MTONS.LOGIN_TOKEN = '${profile.id}';
    </script>

    <!-- jQuery -->
    <script src="${base}/dist/js/jquery.min.js"></script>
    <script src="${base}/dist/js/plugins.js"></script>
    <!-- Bootstrap -->
    <script src="${base}/dist/vendors/bootstrap/js/bootstrap.min.js"></script>
    <script src='${base}/dist/vendors/jquery-validation/jquery.validate.min.js'></script>
    <script src='${base}/dist/vendors/jquery-validation/additional-methods.js'></script>
    <script src='${base}/dist/vendors/jquery-validation/localization/messages_zh.min.js'></script>
    <script src="${base}/dist/vendors/layer/layer.js"></script>
    <script src="${base}/theme/admin/dist/js/site.js"></script>
    <script src="${base}/theme/admin/dist/js/site.base.js"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <!-- Main Header -->
    <header class="main-header">
        <a href="${base}/index" class="logo">
            <span class="logo-mini">MBG</span>
            <span class="logo-lg"><b>M</b>BLOG</span>
        </a>
        <nav class="navbar navbar-static-top">
            <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <li><a href="/" title="跳转到前台" target="_blank"><i class="fa fa-television"></i></a></li>
                    <li class="messages-menu">
                        <a href="${base}/users/${profile.id}/messages">
                            <i class="fa fa-envelope-o"></i>
                            <#if (profile.badgesCount.messages > 0)>
                            <span class="label label-success">${profile.badgesCount.messages}</span>
                            </#if>
                        </a>
                    </li>
                    <li class="dropdown user user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <img src="<@resource src=profile.avatar/>" class="user-image" alt="User Image">
                            <span class="hidden-xs">${profile.username}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="user-header">
                                <img src="<@resource src=profile.avatar/>" class="img-circle" alt="User Image">
                                <p>${profile.username}</p>
                            </li>
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="${base}/settings/profile" class="btn btn-default btn-flat">个人资料</a>
                                </div>
                                <div class="pull-right">
                                    <a href="${base}/logout" class="btn btn-default btn-flat">退出登录</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
    </header>

    <!-- Left side column -->
    <aside class="main-sidebar">
        <section class="sidebar">
            <div class="user-panel">
                <div class="pull-left image">
                    <img src="<@resource src=profile.avatar/>" class="img-circle" alt="User Image">
                </div>
                <div class="pull-left info">
                    <p>${profile.username}</p>
                    <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                </div>
            </div>

            <!-- Sidebar Menu -->
            <ul class="sidebar-menu" data-widget="tree">
                <li class="header">MENUS</li>

                <li>
                    <a href="${base}/admin" class="active"><i class="fa fa-dashboard"></i><span>仪表盘</span></a>
                </li>
                <@menus>
                    <#list results as menu>
                        <li><a href="${base}/${menu.url}"><i class="${menu.icon}"></i><span>${menu.name}</span></a></li>
                    </#list>
                </@menus>
            </ul>
        </section>
    </aside>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <#nested/>
    </div>

    <!-- Main Footer -->
    <footer class="main-footer">
        <!-- To the right -->
        <div class="pull-right hidden-xs">${site.version}</div>
        <!-- Default to the left -->
        <strong>Copyright &copy; 2019 <a href="#">mtons</a>.</strong> All rights reserved.
    </footer>
</body>
</html>
</#macro>

<#macro pager url p spans>
    <#local span = (spans - 3)/2 />
    <#local pageNo = p.number + 1 />
    <#if (url?index_of("?") != -1)>
        <#local cURL = (url + "&pageNo=") />
    <#else>
        <#local cURL = (url + "?pageNo=") />
    </#if>

<ul class="pagination no-margin pull-right">
    <#if (pageNo > 1)>
        <#local prev = pageNo - 1 />
        <li><a class="prev" href="${cURL}${prev}" pageNo="1">&nbsp;<i class="fa fa-angle-left"></i>&nbsp;</a></li>
    </#if>

    <#local totalNo = span * 2 + 3 />
    <#local totalNo1 = totalNo - 1 />
    <#if (p.totalPages > totalNo)>
        <#if (pageNo <= span + 2)>
            <#list 1..totalNo1 as i>
                <@pagelink pageNo, i, cURL/>
            </#list>
            <@pagelink 0, 0, "#"/>
            <@pagelink pageNo, p.totalPages, cURL />
        <#elseif (pageNo > (p.totalPages - (span + 2)))>
            <@pagelink pageNo, 1, cURL />
            <@pagelink 0, 0, "#"/>
            <#local num = p.totalPages - totalNo + 2 />
            <#list num..p.totalPages as i>
                <@pagelink pageNo, i, cURL/>
            </#list>
        <#else>
            <@pagelink pageNo, 1, cURL />
            <@pagelink 0 0 "#" />
            <#local num = pageNo - span />
            <#local num2 = pageNo + span />
            <#list num..num2 as i>
                <@pagelink pageNo, i, cURL />
            </#list>
            <@pagelink 0, 0, "#"/>
            <@pagelink pageNo, p.totalPages, cURL />
        </#if>
    <#elseif (p.totalPages > 1)>
        <#list 1..p.totalPages as i>
            <@pagelink pageNo, i, cURL />
        </#list>
    <#else>
        <@pagelink 1, 1, cURL/>
    </#if>

    <#if (pageNo lt p.totalPages)>
        <#local next = pageNo + 1/>
        <li><a href="${cURL}${next}" pageNo="${next}">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;</a></li>
    </#if>
</ul>
</#macro>

<#macro pagelink pageNo idx url>
    <#if (idx == 0)>
    <li><span>...</span></li>
    <#elseif (pageNo == idx)>
    <li class="active"><a href="javascript:void(0);"><span>${idx}</span></a></li>
    <#else>
    <li><a href="${url}${idx}">${idx}</a></li>
    </#if>
</#macro>
