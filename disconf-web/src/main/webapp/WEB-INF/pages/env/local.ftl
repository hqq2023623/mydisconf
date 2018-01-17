<#assign topMenu="envConfig">
<#assign currentMenu="envLocal">
<#include "../layout/layout.ftl">
<@body>
<section class="content">

    <div class="box box-primary">
        <form id="searchForm" onkeydown="if(event.keyCode==13){return false;}">
            <div class="box-header with-border">
                <h3 class="box-title">配置列表</h3>
            </div>
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
                        <div class="row">
                            <div class="col-md-2">
                                <span class="input-group-addon">环境:</span>
                                <select class="form-control" id="envSelect" name="envId">
                                    <#list envList as env>
                                        <#if env.id == currentEnv.id>
                                            <option value="${env.id}" selected="selected">${env.name!""}</option>
                                        <#else>
                                            <option value="${env.id}">${env.name!""}</option>
                                        </#if>
                                    </#list>
                                </select>
                            </div>
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
        </form>

        <div class="box-body with-border">
            <div id="toolbar">
                <a id="toolbarAdd" class="btn btn-default">
                    <i class="glyphicon glyphicon-plus"></i> 添加
                </a>
                <a id="toolbarEdit" class="btn btn-primary" disabled>
                    <i class="glyphicon glyphicon-pencil"></i> 编辑
                </a>
                <a id="toolbarSaveByText" class="btn btn-info	" disabled>
                    <i class="glyphicon"></i> 文本配置
                </a>
                <a id="toolbarSaveByFile" class="btn btn-info	" disabled>
                    <i class="glyphicon"></i> 上传文件
                </a>
                <a id="toolbarDel" class="btn btn-danger" disabled>
                    <i class="glyphicon glyphicon-ban-circle"></i> 删除
                </a>

                <a href="${request.contextPath}/user/bindRole" class="link_a">
                    <i class="glyphicon glyphicon-link"></i>绑定角色
                </a>
                <a href="${request.contextPath}/user/bindApp" class="link_a">
                    <i class="glyphicon glyphicon-link"></i>绑定APP
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
                   data-url="${request.contextPath}/config/list"
                   data-unique-id="id"
                   data-method="post"
                   data-Content-Type="application/x-www-form-urlencoded">
                <thead>
                <tr>
                    <th data-field="state" data-checkbox="true"></th>
                    <th data-field="id">ID</th>
                    <th data-field="groupName">组名称</th>
                    <th data-field="appName">APP名称</th>
                    <th data-field="configName">配置名称</th>
                    <th data-field="version">版本号</th>
                    <th data-field="envName">环境</th>
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
                <div class="col-md-6">
                    <label>组名</label>
                    <input type="hidden" name="id" value="{{d.id}}">
                    <input type="text" class="form-control" name="groupName" placeholder="组名" value="{{= d.groupName}}">
                </div>
                <div class="col-md-6">
                    <label>APP名称</label>
                    <input type="text" class="form-control" name="appName" placeholder="APP名称" value="{{= d.appName}}">
                </div>
            </div>
            <br/>

            <div class="row">
                <div class="col-md-6">
                    <label>配置名称</label>
                    <input type="text" class="form-control" name="configName" placeholder="配置名称" value="{{= d.configName}}">
                </div>
                <div class="col-md-6">
                    <label>版本号</label>
                    <input type="text" class="form-control" name="version" placeholder="版本号" value="{{= d.version}}">
                </div>
            </div>
            <br/>

            <div class="row">
                <div class="col-md-2">
                    <label>环境 : ${env.name}</label>
                    <input type="hidden" name="envId" value="${currentEnv.id}"/>
                    <input type="hidden" name="envName" value="${currentEnv.name}"/>
<#--                    <select class="form-control" id="envName" name="envName">
                        <#list envList as env>
                            <option value="${env.id}|${env.name}">${env.name!""}</option>
                        </#list>
                    </select>-->
                </div>
            </div>
        </div>
    </form>


</script>

<script id="saveByTextTpl" type="text/html">
    <#--输入文本保存配置文件-->
    <form id="saveByTextForm">
        <div class="container-fluid">
            <br/>
            <div class="row">
                <div class="col-md-6">
                    <input type="hidden" name="id" value="{{=d.id}}">
<#--
                    <input type="hidden" name="type" value="1"/>
-->
                    <textarea class="form-control" name="value" placeholder="请输入文本" style="height: 300px;width: 500px;">{{=d.value}}</textarea>
                </div>
            </div>
            <br/>
        </div>
    </form>
</script>

<script id="saveByFileTpl" type="text/html">
    <#--上传配置文件-->
    <form id="saveByFileForm" enctype="multipart/form-data" method="post" action="${request.contextPath}/config/upload">
        <div class="container-fluid">
            <br/>
            <div class="row">
                <div class="col-md-6">
                    <input type="hidden" name="id" value="{{=d.id}}">
<#--
                    <input type="hidden" name="type" value="0"/>
-->
                    <label for="uploadingFile">上传文件</label>
                    <input type="file" id="uploadingFile" name="uploadingFile" />
                </div>
            </div>
            <br/>
        </div>
    </form>
</script>

<script src="${request.contextPath}/static/lib/jQuery/jquery.form.js"></script>
<script src="${request.contextPath}/static/app/js/config/config.js"></script>

</@body>


