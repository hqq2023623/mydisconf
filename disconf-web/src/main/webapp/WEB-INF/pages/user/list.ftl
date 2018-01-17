<#assign topMenu="system">
<#assign currentMenu="userList">
<#include "../layout/layout.ftl">
<@body>
<section class="content">

    <div class="box box-primary">
        <form id="searchForm" onkeydown="if(event.keyCode==13){return false;}">
            <div class="box-header with-border">
                <h3 class="box-title">用户列表</h3>
            </div>
            <div class="box-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-3">
                            <span class="input-group-addon">登录名:</span>
                            <input class="form-control" name="userName" placeholder="登录名"/>
                        </div>
                        <div class="col-md-3">
                            <span class="input-group-addon">姓名:</span>
                            <input class="form-control" name="realName" placeholder="姓名"/>
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
                <a id="toolbarDel" class="btn btn-danger" disabled>
                    <i class="glyphicon glyphicon-ban-circle"></i> 删除
                </a>
                <a id="resetUserPwd" class="btn btn-primary" disabled>
                    <i class="glyphicon glyphicon-pencil"></i> 重置密码
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
                   data-url="${request.contextPath}/user/list"
                   data-unique-id="id"
                   data-method="post"
                   data-Content-Type="application/x-www-form-urlencoded">
                <thead>
                <tr>
                    <th data-field="state" data-checkbox="true"></th>
                    <th data-field="id">ID</th>
                    <th data-field="userName">登录名</th>
                    <th data-field="realName">真实姓名</th>
                    <th data-field="phone">电话号码</th>
                    <th data-field="email">邮箱</th>
                    <th data-field="ip">IP地址</th>
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
                <div class="col-md-6"><label>用户名</label>
                    <input type="hidden" name="id" value="{{d.id}}">
                    <input type="text" class="form-control" name="userName" placeholder="用户名" value="{{= d.userName}}">
                </div>
                <div class="col-md-6"><label>真实姓名</label>
                    <input type="text" class="form-control" name="realName" placeholder="真实姓名" value="{{= d.realName}}">
                </div>
            </div>
            <br/>

            <div class="row">
                <div class="col-md-6"><label>手机号码</label>
                    <input type="text" class="form-control" name="phone" placeholder="手机号码" value="{{= d.phone}}">
                </div>
                <div class="col-md-6"><label>邮箱</label>
                    <input type="email" class="form-control" name="email" placeholder="电子邮箱" value="{{= d.email}}">
                </div>
            </div>
            <br/>

        </div>
    </form>
</script>

<script src="${request.contextPath}/static/app/js/user/user.js"></script>

</@body>


