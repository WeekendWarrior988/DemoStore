package lt.bta.Demo.coments;

import lt.bta.Demo.jpa.entities.Invoice;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/invoice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface InvoiceServiceInterface {
    @POST
    Response add(Invoice e);

    @DELETE
    @Path("/{id}")
    Response delete(@PathParam("id") int id);

    @GET
    @Path("/{id}")
    Response get(@PathParam("id")int id);

    @PUT
    @Path("/{id}")
    Response update(@PathParam("id")int id, Invoice e);

    @GET
    @Path("/list")
    Response list(@QueryParam("size") @DefaultValue("10") int size, @QueryParam("skip") @DefaultValue("0") int skip);
}
