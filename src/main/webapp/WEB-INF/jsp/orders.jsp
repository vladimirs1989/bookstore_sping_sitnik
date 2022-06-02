<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Orders</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<h1>Orders</h1>
<table>
    <tr>
        <th>Id</th>
        <th>User</th>
        <th>TotalCost</th>
        <th>Timestamp</th>
        <th>Status</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${orders}" var="order">
        <tr>
        <td><a href="orders/order/${order.id}">${order.id}</a></td>
        <td>${order.userDto.lastName}</td>
        <td>${order.totalCost}</td>
        <td>${order.timestamp}</td>
        <td>${order.statusDto.toString().toLowerCase()}</td>
        <td>
            <form action="/orders/edit/${order.userDto.id}" method="get"><input type = "submit" value = "Edit"></form>
        </td>
        <td>
            <form action="/orders/delete/${order.userDto.id}" method="post"><input type = "submit" value = "Delete"></form>
        </td>
    </tr>
    </c:forEach>
</table>
<div> <form action="/orders/create" method="get"><input type = "submit" value = "Create order"></form></div>
</body>
</html>