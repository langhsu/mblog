<@layout.extends name="/inc/layout.ftl">

    <@layout.put block="contents">
        <#assign topId = 1 />

        <!-- top -->
        <@contents channelId=topId size=3>
            <#if  results.content?size gt 0>
                <div class="row banner">
                    <#list results.content as row>
                        <div class="banner-item col-xs-12 col-sm-4 col-md-4">
                            <div class="index-banner-box"
                                <#if row.thumbnail?? && row.thumbnail?length gt 0>
                                 style="background-image:url(<@resource src=row.thumbnail/>)"
                                <#else>
                                 style="background-image:url(${base}/dist/images/spinner-overlay.png)"
                                </#if> >
                                <a class="top" href="${base}/post/${row.id}">
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
                    <@contents pageNo=pageNo>
                    <ul class="posts-list">
                        <@layout.extends name="/inc/posts_item.ftl" />
                        <#list results.content as row>
                            <@posts_item row/>
                        </#list>
                        <#if  results.content?size == 0>
                            <li class="content">
                                <div class="content-box posts-aside">
                                    <div class="posts-item">该目录下还没有内容!</div>
                                </div>
                            </li>
                        </#if>
                    </ul>
                    </@contents>
                </div>
                <div class="text-center">
                    <!-- Pager -->
                    <@utils.pager request.requestURI!"", results, 5/>
                </div>
            </div>
            <div class="col-xs-12 col-md-3 side-right">
                <@layout.extends name="/inc/right.ftl" />
            </div>
        </div>
    </@layout.put>
</@layout.extends>