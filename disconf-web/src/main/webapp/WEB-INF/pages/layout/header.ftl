<header class="main-header">
    <!-- Logo -->
    <a href="index2.html" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><b>D</b>isconf</span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg"><b>Disconf</b></span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <li class="dropdown user user-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="${request.contextPath!""}/static/lib/lte/img/avatar.png" class="user-image"
                             alt="User Image">
                        <span class="hidden-xs">
                        </span>
                    </a>
                    <!-- Control Sidebar Toggle Button -->
                    <ul class="dropdown-menu">
                        <!-- User image -->
                        <li class="user-header">
                            <img src="${request.contextPath!""}/static/lib/lte/img/avatar.png" class="img-circle"
                                 alt="User Image">
                        </li>
                        <!-- Menu Footer-->
                        <li class="user-footer">
                            <div class="pull-left">
                                <a href="/user/update/password" class="btn btn-default btn-flat">修改密码</a>
                            </div>
                            <div class="pull-right">
                                <a href="/auth/logout" class="btn btn-default btn-flat">退出登录</a>
                            </div>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
</header>