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
    <br><a href="/oauth2/authorization/google">Login with Google</a><br>
    <a href="/oauth2/authorization/github">Login with GitHub</a><br>

    <#if Session.SPRING_SECURITY_LAST_EXCEPTION?? && Session.SPRING_SECURITY_LAST_EXCEPTION.message?has_content>
        <h3>${Session.SPRING_SECURITY_LAST_EXCEPTION.message}</h3>
    </#if>
    <form action="/login" method="post">
        <div><label> User Name : <input type="text" name="username"/> </label></div>
        <div><label> Password: <input type="password" name="password"/> </label></div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <div><input type="submit" value="Sign In"/></div>
    </form>

    <a href="/registration">Add new user</a>
    <a href="/restore">Forget password</a>
</body>
</html>
