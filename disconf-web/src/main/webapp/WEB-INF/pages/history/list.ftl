<#assign topMenu="envConfig">
<#assign currentMenu="configHistory">
<#include "../layout/layout.ftl">
<@body>
<section class="content">

    <div class="box box-primary">

        <form id="searchForm" onkeydown="if(event.keyCode==13){return false;}">
            <div class="box-header with-border">
                <h3 class="box-title">历史配置列表</h3>
                <div class="box-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-3">
                                <span class="input-group-addon">组名称:</span>
                                <input class="form-control" name="groupName" placeholder="组名称"/>
                            </div>
                            <div class="col-md-3">
                                <span class="input-group-addon">APP名称:</span>
                                <input class="form-control" name="appName" placeholder="APP名称"/>
                            </div>
                            <div class="col-md-3">
                                <span class="input-group-addon">配置名称:</span>
                                <input class="form-control" name="configName" placeholder="配置名称"/>
                            </div>
                            <div class="col-md-3">
                                <button id="searchBtn" type="button" class="btn btn-primary">
                                    <i class="glyphicon glyphicon-search"></i> 搜索
                                </button>
                                <span class="pull-right">&nbsp;</span>
                                <button id="reset" type="reset" class="btn btn-default">
                                    <i class="glyphicon glyphicon-refresh"></i> 重置
                                </button>
                            </div>
                        </div>
                    </div>
                    <br/>
                </div>
            </div>
        </form>

        <div class="box-body with-border">
            <div id="toolbar">
                <a id="toolbarCompare" class="btn btn-default">
                    <i class="glyphicon glyphicon-plus"></i> 新旧对比
                </a>
            </div>
            <table id="table_content"
                   data-classes="table table-no-bordered"
                   data-click-to-select="true"
                   data-toolbar="#toolbar"
                   data-show-refresh="true"
                   data-show-toggle="true"
                   data-show-columns="true"
                   data-id-field="id"
                   data-toggle="table"
                   data-minimum-count-columns="2"
                   data-pagination="true"
                   data-show-footer="false"
                   data-side-pagination="server"
                   data-response-handler="responseHandler"
                   data-url="/config/history/list"
                   data-unique-id="id"
                   data-method="post"
                   data-Content-Type="application/x-www-form-urlencoded">
                <thead>
                <tr>
                    <th data-field="state" data-checkbox="true"></th>
                    <th data-field="id">ID</th>
                    <th data-field="groupName">组名</th>
                    <th data-field="appName">APP名称</th>
                    <th data-field="configName">配置名称</th>
                    <th data-field="version">版本</th>
                    <th data-field="typeDesc">修改方式</th>
                    <th data-field="createTime" data-formatter="Formatters.DateTime">创建时间</th>
                    <th data-field="updateTime" data-formatter="Formatters.DateTime">修改时间</th>
                    <th data-field="creator">创建人</th>
                    <th data-field="updater">修改人</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</section>


<script id="compareTpl" type="text/html">
    <div class="container-fluid">
        <div class="compareDiv">
            <div>旧的值</div>
            <div id="oldValueDiv">{{=d.oldValue}}</div>
        </div>
        <div class="compareDiv">
            <div>新的值</div>
            <div id="newValueDiv" class="compareDiv">{{=d.newValue}}</div>
        </div>
    </div>
</script>


<script src="${request.contextPath}/static/plugins/compare.js"></script>
<script src="${request.contextPath}/static/app/js/history/history.js"></script>

</@body>


