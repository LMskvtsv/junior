<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Add user</title>
    </head>
    <body>
    <c:if test="${error != ''}">
        <div style="background-color: indianred">
            <c:out value="${error}"></c:out>
        </div>
    </c:if>
        <form action="${pageContext.servletContext.contextPath}/sign_in" method="post">
            Login : <input type="text" name="login"><br>
            Password : <input type="password" name="password"><br>
            <input type="submit" value="Sign in">
        </form>
    </body>
</html>
