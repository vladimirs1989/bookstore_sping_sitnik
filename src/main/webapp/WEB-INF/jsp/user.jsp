<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<h1>User, id = ${user.id}</h1>
                <div>lastName: ${user.lastName}</div>
                <div>firstName: ${user.firstName}</div>
                <div>email: ${user.email}</div>
                <div>login: ${user.login}</div>
                <div>password: ${user.password}</div>
                <div>age: ${user.age}</div>
                <div>role: ${user.role.toString().toLowerCase()}</div>
                <ul>
                     <li><a href="/users?page=0&size=5">All users</a> </li>
                </ul>
</body>
</html>