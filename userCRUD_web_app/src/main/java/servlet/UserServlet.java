package servlet;

import logic.ValidateService;
import org.apache.log4j.Logger;
import persistent.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for /crud/users page.
 * Supports create, read, update, delete operations with Users.
 * GET example: localhost:port/crud/users
 * POST example: localhost:port/crud/users?action=add&name=ivan&login=puffy
 */
public class UserServlet extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(UserServlet.class);
    private final ValidateService validateService = ValidateService.getSingletonInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(validateService.findAll());
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        String userID = req.getParameter("user_id");
        if (new DispatchAction().init().getAction(action).equals(Action.ADD)) {
            User user = new User(req.getParameter("name"), req.getParameter("login"), req.getParameter("email"));
            if (validateService.add(user)) {
                writer.append(String.format("User %s was added.", user.toString()));
            } else {
                writer.append(String.format("Error! User %s was not added.", user.toString()));
            }
        } else if (new DispatchAction().init().getAction(action).equals(Action.UPDATE)) {
            String name = req.getParameter("name");
            String login = req.getParameter("login");
            String email = req.getParameter("email");
            if (validateService.update(req.getParameter("user_id"), name, login, email)) {
                writer.append(String.format("User id %s was updated successfully.", userID));
            } else {
                writer.append(String.format("Error! User id %s was not updated.", userID));
            }
        } else if (new DispatchAction().init().getAction(action).equals(Action.DELETE)) {
            if (validateService.delete(userID)) {
                writer.append(String.format("User id %s was deleted successfully.", userID));
            } else {
                writer.append(String.format("Error! User id %s was not deleted.", userID));
            }
        }
    }
}
