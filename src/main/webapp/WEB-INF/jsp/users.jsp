<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Users</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<h1>Users</h1>
<div> <form action="/users/create" method="get"><input type = "submit" value = "Create user"></form></div>
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
        <td><a href="users/user/${user.id}">${user.lastName}</a></td>
        <td>${user.firstName}</td>
        <td>${user.email}</td>
        <td>${user.login}</td>
        <td>${user.password}</td>
        <td>${user.age}</td>
        <td>${user.role.toString().toLowerCase()}</td>
          <td>
              <form action="/users/edit/${user.id}" method="get"><input type = "submit" value = "Edit"></form>
          </td>
          <td>
              <form action="/users/delete/${user.id}" method="post"><input type = "submit" value = "Delete"></form>
          </td>
    </tr>
    </c:forEach>
</table>

<c:if test="${page.getPageNumber() == 0 && users.size()==page.getPageSize()}">
    <div>
        <a class="page-link" href="/users?page=${page.getPageNumber()+1}&size=5">Next page</a>
    </div>
</c:if>
<c:if test="${page.getPageNumber()!=0 && users.size()==page.getPageSize() }">
    <div>
        <a class="page-link" href="/users?page=${page.getPageNumber()-1}&size=5" >Previous page</a>

        <a class="page-link" href="/users?page=${page.getPageNumber()+1}&size=5">Next page</a>
    </div>
</c:if>
<c:if test="${users.size()<page.getPageSize() }">
    <div>
        <a class="page-link" href="/users?page=${page.getPageNumber()-1}&size=5" >Previous page</a>
    </div>
</c:if>
<ul>
    <li><a href="/books?page=0&size=5">All books</a> </li>
    <li><a href="/orders?page=0&size=5">All orders</a> </li>
</ul>
</body>
</html>