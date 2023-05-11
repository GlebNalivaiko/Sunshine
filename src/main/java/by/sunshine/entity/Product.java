package by.sunshine.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300)
    private String title;

    @Column(name = "quantity_in_stock")
    private Integer quantityInStock;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Image> images;

    private Double price;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private Category category;

    private double discount;


    @Column(name = "quantity_of_feedbacks")
    private Integer quantityOfFeedbacks;

    @ElementCollection
    @CollectionTable(name = "product_characteristic", joinColumns = @JoinColumn(name = "product_id"))
    @MapKeyColumn(name = "details")
    @Column(name = "product_characteristics", length = 10_000)
    private Map<String, String> characteristics;

    @Column(name = "average_rating")
    private Double averageRating;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;


    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Product)) return false;
        return id.equals(((Product) other).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
