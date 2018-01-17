<#assign topMenu="unauthorized">
<#assign currentMenu="unauthorized">
<#include "layout/layout.ftl">
<@body>
    <section class="content">
        对不起您没有权限访问，如需要请联系管理员~~<br/>
        <a href="${request.contextPath}/index">返回主页</a>
    </section>
</@body>