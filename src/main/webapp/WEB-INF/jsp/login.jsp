<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
        <title>Spring Security Example </title>
    </head>
    <body>
        <form th:action="/login" method="post">
            <div><label> Login : <input type="text" name="login"/> </label></div>
            <div><label> Password: <input type="password" name="password"/> </label></div>
            <div><input type="submit" value="Sign In"/></div>
        </form>
    </body>
</html>