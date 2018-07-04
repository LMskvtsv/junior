<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Add user</title>
    </head>
    <body>
        <form action='<%=request.getContextPath()%>/users/add' method='post'>
            Name: <input type='text' name='name'><br>
            Login: <input type='text' name='login'><br>
            E-mail: <input type='text' name='email'><br>
            <input type='submit' value='Add new user'>
        </form>
    </body>
</html>
