package servlets.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import logic.ValidateServiceCars;
import ru.domain.Car;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class JSONAllCarsServlet extends HttpServlet {
    private final ValidateServiceCars validateServiceCars = new ValidateServiceCars();
    private final static Logger LOGGER = Logger.getLogger(JSONAllCarsServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        List<Car> list = validateServiceCars.getAll();
        String json = gson.toJson(list);
        LOGGER.info(json);
        resp.setContentType("text/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(json);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
