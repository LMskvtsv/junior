package servlet;

import logic.ValidateService;
import persistent.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for /crud/edit page.
 * Supports update operations with Users. After saving data redirects on crud/ page.
 * GET example: localhost:port/crud/edit
 * POST example: localhost:port/crud/edit?id=123jj5j&action=add&name=ivan&login=puffy
 */
public class UserEditServlet extends HttpServlet {

    private final ValidateService validateService = ValidateService.getSingletonInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String id = request.getParameter("id");
        if (validateService.update(id, name, login, email)) {
            response.sendRedirect(String.format("%s/", request.getContextPath()));
        } else {
            request.getRequestDispatcher("/WEB-INF/views/UpdatingError.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = validateService.findByID(request.getParameter("id"));
        if (user != null) {
            request.setAttribute("id", user.getId());
            request.setAttribute("name", user.getName());
            request.setAttribute("login", user.getLogin());
            request.setAttribute("email", user.getEmail());
            request.getRequestDispatcher("/WEB-INF/views/UserEdit.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/views/UpdatingError.jsp").forward(request, response);
        }
    }
}
