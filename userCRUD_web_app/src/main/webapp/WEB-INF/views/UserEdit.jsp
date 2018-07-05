<%@ page import="persistent.User" %>
<%@ page import="logic.ValidateService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
    <title>Edit user</title>
    </head>
    <body>
        <form action="${pageContext.servletContext.contextPath}/edit" method="post">
            Name: <input type="text" name="name" value="${name}"><br>
            Login: <input type="text" name="login" value="${login}"><br>
            E-mail: <input type="text" name="email" value="${email}"><br>
            <input type="hidden" name="id" value="${id}">
            <input type="submit" value="Save user">
        </form>
    </body>
</html>
