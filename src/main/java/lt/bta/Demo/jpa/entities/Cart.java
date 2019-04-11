package lt.bta.Demo.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "carts")
@NamedEntityGraph(
        name = Cart.GRAPH_PRODUCTS,
        attributeNodes = @NamedAttributeNode("lines")
)
public class Cart {
    public static final String GRAPH_PRODUCTS = "cart-lines";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private BigDecimal sum;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    private int itemCount;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    //    @JsonIgnore
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartLine> lines;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Set<CartLine> getLines() {
        return lines;
    }

    public void setLines(Set<CartLine> lines) {
        this.lines = lines;
    }

    public void addLine(CartLine line) {
        line.setCart(this);
        getLines().add(line);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", sum=" + sum +
                ", user=" + user +
                ", lines=" + lines +
                '}';
    }
}
