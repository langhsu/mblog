<#macro posts_item row escape=true>
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
                <h2><a href="${base}/view/${row.id}"><#if escape>${row.title?html}<#else>${row.title}</#if></a></h2>
                <div class="posts-gallery-text">${row.summary}</div>
                <div class="posts-default-info posts-gallery-info">
                    <ul>
                        <li class="post-author hidden-xs">
                            <div class="avatar">
                                <img src="<@resource src=row.author.avatar + '?t=' + .now?time/>" class="lazy avatar avatar-50 photo" height="50" width="50">
                            </div>
                            <a href="${base}/users/${row.author.id}" target="_blank">${row.author.name}</a>
                        </li>
                        <li class="ico-cat"><@classify row/></li>
                        <li class="ico-time"><i class="icon-clock"></i>${timeAgo(row.created)}</li>
                        <li class="ico-eye hidden-xs"><i class="icon-book-open"></i>${row.views}</li>
                        <li class="ico-like hidden-xs"><i class="icon-bubble"></i>${row.comments}</li>
                    </ul>
                </div>
            </div>
        </div>
    <#else>
        <div class="content-box posts-aside">
            <div class="posts-default-content">
                <div class="posts-default-title">
                    <h2><a href="${base}/view/${row.id}"><#if escape>${row.title?html}<#else>${row.title}</#if></a></h2>
                </div>
                <div class="posts-text">${row.summary}</div>
                <div class="posts-default-info">
                    <ul>
                        <li class="post-author hidden-xs">
                            <div class="avatar">
                                <img src="<@resource src=row.author.avatar + '?t=' + .now?time/>" class="lazy avatar avatar-50 photo" height="50" width="50">
                            </div>
                            <a href="${base}/users/${row.author.id}" target="_blank">${row.author.name}</a>
                        </li>
                        <li class="ico-cat"><@classify row/></li>
                        <li class="ico-time"><i class="icon-clock"></i>${timeAgo(row.created)}</li>
                        <li class="ico-eye hidden-xs"><i class="icon-book-open"></i>${row.views}</li>
                        <li class="ico-like hidden-xs"><i class="icon-bubble"></i>${row.comments}</li>
                    </ul>
                </div>
            </div>
        </div>
    </#if>
</li>
</#macro>