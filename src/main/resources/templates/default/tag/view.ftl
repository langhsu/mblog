<@layout.extends name="/inc/layout.ftl">
    <@layout.put block="title">
        <title>标签: ${kw}</title>
    </@layout.put>

    <@layout.put block="contents">
        <div class="row streams">
            <div class="col-xs-12 col-md-9 side-left">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <ul class="list-inline topic-filter">
                            <li class="popover-with-html">
                                标签: ${name} 共 ${results.totalElements} 个结果.
                            </li>
                        </ul>
                        <div class="clearfix"></div>
                    </div>

                    <div class="panel-body remove-padding-horizontal">

                        <ul class="list-group row topic-list">
                            <#list results.content as row>
                                <#assign post = row.post />
                                <li class="list-group-item ">
                                    <a class="reply_count_area hidden-xs pull-right" href="#">
                                        <div class="count_set">
                                            <span class="count_of_votes" title="阅读数">${post.views}</span>
                                            <span class="count_seperator">/</span>
                                            <span class="count_of_replies" title="回复数">${post.comments}</span>
                                            <span class="count_seperator">/</span>
                                            <span class="count_of_visits" title="点赞数">${post.favors}</span>
                                            <span class="count_seperator">|</span>
                                            <abbr class="timeago">${timeAgo(post.created)}</abbr>
                                        </div>
                                    </a>
                                    <div class="avatar pull-left">
                                        <@utils.showAva post.author "media-object img-thumbnail avatar avatar-middle"/>
                                    </div>
                                    <div class="infos">
                                        <div class="media-heading">
                                        <#--<span class="hidden-xs label label-warning">${row.channel.name}</span>-->
                                            <a href="${base}/post/${post.id}">${post.title}</a>
                                        </div>
                                    </div>
                                </li>
                            </#list>

                            <#if results.content?size == 0>
                                <li class="list-group-item ">
                                    <div class="infos">
                                        <div class="media-heading">该目录下还没有内容!</div>
                                    </div>
                                </li>
                            </#if>
                        </ul>
                    </div>

                    <div class="panel-footer text-right remove-padding-horizontal pager-footer">
                        <@utils.pager request.requestURI, results, 5/>
                    </div>
                </div>
            </div>
            <div class="col-xs-12 col-md-3 side-right">
                <#include "/default/inc/right.ftl" />
            </div>
        </div>
    </@layout.put>
</@layout.extends>