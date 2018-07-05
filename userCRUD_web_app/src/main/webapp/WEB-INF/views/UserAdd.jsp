<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Add user</title>
    </head>
    <body>
        <form action="${pageContext.servletContext.contextPath}/add" method="post">
            Name: <input type="text" name="name"><br>
            Login: <input type="text" name="login"><br>
            E-mail: <input type="text" name="email"><br>
            <input type="submit" value="Add new user">
        </form>
    </body>
</html>
