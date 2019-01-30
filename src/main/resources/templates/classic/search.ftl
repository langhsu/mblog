<#include "/classic/utils/ui.ftl"/>

<@layout "搜索:" + kw>

<div class="row streams">
    <div class="col-xs-12 col-md-9 side-left">
        <div class="posts ">
            <ul class="ajax-load-box posts-con">
                <li class="ajax-load-con content">
                    <div class="content-box posts-aside">
                        <div class="posts-default-content">搜索: ${kw} 共 ${page.totalElements} 个结果.</div>
                    </div>
                </li>
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
                                <h2><a href="${base}/view/${row.id}" title="">${row.title}</a></h2>
                                <div class="posts-gallery-text">${row.summary}</div>
                                <div class="posts-default-info posts-gallery-info">
                                    <ul>
                                        <li class="post-author hidden-xs">
                                            <div class="avatar">
                                                <img src="<@resource src=row.author.avatar + '?t=' + .now?time/>" class="lazy avatar avatar-50 photo" height="50" width="50">
                                            </div>
                                            <a href="${base}/users/${row.author.id}" target="_blank">${row.author.name}</a>
                                        </li>
                                        <li class="ico-cat"><i class="icon-list-1"></i><@classify row/></li>
                                        <li class="ico-time"><i class="icon-clock"></i>${timeAgo(row.created)}</li>
                                        <li class="ico-eye hidden-xs"><i class="icon-speech"></i> ${row.views}</li>
                                        <li class="ico-like hidden-xs"><i class="icon-bubbles"></i> ${row.comments}</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    <#else>
                        <div class="content-box posts-aside">
                            <div class="posts-default-content">
                                <div class="posts-default-title">
                                    <h2><a href="${base}/view/${row.id}" title="">${row.title}</a></h2>
                                </div>
                                <div class="posts-text">${row.summary}</div>
                                <div class="posts-default-info">
                                    <ul>
                                        <li class="post-author hidden-xs">
                                            <div class="avatar">
                                                <img src="<@resource src=row.author.avatar/>" class="lazy avatar avatar-50 photo" height="50" width="50">
                                            </div>
                                            <a href="${base}/users/${row.author.id}" target="_blank">${row.author.name}</a>
                                        </li>
                                        <li class="ico-cat"><i class="icon-list-1"></i><@classify row/></li>
                                        <li class="ico-time"><i class="icon-clock"></i>${timeAgo(row.created)}</li>
                                        <li class="ico-eye hidden-xs"><i class="icon-speech"></i> ${row.views}</li>
                                        <li class="ico-like hidden-xs"><i class="icon-bubbles"></i> ${row.comments}</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </#if>
                    </li>
                </#list>
                <#if !results?? || results.content?size == 0>
                    <li class="ajax-load-con content">
                        <div class="content-box posts-aside">
                            <div class="posts-default-content">该目录下还没有内容!</div>
                        </div>
                    </li>
                </#if>
            </ul>
        </div>
        <div class="text-center">
            <@pager request.requestURI, results, 5/>
        </div>
    </div>
    <div class="col-xs-12 col-md-3 side-right">
        <#include "/classic/inc/right.ftl" />
    </div>
</div>
</@layout>

