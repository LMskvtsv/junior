package servlet;

import dbcontrol.ValidateService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for /crud/users page.
 * Supports  read, delete operations with Users.
 * GET example: localhost:port/crud/users/list
 * POST example: localhost:port/crud/users/list?action=delete&id=44j6j
 */
public class UserListServlet extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(UserListServlet.class);
    private final  ValidateService validateService = ValidateService.getValidateService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("users", validateService.findAll());
        request.getRequestDispatcher("/WEB-INF/views/UsersList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        String userID = request.getParameter("id");
        if (new DispatchAction().init().getAction(action).equals(Action.DELETE)) {
            if (validateService.delete(userID)) {
                response.sendRedirect(String.format("%s/", request.getContextPath()));
            } else {
                request.getRequestDispatcher("/WEB-INF/views/DeletingError.jsp").forward(request, response);
            }
        }
    }
}
