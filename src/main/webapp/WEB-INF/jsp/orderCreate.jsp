<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<form action="/orders" method="post">
    <label for="book-input">Book (enter number of need book):</label>
    <input id="book-input" name="book" type="text" required/>
    <br/>
    <label for="user-input">User (enter user id):</label>
    <input id="user-input" name="user" type="text" required/>
    <br/>
    <label for="quantity-input">Quantity:</label>
    <input id="quantity-input" name="quantity" type="number" min="1" max="30" required/>
    <br/>


    <input type="submit" value="Create">
</form>
</body>
</html>