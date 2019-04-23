package lt.bta.Demo.jpa.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

// entity vardas pagal default yra klases vardas
@Entity
@Table(name = "clients")
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = Client.GRAPH_INVOICE_ONLY,
                attributeNodes = @NamedAttributeNode(value = "invoices")
        ),
        @NamedEntityGraph(
                name = Client.GRAPH_INVOICE,
                attributeNodes = @NamedAttributeNode(value = "invoices", subgraph = "invoice-lines"),
                subgraphs = {
                        @NamedSubgraph(
                                name = "invoice-lines",
                                attributeNodes = @NamedAttributeNode(value = "lines", subgraph = "line-product")
                        ),
                        @NamedSubgraph(
                                name = "line-product",
                                attributeNodes = @NamedAttributeNode("product")
                        )
                }
        )
})


@NamedQuery(
        name = Client.QUERY_BY_CITY,
        query = "SELECT c FROM Client c WHERE c.city = :city"
)
public class Client {
    public final static String GRAPH_INVOICE_ONLY = "graph.Client.invoices-only";
    public final static String GRAPH_INVOICE = "graph.Client.invoices";
    public final static String QUERY_BY_CITY = "graph.Client.by-city";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String city;

    private Integer discount;

    @JsonIgnore
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Invoice> invoices;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Collection<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Collection<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", discount=" + discount +
                '}';
    }
}
