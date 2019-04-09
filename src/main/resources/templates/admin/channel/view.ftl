<#include "/admin/utils/ui.ftl"/>
<@layout>
<section class="content-header">
    <h1>修改栏目</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">首页</a></li>
        <li><a href="${base}/admin/channel/list">栏目管理</a></li>
        <li class="active">修改栏目</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <form id="qForm" class="form-horizontal form-label-left" method="post" action="update">
                <#if view??>
                    <input type="hidden" name="id" value="${view.id}" />
                </#if>
                <input type="hidden" name="weight" value="${view.weight!0}">
                <input type="hidden" id="thumbnail" name="thumbnail" value="${view.thumbnail}">
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">修改栏目</h3>
                    </div>
                    <div class="box-body">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">名称</label>
                            <div class="col-lg-3">
                                <input type="text" name="name" class="form-control" value="${view.name}" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">唯一标识</label>
                            <div class="col-lg-3">
                                <input type="text" name="key" class="form-control" value="${view.key}" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">导航栏状态</label>
                            <div class="col-lg-3">
                                <select name="status" class="form-control" data-select="${view.status}">
                                    <option value="0">显示</option>
                                    <option value="1">隐藏</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">缩略图</label>
                            <div class="col-lg-3">
                                <div class="thumbnail-box">
                                    <div class="convent_choice" id="thumbnail_image" <#if view.thumbnail?? && view.thumbnail?length gt 0> style="background: url(${base + view.thumbnail}) no-repeat scroll top;" </#if>>
                                        <div class="upload-btn">
                                            <label>
                                                <span>点击选择一张图片</span>
                                                <input id="upload_btn" type="file" name="file" accept="image/*" title="点击添加图片">
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box-footer">
                        <button type="submit" class="btn btn-primary">提交</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</section>
<script type="text/javascript">
var J = jQuery;

$(function() {
    $('#upload_btn').change(function(){
        $(this).upload('${base}/post/upload?crop=thumbnail_channel_size', function(data){
            if (data.status == 200) {
                var path = data.path;
                $("#thumbnail_image").css("background", "url(" + path + ") no-repeat scroll center 0 rgba(0, 0, 0, 0)");
                $("#thumbnail").val(path);
            }
        });
    });
})
</script>
</@layout>