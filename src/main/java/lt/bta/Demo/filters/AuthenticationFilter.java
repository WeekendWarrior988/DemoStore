package lt.bta.Demo.filters;

import io.jsonwebtoken.Claims;
import lt.bta.Demo.helpers.JWTHelper;
import lt.bta.Demo.jpa.entities.User;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Context
    private ResourceInfo resourceInfo;

    @Context
    private HttpServletRequest servletRequest;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Method method = resourceInfo.getResourceMethod();

//        boolean isAnnotated = method.isAnnotationPresent(AccessRoles.class)) return;
        if (!method.isAnnotationPresent(AccessRoles.class)) return;


        // Get the Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

//        if (!isTokenBasedAuthentication(authorizationHeader)) {
//            if (isAnnotated)requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//            return;
//        }

        // Extract the token from the Authorization header
        String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

        // Check if token is valid?
        Claims claims;
        try {
            claims = JWTHelper.decodeJWT(token);
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid token").build());
            return;
        }

        //Verify user access:

        // 1) get user role from token
        String userRole = claims.get("role", String.class);

        // 2) get roles from annotation
        AccessRoles accessRoles = method.getAnnotation (AccessRoles.class);
        Role[] roles = accessRoles.value();

        // 3) check access, i.e. is user role in roles list
        if (Arrays.stream(roles).noneMatch(x -> x.name().equalsIgnoreCase(userRole))) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Insufficient privileges").build());
        }
    }
    //--


    // Check if the Authorization header is valid
    // It must not be null and must be prefixed with "Bearer" plus a whitespace
    // The authentication scheme comparison must be case-insensitive
    private boolean isTokenBasedAuthentication(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

//    @Override
//    public void filter(ContainerRequestContext requestContext) throws IOException {
//        Method method = resourceInfo.getResourceMethod();
//
//        if (!method.isAnnotationPresent(AccessRoles.class)) return;
//
//        HttpSession httpSession = httpServletRequest.getSession();
//        User user = (User) httpSession.getAttribute("user");
//        if (user == null) {
//            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//            return;
//        }
//
//        //Verify user access:
//
//        // 1) get user role from token
//        String userRole = user.getRole();
//
//        // 2) get roles from annotation
//        AccessRoles accessRoles = method.getAnnotation (AccessRoles.class);
//        Role[] roles = accessRoles.value();
//
//        // 3) check access, i.e. is user role in roles list
//        if (Arrays.stream(roles).noneMatch(x -> x.name().equalsIgnoreCase(userRole))) {
//            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
//                    .entity("Insufficient privileges").build());
//        }
}

