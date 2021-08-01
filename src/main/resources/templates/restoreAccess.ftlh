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
    Restore
    <#if message??>
        <h3>${message}</h3>
    </#if>
    <form action="/restore" method="post">
        <div><label> Email: <input type="email" name="email"/> </label></div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <div><input type="submit" value="Next"/></div>
    </form>
</body>
</html>
