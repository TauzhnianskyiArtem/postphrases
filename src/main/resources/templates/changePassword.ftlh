<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Restore</title>
</head>
<body>
    Change password
    <#if message??>
        <h3>${message}</h3>
    </#if>
    <form method="post">
        <div><label> Password: <input type="password" name="password"/> </label></div>
        <div><label> Confirm password: <input type="password" name="password2"/> </label></div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <div><input type="submit" value="Change"/></div>
    </form>
</body>
</html>
