<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Books</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1>Users</h1>
<table>
    <tr>
        <th>Id</th>
        <th>lastName</th>
        <th>firstName</th>
        <th>email</th>
        <th>login</th>
        <th>password</th>
        <th>age</th>
        <th>role</th>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
        <td>${user.id}</td>
        <td><a href="user/${user.id}">${user.lastName}</a></td>
        <td>${user.firstName}</td>
        <td>${user.email}</td>
        <td>${user.login}</td>
        <td>${user.password}</td>
        <td>${user.age}</td>
        <td>${user.role.toString().toLowerCase()}</td>
    </tr>
    </c:forEach>
</table>
</body>
</html>