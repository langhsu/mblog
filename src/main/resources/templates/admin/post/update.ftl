<#include "/admin/utils/ui.ftl"/>
<@layout>
<link rel='stylesheet' media='all' href='${base}/dist/css/plugins.css'/>
<script type="text/javascript" src="${base}/dist/vendors/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>

<section class="content-header">
    <h1>文章编辑</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">首页</a></li>
        <li><a href="${base}/admin/post/list">文章管理</a></li>
        <li class="active">文章编辑</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <form id="qForm" method="post" action="${base}/admin/post/update">
            <div class="col-md-9">
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">文章编辑</h3>
                    </div>
                    <div class="box-body">
                        <#include "/admin/message.ftl">
                        <#if view??>
                            <input type="hidden" name="id" value="${view.id}"/>
                        </#if>
                        <input type="hidden" id="thumbnail" name="thumbnail" value="${view.thumbnail}">
                        <div class="form-group">
                            <label>标题</label>
                            <input type="text" class="form-control" name="title" value="${view.title}" maxlength="64" placeholder="文章标题" required >
                        </div>
                        <div class="form-group">
                            <label>栏目</label>
                            <select class="form-control" name="channelId">
                                <#list groups as row>
                                    <option value="${row.id}" <#if (view.channelId == row.id)> selected </#if>>${row.name}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>内容</label>
                            <#include "/admin/editor/ueditor.ftl"/>
                        </div>
                    </div>
                    <div class="box-footer">
                        <button type="submit" class="btn btn-primary">提交</button>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">预览图</h3>
                    </div>
                    <div class="box-body">
                        <div class="thumbnail-box">
                            <div class="convent_choice" id="thumbnail_image" <#if view.thumbnail?? && view.thumbnail?length gt 0> style="background: url(${base + view.thumbnail});" </#if>>
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
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">标签</h3>
                    </div>
                    <div class="box-body">
                        <input type="text" name="tags" data-role="tagsinput" class="form-control" value="${view.tags}">
                        <p class="help-block" style="font-size: 12px;">添加相关标签，用逗号或空格分隔.</p>
                    </div>
                </div>
            </div>
        </form>
    </div>
</section>
<script type="text/javascript">
$(function() {
    $('#upload_btn').change(function(){
        $(this).upload('${base}/post/upload?crop=1&width=360&height=240', function(data){
            if (data.status == 200) {
                var path = data.path;
                $("#thumbnail_image").css("background", "url(" + path + ") no-repeat scroll center 0 rgba(0, 0, 0, 0)");
                $("#thumbnail").val(path);
            }
        });
    });

    $("form").submit(function () {
        tinyMCE.triggerSave();
    }).validate({
        ignore: "",
        rules: {
            title: "required",
            content: {
                required: true,
                check_editor: true
            }
        },
        errorElement: "em",
        errorPlacement: function (error, element) {
            error.addClass("help-block");

            if (element.prop("type") === "checkbox") {
                error.insertAfter(element.parent("label"));
            } else if (element.is("textarea")) {
                error.insertAfter(element.next());
            } else {
                error.insertAfter(element);
            }
        },
        highlight: function (element, errorClass, validClass) {
            $(element).closest("div").addClass("has-error").removeClass("has-success");
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).closest("div").addClass("has-success").removeClass("has-error");
        }
    });

});
</script>
</@layout>
