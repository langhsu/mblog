<@layout.extends name="/inc/layout.ftl">
    <@layout.put block="title">
        <title>${user.name}的收藏</title>
    </@layout.put>

    <@layout.put block="contents">
        <div class="row users-show">
            <div class="col-xs-12 col-md-3 side-left">
                <#include "/default/inc/user_sidebar.ftl"/>
            </div>
            <div class="col-xs-12 col-md-9 side-right">
                <div class="panel panel-default">
                    <div class="panel-heading">收藏的文章</div>
                    <@user_favorites userId=user.id pageNo=pageNo>
                        <div class="panel-body">
                            <ul class="list-group">
                                <#list results.content as row>
                                    <#assign target = row.post />
                                    <li class="list-group-item" id="loop-${target.id}">
                                        <#if target??>
                                            <a href="${base}/post/${target.id}" class="remove-padding-left">${target.title}</a>
                                        <#else>
                                            <a href="javascript:;" class="remove-padding-left">文章已删除</a>
                                        </#if>
                                        <span class="meta">
                                            <span class="timeago">${timeAgo(row.created)}</span>
                                        </span>

                                        <div class="pull-right hidden-xs">
                                            <#if owner>
                                                <a class="act" href="javascript:void(0);" data-evt="unfavor" data-id="${target.id}">
                                                    <i class="icon icon-close"></i>
                                                </a>
                                            </#if>
                                        </div>
                                    </li>
                                </#list>

                                <#if results.content?size == 0>
                                    <li class="list-group-item ">
                                        <div class="infos">
                                            <div class="media-heading">该目录下还没有内容!</div>
                                        </div>
                                    </li>
                                </#if>
                            </ul>
                        </div>
                        <div class="panel-footer">
                            <@utils.pager request.requestURI!'', results, 5/>
                        </div>
                    </@user_favorites>
                </div>
            </div>
        </div>
        <!-- /end -->

        <script type="text/javascript">
        $(function() {
            $('a[data-evt=unfavor]').click(function () {
                var id = $(this).attr('data-id');

                layer.confirm('确定删除此项吗?', {
                    btn: ['确定','取消'], //按钮
                    shade: false //不显示遮罩
                }, function(){
                    jQuery.getJSON('${base}/user/unfavor', {'id': id}, function (ret) {
                        layer.msg(ret.message, {icon: 1});
                        if (ret.code >=0) {
                            $('#loop-' + id).fadeOut();
                            $('#loop-' + id).remove();
                        }
                    });

                }, function(){

                });
            });
        })
        </script>
    </@layout.put>
</@layout.extends>