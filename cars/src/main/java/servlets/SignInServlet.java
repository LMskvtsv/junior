package servlets;

import logic.ValidateServiceUsers;
import ru.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignInServlet extends HttpServlet {
    private final ValidateServiceUsers validateServiceUsers = new ValidateServiceUsers();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        User activeUser = validateServiceUsers.getUserByCredentials(login, password);
        if (activeUser != null) {
            HttpSession session = request.getSession();
            synchronized (session) {
                session.setAttribute("login", login);
                session.setAttribute("userId", activeUser.getId());
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
