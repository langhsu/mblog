<#include "/default/utils/ui.ftl"/>

<@layout user.name + "的主页">
<div class="row users-show streams">
    <div class="col-xs-12 col-md-3 side-left">
        <ul class="list-group about-user">
            <li class="list-group-item user-card" >
                <div class="user-avatar">
                    <a href="${base}/users/${user.id}">
                        <@showAva user.avatar "img-circle"/>
                    </a>
                </div>
                <div class="user-name">
                    <span>${user.name}</span>
                </div>
            </li>
            <li class="list-group-item">
                <div class="user-datas">
                    <ul>
                        <li><strong>${user.posts}</strong><span>发布</span></li>
                        <li class="noborder"><strong>${user.comments}</strong><span>评论</span></li>
                    </ul>
                </div>
            </li>
			<li class="list-group-item">
                <span>
					<#if user.signature?? && (user.signature?length > 0) >
						${user.signature}
					<#else>
						什么都没留下
					</#if>
				</span>
			</li>
        </ul>
    </div>
    <div class="col-xs-12 col-md-9 side-right">
        <div class="panel panel-default">
            <div class="panel-heading">
                他的文章
            </div>

            <@author_contents uid=user.id pn=pn>
            <div class="panel-body">
                <ul class="list-group">
					<#list results.content as row>
                        <li class="list-group-item" el="loop-${row.id}">
                            <a href="${base}/view/${row.id}" class="remove-padding-left">${row.title}</a>
                            <span class="meta">
								${row.favors} 点赞
								<span> ⋅ </span>
								${row.comments} 回复
								<span> ⋅ </span>
								<span class="timeago">${timeAgo(row.created)}</span>
      						</span>
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
            <div class="panel-footer">
				<@pager request.requestURI!"", results, 5/>
            </div>
            </@author_contents>
        </div>
    </div>
</div>
<!-- /end -->

</@layout>