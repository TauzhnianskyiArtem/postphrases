<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>My Profile</title>
</head>
<body>
    My Profile
    <p>${message?ifExists!""}</p>
    <form action="/profile" method="post">
        <div><label> Email: <input type="email" name="email" value="${user.email}"/> </label></div>
        <div><label>New password: <input type="password" name="password" /> </label></div>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit">Save</button>
    </form>
</body>
</html>