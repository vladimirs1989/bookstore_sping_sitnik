<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<form action="/books" method="post">
    <label for="isbn-input">Isbn:</label>
    <input id="isbn-input" name="isbn" type="text" required/>
    <br/>
    <label for="title-input">Title:</label>
    <input id="title-input" name="title" type="text"required/>
    <br/>
    <label for="author-input">Author:</label>
    <input id="author-input" name="author" type="text" required/>
    <br/>
    <label for="pages-input">Pages:</label>
    <input id="pages-input" name="pages" type="number" min="1" required/>
    <br/>
    <label>Cover:
        <select name="cover">
            <option value="HARD">HARD</option>
            <option value="SOFT">SOFT</option>
            <option value="ANOTHER">ANOTHER</option>
        </select>
    </label>
    <br/>
    <label for="price-input">Price:</label>
    <input id="price-input" name="price" type="number" min="0" required/>
    <br/>
    <input type="submit" value="Create">
</form>
</body>
</html>