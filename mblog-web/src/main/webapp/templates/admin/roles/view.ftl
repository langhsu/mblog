<#include "/admin/utils/ui.ftl"/>
<@layout>


<link rel="stylesheet" href="${base}/dist/vendors/zTree_v3/css/demo.css" type="text/css">
<link rel="stylesheet" href="${base}/dist/vendors/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${base}/dist/vendors/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${base}/dist/vendors/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
<style>
    .ztree .line{
        height: auto;
    }
    a{
        text-decoration: none;
    }
</style>
<SCRIPT type="text/javascript">
    var setting = {
        check: {
            enable: true,
            chkStyle: "checkbox",
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        view: {
            selectedMulti: false
        }

    };
    $(document).ready(function(){
        jQuery.ajax({url:"${base}/admin/authMenus/tree?roleId=${role.id}",success:function(data){
            $.fn.zTree.init($("#treeDemo"), setting, data);
            console.log(data);
        },dataType:"json"});

        $("button").click(function(){
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var nodes = treeObj.getCheckedNodes(true);

            var exp = new Array();
            for(var i=0;i<nodes.length;i++){
                var id = nodes[i].id;
                exp.push(id);
            }
            $("#menus").val(exp);
            $("form").submit();
        });

    });
</SCRIPT>

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>角色管理</h2>
                <div class="clearfix"></div>
            </div>
            <div class="x_content">
                <br>
                <#include "/admin/message.ftl">
                <form id="qForm" class="form-horizontal form-label-left" method="post" action="save">
                    <#if role?? && (role.id > 0)>
                        <input type="hidden" name="id" value="${role.id}" />
                    </#if>

                    <div class="form-group">
                        <label for="name" class="col-lg-2 control-label">角色名称：</label>
                        <div class="col-lg-3">
                            <input type="text" class="form-control" placeholder="请输入角色名称" name="name" value="${role.name}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="item" class="col-lg-2 control-label">分配菜单：</label>
                        <div class="col-lg-6">
                            <input id="menus" type="hidden" name="menus" value="">
                            <div class="zTreeDemoBackground left" style="margin: 0 auto;text-align: center;width:100%">
                                <ul id="treeDemo" class="ztree"></ul>
                            </div>
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
</@layout>