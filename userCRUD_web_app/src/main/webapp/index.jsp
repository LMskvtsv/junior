<%@ page import="logic.ValidateService" %>
<%@ page import="persistent.User" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<form action='<%=request.getContextPath()%>/users/add' method='get'><input type='submit' value='Add user'></form>
<br>
    <% if (ValidateService.getSingletonInstance().findAll().size() > 0) {%>
<table>
    <tr>
        <th>User name</th>
        <th>Login</th>
        <th>Email</th>
        <th></th>
        <th></th>
    </tr>
    <% for (Map.Entry<String, User> entry: ValidateService.getSingletonInstance().findAll().entrySet()) {%>
    <tr>
        <td><%=entry.getValue().getName()%></td>
        <td><%=entry.getValue().getLogin()%></td>
        <td><%=entry.getValue().getEmail()%></td>
        <td>
            <form action='<%=request.getContextPath()%>/users/edit' method='get'>
                <input type='submit' value='edit' name='action'/>
                <input type='hidden' name='id' value='<%=entry.getValue().getId()%>'/>
            </form>
        </td>
        <td>
            <form action='<%=request.getContextPath()%>/users/list' method='post'>
                <input type='submit' value='delete' name='action'>
                <input type='hidden' name='id' value='<%=entry.getValue().getId()%>'>
            </form>
        </td>
    </tr>
<%}%>
</table>
    <%} else {%>
    <p>Users not found. You can add one :)</p>
    <%}%>
</body>
</html>
