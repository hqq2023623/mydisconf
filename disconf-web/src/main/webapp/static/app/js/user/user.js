$(function () {

    var $table = $('#table_content');
    var $searchBtn = $('#searchBtn');
    var $toolbarAdd = $('#toolbarAdd');
    var $toolbarEdit = $('#toolbarEdit');
    var $toolbarDel = $('#toolbarDel');
    var $resetUserPwd = $('#resetUserPwd');
    var $saveModelTpl = $('#saveModelTpl');


    $searchBtn.click(function () {
        Commons.refreshTable();
    });

    $toolbarAdd.click(function () {
        var data = {};
        laytpl($saveModelTpl.html()).render(data, function (render) {
            var index = layer.open({
                type: 1,
                title: '添加用户',
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
            editUser();
        }
    });

    $toolbarDel.bind('click', function () {
        if (!$toolbarDel.attr('disabled')) {
            deleteUser();
        }
    });

    $resetUserPwd.bind('click', function () {
        if (!$resetUserPwd.attr('disabled')) {
            resetPwd();
        }
    });

    /**
     * checkbox事件
     */
    $table.on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $toolbarDel.attr('disabled', !$table.bootstrapTable('getSelections').length);

        $toolbarEdit.attr('disabled', !($table.bootstrapTable('getSelections').length == 1));
        $resetUserPwd.attr('disabled', !($table.bootstrapTable('getSelections').length == 1));
        if ($table.bootstrapTable('getSelections').length == 1) {
            $.each($('.link_a'), function (index, value) {
                var url = $(value).attr('href');
                var originalUrl = $(value).attr('href');
                if (originalUrl.indexOf("userId") < 0) {
                    $(value).attr('href', url + '?userId=' + $table.bootstrapTable('getSelections')[0].id);
                }
            });

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
                userName: "required",
                realName: "required",
                password: "required",
                phone: "required",
                email: "required"
            },
            onkeyup: false
        });
        if ($saveForm.valid()) {
            var url = CONTEXT_PATH + '/user/update';
            if (data.id == null) {
                url = CONTEXT_PATH + '/user/add'
            }
            Ajax.Post(url, JSON.stringify(data), function (result) {
                if (result.code == 0) {
                    Messager.success(result.msg);
                    layer.close(index);
                    Commons.refreshTable();
                    $toolbarDel.attr('disabled', true);
                    $toolbarEdit.attr('disabled', true);
                    $resetUserPwd.attr('disabled', true);
                } else {
                    Messager.warn(result.msg);
                }
            });
        }
    };


    var editUser = function () {
        var row = $table.bootstrapTable('getAllSelections')[0];
        row.createTime = Formatters.DateTime(row.createTime);
        row.updateTime = Formatters.DateTime(row.updateTime);

        var $saveModelTpl = $('#saveModelTpl');
        laytpl($saveModelTpl.html()).render(row, function (render) {

            var index = layer.open({
                type: 1,
                title: '编辑用户',
                area: ['600px', '400px'],
                shadeClose: false,
                content: render,
                btn: ['保存', '取消'],
                yes: function () {
                    save(Commons.formData("#saveForm"), index);
                    Commons.refreshTable();
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
    var deleteUser = function () {

        var ids = Commons.getIdSelections($table, function (row) {
            return row.id
        });

        if (ids.length < 1) {
            Messager.warn("请选择数据");
            return;
        }

        layer.confirm('确定删除吗？', {
            btn: ['确定', '取消'], //按钮
            yes: function () {
                var url = CONTEXT_PATH + '/user/del';
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

    var resetPwd = function () {
        var ids = Commons.getIdSelections($table, function (row) {
            return row.id
        });

        if (ids.length < 1) {
            Messager.warn("请选择数据");
            return;
        }

        Ajax.Post("/user/resetPwd", JSON.stringify(ids[0]), function (result) {
            if (result.code == 0) {
                Messager.success("已重置密码为：" + result.msg);
                Commons.refreshTable();
            } else {
                Messager.warn(result.msg);
            }
        })

    }


});
