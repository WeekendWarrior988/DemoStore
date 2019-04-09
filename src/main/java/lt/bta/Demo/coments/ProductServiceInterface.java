package lt.bta.Demo.coments;

import lt.bta.Demo.jpa.entities.Invoice;
import lt.bta.Demo.jpa.entities.Product;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ProductServiceInterface {
    @POST
    Response add(Product p);

    @DELETE
    @Path("/{id}")
    Response delete(@PathParam("id") int id);

    @GET
    @Path("/{id}")
    Response get(@PathParam("id")int id);

    @PUT
    @Path("/{id}")
    Response update(@PathParam("id")int id, Product p);

    @GET
    @Path("/list")
    Response list(@QueryParam("size") @DefaultValue("10") int size, @QueryParam("skip") @DefaultValue("0") int skip);
}
