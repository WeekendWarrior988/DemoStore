package lt.bta.Demo.api.services;

import lt.bta.Demo.api.Dao;
import lt.bta.Demo.filters.AccessRoles;
import lt.bta.Demo.filters.Role;
import lt.bta.Demo.jpa.entities.Cart;
import lt.bta.Demo.jpa.entities.CartLine;
import lt.bta.Demo.jpa.entities.Product;
import lt.bta.Demo.jpa.entities.User;
import lt.bta.Demo.requests.AddInvoiceLineRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/cart")
public class CartService extends BaseService<Cart> {
    @Context
    private HttpServletRequest servletRequest;

    @Override
    protected Class<Cart> getEntityClass() {
        return Cart.class;
    }

//    @POST
//    @Override
//    public Response add(CartItem cart) {
//        HttpSession session = servletRequest.getSession();
//        User user = session.getAttribute("user");
//
//
//        Dao<Cart> dao =createDao();
//        cart.setUuid(UUID.randomUUID());
//        dao.create(cart);
//        return Response.ok(cart).build();
//    }

    @POST
    @Override
    public Response add(Cart cart) {
        Dao<Cart> dao =createDao();
        cart.setUuid(UUID.randomUUID());
        dao.create(cart);
        return Response.ok(cart).build();
    }

//    @GET
//    @Path("/{id}/f")
//    public Response getFull(@PathParam("id") int id){
//        Dao<Cart> dao = createDao();
//
//        Cart entity = dao.read(id, Cart.GRAPH_PRODUCTS);
//
//        if(entity == null) return Response.status(Response.Status.NOT_FOUND).build();
//        return Response.ok(entity).build();
//    }
//
    @POST
    @Path("/{id}")
    public Response addCartLine(@PathParam("id") int id, AddInvoiceLineRequest addInvoiceLineRequest) {
        Dao<Cart> dao = createDao();
        Cart cart = dao.read(id);
        if(cart == null) return Response.status(Response.Status.NOT_FOUND).build();

        Dao<Product> productDao = new Dao<>(Product.class);
        Product product = productDao.read(addInvoiceLineRequest.getId());
        if(product == null) return Response.status(Response.Status.NOT_FOUND).build();

        CartLine cartLine = new CartLine();
        cartLine.setCart(cart);
        cartLine.setQty(addInvoiceLineRequest.getQty());
        cartLine.setProduct(product);
        cartLine.setPrice(product.getPrice());

        cart.getLines().add(cartLine);
        cart = dao.update(cart);

        return Response.ok(cart).build();
    }
//
////    @AccessRoles({Role.USER, Role.ADMIN})
//    @Override
//    public Response listAll() {
//        Dao<Cart> dao = createDao();
//        return Response.ok().entity(dao.listAll()).build();
//    }
}
