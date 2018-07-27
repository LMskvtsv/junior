package servlet;

import dbcontrol.ValidateService;
import persistent.Role;
import persistent.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for /crud/add page.
 * Supports create operation with Users.
 * GET example: localhost:port/crud/add
 * POST example: localhost:port/crud/add?action=add&name=ivan&login=puffy
 */
public class UserAddServlet extends HttpServlet {

    private final ValidateService validateService = ValidateService.getValidateService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = new User(request.getParameter("name"),
                request.getParameter("login"),
                request.getParameter("email"),
                request.getParameter("password"),
                new Role(Integer.valueOf(request.getParameter("role_id"))));
        user.setCity(request.getParameter("city"));
        user.setCountry(request.getParameter("country"));
        if (validateService.add(user)) {
            response.sendRedirect(String.format("%s/", request.getContextPath()));
        } else {
            request.getRequestDispatcher("/WEB-INF/views/AddingError.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/views/UserAdd.jsp").forward(request, response);
    }
}
