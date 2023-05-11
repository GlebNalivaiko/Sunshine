package by.sunshine.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String opinion;

    private Integer stars;

    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @Column(name = "name_of_user")
    private String nameOfUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product")
    private Product product;
}