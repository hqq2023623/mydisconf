<#assign topMenu="system">
<#assign currentMenu="userList">
<#include "../layout/layout.ftl">
<@body>
<section class="content">

    <div class="box box-primary">
        <div class="box-header with-border">

            <div class="box-header with-border">
                <h3 class="box-title">用户角色管理</h3>
                <a href="${request.contextPath!""}/user/list">
                    <i class="glyphicon glyphicon-fast-backward"></i>返回用户列表
                </a>
            </div>

            <div class="box-body">
                <div class="row">
                    <div class="col-lg-4 form-inline" style="width: auto">
                        <label class="col-sm-6 control-label">用户:</label>
                        <div class="col-sm-7" style="width: 100%;">
                            <select class="form-control" id="J_user_selectforbind" style="width: 100%;">
                            <#if userList??>
                                <#list userList as user>
                                    <option value="${user.id}" <#if userId?? && userId == user.id > selected </#if> > ${user.realName}</option>
                                </#list>
                            </#if>
                            </select>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="zTreeDemoBackground">
                            <ul id="treeDemo" class="ztree"
                                style="height: 230%;border: 0px; background:none; overflow-y: auto;font-size:14px;border-right: solid rgba(208, 206, 206, 0.78) 1px;">
                                <a href="/role/list">还没有角色，去添加</a>
                            </ul>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="form-group">
                            <div class="col-md-2" style="width: 120px;">
                                <input class="btn btn-primary" type="button" value="绑定" id="bindRole">
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
                       data-url="/userRole/list"
                       data-unique-id="id"
                       data-method="post"
                       data-pageSize="10000"
                       data-Content-Type="application/x-www-form-urlencoded"
                       data-click-to-select="true">
                    <thead style="font-weight: bold;">
                    <tr>
                        <th data-field="state" data-checkbox="true"></th>
                        <th data-field="id">ID</th>
                        <th data-field="userId">用户ID</th>
                        <th data-field="userName">用户名</th>
                        <th data-field="roleId">角色ID</th>
                        <th data-field="roleName">角色名称</th>
                        <th data-field="roleDesc">角色描述</th>
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
<script src="${request.contextPath}/static/app/js/user/bindRole.js"></script>

</@body>