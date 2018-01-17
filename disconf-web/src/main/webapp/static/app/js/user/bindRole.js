var $table = $('#table_content');
var $J_user_selectforbind = $('#J_user_selectforbind');


function getRequest(params) {
    var request = {};
    request.offset = params.offset ? options.offset : 0;
    request.limit = 10000;
    request.userId = $J_user_selectforbind.val();
    return request;
}

function responseHandler(res) {
    return {
        data: eval(res).rows
    };
}


function getTreeData() {
    $.ajax({
        type: 'post',
        url: "/role/list",
        data: {offset: 0, limit: 10000},
        success: function (result) {
            result = eval(result)
            if (result != '') {
                $.fn.zTree.init($("#treeDemo"), setting, result.rows);
                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                if (treeObj != null) {
                    treeObj.expandAll(true);
                }
            }

        }
    })

}

$J_user_selectforbind.bind('change', function () {
    refresh();
})

$J_user_selectforbind.select2();

function refresh() {
    $table.bootstrapTable('refresh', {
        query: getRequest
    });
    //ztree全部不选中
    $.fn.zTree.getZTreeObj("treeDemo").checkAllNodes(false);
}

/*ztree*/
function showIconForTree(treeId, treeNode) {
    return !treeNode.isParent;
};

var setting = {
    view: {
        showIcon: showIconForTree
    },
    edit: {
        enable: false
    },
    check: {
        enable: true
    },
    data: {
        key: {
            children: "",
            name: "description",
            title: "",
            url: "##",
            icon: "icon"
        },
        simpleData: {
            enable: true,
            idKey: "id", // id编号命名 默认
            pIdKey: "parentId", // 父id编号命名 默认
            rootPId: -1, // 用于修正根节点父节点数据，即 pIdKey 指定的属性值
            open: true
        }
    }
};


$(function() {

    var $toolbarDel = $('#toolbarDel');

    /**
     * checkbox事件
     */
    $table.on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $toolbarDel.attr('disabled', !$table.bootstrapTable('getSelections').length);
    });


    $toolbarDel.click(function(){

        var ids = Commons.getIdSelections($table, function (row) {
            return row.id
        });

        layer.confirm('确定删除吗？', {
            btn: ['确定', '取消'], //按钮
            yes: function () {
                var url = CONTEXT_PATH + '/userRole/delete';
                Ajax.Post(url, JSON.stringify(ids), function (result) {
                    if (result.code == 0) {
                        Messager.success(result.msg);
                        refresh();
                    } else {
                        Messager.warn(result.msg);
                    }
                })
            }
        });
    })

    /**
     * 获取ztree数据
     */
    getTreeData();


    $('#bindRole').bind('click', function () {
        var userId = $J_user_selectforbind.val();
        var roleIds = new Array();
        var selects = $.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes(true);
        if (selects.length < 1) {
            return;
        }
        for (var i = 0; i < selects.length; i++) {
            roleIds.push(selects[i].id);
        }
        var data = {
            userId: userId,
            roleIds: JSON.stringify(roleIds)
        }

        $.ajax({
            type: "POST",
            url: "/userRole/add",
            data: data,
            success: function () {
                refresh();
                Messager.success("绑定成功！！！");
            },
            error: function () {
                Messager.error("绑定失败！！！");
            }
        });
    })


    $("#J_search_btn").click(function () {
        $table.bootstrapTable('refresh');
    });


})

