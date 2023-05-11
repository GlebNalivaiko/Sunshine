package by.sunshine.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ProductDto {
    private String title;

    private Integer quantityInStock;

    private String images;

    private Double price;

    private String description;

    private Long categoryId;

    private double discount;

    private Map<String, String> characteristics;
}
