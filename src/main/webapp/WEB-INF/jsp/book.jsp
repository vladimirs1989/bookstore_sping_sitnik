<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<h1>Book, id = ${book.id}</h1>
        <div>isbn: ${book.isbn}</div>
        <div>title: ${book.title}</div>
        <div>author: ${book.author}</div>
        <div>pages: ${book.pages}</div>
        <div>cover: ${book.cover.toString().toLowerCase()}</div>
        <div>price: ${book.price}</div>
        <ul>
            <li><a href="/books?page=0&size=10">All books</a> </li>
        </ul>
</body>
</html>