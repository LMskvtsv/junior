<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/add" method="get"><input type="submit" value="Add user"></form>
<br>
    <c:if  test="${users.size() > 0}">
<table>
    <tr>
        <th>User name</th>
        <th>Login</th>
        <th>Email</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${users}" var="entry">
    <tr>
        <td><c:out value="${entry.value.name}"/></td>
        <td><c:out value="${entry.value.login}"/></td>
        <td><c:out value="${entry.value.email}"/></td>
        <td>
            <form action="${pageContext.servletContext.contextPath}/edit" method="get">
                <input type="submit" value="edit" name="action"/>
                <input type="hidden" name="id" value="<c:out value="${entry.key}"/>"/>
            </form>
        </td>
        <td>
            <form action="${pageContext.servletContext.contextPath}/" method="post">
                <input type="submit" value="delete" name="action">
                <input type="hidden" name="id" value="<c:out value="${entry.key}"/>"/>
            </form>
        </td>
    </tr>
   </c:forEach>
</table>
    </c:if>
    <c:if  test="${users.size() <= 0}">
        <p>Users not found. You can add one :)</p>
    </c:if>
</body>
</html>
