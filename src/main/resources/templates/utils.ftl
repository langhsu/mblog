<#-- 用户头像显示 -->
<#macro showAva user clazz>
<a href="${base}/users/${user.id}">
    <img class="${clazz}" src="<@resource src=user.avatar />"/>
</a>
</#macro>

<#-- 栏目名称显示 -->
<#macro showChannel row>
<span class=" hidden-xs label label-default <#if (row.featured > 0)>channel_featured</#if> <#if (row.weight > 0)>channel_top</#if>">${row.channel.name}</span>
</#macro>

<#-- 分页显示 -->
<#macro pager url p spans>
    <#if p??>
        <#local span = (spans - 3)/2 />
        <#if (url?index_of("?") != -1)>
            <#local cURL = (url + "&pageNo=") />
        <#else>
            <#local cURL = (url + "?pageNo=") />
        </#if>

    <ul class="pagination">
        <#assign pageNo = p.number + 1/>
        <#assign pageCount = p.totalPages />
        <#if (pageNo > 1)>
            <li><a href="${cURL}${pageNo - 1}" pageNo="${pageNo - 1}" class="prev"><i class="fa fa-angle-left"></i></a></li>
        <#else>
            <li class="disabled"><span><i class="fa fa-angle-left"></i></span></li>
        </#if>

        <#local totalNo = span * 2 + 3 />
        <#local totalNo1 = totalNo - 1 />
        <#if (pageCount > totalNo)>
            <#if (pageNo <= span + 2)>
                <#list 1..totalNo1 as i>
                    <@pagelink pageNo, i, cURL/>
                </#list>
                <@pagelink 0, 0, "#"/>
                <@pagelink pageNo, pageCount, cURL />
            <#elseif (pageNo > (pageCount - (span + 2)))>
                <@pagelink pageNo, 1, cURL />
                <@pagelink 0, 0, "#"/>
                <#local num = pageCount - totalNo + 2 />
                <#list num..pageCount as i>
                    <@pagelink pageNo, i, cURL/>
                </#list>
            <#else>
                <@pagelink pageNo, 1, cURL />
                <@pagelink 0 0 "#" />
                <#local num = pageNo - span />
                <#local num2 = pageNo + span />
                <#list num..num2 as i>
                    <@pagelink pageNo, i, cURL />
                </#list>
                <@pagelink 0, 0, "#"/>
                <@pagelink pageNo, pageCount, cURL />
            </#if>
        <#elseif (pageCount > 1)>
            <#list 1..pageCount as i>
                <@pagelink pageNo, i, cURL />
            </#list>
        <#else>
            <@pagelink 1, 1, cURL/>
        </#if>

        <#if (pageNo < pageCount)>
            <li><a href="${cURL}${pageNo + 1}" pageNo="${pageNo + 1}" class="next"><i class="fa fa-angle-right"></i></a></li>
        <#else>
            <li class="disabled"><span><i class="fa fa-angle-right"></i></span></li>
        </#if>
    </ul>
    </#if>
</#macro>

<#macro pagelink pageNo idx url>
    <#if (idx == 0)>
    <li><span>...</span></li>
    <#elseif (pageNo == idx)>
    <li class="active"><span>${idx}</span></li>
    <#else>
    <li><a href="${url}${idx}">${idx}</a></li>
    </#if>
</#macro>
