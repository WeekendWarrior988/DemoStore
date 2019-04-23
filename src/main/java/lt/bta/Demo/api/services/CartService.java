package lt.bta.Demo.api.services;

import lt.bta.Demo.api.Dao;
import lt.bta.Demo.jpa.entities.Cart;
import lt.bta.Demo.jpa.entities.CartLine;
import lt.bta.Demo.jpa.entities.Product;
import lt.bta.Demo.requests.AddCartLineRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Optional;

@Path("/cart")
public class CartService extends BaseService<Cart> {
    @Context
    private HttpServletRequest servletRequest;

    @Override
    protected Class<Cart> getEntityClass() {
        return Cart.class;
    }

    @Override
    public Response add(Cart cart) {

        try (Dao<Cart> dao = createDao()) {

            dao.create(cart);
            return Response.ok(cart).build();
        }
    }

    @GET
    @Path("/{id}/f")
    public Response getFull(@PathParam("id") int id) {
        try (Dao<Cart> dao = createDao()) {

            Cart entity = dao.read(id, Cart.GRAPH_PRODUCTS);

            if (entity == null) return Response.status(Response.Status.NOT_FOUND).build();
            return Response.ok(entity).build();
        }
    }

    @GET
    public Response getCart() {
        HttpSession session = servletRequest.getSession();
        return Response.ok(session.getAttribute("cart")).build();
    }

    @POST
    @Path("/add")
    public Response addCartline(AddCartLineRequest addCartLineRequest) {
        Dao<Product> productDao = new Dao<>(Product.class);
        Product product = productDao.read(addCartLineRequest.getId());
        if (product == null) return Response.status(Response.Status.NOT_FOUND).build();

        HttpSession session = servletRequest.getSession();

        Object obj = session.getAttribute("cart");
        Cart cart;
        if (obj instanceof Cart) {
            cart = (Cart) obj;
        } else {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        if (cart.getCartLines() == null) cart.setCartLines(new HashSet<>());

        Optional<CartLine> line = cart.getCartLines().stream()
                .filter(x -> x.getProduct().getId() == addCartLineRequest.getId())
                .findFirst();

        if (line.isPresent()) {
            CartLine cartLine = line.get();
            cartLine.setQty(cartLine.getQty() + addCartLineRequest.getQty());
        } else {
            CartLine cartLine = new CartLine();
            cartLine.setProduct(product);
            cartLine.setQty(addCartLineRequest.getQty());
            cartLine.setPrice(product.getPrice());
            cart.getCartLines().add(cartLine);
        }

        return Response.ok(cart).build();
    }



    @DELETE
    @Path("/deleteLine/{id}")
    public Response deleteCart(@PathParam("id") int id){
        HttpSession session = servletRequest.getSession();

        Object obj = session.getAttribute("cart");
        Cart cart;
        if (obj instanceof Cart) {
            cart = (Cart) obj;
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Optional<CartLine> line = cart.getCartLines().stream()
                .filter(x->x.getProduct().getId() == id)
                .findFirst();

        cart.getCartLines().remove(line);

        return Response.ok(cart).build();

    }

}
