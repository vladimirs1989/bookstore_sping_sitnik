<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BookStore</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<h1>Welcome to our bookstore</h1>
<form action="/logout" method="post">
    <input type="submit" value="Sign Out"/>
</form>
<ul>
    <li><a href="/books?page=0&size=5">All books</a> </li>
    <li><a href="/users?page=0&size=5">All users</a> </li>
    <li><a href="/orders?page=0&size=5">All orders</a> </li>
</ul>
</body>
</html>