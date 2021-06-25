<#import "parts/common.ftlh" as c>

<@c.page>

    <p>${message?ifExists!""}</p>
    My Profile
    <form action="/profile" method="post">
        <input type="email" name="email" value="${user.email}">
        <input type="text" name="password" value="${user.password}">

        <input type="hidden" value="${user.id!""}" name="userId">
        <input type="hidden" value="${_csrf.token!""}" name="_csrf">
        <button type="submit">Save</button>
    </form>
</@c.page>