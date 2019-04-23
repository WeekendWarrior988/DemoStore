package lt.bta.Demo.api.services;

import lt.bta.Demo.api.Dao;
import lt.bta.Demo.jpa.entities.InvLine;
import lt.bta.Demo.jpa.entities.Invoice;
import lt.bta.Demo.jpa.entities.Product;
import lt.bta.Demo.requests.AddCartLineRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/invoice")
public class InvoiceService extends BaseService<Invoice> {
    @Context
    private HttpServletRequest servletRequest;

    @Override
    protected Class<Invoice> getEntityClass() {
        return Invoice.class;
    }

    @Override
    public Response list(int size, int skip) {
        HttpSession session = servletRequest.getSession();
        if (session.getAttribute("user") != null) {
            return super.list(size, skip);
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
//        return super.list(size, skip);
    }

    @GET
    @Path("/{id}/f")
    public Response getFull(@PathParam("id") int id){
        Dao<Invoice> dao = createDao();

        Invoice entity = dao.read(id, Invoice.GRAPH_LINE);

        if(entity == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(entity).build();
    }

    @POST
    @Path("/{id}")
    public Response addInvoiceLine(@PathParam("id") int id, AddCartLineRequest addCartLineRequest) {
        Dao<Invoice> dao = createDao();
        Invoice invoice = dao.read(id);
        if(invoice == null) return Response.status(Response.Status.NOT_FOUND).build();

        Dao<Product> productDao = new Dao<>(Product.class);
        Product product = productDao.read(addCartLineRequest.getId());
        if(product == null) return Response.status(Response.Status.NOT_FOUND).build();

        InvLine invLine = new InvLine();
        invLine.setInvoice(invoice);
        invLine.setQty(addCartLineRequest.getQty());
        invLine.setProduct(product);
        invLine.setPrice(product.getPrice());

        invoice.getLines().add(invLine);
        invoice = dao.update(invoice);

        return Response.ok(invoice).build();
    }
}
