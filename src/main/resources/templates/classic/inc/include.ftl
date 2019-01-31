<meta property="mtons:mblog" content="${site.version}">
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
