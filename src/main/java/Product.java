import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
@Setter
@Getter
@Data

public class Product {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int id;
    private String type;
    private double price;
    private boolean discount;
    private LocalDate createDate;

    public Product(String type, double price, boolean discount, LocalDate createDate) {
        this.id = count.incrementAndGet();
        this.type = type;
        this.price = price;
        this.discount = discount;
        this.createDate = createDate;
    }
}
