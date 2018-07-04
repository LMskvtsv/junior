package servlet;

import logic.ValidateService;
import persistent.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for /crud/users page.
 * Supports update operations with Users. After saving data redirects on user/list page.
 * GET example: localhost:port/crud/users/edit
 * POST example: localhost:port/crud/users/edit?id=123jj5j&action=add&name=ivan&login=puffy
 */
public class UserEditServlet extends HttpServlet {

    private final ValidateService validateService = ValidateService.getSingletonInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String id = request.getParameter("id");
        if (validateService.update(id, name, login, email)) {
            response.sendRedirect(String.format("%s/index.jsp", request.getContextPath()));
        } else {
            response.sendRedirect(String.format("%s/UpdatingError.jsp", request.getContextPath()));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = validateService.findByID(request.getParameter("id"));
        if (user != null) {
            request.setAttribute("id", user.getId());
            request.setAttribute("name", user.getName());
            request.setAttribute("login", user.getLogin());
            request.setAttribute("email", user.getEmail());
            try {
                getServletContext().getRequestDispatcher("/UserEdit.jsp").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        } else {
            response.sendRedirect(String.format("%s/UpdatingError.jsp", request.getContextPath()));
        }
    }
}
