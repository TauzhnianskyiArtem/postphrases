<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>User editor</title>
</head>
<body>
    <h3>User editor</h3>

    <form action="/user" method="post">
        <input type="text" name="username" value="${user.username}">
        <#list roles as role>
            <div>
                <label><input type="checkbox" name="${role!""}" ${user.roles?seq_contains(role)?string("checked", "")!""}>${role!""}</label>
            </div>
        </#list>
        <input type="hidden" value="${user.id!""}" name="userId">
        <input type="hidden" value="${_csrf.token!""}" name="_csrf">
        <button type="submit">Save</button>
    </form>
</form>
</body>
</html>
