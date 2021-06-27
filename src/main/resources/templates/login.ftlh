<#import "parts/login.ftlh" as l>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Postphrases</title>
</head>
<body>
    Login page
    <br><a href="/oauth2/authorization/google">Войти с помощью Google</a><br>
    ${message?ifExists!""}
    <@l.login "/login" />
    <a href="/registration">Add new user</a>
</body>
</html>
