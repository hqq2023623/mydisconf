var configParams = function() {
    var request = {};
    request.offset = 0;
    request.limit = 10;
    request.envId = currentEnvId;
    return request;
};
$(function () {

    var $table = $('#table_content');
    var $searchBtn = $('#searchBtn');
    var $toolbarAdd = $('#toolbarAdd');
    var $toolbarEdit = $('#toolbarEdit');
    var $toolbarDel = $('#toolbarDel');
    var $saveModelTpl = $('#saveModelTpl');

    var $saveByTextTpl = $("#saveByTextTpl");
    var $toolbarSaveByText = $("#toolbarSaveByText");
    var $saveByFileTpl = $("#saveByFileTpl");
    var $toolbarSaveByFile = $("#toolbarSaveByFile");

    $searchBtn.click(function () {
        Commons.refreshTable();
    });

    $toolbarSaveByText.click(function() {
        if (!$toolbarSaveByText.attr('disabled')) {
            var row = $table.bootstrapTable('getAllSelections')[0];
            laytpl($saveByTextTpl.html()).render(row, function (render) {
                var index = layer.open({
                    type: 1,
                    title: '输入文本',
                    area: ['600px', '400px'],
                    shadeClose: false,
                    content: render,
                    btn: ['保存', '取消'],
                    yes: function () {
                        Ajax.Post(CONTEXT_PATH + "/config/update/text", JSON.stringify(Commons.formData("#saveByTextForm")), function (result) {
                            if (result.code == 0) {
                                Messager.success(result.msg);
                                layer.close(index);
                                Commons.refreshTable();
                                $toolbarDel.attr('disabled', true);
                                $toolbarEdit.attr('disabled', true);
                                $toolbarSaveByText.attr('disabled', true);
                                $toolbarSaveByFile.attr('disabled', true);

                            } else {
                                Messager.warn(result.msg);
                            }
                        });
                    },
                    close: function () {
                        layer.close(index);
                    }
                });
            });
        }
    });

    $toolbarSaveByFile.click(function() {
        if ($toolbarSaveByFile.attr('disabled')) {
            return ;
        }
        var row = $table.bootstrapTable('getAllSelections')[0];
        laytpl($saveByFileTpl.html()).render(row, function (render) {
            var index = layer.open({
                type: 1,
                title: '上传文件',
                area: ['600px', '400px'],
                shadeClose: false,
                content: render,
                btn: ['保存', '取消'],
                yes: function () {
                    var formJson = Commons.formData("#saveByFileForm");
                    formJson['uploadingFile'] = $("#uploadingFile").val();
                    var formValues = JSON.stringify(formJson);
                    console.log("click");

                    $("#saveByFileForm").ajaxSubmit({
                        url: CONTEXT_PATH + "/config/upload/" + formJson.id,

                        success: function (result) {
                            if (result.code == 0) {
                                Messager.success(result.msg);
                                layer.close(index);
                                Commons.refreshTable();
                                $toolbarDel.attr('disabled', true);
                                $toolbarEdit.attr('disabled', true);
                                $toolbarSaveByText.attr('disabled', true);
                                $toolbarSaveByFile.attr('disabled', true);

                            } else {
                                Messager.warn(result.msg);
                            }
                        }
                    });
                },
                close: function () {
                    layer.close(index);
                }
            });
        });
    });

    $toolbarAdd.click(function () {
        var data = {};
        laytpl($saveModelTpl.html()).render(data, function (render) {
            var index = layer.open({
                type: 1,
                title: '添加配置',
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
            editTpl();
        }
    });

    $toolbarDel.bind('click', function () {
        if (!$toolbarDel.attr('disabled')) {
            deleteFunction();
        }
    });

    /**
     * checkbox事件
     */
    $table.on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $toolbarDel.attr('disabled', !$table.bootstrapTable('getSelections').length);
        $toolbarEdit.attr('disabled', !($table.bootstrapTable('getSelections').length == 1));
        $toolbarSaveByText.attr('disabled', !($table.bootstrapTable('getSelections').length == 1));
        $toolbarSaveByFile.attr('disabled', !($table.bootstrapTable('getSelections').length == 1));

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
                groupName: "required",
                appName: "required",
                configName: "required",
                version: "required"
            },
            onkeyup: false
        });
        if ($saveForm.valid()) {
            var url = CONTEXT_PATH + '/config/update';
            if (data.id == null) {
                url = CONTEXT_PATH + '/config/add'
            }
            Ajax.Post(url, JSON.stringify(data), function (result) {
                if (result.code == 0) {
                    Messager.success('保存成功');
                    layer.close(index);
                    Commons.refreshTable();
                    $toolbarDel.attr('disabled', true);
                    $toolbarEdit.attr('disabled', true);
                    $toolbarSaveByText.attr('disabled', true);
                    $toolbarSaveByFile.attr('disabled', true);

                } else {
                    Messager.warn(result.msg);
                }
            });
        }
    };


    var editTpl = function () {
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
    var deleteFunction = function () {

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
                var url = CONTEXT_PATH + '/config/del';
                Ajax.Post(url, JSON.stringify(ids), function (result) {
                    if (result.code == 0) {
                        Messager.success('删除成功！');
                        Commons.refreshTable();
                    } else {
                        Messager.warn(result.msg);
                    }
                })
            }
        });
    };

});
