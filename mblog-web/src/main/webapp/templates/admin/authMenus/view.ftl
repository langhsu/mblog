<#include "/admin/utils/ui.ftl"/>
<@layout>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>菜单<#if authMenu.id!=0>修改<#else>添加</#if></h2>
                <div class="clearfix"></div>
            </div>
            <div class="x_content">
                <br>
                <#include "/admin/message.ftl">
                <form id="qForm" class="form-horizontal form-label-left" method="post" action="save">
                    <#if authMenu?? && (authMenu.id > 0)>
                        <input type="hidden" name="id" value="${authMenu.id}" />
                    </#if>

                    <div class="form-group">
                        <label for="parentId" class="col-lg-2 control-label">上级菜单：</label>
                        <div class="col-lg-6">
                            <input id="parentId" type="hidden" name="parentId" value="<#if parentId??>${parentId}<#else>${authMenu.parent.id}</#if>">
                            <input id="parentName" type="text" class="form-control"  readonly="true" placeholder="请选择上级菜单"  value="<#if parentId??>${parent.name} <#else> 根目录</#if>" style="cursor: pointer">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="name" class="col-lg-2 control-label">菜单名称：</label>
                        <div class="col-lg-6">
                            <input type="text" class="form-control" placeholder="请输入菜单名称" name="name" value="${authMenu.name}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="url" class="col-lg-2 control-label">URL：</label>
                        <div class="col-lg-6">
                            <input type="text" class="form-control" placeholder="以网站根目录'/'开始" name="url" value="${authMenu.url}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="permission" class="col-lg-2 control-label">权限点：</label>
                        <div class="col-lg-6">
                            <input type="text" class="form-control" placeholder="如admin:user:view" name="permission" value="${authMenu.permission}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sort" class="col-lg-2 control-label">排序：</label>
                        <div class="col-lg-6">
                            <input type="text" class="form-control" placeholder="请输入顺序" name="sort" value="${authMenu.sort}">
                        </div>
                    </div>

                    <div class="ln_solid"></div>
                    <div class="form-group">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <button type="submit" class="btn btn-success">提交</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    $(function(){
        $("#parentName").click(function(){
            //iframe层-父子操作
            layer.open({
                type: 2,
                area: ['250px', '530px'],
                fix: false, //不固定
                maxmin: false,
                content: '${base}/admin/authMenus/treeView?parentId=${parentId}'
            });
        });

    });
</script>
</@layout>