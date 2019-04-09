package lt.bta.Demo.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Table(name = "users", indexes = @Index(columnList = ("username"), unique = true))
//@NamedEntityGraphs({
//        @NamedEntityGraph(
//                name = User.GRAPH_CART_ONLY,
//                attributeNodes = @NamedAttributeNode(value = "carts")
//        ),
//        @NamedEntityGraph(
//                name = User.GRAPH_CART_LINES,
//                attributeNodes = @NamedAttributeNode(value = "carts", subgraph = "cart-lines"),
//                subgraphs = {
//                        @NamedSubgraph(
//                                name = "cart-lines",
//                                attributeNodes = @NamedAttributeNode(value = "lines", subgraph = "line-product")
//                        ),
//                        @NamedSubgraph(
//                                name = "line-product",
//                                attributeNodes = @NamedAttributeNode("product")
//                        )
//                }
//        )
//})
public class User {

//    public final static String GRAPH_CART_ONLY = "graph.User.carts-only";
//    public final static String GRAPH_CART_LINES = "graph.User.carts";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String secret;

    private String role;

//    @JsonIgnore
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private Collection<Cart> carts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

//    public Collection<Cart> getCarts() {
//        return carts;
//    }
//
//    public void setCarts(Collection<Cart> carts) {
//        this.carts = carts;
//    }
}
