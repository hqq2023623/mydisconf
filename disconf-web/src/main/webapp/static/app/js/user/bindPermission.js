var getRequest = function (params) {
    var request = {};
    request.offset = params.offset ? options.offset : 0;
    request.limit = 20;
    request.roleId = $J_role_selectforbind.val();
    return request;
}

var $table = $('#table_content');
var $J_role_selectforbind = $('#J_role_selectforbind');

function responseHandler(res) {
    return {
        data: eval(res).rows
    };
}

$("#J_search_btn").click(function () {
    $table.bootstrapTable('refresh');
});


function refresh() {
    //刷新table
    $table.bootstrapTable('refresh', {
        query: getRequest
    });
    //ztree全部不选中
    $.fn.zTree.getZTreeObj("treeDemo").checkAllNodes(false);
}

$J_role_selectforbind.bind('change', function () {
    refresh();
})

$J_role_selectforbind.select2();


/*ztree*/
function showIconForTree(treeId, treeNode) {
    return !treeNode.isParent;
};

var setting = {
    view: {
        showIcon: showIconForTree,
        addHoverDom: addHoverDom,
        removeHoverDom: removeHoverDom
    },
    edit: {
        enable: true,
        removeTitle: "删除",
        renameTitle: "修改",
        drag: function () {
        }
    },
    check: {
        enable: true
    },
    data: {
        key: {
            children: "",
            name: "name",
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
    },
    callback: {
        onRemove: onRemove,
        onRename: onRename,
        beforeRemove: beforeRemove,
        beforeRename: beforeRename
    }
};

//添加树节点
function addHoverDom(treeId, treeNode) {
    //只有root节点和第二层节点有添加按钮
    var rootNode = $.fn.zTree.getZTreeObj(treeId).getNodes()[0];
    if (treeNode.parentId == -1/*||(rootNode.id == treeNode.parentId)*/) {//root节点
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
            + "' title='添加' onfocus='this.blur();'></span>";
        sObj.after(addStr);
        var btn = $("#addBtn_" + treeNode.tId);
        if (btn) btn.bind("click", function () {
            var id = eval(treeNode).id;
            $('#addpermission-btn').val(id);
            $('#addpermission-btn').click();

        });
    }

};

function removeHoverDom(treeId, treeNode) {
    $("#addBtn_" + treeNode.tId).unbind().remove();
};

//删除节点
function onRemove(e, treeId, treeNode) {
    var ids = new Array();
    ids[0] = eval(treeNode).id;
    layer.confirm('确定删除吗？', {
        btn: ['确定', '取消'], //按钮
        yes: function () {
            var url = CONTEXT_PATH + '/permission/del';
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
}

//修改节点
function onRename(e, treeId, treeNode) {
    //根节点
    var id = eval(treeNode).id;
    var name = eval(treeNode).name;
    var result = confirm("确定要修改吗？")
    if (result == false) {
        return;
    }
    var url = CONTEXT_PATH + "/permission/update";
    var data = {
        id: id,
        name: name
    }

    Ajax.Post(url, JSON.stringify(data), function (result) {
        if (result.code == 0) {
            Messager.success('保存成功');
            refresh();
        } else {
            refresh();
        }
    });
}

function beforeRemove(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.selectNode(treeNode);
    return confirm("确认删除 -- " + treeNode.name + " 吗？");
}

function beforeRename(treeId, treeNode, newName, isCancel) {
    var level = treeNode.level
    if (level < 2 && newName == '') {
        Messager.error("节点名称不能为空.");
        return false;
    }
    return true;
}

function getTreeData() {
    $.ajax({
        type: 'post',
        url: "/permission/list",
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


$(function () {

    var $toolbarDel = $('#toolbarDel');
    var $addpermissionbtn = $('#addpermission-btn');
    var $saveModelTpl = $('#saveModelTpl');

    /**
     * checkbox事件
     */
    $table.on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $toolbarDel.attr('disabled', !$table.bootstrapTable('getSelections').length);
    });

    $toolbarDel.bind('click', function () {
        deleteBindPermission();
    })

    $addpermissionbtn.click(function () {
        var parentId = $addpermissionbtn.val();
        laytpl($saveModelTpl.html()).render({parentId: parentId}, function (render) {
            var index = layer.open({
                type: 1,
                title: '添加权限',
                area: ['600px', '400px'],
                shadeClose: false,
                content: render,
                btn: ['保存', '取消'],
                yes: function () {
                    save(Commons.formData("#saveForm"), index);
                },
                close: function () {
                    layer.close(index);
                }
            });
        });
    });

    getTreeData();

    $('#bindPermission').bind('click', function () {
        var roleId = $J_role_selectforbind.val();
        var permissionIds = new Array();
        var selects = $.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes(true);
        if (selects.length < 1) {
            return;
        }
        for (var i = 0; i < selects.length; i++) {
            permissionIds.push(selects[i].id);
        }
        var data = {
            roleId: roleId,
            permissionIds: JSON.stringify(permissionIds)
        }
        $.ajax({
            type: "POST",
            url: "/permission/rolePermission/add",
            data: data,
            success: function () {
                Messager.success("绑定成功！！！");
                refresh();
            },
            error: function () {
                Messager.error("绑定失败！！！");
            }
        });

    })
})


/**
 * 删除
 */
var deleteBindPermission = function () {

    var ids = Commons.getIdSelections($table, function (row) {
        return row.id
    });

    if (!$('#deleteBindPermission').attr('disabled')) {
        layer.confirm('确定删除吗？', {
            btn: ['确定', '取消'], //按钮
            yes: function () {
                var url = CONTEXT_PATH + '/permission/rolePermission/del';
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
    }
};

/**
 * 保存
 * @param data
 * @param index
 */
var save = function (data, index) {
    var $saveForm = $('#saveForm');
    $saveForm.validate({
        errorClass: "has-error",
        rules: {
            permissionName: "required",
            describ: "required",
            url: "required"
        },
        onkeyup: false
    });
    if ($saveForm.valid()) {
        var url = CONTEXT_PATH + '/permission/add';
        Ajax.Post(url, JSON.stringify(data), function (result) {
            if (result.code == 0) {
                Messager.success('保存成功');
                layer.close(index);
                refresh();
            } else {
                refresh();
            }
        });
    }
};

