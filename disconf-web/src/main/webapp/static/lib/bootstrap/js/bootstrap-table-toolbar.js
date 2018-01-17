/**
 * @author: aperez <aperez@datadec.es>
 * @version: v2.0.0
 *
 * @update Dennis Hernández <http://djhvscf.github.io/Blog>
 */

!function ($) {
    'use strict';

    var firstLoad = false;

    var sprintf = $.fn.bootstrapTable.utils.sprintf;

    var showAvdSearch = function (pColumns, searchTitle, searchText, that) {
        if (!$("#avdSearchModal" + "_" + that.options.idTable).hasClass("modal")) {
            var vModal = sprintf("<div id=\"avdSearchModal%s\"  class=\"modal fade\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"mySmallModalLabel\" aria-hidden=\"true\">", "_" + that.options.idTable);
            vModal += "<div class=\"modal-dialog modal-xs\">";
            vModal += " <div class=\"modal-content\">";
            vModal += "  <div class=\"modal-header\">";
            vModal += "   <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\" >&times;</button>";
            vModal += sprintf("   <h4 class=\"modal-title\">%s</h4>", searchTitle);
            vModal += "  </div>";
            vModal += "  <div class=\"modal-body modal-body-custom\">";
            vModal += sprintf("   <div class=\"container-fluid\" id=\"avdSearchModalContent%s\" style=\"padding-right: 0px;padding-left: 0px;\" >", "_" + that.options.idTable);
            vModal += "   </div>";
            vModal += "  </div>";
            vModal += "  </div>";
            vModal += " </div>";
            vModal += "</div>";

            $("body").append($(vModal));

            var vFormAvd = createFormAvd(pColumns, searchText, that);

            $('#avdSearchModalContent' + "_" + that.options.idTable).append(vFormAvd.join(''));

            $("#btnConfirmAvd" + "_" + that.options.idTable).click(function (event) {
                var searchFields = $(".search-form-field-" + that.options.idTable);
                var length = searchFields.length;
                for (var i = 0; i < length; i++) {
                    var aSearchField = searchFields[i];
                    var text;
                    var $field = aSearchField.id;
                    if ("SELECT" == aSearchField.tagName) {
                        text = $(sprintf('#%s option:selected', $field)).val()
                    } else {
                        text = aSearchField.value;
                    }
                    if ($.isEmptyObject(this.filterColumnsPartial)) {
                        this.filterColumnsPartial = {};
                    }
                    if (text) {
                        this.filterColumnsPartial[$field] = text;
                    } else {
                        delete this.filterColumnsPartial[$field];
                    }
                }
                that.onColumnAdvancedSearch(event, this.filterColumnsPartial);
                $("#avdSearchModal" + "_" + that.options.idTable).modal('hide');
            });

            $("#avdSearchModal" + "_" + that.options.idTable).modal();
        } else {
            $("#avdSearchModal" + "_" + that.options.idTable).modal();
        }
    };

    var buildRangeFromGroup = function (htmlForm, vObjCol, that, type) {
        htmlForm.push('<div class="form-group">');
        htmlForm.push(sprintf('<label class="col-sm-4 control-label">%s From</label>', vObjCol.title));
        htmlForm.push('<div class="col-sm-6">');
        htmlForm.push(sprintf('<input type="%s" class="form-control input-md search-form-field-%s" name="%sFrom" placeholder="%s From" id="%sFrom">', type, that.options.idTable, vObjCol.field, vObjCol.title, vObjCol.field));
        htmlForm.push('</div>');
        htmlForm.push('</div>');
        htmlForm.push('<div class="form-group">');
        htmlForm.push(sprintf('<label class="col-sm-4 control-label">%s To</label>', vObjCol.title));
        htmlForm.push('<div class="col-sm-6">');
        htmlForm.push(sprintf('<input type="%s" class="form-control input-md search-form-field-%s" name="%sTo" placeholder="%s To" id="%sTo">', type, that.options.idTable, vObjCol.field, vObjCol.title, vObjCol.field));
    };

    var buildFromGroup = function (htmlForm, vObjCol, that, type) {
        htmlForm.push('<div class="form-group">');
        htmlForm.push(sprintf('<label class="col-sm-4 control-label">%s</label>', vObjCol.title));
        htmlForm.push('<div class="col-sm-6">');
        htmlForm.push(sprintf('<input type="%s" class="form-control input-md search-form-field-%s" name="%s" placeholder="%s" id="%s">', type, that.options.idTable, vObjCol.field, vObjCol.title, vObjCol.field));
    };

    var createFormAvd = function (pColumns, searchText, that) {
        var htmlForm = [];
        htmlForm.push(sprintf('<form class="form-horizontal" id="%s" action="%s" >', that.options.idForm, that.options.actionForm));
        for (var i in pColumns) {
            var vObjCol = pColumns[i];
            if (!vObjCol.checkbox && vObjCol.visible && vObjCol.searchField == true && vObjCol.searchable) {
                switch (vObjCol.fieldType) {
                    case 'select':
                        //下拉框
                        htmlForm.push('<div class="form-group">');
                        htmlForm.push(sprintf('<label class="col-sm-4 control-label">%s</label>', vObjCol.title));
                        htmlForm.push('<div class="col-sm-6">');
                        htmlForm.push(sprintf('<select class="form-control search-form-field-%s" name="%s" id="%s">', that.options.idTable, vObjCol.title, vObjCol.field));
                        var options = eval(vObjCol.selectOptions);
                        var length = options.length;
                        for (var oi = 0; oi < length; oi++) {
                            var option = options[oi];
                            htmlForm.push(sprintf('<option value="%s">%s</option>', option.value, option.title));
                        }
                        htmlForm.push(sprintf('</select>'));
                        break;
                    case 'daterange':
                        //日期区间
                        buildRangeFromGroup(htmlForm, vObjCol, that, 'date');
                        break;
                    case 'datetimerange':
                        //日期时间区间
                        buildRangeFromGroup(htmlForm, vObjCol, that, 'datetime-local');
                        break;
                    case 'date':
                        //日期
                        buildFromGroup(htmlForm, vObjCol, that, 'date');
                        break;
                    case 'datetime':
                        //日期时间
                        buildFromGroup(htmlForm, vObjCol, that, 'datetime');
                        break;
                    case 'textrange':
                        //文本区间
                        buildRangeFromGroup(htmlForm, vObjCol, that, 'text');
                        break;
                    default :
                        buildFromGroup(htmlForm, vObjCol, that, 'text');
                }
                htmlForm.push('</div>');
                htmlForm.push('</div>');
            }
        }

        htmlForm.push('<div class="form-group">');
        htmlForm.push('<div class="col-sm-offset-9 col-sm-3">');
        htmlForm.push(sprintf('<button type="button" id="btnConfirmAvd%s" class="btn btn-default" >%s</button>', "_" + that.options.idTable, "Confirm"));
        htmlForm.push('</div>');
        htmlForm.push('</div>');
        htmlForm.push('</form>');

        return htmlForm;
    };

    $.extend($.fn.bootstrapTable.defaults, {
        advancedSearch: false,
        idForm: 'advancedSearch',
        actionForm: '',
        idTable: undefined,
        onColumnAdvancedSearch: function (field, text) {
            return false;
        }
    });

    $.extend($.fn.bootstrapTable.defaults.icons, {
        advancedSearchIcon: 'glyphicon-chevron-down'
    });

    $.extend($.fn.bootstrapTable.Constructor.EVENTS, {
        'column-advanced-search.bs.table': 'onColumnAdvancedSearch'
    });

    $.extend($.fn.bootstrapTable.locales, {
        formatAdvancedSearch: function () {
            return 'Advanced search';
        },
        formatAdvancedCloseButton: function () {
            return "Close";
        }
    });

    $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales);

    var BootstrapTable = $.fn.bootstrapTable.Constructor,
        _initToolbar = BootstrapTable.prototype.initToolbar,
        _load = BootstrapTable.prototype.load,
        _initSearch = BootstrapTable.prototype.initSearch;

    BootstrapTable.prototype.initToolbar = function () {
        _initToolbar.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.search) {
            return;
        }

        if (!this.options.advancedSearch) {
            return;
        }

        if (!this.options.idTable) {
            return;
        }

        var that = this,
            html = [];

        html.push(sprintf('<div class="columns columns-%s btn-group pull-%s" role="group">', this.options.buttonsAlign, this.options.buttonsAlign));
        html.push(sprintf('<button class="btn btn-default%s' + '" type="button" name="advancedSearch" title="%s">', that.options.iconSize === undefined ? '' : ' btn-' + that.options.iconSize, that.options.formatAdvancedSearch()));
        html.push(sprintf('<i class="%s %s"></i>', that.options.iconsPrefix, that.options.icons.advancedSearchIcon))
        html.push('</button></div>');

        that.$toolbar.prepend(html.join(''));

        that.$toolbar.find('button[name="advancedSearch"]')
            .off('click').on('click', function () {
                showAvdSearch(that.columns, that.options.formatAdvancedSearch(), that.options.formatAdvancedCloseButton(), that);
            });
    };

    BootstrapTable.prototype.load = function (data) {
        _load.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.advancedSearch) {
            return;
        }

        if (typeof this.options.idTable === 'undefined') {
            return;
        } else {
            if (!firstLoad) {
                var height = parseInt($(".bootstrap-table").height());
                height += 10;
                $("#" + this.options.idTable).bootstrapTable("resetView", {height: height});
                firstLoad = true;
            }
        }
    };

    BootstrapTable.prototype.initSearch = function () {
        _initSearch.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.advancedSearch) {
            return;
        }

        var that = this;
        var fp = $.isEmptyObject(this.filterColumnsPartial) ? null : this.filterColumnsPartial;

        this.data = fp ? $.grep(this.data, function (item, i) {
            for (var key in fp) {
                var fval = fp[key].toLowerCase();
                var value = item[key];
                value = $.fn.bootstrapTable.utils.calculateObjectValue(that.header,
                    that.header.formatters[$.inArray(key, that.header.fields)],
                    [value, item, i], value);

                if (!($.inArray(key, that.header.fields) !== -1 &&
                    (typeof value === 'string' || typeof value === 'number') &&
                    (value + '').toLowerCase().indexOf(fval) !== -1)) {
                    return false;
                }
            }
            return true;
        }) : this.data;
    };

    BootstrapTable.prototype.onColumnAdvancedSearch = function (event) {
        var text = $.trim($(event.currentTarget).val());
        var $field = $(event.currentTarget)[0].id;

        if ($.isEmptyObject(this.filterColumnsPartial)) {
            this.filterColumnsPartial = {};
        }
        if (text) {
            this.filterColumnsPartial[$field] = text;
        } else {
            delete this.filterColumnsPartial[$field];
        }

        this.options.pageNumber = 1;
        this.onSearch(event);
        this.updatePagination();
        this.trigger('column-advanced-search', $field, text);
    };

    BootstrapTable.prototype.onColumnAdvancedSearch = function (event, filterColumnsPartial) {
        this.filterColumnsPartial = filterColumnsPartial;
        this.options.pageNumber = 1;
        this.onSearch(event);
        this.updatePagination();
    };
}(jQuery);
