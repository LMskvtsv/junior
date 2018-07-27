package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dbcontrol.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for /crud/add page.
 * Supports create operation with Users.
 * GET example: localhost:port/crud/add
 * POST example: localhost:port/crud/add?action=add&name=ivan&login=puffy
 */
public class CountriesServlet extends HttpServlet {

    private final ValidateService validateService = ValidateService.getValidateService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Gson gson = new GsonBuilder()
                          .setPrettyPrinting()
                        .create();
        String json = gson.toJson(validateService.getCountries());
        System.out.println(json);
        response.setContentType("text/json");
        PrintWriter writer = new PrintWriter(response.getOutputStream());
        writer.append(json);
        writer.flush();
    }
}
