package lt.bta.Demo.api;

import lt.bta.Demo.api.services.CartService;
import lt.bta.Demo.api.services.InvoiceService;
import lt.bta.Demo.api.services.ProductService;
import lt.bta.Demo.api.services.UserService;
//import lt.bta.Demo.filters.AuthenticationFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.ws.rs.ApplicationPath;


@ApplicationPath("/api")
public class App extends ResourceConfig {

    public App() {
        register(ObjectMapperContextResolver.class);
        register(InvoiceService.class);
        register(ProductService.class);
        register(UserService.class);
//        register(AuthenticationFilter.class);
        register(CartService.class);
        property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
    }
}