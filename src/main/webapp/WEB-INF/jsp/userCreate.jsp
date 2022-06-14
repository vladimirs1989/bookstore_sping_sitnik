<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<form action="/users" method="post">
    <label for="lastName-input">LastName:</label>
    <input id="lastName-input" name="lastName" type="text" required/>
    <br/>
    <label for="firstName-input">FirstName:</label>
    <input id="firstName-input" name="firstName" type="text" required/>
    <br/>
    <label for="email-input">Email:</label>
    <input id="email-input" name="email" type="text" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2, 4}$" required/>
    <br/>
    <label for="login-input">Login:</label>
    <input id="login-input" name="login" type="text" required/>
    <br/>
    <label for="password-input">Password:</label>
    <input id="password-input" name="password" type="text" required/>
    <br/>
    <label for="age-input">Age:</label>
    <input id="age-input" name="age" type="number" min="1" max="100" required/>
    <br/>
    <label>Role:
        <select name="role">
            <option value="ADMIN">ADMIN</option>
            <option value="MANAGER">MANAGER</option>
            <option value="CUSTOMER" selected>CUSTOMER</option>
        </select>
    </label>
    <br/>
    <input type="submit" value="Create">
</form>
</body>
</html>