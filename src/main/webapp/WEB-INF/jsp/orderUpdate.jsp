<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<form action="/orders/${order.id}" method="post">
    <label for="book-input">Book:</label>
        <input id="book-input" name="book" type="text"/>
        <br/>
        <label for="price-input">Price:</label>
        <input id="price-input" name="price" type="text"/>
        <br/>
        <label for="quantity-input">Quantity:</label>
        <input id="quantity-input" name="quantity" type="number"/>
        <br/>
    <input type="submit" value="Update">
</form>
</body>
</html>