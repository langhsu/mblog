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
                <#include "/classic/inc/posts_item.ftl"/>
                <#list results.content as row>
                    <@posts_item row false/>
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

