<#import "parts/login.ftlh" as l>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Main</title>
</head>
<body>
<div>
    <@l.logout />
    <h3>Hello, <@security.authentication property="principal.username"/></h3>

    <@security.authorize access="hasAnyAuthority('ADMIN')">
        <span><a href="/user">User list</a></span><br>
    </@security.authorize>
    <span><a href="/profile">My Profile</a></span>
</div>
    <div>
        <form method="post" enctype="multipart/form-data">
            <input type="text" name="text" placeholder="Введите сообщение"/>
            <input type="text" name="tag" placeholder="Тэг">
            <input type="file" name="file">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Добавить</button>
        </form>
    </div>
    <div>Список сообщений</div>
    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter?ifExists}">
        <button type="submit">Найти</button>
    </form>
    <#list messages as message>
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
            <div>
                <#if message.fileName??>
                    <img src="/img/${message.fileName}">
                </#if>
            </div>
        </div>
    <#else>
        No message
    </#list>
</body>
</html>
