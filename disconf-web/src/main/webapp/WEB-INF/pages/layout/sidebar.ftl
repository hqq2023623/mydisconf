<aside class="main-sidebar">
    <section class="sidebar">
        <ul class="sidebar-menu">
            <li class="header">菜单</li>
            <#if topMenu="system">
                <li class="treeview active">
            <#else>
                <li class="treeview">
            </#if>
                <a href="#">
                    <i class="fa fa-gears"></i> <span>系统管理</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li <#if currentMenu =="userList">class="active"</#if>>
                        <a href="${request.contextPath}/user/list">
                            <i class="fa fa-user"></i> 用户
                        </a>
                    </li>
                    <li <#if currentMenu =="roleList">class="active"</#if>>
                        <a href="${request.contextPath}/role/list">
                            <i class="fa fa-users"></i> 角色
                        </a>
                    </li>
                    <li <#if currentMenu =="permissionList">class="active"</#if>>
                        <a href="${request.contextPath}/permission/list">
                            <i class="fa fa-user-secret"></i> 权限
                        </a>
                    </li>

                   <#-- <li <#if currentMenu =="groupList">class="active"</#if>>
                        <a href="${request.contextPath}/group/list">
                            <i class="fa fa-user-secret"></i>GROUP
                        </a>
                    </li>-->
                </ul>
            </li>

            <#if topMenu="app">
                <li class="treeview active">
            <#else>
                <li class="treeview">
            </#if>
                <a href="#">
                    <i class="fa fa-gears"></i> <span>APP管理</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li <#if currentMenu =="appList">class="active"</#if>>
                        <a href="${request.contextPath}/app/list">
                            <i class="fa fa-user-secret"></i> APP列表
                        </a>
                    </li>
                  <#--  <li <#if currentMenu =="configList">class="active"</#if>>
                        <a href="${request.contextPath}/config/list">
                            <i class="fa fa-user-secret"></i> 配置列表
                        </a>
                    </li>-->

                </ul>
            </li>

        <#if topMenu="envConfig">
        <li class="treeview active">
        <#else>
        <li class="treeview">
        </#if>
            <a href="#">
                <i class="fa fa-gears"></i> <span>配置管理</span>
                <i class="fa fa-angle-left pull-right"></i>
            </a>
            <ul class="treeview-menu">
                <li <#if currentMenu =="local">class="active"</#if>>
                    <a href="${request.contextPath}/config/list/1">
                        <i class="fa fa-user-secret"></i> local
                    </a>
                </li>
                <li <#if currentMenu =="test">class="active"</#if>>
                    <a href="${request.contextPath}/config/list/2">
                        <i class="fa fa-user-secret"></i> test
                    </a>
                </li>
                <li <#if currentMenu =="pre">class="active"</#if>>
                    <a href="${request.contextPath}/config/list/3">
                        <i class="fa fa-user-secret"></i> pre
                    </a>
                </li>
                <li <#if currentMenu =="online">class="active"</#if>>
                    <a href="${request.contextPath}/config/list/4">
                        <i class="fa fa-user-secret"></i> online
                    </a>
                </li>
                <li <#if currentMenu =="configHistory">class="active"</#if>>
                    <a href="${request.contextPath}/config/history/list">
                        <i class="fa fa-user-secret"></i> 历史配置列表
                    </a>
                </li>
            </ul>
        </li>


        </ul>
    </section>
</aside>
<div class="control-sidebar-bg"></div>