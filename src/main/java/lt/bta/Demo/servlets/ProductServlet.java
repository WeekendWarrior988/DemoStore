package lt.bta.Demo.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/a/product")
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


//        Dao<Product> productDao = new Dao<>();
//        PrintWriter writer = resp.getWriter();
//        List<Product> products = productDao.list(Product.class, 2, 1);
//        writer.println("<h1>"+products+"</h1>");
//        products.forEach(writer::println);

//        resp.setContentType("application/json");
//        resp.setCharacterEncoding("utf-8");
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        //graziai uzregistruojam datas, be papildomu par, sitos 2 eilutes
//        mapper.registerModule(new JavaTimeModule());
//        mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
//
//        List<Product> products = productDao.list(Product.class, 2, 1);
//
//        mapper.writeValue(resp.getWriter(), products);
    }
}
