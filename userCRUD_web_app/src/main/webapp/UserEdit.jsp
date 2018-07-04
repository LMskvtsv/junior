<%@ page import="persistent.User" %>
<%@ page import="logic.ValidateService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
    <title>Edit user</title>
    </head>
    <body>
        <form action='<%=request.getContextPath()%>/users/edit' method='post'>
            Name: <input type='text' name='name' value='<%=request.getAttribute("name")%>'><br>
            Login: <input type='text' name='login' value='<%=request.getAttribute("login")%>'><br>
            E-mail: <input type='text' name='email' value='<%=request.getAttribute("email")%>'><br>
            <input type='hidden' name='id' value='<%=request.getAttribute("id")%>'>
            <input type='submit' value='Save user'>
        </form>
    </body>
</html>
