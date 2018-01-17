$(function () {

    var $table = $('#table_content');
    var $toolbarCompare = $('#toolbarCompare');
    var $compareTpl = $("#compareTpl");
    var $searchBtn = $('#searchBtn');
    var $oldValueDiv = $("#oldValueDiv");
    var $newValueDiv = $("#newValueDiv");

    $searchBtn.click(function () {
        Commons.refreshTable();
    });

    $toolbarCompare.click(function () {
        var row = $table.bootstrapTable('getAllSelections')[0];
        laytpl($compareTpl.html()).render(row, function (render) {
            var index = layer.open({
                type: 1,
                title: '添加APP',
                area: ['800px', '600px'],
                shadeClose: false,
                content: render,
                btn: ['对比','关闭'],
                yes : function(){
                    $.fn.CompareTxt("#oldValueDiv","#newValueDiv");
                },
                close: function () {
                    layer.close(index);
                }
            });
        });
    });

    /**
     * checkbox事件
     */
    $table.on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $toolbarCompare.attr('disabled', !$table.bootstrapTable('getSelections').length);
    });



});