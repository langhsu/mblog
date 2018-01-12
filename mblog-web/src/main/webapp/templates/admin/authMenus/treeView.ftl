#set($layout = "/default/layout/columns_noop.vm")
<html>
<head>
    <link rel="stylesheet" href="$base/dist/vendors/zTree_v3/css/demo.css" type="text/css">
    <link rel="stylesheet" href="$base/dist/vendors/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="$base/dist/vendors/zTree_v3/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="$base/dist/vendors/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
    <SCRIPT type="text/javascript">
        var index = parent.layer.getFrameIndex(window.name);
        var setting = {
            data: {
                simpleData: {
                    enable: true
                }
            },
            view: {
                selectedMulti: false
            },
        };
        var zNodes;
        window.fuck = $.fn.zTree
        $(document).ready(function(){
            jQuery.ajax({url:"$base/admin/authMenus/tree",success:function(data){
                zNodes = data;
                fuck.init($("#treeDemo"), setting, zNodes);
                var treeObj = fuck.getZTreeObj("treeDemo");
                var nodes = treeObj.getNodes();
                if (nodes.length>0) {
                    for(var i=0;i<zNodes.length;i++){
                        if(zNodes[i].id==$parentId){
                            treeObj.selectNode(treeObj.getNodeByParam("id",zNodes[i].id));
                            ;
                        }
                    }
                }

            },dataType:"json"});

        });

        function closeLayer(){
            var treeObj = fuck.getZTreeObj("treeDemo");
            var nodes = treeObj.getSelectedNodes();
            parent.$('#parentId').val(nodes[0].id);
            parent.$('#parentName').val(nodes[0].name);
            parent.layer.close(index);
        }
    </SCRIPT>
    <style>
        .ztree{
            margin-left: 9px;
        }

        button{
            margin-top: 25px;
            background: #EAEAEA;
            width: 80px;
            line-height: 30px;
            border: 1px solid #EAEAEA;
            border-radius: 2px;
            cursor: pointer;
        }
        button:hover{
            background: #d5d5d5;
            border: 1px solid #d5d5d5;
        }
    </style>
</head>
<body style="text-align: center">
<div class="zTreeDemoBackground left" style="margin: 0 auto;text-align: center;width:100%">
    <ul id="treeDemo" class="ztree"></ul>
</div>
<button id="transmit" onclick="closeLayer()">确定</button>
</body>
</html>