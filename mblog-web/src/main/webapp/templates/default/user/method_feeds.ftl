<#include "/default/utils/ui.ftl"/>

<@layout "我的主页">

<div class="row users-show streams">
    <div class="col-xs-12 col-md-3 side-left">
        <#include "/default/user/right.ftl"/>
    </div>
    <div class="col-xs-12 col-md-9 side-right">
        <div class="panel panel-default">
            <div class="panel-heading">
                动态
            </div>

            <div class="panel-body remove-padding-horizontal">
                <ul class="list-group topic-list">
                    <#list page.content as row>
                        <#assign target = row.post>
                        <li class="list-group-item " style="padding: 0 15px;">
                            <a class="reply_count_area hidden-xs pull-right" href="#">
                                <div class="count_set">
                                    <span class="count_of_votes" title="阅读数">${target.views}</span>
                                    <span class="count_seperator">/</span>
                                    <span class="count_of_replies" title="回复数">${target.comments}</span>
                                    <span class="count_seperator">/</span>
                                    <span class="count_of_visits" title="点赞数">${target.favors}</span>
                                    <span class="count_seperator">|</span>
                                    <abbr class="timeago">${timeAgo(target.created)}</abbr>
                                </div>
                            </a>
                            <div class="avatar pull-left">
                                <a href="${base}/users/${target.author.id}">
                                    <img class="media-object img-thumbnail avatar avatar-middle"
                                         src="${base + target.author.avatar}">
                                </a>
                            </div>
                            <div class="infos">
                                <div class="media-heading">
                                    <@classify target/><a href="${base}/view/${target.id}">${target.title}</a>
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
            <div class="panel-footer">
                <@pager request.requestURI!"", page, 5/>
            </div>
        </div>
    </div>
</div>
<!-- /end -->

</@layout>