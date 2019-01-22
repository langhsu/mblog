<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>
        仪表盘
        <small>欢迎使用mblog系统</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li class="active">仪表盘</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">系统占用情况</h3>

                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                        </button>
                    </div>
                </div>
                <div class="box-body">
                    <div class="row">
                        <div class="col-md-4">
                            <canvas id="canvas"></canvas>
                        </div>
                        <div class="col-md-4">
                            <table class="table table-bordered">
                                <tr>
                                    <td>内存消耗:</td>
                                    <td>
                                    ${usedMemory}M / ${totalMemory}M
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
                        <div class="col-md-4">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default" data-action="flush_conf">
                                    刷新系统变量
                                </button>
                                <button type="button" class="btn btn-default" data-action="flush_indexs">
                                    重建索引
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script type='text/javascript' src='${base}/dist/vendors/Chart.js/dist/Chart.min.js'></script>
<script>
    var J = jQuery;

    function ajaxReload(json){
        if(json.code >= 0){
            if(json.message != null && json.message != ''){
                alert(json.message);
            }
            window.location.reload();
        }else{
            alert(json.message);
        }
    }
	$(function () {
        var ctx = document.getElementById("canvas");
        var data = {
            labels: [
                "可用",
                "已用"
            ],
            datasets: [{
                data: [${memPercent}, 100 - ${memPercent}],
                backgroundColor: [
                    "#455C73",
                    "#9B59B6"
                ],
                hoverBackgroundColor: [
                    "#34495E",
                    "#B370CF"
                ]
            }]
        };

        var canvasDoughnut = new Chart(ctx, {
            type: 'doughnut',
            tooltipFillColor: "rgba(51, 51, 51, 0.55)",
            data: data
        });

        // 刷新系统变量
        $('button[data-action="flush_conf"]').bind('click', function(){
            if(confirm('确定要刷新系统变量的缓存吗？')){
                J.getJSON('${base}/admin/config/flush_conf', ajaxReload);
            }
            return false;
        });

        // 重建索引
        $('button[data-action="flush_indexs"]').bind('click', function(){
            if(confirm('确定要重建文章索引吗？')){
                J.getJSON('${base}/admin/config/flush_indexs', ajaxReload);
            }
            return false;
        });
    })
</script>
</@layout>