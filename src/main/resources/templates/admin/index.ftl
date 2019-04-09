<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>仪表盘</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li class="active">仪表盘</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-lg-3 col-xs-6">
            <!-- small box -->
            <div class="small-box bg-aqua">
                <div class="inner">
                    <h3>${channelCount}</h3>
                    <p>栏目</p>
                </div>
                <div class="icon">
                    <i class="fa fa-bars"></i>
                </div>
                <a href="${base}/admin/channel/list" class="small-box-footer">更多 <i class="fa fa-arrow-circle-right"></i></a>
            </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
            <!-- small box -->
            <div class="small-box bg-green">
                <div class="inner">
                    <h3>${postCount}</h3>
                    <p>文章</p>
                </div>
                <div class="icon">
                    <i class="fa fa-clone"></i>
                </div>
                <a href="${base}/admin/post/list" class="small-box-footer">更多 <i class="fa fa-arrow-circle-right"></i></a>
            </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
            <!-- small box -->
            <div class="small-box bg-yellow">
                <div class="inner">
                    <h3>${commentCount}</h3>
                    <p>评论</p>
                </div>
                <div class="icon">
                    <i class="fa fa-comments-o"></i>
                </div>
                <a href="${base}/admin/comment/list" class="small-box-footer">更多 <i class="fa fa-arrow-circle-right"></i></a>
            </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
            <!-- small box -->
            <div class="small-box bg-red">
                <div class="inner">
                    <h3>${userCount}</h3>
                    <p>用户</p>
                </div>
                <div class="icon">
                    <i class="fa fa-user"></i>
                </div>
                <a href="${base}/admin/user/list" class="small-box-footer">更多 <i class="fa fa-arrow-circle-right"></i></a>
            </div>
        </div>
        <!-- ./col -->
    </div>
    <div class="row">
        <div class="col-md-6">
            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">系统占用情况</h3>

                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                        </button>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table table-bordered">
                        <tr>
                            <td>内存消耗:</td>
                            <td>
                                <div class="progress">
                                    <div class="progress-bar progress-bar-info progress-bar-striped" role="progressbar" style="width: ${memPercent}%; min-width: 2em;">
                                        <span>${usedMemory}M / ${totalMemory}M</span>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:120px;">操作系统:</td>
                            <td>${os}</td>
                        </tr>
                        <tr>
                            <td style="width:120px;">JDK版本:</td>
                            <td>${javaVersion}</td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="box box-success">
                <div class="box-header with-border">
                    <h3 class="box-title">缓存</h3>
                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                        </button>
                    </div>
                </div>
                <div class="box-body">
                    <button type="button" class="btn btn-primary" data-action="reload_options">
                        刷新系统变量
                    </button>
                    <button type="button" class="btn btn-info" data-action="reset_indexes">
                        重建索引
                    </button>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="box box-info">
                <div class="box-header with-border">
                    <h3 class="box-title">最新评论</h3>
                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                        </button>
                    </div>
                </div>
                <div class="box-body chat" id="chat-box">
                    <!-- chat item -->
                    <div class="item">
                        <p>没有最新内容</p>
                    </div>
                    <!-- /.item -->
                </div>
            </div>
        </div>
    </div>
</section>
<script type="text/plain" id="chat">
    <div class="item">
        <img src="{0}" alt="user image" class="offline">

        <p class="message">
            <a href="${base}/users/{1}" class="name">
                <small class="text-muted pull-right"><i class="fa fa-clock-o"></i> {2}</small>
                {3}
            </a>
            {4}
        </p>
    </div>
</script>
<script>
    var J = jQuery;

    function ajaxReload(json){
        layer.alert(json.message);
    }
	$(function () {
        // 刷新系统变量
        $('button[data-action="reload_options"]').bind('click', function(){
            if(confirm('确定要刷新系统变量的缓存吗？')){
                J.getJSON('${base}/admin/options/reload_options', ajaxReload);
            }
            return false;
        });

        // 重建索引
        $('button[data-action="reset_indexes"]').bind('click', function(){
            if(confirm('确定要重建文章索引吗？')){
                J.getJSON('${base}/admin/options/reset_indexes', ajaxReload);
            }
            return false;
        });
        
        J.getJSON('${base}/api/latest_comments', function (result) {
            if (result.length > 0) {
                var template = $('#chat')[0].text;
                var html = [];
                J.each(result, function (i, n) {
                    var row = J.format(template, n.author.avatar, n.author.id, n.created, n.author.name, n.content);
                    html.push(row);
                })
                $('#chat-box').html(html);
            }
        })
    })
</script>
</@layout>
