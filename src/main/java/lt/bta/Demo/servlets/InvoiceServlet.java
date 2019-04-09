package lt.bta.Demo.servlets;

//@WebServlet(name = "invoice", urlPatterns = "/a/invoice")
//public class InvoiceServlet extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        resp.setContentType("application/json");
//        resp.setCharacterEncoding("utf-8");
//
//        String id = req.getParameter("id");
//
////        PrintWriter writer = resp.getWriter();
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        mapper.registerModule(new Hibernate5Module());
//        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

//        if (id == null) {
////            writer.println("{\"error\": \"Nenurodytas id\"}");
//            Response error = new Response("Nenurodytas id", 100);
//            mapper.writeValue(resp.getWriter(), error);
//            return;
//        }
//
//        Dao<Invoice> invoiceDao = new Dao<>(EntityManagerHelper.getEntityManager());
//        Invoice invoice = invoiceDao.read(Invoice.class, Integer.valueOf(id));
//        if (invoice == null) {
////            writer.println("Invoice su tokiu id " + id + " nerastas");
//            Response error = new Response("Invoice su tokiu id " + id + " nerastas", 110);
//            mapper.writeValue(resp.getWriter(), error);
//        } else {
////            writer.println(invoice.getDate() + " " + invoice.getNumber());
//            Response data = new Response(invoice);
//            mapper.writeValue(resp.getWriter(), data);
//        }
//    }
//}

