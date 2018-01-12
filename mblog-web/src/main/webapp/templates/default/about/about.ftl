<#include "/default/utils/ui.ftl"/>

<@layout "关于我们">

<div class="row main">
    <div class="col-xs-12 col-md-9 side-left topics-show">
        <!-- view show -->
        <div class="topic panel panel-default">
            <div class="infos panel-heading">

                <h1 class="panel-title topic-title">关于我们</h1>

                <div class="meta inline-block">
                    <a class="author" href="#">官方团队</a>
                </div>
                <div class="clearfix"></div>
            </div>

            <div class="content-body entry-content panel-body ">
                <div class="markdown-body" id="emojify">
                    <p><strong>关于Mtons</strong><br/></p>
                    <p>mtons(中文名:影通网)是一个以轻内容为主的博客分享平台，我们主张轻量、极简；在这里你快速的找到你赶兴趣的内容。</p>
                    <p><strong>知识产权</strong><br/></p>
                    <p>网站内容来自用户自行发起，或转载于互联网，如有涉及到版权问题，请告诉我们，我们将会对其进行删除处理</p>
                    <!--
                    <p><strong>技术架构</strong><br/></p>
                    <p>mtons采用通用的SSH框架构建</p>
                    <p> 页面端采用Velocity模板语言</p>
                    <p>存储采用Ehcache、MySQL，搜索引擎使用的Lucene</p>
                    <p>前端为响应式设计，主要使用Bootstrap框架+jQuery。</p>
                    -->
                    <p><strong>本网站为贡献广大网友，特此开源</strong></p>
                    <p>代码托管在OSC <a href="http://git.oschina.net/mtons/mblog" target="_blank">http://git.oschina.net/mtons/mblog</a></p>

				</div>
            </div>
            <div class="panel-footer operate">
            </div>
            <div class="panel-footer operate">
                <div class="hidden-xs">
                    <div class="social-share" data-sites="weibo, wechat, facebook, twitter, google, qzone, qq"></div>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
        <!-- /view show -->
    </div>
    <div class="col-xs-12 col-md-3 side-right hidden-xs hidden-sm">
		<#include "/default/inc/right.ftl"/>
    </div>
</div>

</@layout>