<#include "/default/utils/ui.ftl"/>

<@layout "操作提示">
<div class="row">
    <div class="col-md-4 col-md-offset-4 floating-box">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">操作提示</h3>
            </div>
            <div class="panel-body">
                <div id="message"><#include "/default/inc/action_message.ftl"/></div>
				<div class="text-center">
					<#list data.links as row>
						<a href="${base}/${row.link}" class="btn btn-success">${row.text}</a>
					</#list>
				</div>
            </div>
        </div>
    </div>
</div>

</@layout>