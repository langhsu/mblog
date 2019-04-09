<#include "/default/inc/layout.ftl"/>

<@layout "消息提示">

<div class="panel panel-default" style="min-height: 300px; max-width: 460px; margin: 30px auto;">
	<div class="panel-heading">提示</div>
	<div class="panel-body">
		<fieldset>
			<#if error??>
				${error}
			</#if>
		</fieldset>
	</div><!-- /panel-content -->
</div><!-- /panel -->
</@layout>