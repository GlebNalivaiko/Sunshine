package by.sunshine.converter;

import by.sunshine.dto.ProductDto;
import by.sunshine.entity.Image;
import by.sunshine.entity.Product;
import by.sunshine.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductConverter {

    private final CategoryService categoryService;


    public Product convert(ProductDto productDto) {
        Product product = new Product();
        product.setQuantityInStock(productDto.getQuantityInStock());
        product.setDescription(productDto.getDescription());
        product.setDiscount(productDto.getDiscount());
        product.setPrice(productDto.getPrice());
        product.setCharacteristics(productDto.getCharacteristics());
        product.setTitle(productDto.getTitle());
        product.setCategory(categoryService.findById(productDto.getCategoryId()));
        List<Image> images = Arrays.stream(productDto.getImages().split(" ")).map(Image::new).toList();
        product.setImages(images);
        product.setComments(new ArrayList<>());
        return product;
    }
}
