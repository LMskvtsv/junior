<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/add" method="get"><input type="submit" value="Add user"
        <c:if test="${sessionScope.role == 2 && sessionScope.login != entry.value.login}">
            <c:out value="disabled"/>
        </c:if>></form>
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
                <input type="submit" value="edit" name="action"
                        <c:if test="${sessionScope.role == 2 && sessionScope.login != entry.value.login}">
                            <c:out value="disabled"/>
                        </c:if>/>
                <input type="hidden" name="id" value="<c:out value="${entry.key}"/>"/>
                <input type="hidden" name="role_id" value="<c:out value="${entry.value.role.id}"/>"/>
            </form>
        </td>
        <td>
            <form action="${pageContext.servletContext.contextPath}/" method="post">
                <input type="submit" value="delete" name="action"
                <c:if test="${sessionScope.role == 2}">
                    <c:out value="disabled"/>
                </c:if>>
                <input type="hidden" name="id" value="<c:out value="${entry.key}"/>"/>
                <input type="hidden" name="role_id" value="<c:out value="${entry.value.role.id}"/>"/>
            </form>
        </td>
    </tr>
   </c:forEach>
</table>
<br>
<form action="${pageContext.servletContext.contextPath}/sign_out" method="post"><input type="submit" value="Log out" ></form>
<br>
    </c:if>
    <c:if  test="${users.size() <= 0}">
        <p>Users not found. You can add one :)</p>
    </c:if>
</body>
</html>
