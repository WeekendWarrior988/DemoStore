package lt.bta.Demo.api.services;

import lt.bta.Demo.filters.AccessRoles;
import lt.bta.Demo.filters.Role;
import lt.bta.Demo.jpa.entities.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/product")
public class ProductService extends BaseService<Product> {
    @Context
    private HttpServletRequest servletRequest;

    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }

    @AccessRoles({Role.USER, Role.ADMIN})
    @Override
    public Response list(int size, int skip) {
//        HttpSession session=servletRequest.getSession();
//        if (session.getAttribute("user")!=null){
//            return super.list(size,skip);

//        }
//        return Response.status(Response.Status.UNAUTHORIZED).build();
        return super.list(size, skip);
    }
}