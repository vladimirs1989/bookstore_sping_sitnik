<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<form action="/books" method="post">
    <label for="isbn-input">Isbn:</label>
    <input id="isbn-input" name="isbn" type="text"/>
    <br/>
    <label for="title-input">Title:</label>
    <input id="title-input" name="title" type="text"/>
    <br/>
    <label for="author-input">Author:</label>
    <input id="author-input" name="author" type="text"/>
    <br/>
    <label for="pages-input">Pages:</label>
    <input id="pages-input" name="pages" type="number"/>
    <br/>
    <label for="cover-input">Cover:</label>
    <input id="cover-input" name="cover" type="text"/>
    <br/>
    <!--<label>Cover:
        <select name="covers">
            <option value="1">HARD</option>
            <option value="2">SOFT</option>
            <option value="3">ANOTHER</option>
        </select>
    </label>
    <br/>-->
    <label for="price-input">Price:</label>
    <input id="price-input" name="price" type="number"/>
    <br/>
    <input type="submit" value="Update">
</form>
</body>
</html>