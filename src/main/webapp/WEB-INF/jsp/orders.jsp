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
            <form action="/orders/edit/${order.id}" method="get"><input type = "submit" value = "Edit"></form>
        </td>
        <td>
            <form action="/orders/delete/${order.id}" method="post"><input type = "submit" value = "Delete"></form>
        </td>
    </tr>
    </c:forEach>
</table>
<div> <form action="/orders/create" method="get"><input type = "submit" value = "Create order"></form></div>

<c:if test="${page.getPageNumber() == 0 && orders.size()==page.getPageSize()}">
    <div>
        <a class="page-link" href="/orders?page=${page.getPageNumber()+1}&size=5">Next page</a>
    </div>
</c:if>
<c:if test="${page.getPageNumber()!=0 && orders.size()==page.getPageSize() }">
    <div>
        <a class="page-link" href="/orders?page=${page.getPageNumber()-1}&size=5" >Previous page</a>

        <a class="page-link" href="/orders?page=${page.getPageNumber()+1}&size=5">Next page</a>
    </div>
</c:if>
<c:if test="${orders.size()<page.getPageSize() }">
    <div>
        <a class="page-link" href="/orders?page=${page.getPageNumber()-1}&size=5" >Previous page</a>
    </div>
</c:if>

<ul>
    <li><a href="/books?page=0&size=5">All books</a> </li>
    <li><a href="/users?page=0&size=5">All users</a> </li>
</ul>
</body>
</html>