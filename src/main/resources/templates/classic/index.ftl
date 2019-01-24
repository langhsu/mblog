<#include "/classic/utils/ui.ftl"/>
<#assign topId = 1 />

<@layout>
<!-- top -->
<@contents channelId=topId size=3>
    <#if  results.content?size gt 0>
        <div class="row banner">
            <#list results.content as row>
                <div class="banner-item col-xs-12 col-sm-4 col-md-4">
                    <div class="index-banner-box"
                        <#if row.thumbnail??>
                         style="background-image:url(<@resource src=row.thumbnail/>)"
                        <#else>
                         style="background-image:url(${base}/dist/images/spinner-overlay.png)"
                        </#if> >
                        <a class="top" href="${base}/view/${row.id}">
                            <div class="overlay"></div>
                            <div class="line"></div>
                            <div class="title">
                                <h3>${row.title?html}</h3>
                            </div>
                        </a>
                    </div>
                </div>
            </#list>
        </div>
    </#if>
</@contents>

<!-- top/end -->

<div class="row">
    <div class="col-xs-12 col-md-9 side-left">
        <div class="posts">
            <@contents pn=pn>
            <ul class="ajax-load-box posts-con">
                <#list results.content as row>
                <li class="ajax-load-con content">
                    <#if row.thumbnail?? && row.thumbnail?length gt 0>
                        <div class="content-box posts-gallery-box">
                            <div class="posts-gallery-img">
                                <a href="${base}/view/${row.id}" title="">
                                    <div class="overlay"></div>
                                    <img class="lazy thumbnail" src="<@resource src=row.thumbnail/>" style="display: inline-block;">
                                </a>
                            </div>
                            <div class="posts-gallery-content">
                                <h2><a href="${base}/view/${row.id}" title="">${row.title?html}</a></h2>
                                <div class="posts-gallery-text">${row.summary}</div>
                                <div class="posts-default-info">
                                    <ul>
                                        <li class="post-author">
                                            <div class="avatar">
                                                <img src="<@resource src=row.author.avatar + '?t=' + .now?time/>" class="lazy avatar avatar-50 photo" height="50" width="50">
                                            </div>
                                            <a href="${base}/users/${row.author.id}" target="_blank">${row.author.name}</a>
                                        </li>
                                        <li class="ico-cat"><i class="icon-list-1"></i><@classify row/></li>
                                        <li class="ico-time"><i class="icon-clock"></i>${timeAgo(row.created)}</li>
                                        <li class="ico-eye"><i class="icon-speech"></i> ${row.views}</li>
                                        <li class="ico-like"><i class="icon-bubbles"></i> ${row.comments}</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    <#else>
                        <div class="content-box posts-aside">
                            <div class="posts-default-content">
                                <div class="posts-default-title">
                                    <h2><a href="${base}/view/${row.id}" title="">${row.title?html}</a></h2>
                                </div>
                                <div class="posts-text">${row.summary}</div>
                                <div class="posts-default-info">
                                    <ul>
                                        <li class="post-author">
                                            <div class="avatar">
                                                <img src="<@resource src=row.author.avatar + '?t=' + .now?time/>" class="lazy avatar avatar-50 photo" height="50" width="50">
                                            </div>
                                            <a href="${base}/users/${row.author.id}" target="_blank">${row.author.name}</a>
                                        </li>
                                        <li class="ico-cat"><i class="icon-list-1"></i><@classify row/></li>
                                        <li class="ico-time"><i class="icon-clock"></i>${timeAgo(row.created)}</li>
                                        <li class="ico-eye"><i class="icon-speech"></i> ${row.views}</li>
                                        <li class="ico-like"><i class="icon-bubbles"></i> ${row.comments}</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </#if>
                </li>
                </#list>
                <#if  results.content?size == 0>
                    <li class="ajax-load-con content">
                        <div class="content-box posts-aside">
                            <div class="posts-default-content">该目录下还没有内容!</div>
                        </div>
                    </li>
                </#if>
            </ul>
            </@contents>
        </div>
        <div class="text-center">
            <!-- Pager -->
            <@pager request.requestURI!"", results, 5/>
        </div>
    </div>
    <div class="col-xs-12 col-md-3 side-right">
        <#include "/classic/inc/right.ftl"/>
    </div>
</div>

</@layout>