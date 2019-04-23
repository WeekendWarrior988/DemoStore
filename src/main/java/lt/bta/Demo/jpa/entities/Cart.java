package lt.bta.Demo.jpa.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "carts")
@NamedEntityGraph(
        name = Cart.GRAPH_PRODUCTS,
        attributeNodes = @NamedAttributeNode("cartLines")
)
public class Cart {
    public static final String GRAPH_PRODUCTS = "graph.cart.lines";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private BigDecimal sum;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartLine> cartLines;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getSum() {
        BigDecimal cartSum = BigDecimal.ZERO;

        for (CartLine cartLine : cartLines) {
            if (cartLine.getProduct().getPrice() != null) {
                cartSum = cartSum.add(BigDecimal.valueOf(cartLine.getQty()).multiply(cartLine.getProduct().getPrice()));
            }
        }
        return cartSum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Set<CartLine> getCartLines() {
        return cartLines;
    }

    public void setCartLines(Set<CartLine> lines) {
        this.cartLines = lines;
    }

    public void addLine(CartLine line) {
        line.setCart(this);
        getCartLines().add(line);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", sum=" + sum +
                ", user=" + user +
                ", lines=" + cartLines +
                '}';
    }
}
