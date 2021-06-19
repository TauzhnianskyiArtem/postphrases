<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
Login page
<br><a href="/oauth2/authorization/google">Войти с помощью Google</a><br>
<@l.login "/login" />
<a href="/registration">Add new user</a>
</@c.page>