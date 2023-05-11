package by.sunshine.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@NoArgsConstructor
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "pix", length = Integer.MAX_VALUE)
    private String pixels;

    @Column(length = 100)
    private String description;

    public Image(String pixels) {
        this.pixels = pixels;
    }
}
