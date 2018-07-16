package servlet;

import logic.DataManipulation;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignInServlet extends HttpServlet {
    private final DataManipulation dataManipulation = DataManipulation.getDataManipulation();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        User activeUser = dataManipulation.findUserByCretentials(login, password);
        if (activeUser.getId() != 0 && activeUser.getLogin() != null) {
            HttpSession session = request.getSession();
            synchronized (session) {
                session.setAttribute("login", login);
                session.setAttribute("role", activeUser.getRoleID());
            }
            response.sendRedirect(String.format("%s/", request.getContextPath()));
        } else {
            request.setAttribute("error", "Incorrect login or password! Try again.");
            doGet(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/SignIn.jsp").forward(request, response);
    }
}
