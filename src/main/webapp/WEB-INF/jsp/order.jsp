<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<h1>Order, id = ${order.id}</h1>
        <div>User: ${order.userDto.lastName}</div>
        <div>TotalCost: ${order.totalCost}</div>
        <div>Date: ${order.timestamp}</div>
        <div>Status: ${order.statusDto.toString().toLowerCase()}</div>
        <ul>
            <li><a href="/orders">All orders</a> </li>
        </ul>
</body>
</html>