<#assign topMenu="system">
<#assign currentMenu="roleList">
<#include "../layout/layout.ftl">
<@body>
<section class="content">

    <div class="box box-primary">
        <div class="box-header with-border">

            <div class="box-header with-border">
                <h3 class="box-title">角色权限管理</h3>
            </div>

            <div class="box-body">
                <div class="row">
                    <div class="col-lg-6 form-inline" style="width: auto">
                        <div class="form-group">
                            <label class="col-sm-6 control-label">角色:</label>
                            <div class="col-sm-8" style="width: 100%;">
                                <select class="form-control" id="J_role_selectforbind" style="width: 100%;">
                                    <#if roleList??>
                                        <#list roleList as role>
                                            <option value="${role.id}" <#if roleId?? && roleId == role.id > selected </#if> >${role.description}</option>
                                        </#list>
                                    </#if>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-5">
                        <div class="zTreeDemoBackground">
                            <ul id="treeDemo" class="ztree"
                                style="height: 230%;border: 0px; background:none; overflow-y: auto;font-size:14px;border-right: solid rgba(208, 206, 206, 0.78) 1px;">
                                <a href="/permission/list">还没有权限，去添加</a>
                            </ul>
                        </div>
                    </div>
                    <div class="col-lg-2">
                        <div class="form-group">
                            <div class="col-md-2" style="width: 120px;">
                                <input class="btn btn-primary" type="button" value="绑定" id="bindPermission">
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>


    </div>

    <div class="box box-primary">
        <div class="box-body with-border">
            <div classs="row" id="toolbar">
                <a id="toolbarDel" class="btn btn-danger" disabled>
                    <i class="glyphicon glyphicon-ban-circle"></i> 删除
                </a>
            </div>

            <div style="margin: 25px;">
                <table id="table_content"
                       data-classes="table table-bordered"
                       data-toolbar="#toolbar"
                       data-show-refresh="true"
                       data-show-toggle="true"
                       data-show-columns="true"
                       data-id-field="id"
                       data-toggle="table"
                       data-minimum-count-columns="2"
                       data-show-footer="false"
                       data-query-params="getRequest"
                       data-response-handler="responseHandler"
                       data-url="/role/permissionList"
                       data-unique-id="id"
                       data-method="post"
                       data-pageSize="10000"
                       data-Content-Type="application/x-www-form-urlencoded"
                       data-click-to-select="true">
                    <thead style="font-weight: bold;">
                    <tr>
                        <th data-field="state" data-checkbox="true"></th>
                        <th data-field="id">ID</th>
                        <th data-field="roleId">角色ID</th>
                        <th data-field="roleName">角色名</th>
                        <th data-field="roleDesc">角色描述</th>
                        <th data-field="permissionId">权限ID</th>
                        <th data-field="permissionName">权限名称</th>
                        <th data-field="permissionDesc">权限描述</th>
                        <th data-field="url">url</th>
                        <th data-field="createTime" data-formatter="Formatters.DateTime">创建时间</th>
                        <th data-field="updateTime" data-formatter="Formatters.DateTime">修改时间</th>
                        <th data-field="creator">创建人</th>
                        <th data-field="updater">修改人</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>

</section>

<!------------添加权限 -------------------->
<input type="button" id="addpermission-btn" class="btn-primary btn-sm" value="添加权限" hidden>

<div class="modal fade" id="delebindpermissionDialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">删除角色</h4>
            </div>
            <div class="modal-body">
                <input id="id" hidden>
                <p>确定删除吗</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="J_sure_dele">确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div><!-- /.modal -->


<script id="saveModelTpl" type="text/html">
    <form id="saveForm">
        <div class="container-fluid">
            <br/>
            <div class="row">
                <div class="col-md-6"><label>权限名称</label>
                    <input type="hidden" name="parentId" value="{{d.parentId}}">
                    <input type="text" class="form-control" name="permissionName" placeholder="权限名称" value="{{= d.name}}">
                </div>
                <div class="col-md-6"><label>描述</label>
                    <input type="text" class="form-control" name="description" placeholder="描述" value="{{= d.description}}">
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-md-6"><label>url</label>
                    <input type="text" class="form-control" name="url" placeholder="url" value="{{= d.url}}">
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

<script src="${request.contextPath}/static/app/js/user/bindPermission.js"></script>


</@body>
