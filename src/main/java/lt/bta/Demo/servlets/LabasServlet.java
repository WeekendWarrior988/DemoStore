package lt.bta.Demo.servlets;


import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.*;

@WebServlet(name = "Labas", urlPatterns = "/a/table")
public class LabasServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//       PrintWriter out = resp.getWriter();
//       out.println("<h1>YES</h1>");

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String title = "Database Result";

        out.println(
                "<html>\n" +
                        "<head><title>" + title + "</title></head>\n" +
                        "<body>\n" +
                        "<h1 align = \"center\">" + title + "</h1>\n");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/store?user=root&password=");

            // Execute SQL query
            Statement stmt = conn.createStatement();
            String sql = "SELECT id, city, name FROM clients";
            ResultSet rs = stmt.executeQuery(sql);

            out.println("<table align = \"center\">");
            out.println("<tr>" +
                    "    <th>ID</th>" +
                    "    <th>city</th> " +
                    "    <th>name</th>" +
                    "  </tr");
            // Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                String city = rs.getString("city");
                String name = rs.getString("name");

                //Display values
                out.println("<tr>" +
                        "<td>" + id + "</td>" +
                        "<td>" + city + "</td>" +
                        "<td>" + name + "</td>" +
                        "</tr>");
            }
            out.println("</table>");
            out.println("</body></html>");

            // Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
