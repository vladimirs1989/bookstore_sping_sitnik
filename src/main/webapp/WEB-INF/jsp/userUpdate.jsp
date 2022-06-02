<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<form action="/users/${user.id}" method="post">
    <label for="lastName-input">LastName:</label>
    <input id="lastName-input" name="lastName" type="text"/>
    <br/>
    <label for="firstName-input">FirstName:</label>
    <input id="firstName-input" name="firstName" type="text"/>
    <br/>
    <label for="email-input">Email:</label>
    <input id="email-input" name="email" type="text"/>
    <br/>
    <label for="login-input">Login:</label>
    <input id="login-input" name="login" type="text"/>
    <br/>
    <label for="password-input">Password:</label>
    <input id="password-input" name="password" type="text"/>
    <br/>
    <label for="age-input">Age:</label>
    <input id="age-input" name="age" type="number"/>
    <br/>
    <label for="role-input">Role:</label>
    <input id="role-input" name="role" type="text"/>
    <br/>
    <input type="submit" value="Update">
</form>
</body>
</html>