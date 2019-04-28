package lt.bta.Demo.api.services;

import lt.bta.Demo.api.Dao;
import lt.bta.Demo.filters.AccessRoles;
import lt.bta.Demo.filters.Role;
import lt.bta.Demo.jpa.entities.Cart;
import lt.bta.Demo.jpa.entities.CartLine;
import lt.bta.Demo.jpa.entities.Product;
import lt.bta.Demo.jpa.entities.User;
import lt.bta.Demo.requests.AddCartLineRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;



@Path("/cart")
public class CartService extends BaseService<Cart> {
    @Context
    private HttpServletRequest servletRequest;

    @Override
    protected Class<Cart> getEntityClass() {
        return Cart.class;
    }

    @POST
    @Path("/new")
    public Response addCart(Cart cart) {
        HttpSession session = servletRequest.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        try (Dao<Cart> dao = createDao()) {
            dao.read(user.getId());
            cart.setUser(user);
            dao.create(cart);
        }
        return Response.ok(cart).build();
    }

    @GET
    @Path("/{id}/f")
    public Response getFull(@PathParam("id") int id) {
        try (Dao<Cart> dao = createDao()) {

            Cart entity = dao.read(id, Cart.GRAPH_CART_LINES);

            if (entity == null) return Response.status(Response.Status.NOT_FOUND).build();
            return Response.ok(entity).build();
        }
    }

    @GET
    public Response getCart() {
        HttpSession session = servletRequest.getSession();
        return Response.ok(session.getAttribute("cart")).build();
    }

    @PUT
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
            cart.getCartLines().add(cartLine);
        }

        return Response.ok(cart).build();
    }

    @AccessRoles({Role.USER, Role.ADMIN})
    @PUT
    @Path("/update/{id}")
    public Response addCartLine(@PathParam("id") int cartId, AddCartLineRequest addCartLineRequest) {

        int productId = addCartLineRequest.getId();
        int qty = addCartLineRequest.getQty();

        Dao<Product> productDao = new Dao<>(Product.class);
        Product product = productDao.read(productId);
        if (product == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        try (Dao<Cart> cartDao = createDao()) {

            Cart cart = cartDao.read(cartId);
            if (cart == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }


            boolean isCartLine = false;
            for (CartLine cartLine : cart.getCartLines()) {

                if (cartLine.getProduct().getId() == product.getId()) {

                    cartLine.setQty(cartLine.getQty() + qty);
                    isCartLine = true;
                    break;
                }
            }

            if (!isCartLine) {

                CartLine cartLine = new CartLine();
                cartLine.setCart(cart);
                cartLine.setQty(qty);
                cartLine.setProduct(product);
                cart.getCartLines().add(cartLine);
            }

            cart = cartDao.update(cart);
            return Response.ok(cart).build();
        }
    }

    @AccessRoles({Role.USER, Role.ADMIN})
    @PUT
    @Path("/sync")
    public Response syncCart() {

        HttpSession session = servletRequest.getSession();

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Object obj = session.getAttribute("cart");
        Cart sessionCart;
        if (obj instanceof Cart) {
            sessionCart = (Cart) obj;
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try (Dao<Cart> cartDao = createDao()) {

            Cart userCart = cartDao.read(user.getId(), Cart.GRAPH_CART_LINES);

            if (userCart.getCartLines() == null) {
                userCart.setCartLines(new LinkedHashSet<>());
            } else {
                userCart.getCartLines().clear();
            }

            userCart.getCartLines().addAll(sessionCart.getCartLines());
            userCart.getCartLines().forEach(x -> x.setCart(userCart));
            userCart.setSum(sessionCart.getSum());
            cartDao.update(userCart);
            return Response.ok(userCart).build();
        }
    }

    @DELETE
    @Path("/deleteLine/{id}")
    public Response deleteCart(@PathParam("id") int id) {
        HttpSession session = servletRequest.getSession();

        Object obj = session.getAttribute("cart");
        Cart cart;
        if (obj instanceof Cart) {
            cart = (Cart) obj;
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        for (CartLine cartLine : cart.getCartLines()) {

            if (cartLine.getProduct().getId() == id) {

                cart.getCartLines().remove(cartLine);
                break;
            }
        }
        return Response.ok().build();
    }
}
