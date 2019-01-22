<#include "/admin/utils/ui.ftl"/>
<@layout>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <span>系统配置</span>
            </div>
            <div class="panel-body">
                <ul class="nav nav-pills">
                    <li class="active">
                        <a href="#sites" data-toggle="tab" aria-expanded="true">站点配置</a>
                    </li>
                    <li class="">
                        <a href="#settings" data-toggle="tab" aria-expanded="false">系统配置</a>
                    </li>
                    <li class="">
                        <a href="#cache" data-toggle="tab" aria-expanded="false">缓存及索引</a>
                    </li>
                </ul>
                <!-- Tab panes -->
                <div class="tab-content" style="margin-top: 15px;">
                    <div class="tab-pane active" id="sites">
                        <#include "/admin/config/sites.ftl">
                    </div>
                    <div class="tab-pane" id="settings">
                        <#include "/admin/config/settings.ftl">
                    </div>
                    <div class="tab-pane" id="cache">
                        <div class="btn-channel" role="channel" aria-label="...">
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

<script type="text/javascript">
var J = jQuery;

function ajaxReload(json){
    if(json.code >= 0){
        if(json.message != null && json.message != ''){
            alert(json.message);
        }
        $('#qForm').submit();
    }else{
        alert(json.message);
    }
}

$(function() {
	// 刷新缓存
    $('button[data-action="flush_cache"]').bind('click', function(){
        if(confirm('确定要刷新Ehcache的缓存吗？')){
            J.getJSON('${base}/admin/config/flush_cache', ajaxReload);
        }
        return false;
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