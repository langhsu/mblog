<#include "/admin/utils/ui.ftl"/>
<@layout>

<div class="page-title">
    <div class="title_left">
        <h3>${profile.username} <small> 欢迎使用Mblog</small></h3>
    </div>
</div>
<div class="clearfix">
</div>
<div class="row">
    <div class="col-md-6 col-sm-6 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>系统状态</h2>
                <div class="clearfix"></div>
            </div>
            <div class="x_content" style="height: 231px;">
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
        </div>
    </div>

    <div class="col-md-6 col-sm-6 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>内存使用情况</h2>
                <div class="clearfix"></div>
            </div>
            <div class="x_content">
                <canvas id="canvas"></canvas>
            </div>
        </div>
    </div>
</div>

<script type='text/javascript' src='${base}/dist/vendors/Chart.js/dist/Chart.min.js'></script>
<script>
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
    })
</script>
</@layout>