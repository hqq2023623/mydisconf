<#include "./layout/layout.ftl">
<@body>
<section class="content-header"></section>
<section class="content">

    <div class="row"
         style="margin: 2% -30px auto -30px;">
        <div class="login-box">
            <div class="login-box-body">
                <h3 class="login-box-msg login-font"><strong>Disconf</strong>登录</h3>

                <form id="loginForm" action="/login" method="post">
                    <div class="form-group">
                        <input type="text" name="username" class="form-control login-font" placeholder="用户名" required>
                    </div>
                    <div class="form-group">
                        <input type="password" name="password" class="form-control login-font" placeholder="密码"
                               required>
                    </div>
                    <br/>

                    <div class="form-group">
                        <button type="submit" class="btn btn-primary btn-block btn-flat login-font"><strong>登 录</strong>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>


</section>
<script>
if ('${logininfo!""}' != '') {
    Messager.error("${logininfo!''}");
}
</script>

</@body>


