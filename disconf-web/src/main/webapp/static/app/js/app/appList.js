
$(function () {

    var $table = $('#table_content');
    var $toolbarAdd = $('#toolbarAdd');
    var $toolbarEdit = $('#toolbarEdit');
    var $toolbarDelete = $('#toolbarDelete');
    var $saveModelTpl = $('#saveModelTpl');
    var $searchBtn = $('#searchBtn');

    $searchBtn.click(function () {
        Commons.refreshTable();
    });

    $toolbarAdd.click(function () {
        laytpl($saveModelTpl.html()).render({}, function (render) {
            var index = layer.open({
                type: 1,
                title: '添加APP',
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

    $toolbarEdit.bind('click', function () {
        if (!$toolbarEdit.attr('disabled')) {
            editPermission();
        }
    });

    $toolbarDelete.bind('click', function () {
        if (!$toolbarDelete.attr('disabled')) {
            deletePermission();
        }
    });

    /**
     * checkbox事件
     */
    $table.on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $toolbarDelete.attr('disabled', !$table.bootstrapTable('getSelections').length);
        $toolbarEdit.attr('disabled', !($table.bootstrapTable('getSelections').length == 1));
    });

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
                groupName: "required",
                description: "required",
                name: "required",
                emails: "required"
            },
            onkeyup: false
        });
        if ($saveForm.valid()) {
            var url = CONTEXT_PATH + '/app/update';
            if (!data.id) {
                url = CONTEXT_PATH + '/app/add'
            }
            Ajax.Post(url, JSON.stringify(data), function (result) {
                if (result.code == 0) {
                    Messager.success('保存成功');
                    layer.close(index);
                    Commons.refreshTable();
                } else {
                    Messager.warn(result.msg);
                }
            });
        }
    };


    var editPermission = function () {
        var row = $table.bootstrapTable('getAllSelections')[0];
        row.createTime = Formatters.DateTime(row.createTime);
        row.updateTime = Formatters.DateTime(row.updateTime);
        var $saveModelTpl = $('#saveModelTpl');
        laytpl($saveModelTpl.html()).render(row, function (render) {
            var index = layer.open({
                type: 1,
                title: '编辑权限',
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
    };

    /**
     * 删除
     */
    var deletePermission = function () {

        var ids = Commons.getIdSelections($table, function (row) {
            return row.id
        });

        layer.confirm('确定删除吗？', {
            btn: ['确定', '取消'], //按钮
            yes: function () {
                var url = CONTEXT_PATH + '/app/del';
                Ajax.Post(url, JSON.stringify(ids), function (result) {
                    if (result.code == 0) {
                        Messager.success(result.msg);
                        Commons.refreshTable();
                    } else {
                        Messager.warn(result.msg);
                    }
                })
            }
        });
    };



});