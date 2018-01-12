<#include "/default/utils/ui.ftl"/>

<@layout "我的粉丝">
<div class="row users-show streams">
    <div class="col-xs-12 col-md-3 side-left">
        <#include "/default/user/right.ftl"/>
    </div>
    <div class="col-xs-12 col-md-9 side-right">
        <div class="panel panel-default">
            <div class="panel-heading">
                我的粉丝
            </div>

            <div class="panel-body remove-padding-horizontal">
                <ul class="list-group topic-list">
                    <#list page.content as row>
                        <li class="list-group-item " style="padding: 0 15px;" id="loop-${row.id}">
                            <div class="reply_count_area hidden-xs pull-right" href="#">
                                <div class="count_set">
                                    <span class="count_of_replies" title="回复数">${row.comments}</span>
                                    <span class="count_seperator">/</span>
                                    <span class="count_of_visits" title="点赞数">${row.favors}</span>
                                </div>
                            </div>
                            <div class="avatar pull-left">
                                <a href="${base}/users/${row.id}">
                                    <img class="media-object img-thumbnail avatar avatar-middle"
                                         src="${base + row.avatar}">
                                </a>
                            </div>
                            <div class="infos">
                                <div class="media-heading">
                                <#--<span class="hidden-xs label label-warning">${row.channel.name}</span>-->
                                    <a href="${base}/users/${row.id}">${row.name}</a>
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