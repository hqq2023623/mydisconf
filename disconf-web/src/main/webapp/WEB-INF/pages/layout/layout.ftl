<#macro head>
        <#nested />
</#macro>
<#macro body>
<!DOCTYPE html>
<html>
<head>
    <link href="${request.contextPath}/static/files/favicon.ico" rel="shortcut icon" type="image/x-icon">
    <link href="${request.contextPath}/static/files/favicon.ico" rel="icon" type="image/x-icon">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Disconf</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <#include "static.ftl">
    <script>
        var CONTEXT_PATH = "${request.contextPath}";
    </script>

</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper">
        <#include 'header.ftl'/>
        <#if topMenu??>
            <#include 'sidebar.ftl'/>
        </#if>
        <div class="content-wrapper">
            <#nested />
        </div>
        <script id="batchInputTpl" type="text/html">
            <form id="batchInputForm">
                <div class="container-fluid">
                    </br>
                    <div class="row">
                        <div class="col-md-11">
                    <textarea rows="9" id="codeList" class="form-control"
                              name="codeList"></textarea>
                        </div>
                    </div>
                    </br>
                </div>
            </form>
        </script>
        <#include 'footer.ftl'/>
    </div>
</body>
</html>
</#macro>


