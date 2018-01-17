<#assign topMenu="system">
<#assign currentMenu="userList">
<#include "../layout/layout.ftl">
<@body>
    <section class="content">
           <div class="box box-primary">
               <div class="box-header with-border">
                   <h3 class="box-title">修改密码</h3>
               </div>
           </div>
           <div class="box box-primary">
               <div class="box-body with-border">
                    <form class="form-horizontal" id="J_updatepwd_form">
                        <div class="form-group">
                            <label class="control-label col-sm-3">新密码</label>
                            <div class="col-sm-5">
                                <input type="password" class="form-control" name="pwd" id="pwd">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3">确认密码</label>
                            <div class="col-sm-5">
                                <input type="password" class="form-control" name="confirmPwd" id="confirmPwd">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-5">
                                <button type="button" id="J_updatepwd_confirmBtn" class="btn btn-default">确认</button>
                            </div>
                        </div>
                    </form>
               </div>
           </div>
    </section>
<script type="text/javascript">
    $('#J_updatepwd_confirmBtn').bind('click', function () {
        var data = Commons.formData('#J_updatepwd_form');
        if(data.pwd == null) {
            Messager.error('请输入新密码');
            return;
        }
        if(data.confirmPwd == null) {
            Messager.error('请输入确认密码');
            return;
        }
        if(data.pwd != data.confirmPwd) {
            Messager.error('两次密码输入不一致，请重新输入');
            return;
        }
        if(data.pwd.length < 6) {
            Messager.error('密码不能小于六位');
            return;
        }
        $.ajax({
            type: "POST",
            url: "/user/update/password",
            data: data,
            success: function(){
                Messager.success("密码已修改！！！");
                setTimeout(function(){
                    window.location.reload();
                }),1000
            },
            error:function(){
                Messager.error("密码修改失败！！！");
            }
        });
    })
</script>
</@body>