package lt.bta.Demo.api.services;

import lt.bta.Demo.requests.CredentialsRequest;
import lt.bta.Demo.helpers.JWTHelper;
import lt.bta.Demo.api.Dao;
import lt.bta.Demo.jpa.entities.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserService {

    @Context
    private HttpServletRequest servletRequest;

    @GET
    @Path("/genkey/{bits}")
    public Response genkey(@PathParam("bits") int bits) {
        if (bits % 8 != 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Must be full bytes length, i.e. N x 8, example: 256 or 384 or 512").build();
        }

        Random random = new Random();
        byte[] bytes = new byte[bits / 8];
        random.nextBytes(bytes);
        return Response.ok().entity(DatatypeConverter.printBase64Binary(bytes)).build();
    }

    @POST
    @Path("/create")
    public Response create(CredentialsRequest userRequest) {
        Dao<User> userDao = new Dao<>(User.class);
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setSecret(BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt()));
        user.setRole(userRequest.getRole());
        userDao.create(user);
        return Response.ok().build();
    }

    @POST
    @Path("/login")
    public Response login(CredentialsRequest userRequest) {
        Dao<User> userDao = new Dao<>(User.class);

        List<User> users = userDao.findBy("username", userRequest.getUsername());

        if (users == null || users.size() != 1) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if (!BCrypt.checkpw(userRequest.getPassword(), users.get(0).getSecret())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

//        User user = users.get(0);
//
//        HttpSession session = servletRequest.getSession();
//        session.setAttribute("user", user);

        User user = users.get(0);
        String token = JWTHelper.createJWT("my-app",
                user.getId(), user.getUsername(), user.getRole(), 1000L * 60 * 60);

        return Response.ok().entity(Collections.singletonMap("token", token)).build();
//        return Response.ok().build();
    }

    @GET
    @Path("/logout")
    public Response logout() {
        HttpSession session = servletRequest.getSession(false);
        if (session != null) {
            session.removeAttribute("user");
        }
        return Response.ok().build();
    }
}
