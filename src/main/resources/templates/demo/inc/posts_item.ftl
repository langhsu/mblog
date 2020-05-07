<#macro posts_item row escape=true>
<li class="content">
    <#if row.thumbnail?? && row.thumbnail?length gt 0>
        <div class="content-box">
            <div class="posts-item-img">
                <a href="${base}/post/${row.id}" title="">
                    <div class="overlay"></div>
                    <img class="lazy thumbnail" src="<@resource src=row.thumbnail/>" style="display: inline-block;">
                </a>
            </div>
            <div class="posts-item posts-item-gallery">
                <h2><a href="${base}/post/${row.id}"><#if escape>${row.title?html}<#else>${row.title}</#if></a></h2>
                <div class="item-text">${row.summary}</div>
                <div class="item-info">
                    <ul>
                        <li class="post-author hidden-xs">
                            <div class="avatar">
                                <img src="<@resource src=row.author.avatar + '?t=' + .now?time/>" class="lazy avatar avatar-50 photo" height="50" width="50">
                            </div>
                            <a href="${base}/users/${row.author.id}" target="_blank">${row.author.name}</a>
                        </li>
                        <li class="ico-cat"><@utils.showChannel row/></li>
                        <li class="ico-time"><i class="icon-clock"></i>${timeAgo(row.created)}</li>
                        <li class="ico-eye hidden-xs"><i class="icon-book-open"></i>${row.views}</li>
                        <li class="ico-like hidden-xs"><i class="icon-bubble"></i>${row.comments}</li>
                    </ul>
                </div>
            </div>
        </div>
    <#else>
        <div class="content-box posts-aside">
            <div class="posts-item">
                <div class="item-title">
                    <h2><a href="${base}/post/${row.id}"><#if escape>${row.title?html}<#else>${row.title}</#if></a></h2>
                </div>
                <div class="item-text">${row.summary}</div>
                <div class="item-info">
                    <ul>
                        <li class="post-author hidden-xs">
                            <div class="avatar">
                                <img src="<@resource src=row.author.avatar + '?t=' + .now?time/>" class="lazy avatar avatar-50 photo" height="50" width="50">
                            </div>
                            <a href="${base}/users/${row.author.id}" target="_blank">${row.author.name}</a>
                        </li>
                        <li class="ico-cat"><@utils.showChannel row/></li>
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