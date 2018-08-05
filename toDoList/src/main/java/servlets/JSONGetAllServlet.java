package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.domain.Item;
import ru.service.validation.Validation;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

public class JSONGetAllServlet extends HttpServlet {

    private final Validation validation = new Validation();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Item item = new Item();
        item.setDescription(request.getParameter("description"));
        item.setCreated(new Timestamp(System.currentTimeMillis()));
        if (request.getParameter("action").equals("add")) {
            item.setIsDone(0);
            Item itemCreated = validation.validateAndCreate(item);
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            response.setContentType("text/json");
            PrintWriter writer = new PrintWriter(response.getOutputStream());
            writer.append(gson.toJson(itemCreated));
            writer.flush();
        } else {
            item.setId(Integer.valueOf(request.getParameter("id")));
            item.setIsDone(Integer.valueOf(request.getParameter("isDone")));
            validation.validateAndUpdate(item);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        List<Item> list = validation.getAll();
        String json = gson.toJson(list);
        resp.setContentType("text/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(json);
        writer.flush();
    }
}
