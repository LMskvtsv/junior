package servlet;

import logic.ValidateService;
import persistent.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Servlet for /crud/users page.
 * Supports create operation with Users.
 * GET example: localhost:port/crud/users/add
 * POST example: localhost:port/crud/users?action=add&name=ivan&login=puffy
 */
public class UserAddServlet extends HttpServlet {
    private final ValidateService validateService = ValidateService.getSingletonInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        User user = new User(request.getParameter("name"), request.getParameter("login"), request.getParameter("email"));
        if (validateService.add(user)) {
            response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+ "/users/list");
        } else{
            writer.append(String.format("Error! User %s was not added.", user.toString()));
            writer.flush();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = new PrintWriter(response.getOutputStream());
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html>");
        builder.append("<html lang=\"en\">");
        builder.append("<head>");
        builder.append("<meta charset=\"UTF-8\">");
        builder.append("<title>Users</title>");
        builder.append("</head>");
        builder.append("<body>");
        builder.append("<form action='" + request.getContextPath()+"/users/add' method='post'>");
        builder.append("Name: <input type='text' name='name'><br>");
        builder.append("Login: <input type='text' name='login'><br>");
        builder.append("E-mail: <input type='text' name='email'><br>");
        builder.append("<input type='submit' value='Add new user'>");
        builder.append("</form>");
        builder.append("</body>");
        builder.append("</html>");
        writer.append(builder.toString());
        writer.flush();
    }
}
