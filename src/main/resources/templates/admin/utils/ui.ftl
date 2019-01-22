<#-- layout -->
<#macro layout>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>${site_name} - 后台管理</title>

    <!-- Bootstrap -->
    <link href="${base}/dist/vendors/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${base}/dist/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="${base}/theme/admin/css/custom-styles.css" rel="stylesheet">

    <!-- jQuery -->
    <script src="${base}/dist/js/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="${base}/dist/vendors/bootstrap/js/bootstrap.min.js"></script>
    <script src='${base}/dist/vendors/validate/jquery-validate.js'></script>
    <script src="${base}/dist/vendors/layer/layer.js"></script>
</head>
<body>
    <div id="wrapper">
        <nav class="navbar navbar-default top-navbar" role="navigation">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a href="${base}/index" class="navbar-brand">
                    <img src="${base}/dist/images/logo/logo.png" style="height: 30px;"/>
                </a>
            </div>

            <ul class="nav navbar-top-links navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                        <img src="${base}${profile.avatar}" style="width: 22px;margin-right: 5px; border-radius: 25px;">${profile.username}
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="${base}/user/profile"><i class="fa fa-user fa-fw"></i> Profile</a></li>
                        <li><a href="${base}/logout"><i class="fa fa-sign-out fa-fw"></i> Log Out</a></li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
        </nav>
        <!--/. NAV TOP  -->

        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
                    <li>
                        <a href="${base}/admin" nav="${base}/admin"><i class="fa fa-dashboard"></i> 面板</a>
                    </li>
                    <@menus>
                        <#list results as menu>
                            <li><a href="${base}/${menu.url}" nav="${base}/${menu.url}"><i class="${menu.icon}"></i>${menu.name}</a></li>
                        </#list>
                    </@menus>
                </ul>
            </div>
        </nav>
        <!-- /. NAV SIDE  -->

        <div id="page-wrapper">
            <div id="page-inner">
                <#nested/>
            </div>
        </div>
        <!-- /. PAGE WRAPPER  -->
    </div>

    <!-- Custom Theme Scripts -->
    <script src="${base}/theme/admin/js/jquery.metisMenu.js"></script>
    <script src="${base}/theme/admin/js/custom-scripts.js"></script>
    <script src="${base}/theme/admin/js/app.data.js"></script>
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