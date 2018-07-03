package servlet;

import logic.ValidateService;
import org.apache.log4j.Logger;
import persistent.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Servlet for /crud/users page.
 * Supports  read, delete operations with Users.
 * GET example: localhost:port/crud/users/list
 * POST example: localhost:port/crud/users/list?action=delete&id=44j6j
 */
public class UserListServlet extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(UserListServlet.class);
    private final ValidateService validateService = ValidateService.getSingletonInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html>");
        builder.append("<html lang=\"en\">");
        builder.append("<head>");
        builder.append("<meta charset=\"UTF-8\">");
        builder.append("<title>Users</title>");
        builder.append("</head>");
        builder.append("<body>");
        builder.append("<form action='" + req.getContextPath() + "/users/add' method='get'><input type=\"submit\" value=\"Add new user\"></form><br>");
        builder.append("<table>");
        if (validateService.findAll().size() > 0) {
            for (Map.Entry<String, User> entry: validateService.findAll().entrySet()) {
                builder.append("<tr>");
                builder.append("<td>");
                builder.append(entry.getValue().getName());
                builder.append("</td>");
                builder.append("<td>");
                builder.append(entry.getValue().getLogin());
                builder.append("</td>");
                builder.append("<td>");
                builder.append(entry.getValue().getEmail());
                builder.append("</td>");
                builder.append("<td>");
                builder.append("<form action=\"" + req.getContextPath() + "/users/edit\" method=\"get\"><input type='submit' value='edit' name='action'><input type=\"hidden\" name=\"id\" value=\"" + entry.getValue().getId() + "\"/></form>");
                builder.append("</td>");
                builder.append("<td>");
                builder.append("<form action=\"" + req.getContextPath() + "/users/list\" method=\"post\"><input type='submit' value='delete' name='action'><input type=\"hidden\" name=\"id\" value=\"" + entry.getValue().getId() + "\"/></form>");
                builder.append("</td>");
                builder.append("</tr>");
            }
        } else {
            builder.append("<tr>");
            builder.append("<td>");
            builder.append("Users not found. You can add one :)");
            builder.append("</td>");
        }
        builder.append("</table>");
        builder.append("</body>");
        builder.append("</html>");
        writer.append(builder.toString());
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        String userID = req.getParameter("id");
        if (new DispatchAction().init().getAction(action).equals(Action.DELETE)) {
            if (validateService.delete(userID)) {
                resp.sendRedirect("http://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/users/list");
            } else {
                writer.append(String.format("Error! User id %s was not deleted.", userID));
                writer.flush();
            }
        }
    }
}
