<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Books</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1>Books</h1>
<table>
    <tr>
        <th>Id</th>
        <th>Isbn</th>
        <th>Title</th>
        <th>Author</th>
        <th>Pages</th>
        <th>Cover</th>
        <th>Price</th>
    </tr>
    <c:forEach items="${books}" var="book">
        <tr>
        <td>${book.id}</td>
        <td>${book.isbn}</td>
        <td><a href="controller?command=book&id=${book.id}">${book.title}</a></td>
        <td>${book.author}</td>
        <td>${book.pages}</td>
        <td>${book.cover.toString().toLowerCase()}</td>
        <td>${book.price}</td>
    </tr>
    </c:forEach>
</table>
</body>
</html>