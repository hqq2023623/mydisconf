//公用组件
const Commons = {
    /**
     * 根据formId将from里的所有元素转成对象,
     * disable的元素不能读取，不可编辑的话用readonly
     * @param form
     */
    formData: function (selector, withoutTemp) {
        var formValues = $(selector).serializeArray();
        var paramObject = {};
        for (var i = 0, len = formValues.length; i < len; i++) {
            if (!withoutTemp || !formValues[i].name.startsWith("temp_")) {
                if (formValues[i].value == '' || formValues[i].value == null) {
                    paramObject[formValues[i].name] = undefined;
                } else {
                    paramObject[formValues[i].name] = Commons.xssFilter(formValues[i].value);
                }
            }
        }
        return paramObject;
    },

    commonFormPrams : function(params) {
        if(!params) {
            params = {};
            params.formId = "#searchForm";
            params.tableId = "#table_content";
            var $table = $(params.tableId);
            params.$table = $table;
            params.url = $table.attr("data-url");
        } else {
            if(!params.formId) {
                params.formId = "#searchForm";
            }
            if(!params.tableId) {
                params.tableId = "#table_content";
            }
            var $table = $(params.tableId);
            params.$table = $table;
            if(!params.url) {
                params.url = $table.attr("data-url");
            }
        }

        var formValues = {};
        formValues.query = Commons.formData(params.formId);
        formValues.query.offset = 0;
        formValues.query.limit = 10;
        formValues.$table = params.$table;
        formValues.url = params.url;
        return formValues;
    },

    /**
     * 刷新 bootstrap table，默认参数为 #
     * @param tableId 默认 #table_content
     * @param url
     * @param formId #searchForm 表单
     */
    refreshTable : function(params) {
        var formValues = Commons.commonFormPrams(params);
        formValues.$table.bootstrapTable('refresh', {
            query: formValues.query,
            url: formValues.url
        });

    },

    //下拉列表赋值
    selectDropDown: function (selectOptions) {
        var dropdownMenu = $("ul.dropdown-menu li");
        $.each(dropdownMenu, function (index, item) {
            $(item).click(function () {
                var selectKeyDom = $(this).parent();
                var dropdown = $(selectKeyDom).prev(".dropdown-toggle").children(".J_dropdown_val");
                var selectKey = $(selectKeyDom).attr("select-key");
                var selectVal = $(this).attr("select-option");
                var selectHtml = $(this).children(0).html();
                $(dropdown).html(selectHtml);
                selectOptions[selectKey] = selectVal;
            });
        });
    },

    compTime: function (beginTime, endTime) {
        beginTime = new Date(beginTime.replace(/\-/g, "\/"));
        endTime = new Date(endTime.replace(/\-/g, "\/"));
        return beginTime <= endTime;
    },

    calculateDay: function (beginTime, endTime) {
        var oDate1 = new Date(beginTime);
        var oDate2 = new Date(endTime);
        var iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24);
        return iDays;
    },

    sprintf: function (str) {
        var args = arguments,
            flag = true,
            i = 1;
        str = str.replace(/%s/g, function () {
            var arg = args[i++];
            if (typeof arg === 'undefined') {
                flag = false;
                return '';
            }
            return arg;
        });
        return flag ? str : '';
    },

    /**
     * 获取选中的id
     * @param table
     * @param row
     * @returns {*}
     */
    getIdSelections: function (table, row) {
        return $.map(table.bootstrapTable('getSelections'), row);
    },

    getSelectedValue: function (selector) {
        var element = $(selector)[0];
        var value = [];
        for (var i = 0, len = element.length; i < len; i++) {
            if (element[i].selected) {
                value.push(element[i].value);
            }
        }
        return value;
    },

    /**
     * Js端简单Xss过滤
     * @param s
     * @returns {string}
     */
    xssFilter: function (s) {
        return s.replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            //.replace(/\//g, '&#x2f;')
            .replace(/"\s*[a-zA-Z0-9]*\s*javascript:/g, '&quot;javascript:')
            .replace(/'\s*[a-zA-Z0-9]*\s*javascript:/g, '&#x27;javascript:');
    }
};

const Maths = {
    division: function (num1, num2) {
        var baseNum1 = 0, baseNum2 = 0;
        var baseNum3, baseNum4;
        try {
            baseNum1 = num1.toString().split(".")[1].length;
        } catch (e) {
            baseNum1 = 0;
        }
        try {
            baseNum2 = num2.toString().split(".")[1].length;
        } catch (e) {
            baseNum2 = 0;
        }
        with (Math) {
            baseNum3 = Number(num1.toString().replace(".", ""));
            baseNum4 = Number(num2.toString().replace(".", ""));
            return (baseNum3 / baseNum4) * pow(10, baseNum2 - baseNum1);
        }
    },
    multiplication: function (num1, num2) {
        var baseNum = 0;
        try {
            baseNum += num1.toString().split(".")[1].length;
        } catch (e) {
        }
        try {
            baseNum += num2.toString().split(".")[1].length;
        } catch (e) {
        }
        return Number(num1.toString().replace(".", "")) * Number(num2.toString().replace(".", "")) / Math.pow(10, baseNum);
    },
    addition: function (num1, num2) {
        var baseNum, baseNum1, baseNum2;
        try {
            baseNum1 = num1.toString().split(".")[1].length;
        } catch (e) {
            baseNum1 = 0;
        }
        try {
            baseNum2 = num2.toString().split(".")[1].length;
        } catch (e) {
            baseNum2 = 0;
        }
        baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
        return (num1 * baseNum + num2 * baseNum) / baseNum;
    },
    subtraction: function (num1, num2) {
        var baseNum, baseNum1, baseNum2;
        var precision;// 精度
        try {
            baseNum1 = num1.toString().split(".")[1].length;
        } catch (e) {
            baseNum1 = 0;
        }
        try {
            baseNum2 = num2.toString().split(".")[1].length;
        } catch (e) {
            baseNum2 = 0;
        }
        baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
        precision = (baseNum1 >= baseNum2) ? baseNum1 : baseNum2;
        return ((num1 * baseNum - num2 * baseNum) / baseNum).toFixed(precision);
    }
};

//Ajax工具类
const Ajax = {
    RequestMethod: {GET: "GET", POST: "POST"},
    DataType: {JSON: "json", XML: "xml", JSONP: "jsonp", TEXT: "text", SCRIPT: "script", HTML: "html"},
    ContentType: {
        APP_JSON: "application/json;charset=UTF-8",
        APP_URL: "application/x-www-form-urlencoded;charset=UTF-8"
    },
    Get: function (url, data, success, type) {
        return this.Request(url, this.RequestMethod.GET, data, success, type);
    },
    GetByJson: function (url, data, success) {
        return this.Request(url, this.RequestMethod.GET, data, success, this.DataType.JSON);
    },
    Post: function (url, data, success, type) {
        return this.Request(url, this.RequestMethod.POST, data, success, type);
    },
    Request: function (url, method, data, success, type) {
        var ajax = {
            type: method,
            url: url,
            data: data,
            success: function (data, textStatus) {
                success(data, textStatus);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.error(XMLHttpRequest);
                success({code: -1, msg: textStatus}, textStatus)
            }
        };
        if (type) {
            ajax.dataType = !type.dataType ? this.DataType.JSON : type.dataType;
            ajax.contentType = !type.contentType ? this.ContentType.APP_JSON : type.contentType;
        } else {
            ajax.dataType = this.DataType.JSON;
            ajax.contentType = this.ContentType.APP_JSON;
        }
        if (method === this.RequestMethod.POST) {
            ajax.headers = {};
            ajax.headers['_RequestToken'] = $("#merchantCsrfToken").val();
        }
        return $.ajax(ajax);
    },
};

/**
 * validator
 */
$.extend(jQuery.validator.messages, {
    required: "必选字段",
    remote: "请修正该字段",
    email: "请输入正确格式的电子邮件",
    emails: "请输入正确格式的电子邮件",
    url: "请输入合法的网址",
    date: "请输入合法的日期",
    dateISO: "请输入合法的日期 (ISO).",
    number: "请输入合法的数字",
    digits: "只能输入整数",
    equalTo: "请再次输入相同的值",
    accept: "请输入拥有合法后缀名的字符串",
    maxlength: jQuery.validator.format("请输入一个长度最多是 {0} 的字符串"),
    minlength: jQuery.validator.format("请输入一个长度最少是 {0} 的字符串"),
    rangelength: jQuery.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
    range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
    max: jQuery.validator.format("请输入一个最大为 {0} 的值"),
    min: jQuery.validator.format("请输入一个最小为 {0} 的值")
});

$.validator.addMethod("zipCode", function (value, element) {
    var zipCode = /^[0-9]{6}$/;
    return this.optional(element) || (zipCode.test(value));
}, "请正确填写您的邮政编码");

$.validator.addMethod("password", function (value, element) {
    var pwdRex = /^((?=.*?\d)(?=.*?[A-Za-z])|(?=.*?\d)(?=.*?[!@#$%^&*/().,\]\[_+{}|:;<>?'"`~-])|(?=.*?[A-Za-z])(?=.*?[!@#$%^&*/().,\]\[_+{}|:;<>?'"`~-]))[\dA-Za-z!@#$%^&*/().,\]\[_+{}|:;<>?'"`~-]+$/;
    return this.optional(element) || (pwdRex.test(value));
}, "必须包含两种字符");

$.validator.addMethod("specialCharFilter", function (value, element) {
    var pattern = /^[a-zA-Z\d\u4e00-\u9fa5]+$/;
    return this.optional(element) || (pattern.test(value));
}, "不能包含特殊字符");

$.validator.addMethod("specialCharFilter", function (value, element) {
    var pattern = /^[a-zA-Z\d\u4e00-\u9fa5]+$/;
    return this.optional(element) || (pattern.test(value));
}, "只能包含数字字母及汉字");

$.validator.addMethod("specialCharFilter2", function (value, element) {
    var pattern = /^[a-zA-Z\d\u4e00-\u9fa5\s,，;；、。.()（）]+$/;
    return this.optional(element) || (pattern.test(value));
}, "不能包含特殊字符");

$.validator.addMethod("upperCaseAndNumber", function (value, element) {
    var pattern = /^[A-Z\d]+$/;
    return this.optional(element) || (pattern.test(value));
}, "只能包含大写字母与数字");

$.validator.addMethod("letterAndNumber", function (value, element) {
    var pattern = /^[a-zA-Z\d]+$/;
    return this.optional(element) || (pattern.test(value));
}, "只能包含字母与数字");

$.validator.addMethod("identityCardNo", function (value, element) {
    var pattern = /^[\dLX]+$/;
    return this.optional(element) || ((pattern.test(value)) && (value.length == 15 || value.length == 18 || value.length == 20));
}, "证件号码格式错误");

$.validator.addMethod("telephone", function (value, element) {
    var pattern = /^(010|02\d|0[3-9]\d{2})-?\d{6,8}$/;
    return this.optional(element) || (pattern.test(value));
}, "无效的电话号码");

$.validator.addMethod("phoneCN", function (value, element) {
    var pattern = /^\+?(86)?1([3458]\d|70)\d{8}$/;
    return this.optional(element) || (pattern.test(value));
}, "无效的手机号码");

$.validator.addMethod("allPhoneNumber", function (value, element) {
    var telephone = /^(010|02\d|0[3-9]\d{2})-?\d{6,8}$/;
    var phoneCN = /^\+?(86)?1([3458]\d|70)\d{8}$/;
    var serviceTelephone = /^(400|800)-?\d{3}-?\d{4}$/;
    return this.optional(element) || (telephone.test(value)) || (phoneCN.test(value)) || (serviceTelephone.test(value));
}, "无效的电话号码");

$.validator.addMethod("address", function (value, element) {
    var address = /^[a-zA-Z\d\u4e00-\u9fa5\s\-\#,，、。.]+$/;
    return this.optional(element) || address.test(value);
}, "无效的地址格式");

//业务要求的证件统一规则
$.validator.addMethod("credentials", function (value, element) {
    var pattern = /^[a-zA-Z\d\-]+$/;
    return this.optional(element) || (pattern.test(value));
}, "支持数字、英文字符、英文符号\"-\"");

jQuery.validator.addMethod("decimal", function (value, element) {
    var decimal = /^-?\d+(\.\d{1,2})?$/;
    return this.optional(element) || (decimal.test(value));
}, "小数位数不能超过两位");

$.messager.model = {
    ok: {text: "确认", classed: 'btn-primary'},
    cancel: {text: "取消", classed: 'btn-default'}
};

/**
 * 消息
 * @type {{error: Function, success: Function, warn: Function, message: Function}}
 */
const Messager = {
    error: function (e) {
        layer.msg(e, {
            icon: 2, offset: 0,
            shift: 6
        });
    },
    success: function (e) {
        layer.msg(e, {icon: 1});
    },
    warn: function (e) {
        layer.msg(e, {icon: 7});
    },
    popup: function (model, e) {
        var r;
        (r = $(model), $("body").append(r)), r.find(".modal-body").html(e), r.modal({
            show: !0,
            backdrop: !1
        }), setTimeout(function () {
            r.modal("hide")
        }, 1500)
    }
};

$(document).ready(function () {
    $('.reset-modal').on('hide.bs.modal', function () {
        var forms = $(this).find('form');
        if (forms.length > 0) {
            _.each(forms, function (form) {
                form.reset();
            });
        }
    });
});

Date.prototype.formatDate = function (formatStr, formatDate) {

    var strDate;
    if (formatStr == "yyyy-MM-dd HH:ii:ss") {
        strDate = formatDate.getFullYear() + "-" + (formatDate.getMonth() + 1) + "-"
            + formatDate.getDate() + " " + formatDate.getHours() + ":"
            + formatDate.getMinutes() + ":" + formatDate.getSeconds();
    }
    return strDate;
};

function batchInput($this){
    laytpl($('#batchInputTpl').html()).render({}, function (render) {
        var index = layer.open({
            type: 1,
            title: '单据号列表',
            area: ['500px', '280px'],
            shadeClose: false,
            content: render,
            btn: ['保存', '取消'],
            yes: function () {
                var data = Commons.formData("#batchInputForm");
                $('#' + $this).val(data.codeList);
                layer.close(index);
            },
            close: function () {
                layer.close(index);
            }
        });
    });
}

jQuery.download = function (url, data, method) {
    var inputs = '';
    if (data != null) {
        data = eval(data);
        for (var element in data) {
            if (data[element] == null) {
                continue;
            }
            inputs += '<input type="hidden" name="' + element + '" value="' + data[element] + '" />';
        }
    }
    ;
    //send request
    jQuery('<form action="' + url + '" method="' + (method || 'post') + '" target="_blank"' +'">' + inputs + '</form>')
        .appendTo('body').submit().remove();
};

function responseHandler(res) {
    return eval(res);
}
