package lt.bta.Demo.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "invoices")
@NamedEntityGraph(
        name = Invoice.GRAPH_LINE,
        attributeNodes = @NamedAttributeNode("lines")
)
public class Invoice {

    public static final String GRAPH_LINE = "invoice-lines";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;

    private String number;

    private BigDecimal sum;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

//    @JsonIgnore
    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<InvLine> lines;

    public Set<InvLine> getLines() {
        return lines;
    }

    public void setLines(Set<InvLine> lines) {
        this.lines = lines;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void addLine(InvLine line) {
        line.setInvoice(this);
        getLines().add(line);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", date=" + date +
                ", number='" + number + '\'' +
                ", sum=" + sum +
                ", client=" + client +
                ", lines=" + lines +
                '}';
    }
}