<#assign topMenu="app">
<#assign currentMenu="appList">
<#include "../layout/layout.ftl">
<@body>
<section class="content">

    <div class="box box-primary">

        <form id="searchForm" onkeydown="if(event.keyCode==13){return false;}">
            <div class="box-header with-border">
                <h3 class="box-title">APP列表</h3>
                <div class="box-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-3">
                                <span class="input-group-addon">组名称:</span>
                                <input class="form-control" name="groupName" placeholder="组名称"/>
                            </div>
                            <div class="col-md-3">
                                <span class="input-group-addon">APP名称:</span>
                                <input class="form-control" name="name" placeholder="APP名称"/>
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
                <a id="toolbarAdd" class="btn btn-default">
                    <i class="glyphicon glyphicon-plus"></i> 添加
                </a>
                <a id="toolbarEdit" class="btn btn-primary" disabled>
                    <i class="glyphicon glyphicon-pencil"></i> 编辑
                </a>
                <a id="toolbarDelete" class="btn btn-danger" disabled>
                    <i class="glyphicon glyphicon-ban-circle"></i> 删除
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
                   data-url="/app/list"
                   data-unique-id="id"
                   data-method="post"
                   data-Content-Type="application/x-www-form-urlencoded">
                <thead>
                <tr>
                    <th data-field="state" data-checkbox="true"></th>
                    <th data-field="id">ID</th>
                    <th data-field="name">APP名称</th>
                    <th data-field="description">描述</th>
                    <th data-field="groupName">组名</th>
                    <th data-field="emails">邮箱</th>
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


<script id="saveModelTpl" type="text/html">
    <form id="saveForm">
        <div class="container-fluid">
            <br/>
            <div class="row">
                <div class="col-md-6"><label>APP名称</label>
                    <input type="hidden" name="id" value="{{d.id}}">
                    <input type="text" class="form-control" name="name" placeholder="APP名称" value="{{= d.name}}">
                </div>
                <div class="col-md-6"><label>APP描述</label>
                    <input type="text" class="form-control" name="description" placeholder="描述" value="{{= d.description}}">
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-md-6"><label>组名</label>
                    <input type="text" class="form-control" name="groupName" placeholder="组名" value="{{= d.groupName}}">
                </div>
                <div class="col-md-6"><label>邮箱</label>
                    <input type="email" class="form-control" name="emails" placeholder="邮箱" value="{{= d.emails}}">
                </div>
            </div>
            <br/>
            <div class=" row">
                <div class="col-md-6"><label>创建人</label>
                    <input type="text" class="form-control" placeholder="创建人" value="{{= d.creator}}" disabled>
                </div>
                <div class="col-md-6"><label>创建时间</label>
                    <input type="text" class="form-control" placeholder="创建时间" value="{{= d.createTime}}" disabled>
                </div>
            </div>
            <br/>
            <div class=" row">
                <div class="col-md-6"><label>最后修改人</label>
                    <input type="text" class="form-control" placeholder="最后修改人" value="{{= d.updater}}" disabled>
                </div>
                <div class="col-md-6"><label>最后修改时间</label>
                    <input type="text" class="form-control" placeholder="最后修改时间" value="{{= d.updateTime}}" disabled>
                </div>
            </div>
        </div>
    </form>
</script>

<script src="${request.contextPath}/static/app/js/app/appList.js"></script>

</@body>


