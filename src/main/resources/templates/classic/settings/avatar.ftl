<#include "/classic/inc/layout.ftl"/>
<@layout "修改用户信息">

<div class="panel panel-default stacked">
	<div class="panel-heading">
		<ul class="nav nav-pills account-tab">
			<li><a href="profile">基本信息</a></li>
            <li><a href="email">修改邮箱</a></li>
			<li class="active"><a href="avatar">修改头像</a></li>
			<li><a href="password">修改密码</a></li>
		</ul>
	</div>
	<div class="panel-body">
		<div id="message">
			<#include "/classic/inc/action_message.ftl"/>
		</div>
			<div class="upload-btn">
				<label>
					<span>点击选择一张图片</span>
					<input id="upload_btn" type="file" name="file" accept="image/*" title="点击添加图片">
				</label>
			</div>
			<div class="update_ava">
				<img src="<@resource src=profile.avatar/>" id="target" alt="[Example]" />
			</div>
	</div><!-- /panel-content -->
</div><!-- /panel -->

<script type="text/javascript">
    seajs.use('avatar');
</script>
</@layout>