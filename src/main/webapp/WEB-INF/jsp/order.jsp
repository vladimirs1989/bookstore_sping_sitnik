<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Order</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<h1>Order, id = ${order.id}</h1>
        <div>User Last name: ${order.userDto.lastName}</div>
        <div>TotalCost: ${order.totalCost}</div>
        <div>Date: ${order.timestamp}</div>
        <div>Status: ${order.statusDto.toString().toLowerCase()}</div>
<h2>Items: </h2>
<table>
    <tr>
        <th>Id</th>
        <th>Book Title</th>
        <th>Book Author</th>
        <th>Quantity</th>
        <th>Price</th>
    </tr>
       <c:forEach items="${order.items}" var="orderI">
               <tr>
               <td>${orderI.id}</td>
                   <td>${orderI.bookDto.title}</td>
                   <td>${orderI.bookDto.author}</td>
               <td>${orderI.quantity}</td>
               <td>${orderI.price}</td>
               </tr>
           </c:forEach>
</table>

            <li><a href="/orders?page=0&size=5">All orders</a> </li>

</body>
</html>