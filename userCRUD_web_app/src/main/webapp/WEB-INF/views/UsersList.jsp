<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Users</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="form-group">
    <div class="col-sm-offset-0.5 col-sm-12">
        <form action="${pageContext.servletContext.contextPath}/add" method="get">
            <input class="btn btn-default" type="submit" value="Add user"
        <c:if test="${sessionScope.role == 2 && sessionScope.login != entry.value.login}">
            <c:out value="disabled"/>
        </c:if>></form>
    </div>
</div>
<br>
<c:if test="${users.size() > 0}">
<div class="container">
    <h2>Users</h2>
    <table id="users" name="mytable" class="table table-hover">
        <thead>
        <tr id = "header-users">
            <th>User name</th>
            <th>Login</th>
            <th>Email</th>
            <th>Country</th>
            <th>City</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody id = "body-users">
        <c:forEach items="${users}" var="entry">
            <tr>
                <td><c:out value="${entry.value.name}"/></td>
                <td><c:out value="${entry.value.login}"/></td>
                <td><c:out value="${entry.value.email}"/></td>
                <td><c:out value="${entry.value.country}"/></td>
                <td><c:out value="${entry.value.city}"/></td>
                <td>
                    <form action="${pageContext.servletContext.contextPath}/edit" method="get">
                        <input type="submit" class="btn btn-default" value="edit" name="action"
                                <c:if test="${sessionScope.role == 2 && sessionScope.login != entry.value.login}">
                                    <c:out value="disabled"/>
                                </c:if>/>
                        <input type="hidden" name="id" value="<c:out value="${entry.key}"/>"/>
                        <input type="hidden" name="role_id" value="<c:out value="${entry.value.role.id}"/>"/>
                    </form>
                </td>
                <td>
                    <form action="${pageContext.servletContext.contextPath}/" method="post">
                        <input type="submit" class="btn btn-default" value="delete" name="action"
                        <c:if test="${sessionScope.role == 2}">
                            <c:out value="disabled"/>
                        </c:if>>
                        <input type="hidden" name="id" value="<c:out value="${entry.key}"/>"/>
                        <input type="hidden" name="role_id" value="<c:out value="${entry.value.role.id}"/>"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</c:if>
<c:if  test="${users.size() <= 0}">
    <div class="form-group">
    <p>Users not found. You can add one :)</p>
    </div>
</c:if>

<div  class="col-sm-offset-0.5 col-sm-12">
<form action="${pageContext.servletContext.contextPath}/sign_out" method="post">
    <input type="submit" class="btn btn-default" value="Log out"></form>
</div>
</body>
</html>
