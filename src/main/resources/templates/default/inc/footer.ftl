<footer class="footer">
    <div class="container">
        <div class="footer-col footer-col-logo hidden-xs hidden-sm">
            <img src="<@resource src=options['site_logo']/>" alt="Mblog"/>
        </div>
        <div class="footer-col footer-col-copy">
            <ul class="footer-nav hidden-xs">
                <li class="menu-item"><a href="${base}/about">关于我们</a></li>
                <li class="menu-item"><a href="${base}/joinus">联系我们</a></li>
                <li class="menu-item"><a href="${base}/faqs">常见问题</a></li>
            </ul>
            <div class="copyright">
				<span>${options['site_copyright']}. ${options['site_icp']}</span>
			</div>
        </div>
        <div class="footer-col footer-col-sns hidden-xs hidden-sm">
            <div class="footer-sns">
                <!-- 请保留此处标识-->
                <span>Powered by <a href="https://github.com/langhsu/mblog" target="_blank">mblog</a></span>
            </div>
        </div>
    </div>
</footer>

<a href="#" class="site-scroll-top"></a>

<script type="text/javascript">
    seajs.use('main', function (main) {
        main.init();
    });
</script>