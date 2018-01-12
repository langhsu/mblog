<#-- layout -->
<#macro layout>
<!DOCTYPE html>
<html lang="en" class="app">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${site_name} - 后台管理</title>

    <!-- Bootstrap -->
    <link href="${base}/dist/vendors/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${base}/dist/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="${base}/dist/vendors/treeTable/themes/vsStyle/treeTable.min.css" rel="stylesheet"/>

    <!-- Custom Theme Style -->
    <link href="${base}/static/admin/css/custom.min.css" rel="stylesheet">

    <!-- jQuery -->
    <script src="${base}/dist/js/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="${base}/dist/vendors/bootstrap/js/bootstrap.min.js"></script>
    <script src='${base}/dist/vendors/validate/jquery-validate.js'></script>
    <!-- FastClick -->
    <script src="${base}/dist/vendors/fastclick/lib/fastclick.js"></script>

    <script src="${base}/dist/vendors/layer/layer.js"></script>
    <script src="${base}/dist/vendors/treeTable/jquery.treeTable.min.js"></script>

    <script type="text/javascript">
        window.UEDITOR_HOME_URL = '${base}/dist/vendors/ueditor/';
    </script>
</head>
<body class="nav-md">
<div class="container body">
    <div class="main_container">
        <div class="col-md-3 left_col">
            <div class="left_col scroll-view">
                <div class="navbar nav_title" style="border: 0;">
                    <a href="${base}/index" class="site_title"><span>Mtons</span></a>
                </div>

                <div class="clearfix"></div>

                <br/>

                <!-- sidebar menu -->
                <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
                    <div class="menu_section">
                        <h3>系统菜单</h3>
                        <ul class="nav side-menu">
                            <li><a href="${base}/admin"><i class="fa fa-home"></i> Home</a>
                            </li>
                            <@authc pid=2>
                                <#list results as menu>
                                    <@shiro.hasPermission name=menu.permission>
                                        <li><a href="${base}/${menu.url}" nav="${menu.sort}"><i class="${menu.icon}"></i>${menu.name}</a></li>
                                    </@shiro.hasPermission>
                                </#list>
                            </@authc>

                        </ul>
                    </div>
                </div>
                <!-- /sidebar menu -->
            </div>
        </div>

        <!-- top navigation -->
        <div class="top_nav">
            <div class="nav_menu">
                <nav>
                    <div class="nav toggle">
                        <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                    </div>

                    <ul class="nav navbar-nav navbar-right">
                        <li class="">
                            <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown"
                               aria-expanded="false">
                                <img src="${base}${profile.avatar}" alt="">${profile.username}
                                <span class=" fa fa-angle-down"></span>
                            </a>
                            <ul class="dropdown-menu dropdown-usermenu pull-right">
                                <li><a href="${base}/user/profile"> Profile</a></li>
                                <li><a href="${base}/logout"><i class="fa fa-sign-out pull-right"></i> Log Out</a></li>
                            </ul>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
            <div>
                <#nested/>
            </div>
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <footer>
            <div class="pull-right">
                Mblog - Powered By <a href="http://mtons.com/?copyright" target="_blank">Mtons</a>
            </div>
            <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
    </div>
    <!-- Custom Theme Scripts -->
    <script src="${base}/static/admin/js/custom.min.js"></script>
    <script src="${base}/static/admin/js/app.data.js"></script>
</body>
</html>
</#macro>

<#macro pager url p spans>
    <#local span = (spans - 3)/2 />
    <#local pageNo = p.number + 1 />
    <#if (url?index_of("?") != -1)>
        <#local cURL = (url + "&pn=") />
    <#else>
        <#local cURL = (url + "?pn=") />
    </#if>

<ul class="pagination">
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