$(function () {

    var $table = $('#table_content');
    var $searchBtn = $("#searchBtn");
    var $toolbarAdd = $('#toolbarAdd');
    var $toolbarEdit = $('#toolbarEdit');
    var $toolbarDel = $('#toolbarDel');
    var $saveModelTpl = $('#saveModelTpl');

    $searchBtn.click(function () {
        Commons.refreshTable();
    });

    $toolbarAdd.click(function () {
        laytpl($saveModelTpl.html()).render({}, function (render) {
            var index = layer.open({
                type: 1,
                title: '添加角色',
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
            editRole();
        }
    });

    $toolbarDel.bind('click', function () {
        if (!$toolbarDel.attr('disabled')) {
            deleteRole();
        }
    });

    /**
     * checkbox事件
     */
    $table.on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $toolbarDel.attr('disabled', !$table.bootstrapTable('getSelections').length);
        $toolbarEdit.attr('disabled', !($table.bootstrapTable('getSelections').length == 1));
        if($table.bootstrapTable('getSelections').length == 1) {
            $.each($('.link_a'), function(index, value){
                var url = $(value).attr('href');
                $(value).attr('href', url + '?roleId=' + $table.bootstrapTable('getSelections')[0].id);
            })
        }
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
                name: "required",
                description: "required"
            },
            onkeyup: false
        });
        if ($saveForm.valid()) {
            var url = CONTEXT_PATH + '/role/update';
            if (!data || !data.id) {
                url = CONTEXT_PATH + '/role/add'
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


    var editRole = function () {
        var row = $table.bootstrapTable('getAllSelections')[0];
        var $saveModelTpl = $('#saveModelTpl');
        laytpl($saveModelTpl.html()).render(row, function (render) {
            var index = layer.open({
                type: 1,
                title: '编辑角色',
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
    var deleteRole = function () {

        var ids = Commons.getIdSelections($table, function (row) {
            return row.id
        });

        layer.confirm('确定删除吗？', {
            btn: ['确定', '取消'], //按钮
            yes: function () {
                var url = CONTEXT_PATH + '/role/del';
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
