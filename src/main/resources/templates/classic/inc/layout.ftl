<#-- Layout -->
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <!--
    ------------------------------------------------------
     _____ ______   ________  ___       ________  ________
    |\   _ \  _   \|\   __  \|\  \     |\   __  \|\   ____\
    \ \  \\\__\ \  \ \  \|\ /\ \  \    \ \  \|\  \ \  \___|
     \ \  \\|__| \  \ \   __  \ \  \    \ \  \\\  \ \  \  ___
      \ \  \    \ \  \ \  \|\  \ \  \____\ \  \\\  \ \  \|\  \
       \ \__\    \ \__\ \_______\ \_______\ \_______\ \_______\
        \|__|     \|__|\|_______|\|_______|\|_______|\|_______|
    ------------------------------------------------------------
    version: ${site.version}
    github : https://github.com/langhsu/mblog
    ------------------------------------------------------------
    -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--[if IE]>
    <meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1'/>
    <![endif]-->

    <@layout.block name="title">
        <title>${options['site_name']}</title>
    </@layout.block>

    <@layout.block name="keywords">
        <meta name="keywords" content="mtons, ${options['site_keywords']}">
    </@layout.block>

    <@layout.block name="keywords">
        <meta name="description" content="${options['site_description']}">
    </@layout.block>

    <meta name="mtons:mblog" content="${site.version}">

    ${options['site_metas']}

    <link href="${base}/dist/vendors/pace/themes/pace-theme-minimal.css" rel="stylesheet"/>
    <link href="${base}/dist/vendors/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>

    <link href="${base}/dist/css/editor.css" rel="stylesheet"/>
    <link href="${base}/dist/css/plugins.css" rel="stylesheet"/>
    <link href="${base}/theme/classic/dist/css/style.css" rel="stylesheet"/>

    <link href="${base}/dist/vendors/simple-line-icons/css/simple-line-icons.css" rel="stylesheet"/>
    <link href="${base}/dist/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>

    <script src="${base}/dist/vendors/pace/pace.min.js"></script>

    <script src="${base}/dist/js/jquery.min.js"></script>
    <script src="${base}/dist/vendors/layer/layer.js"></script>
    <script src="${base}/dist/vendors/bootstrap/js/bootstrap.min.js"></script>

    <script type="text/javascript">
        var _MTONS = _MTONS || {};
        _MTONS.BASE_PATH = '${base}';
        _MTONS.LOGIN_TOKEN = '${profile.id}';
    </script>

    <script src="${base}/dist/js/sea.js"></script>
    <script src="${base}/dist/js/sea.config.js"></script>

    <!-- Favicons -->
    <link href="<@resource src=options['site_favicon']/>" rel="apple-touch-icon-precomposed" />
    <link href="<@resource src=options['site_favicon']/>" rel="shortcut icon" />

    <@layout.block name="head">
        <script src="${base}/dist/js/sea.js"></script>
        <script src="${base}/dist/js/sea.config.js"></script>
    </@layout.block>
</head>
<body>
    <!-- header -->
    <@layout.block name="header">
        <#include "/classic/inc/header.ftl"/>
    </@layout.block>
    <!-- /header -->

    <!-- content -->
    <div class="wrap">
        <!-- Main -->
        <div class="container">
            <@layout.block name="contents">
                <h2>Contents will be here</h2>
            </@layout.block>
        </div>
    </div>
    <!-- /content -->

    <!-- footer -->
    <@layout.block name="footer">
        <#include "/classic/inc/footer.ftl"/>
    </@layout.block>
</body>
</html>