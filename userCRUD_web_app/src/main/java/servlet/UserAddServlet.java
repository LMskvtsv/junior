package servlet;

import logic.ValidateService;
import org.apache.log4j.Logger;
import persistent.User;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for /crud/users page.
 * Supports create operation with Users.
 * GET example: localhost:port/crud/users/add
 * POST example: localhost:port/crud/users?action=add&name=ivan&login=puffy
 */
public class UserAddServlet extends HttpServlet {

    private final ValidateService validateService = ValidateService.getSingletonInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = new User(request.getParameter("name"), request.getParameter("login"), request.getParameter("email"));
        if (validateService.add(user)) {
            response.sendRedirect(String.format("%s/index.jsp", request.getContextPath()));
        } else {
            response.sendRedirect(String.format("%s/AddingError.jsp", request.getContextPath()));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(String.format("%s/UserAdd.jsp", request.getContextPath()));
    }
}
