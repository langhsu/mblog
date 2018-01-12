<#include "/default/utils/ui.ftl"/>

<@layout "标签:" + kw>

<div class="row streams">
    <div class="col-xs-12 col-md-9 side-left">
        <div class="panel panel-default">
            <div class="panel-heading">
                <ul class="list-inline topic-filter">
                    <li class="popover-with-html">
                        标签: ${kw} 共 ${page.totalElements} 个结果.
                    </li>
                </ul>
                <div class="clearfix"></div>
            </div>

            <div class="panel-body remove-padding-horizontal">

                <ul class="list-group row topic-list">
					<#list page.content as row>
                        <li class="list-group-item ">
                            <a class="reply_count_area hidden-xs pull-right" href="#">
                                <div class="count_set">
                                    <span class="count_of_votes" title="阅读数">${row.views}</span>
                                    <span class="count_seperator">/</span>
                                    <span class="count_of_replies" title="回复数">${row.comments}</span>
                                    <span class="count_seperator">/</span>
                                    <span class="count_of_visits" title="点赞数">${row.favors}</span>
                                    <span class="count_seperator">|</span>
                                    <abbr class="timeago">${timeAgo(row.created)}</abbr>
                                </div>
                            </a>
                            <div class="avatar pull-left">
                                <a href="${base}/users/${row.author.id}">
                                    <img class="media-object img-thumbnail avatar avatar-middle"
                                         src="${base + row.author.avatar}">
                                </a>
                            </div>
                            <div class="infos">
                                <div class="media-heading">
								<#--<span class="hidden-xs label label-warning">${row.channel.name}</span>-->
                                    <a href="${base}/view/${row.id}">${row.title}</a>
                                </div>
                            </div>
                        </li>
					</#list>

					<#if page.content?size == 0>
                        <li class="list-group-item ">
                            <div class="infos">
                                <div class="media-heading">该目录下还没有内容!</div>
                            </div>
                        </li>
					</#if>
                </ul>
            </div>

            <div class="panel-footer text-right remove-padding-horizontal pager-footer">
				<@pager request.requestURI, page, 5/>
            </div>
        </div>
    </div>
    <div class="col-xs-12 col-md-3 side-right">
		<#include "/default/inc/right.ftl" />
    </div>
</div>
</@layout>