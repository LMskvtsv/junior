package servlet;

import logic.ValidateService;
import persistent.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for /crud/users page.
 * Supports update operations with Users. After saving data redirects on user/list page.
 * GET example: localhost:port/crud/users/edit
 * POST example: localhost:port/crud/users/edit?id=123jj5j&action=add&name=ivan&login=puffy
 */
public class UserEditServlet extends HttpServlet {

    private final ValidateService validateService = ValidateService.getSingletonInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String id = request.getParameter("id");
        if (validateService.update(id, name, login, email)) {
            response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/users/list");
        } else {
            writer.append(String.format("Error! User id %s was not updated.", id));
            writer.flush();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = new PrintWriter(response.getOutputStream());
        StringBuilder builder = new StringBuilder();
        String id = request.getParameter("id");
        User u = validateService.findByID(id);
        builder.append("<!DOCTYPE html>");
        builder.append("<html lang=\"en\">");
        builder.append("<head>");
        builder.append("<meta charset=\"UTF-8\">");
        builder.append("<title>Users</title>");
        builder.append("</head>");
        builder.append("<body>");
        builder.append("<form action='" + request.getContextPath() + "/users/edit' method='post'>");
        builder.append("Name: <input type='text' name='name' value='" + u.getName() + "'><br>");
        builder.append("Login: <input type='text' name='login' value='" + u.getLogin() + "'><br>");
        builder.append("E-mail: <input type='text' name='email' value='" + u.getEmail() + "'><br>");
        builder.append("<input type='hidden' name='id' value='" + id + "'>");
        builder.append("<input type='submit' value='Save user'>");
        builder.append("</form>");
        builder.append("</body>");
        builder.append("</html>");
        writer.append(builder.toString());
        writer.flush();
    }
}
