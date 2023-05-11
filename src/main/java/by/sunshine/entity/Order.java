package by.sunshine.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300)
    private String title;

    private Double price;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "is_commented")
    private boolean isCommented;

    @OneToOne(fetch = FetchType.EAGER)
    private Image image;

    private Integer quantity;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime orderDate;

    @Column(name = "arrival_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime arrivalDate;

    @Transient
    private Integer partOfWay;
}
