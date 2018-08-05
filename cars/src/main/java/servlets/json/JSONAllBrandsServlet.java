package servlets.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dbcontrol.BrandControl;
import ru.domain.Brand;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class JSONAllBrandsServlet extends HttpServlet {
    private final BrandControl brandControl = new BrandControl();
    private final static Logger LOGGER = Logger.getLogger(JSONAllBrandsServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        List<Brand> list = brandControl.getAll();
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
