<#if message??>
	<div class="alert alert-danger">
		<#--<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>-->
		${message}
	</div>
</#if>
<#if data??>
	<#if (data.code >= 0)>
		<div class="alert alert-success">
			<#--<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>-->
			${data.message}
		</div>
	<#else>
		<div class="alert alert-danger">
			<#--<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>-->
			${data.message}
		</div>
	</#if>
</#if>