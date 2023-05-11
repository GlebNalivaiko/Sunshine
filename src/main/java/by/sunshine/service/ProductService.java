package by.sunshine.service;

import by.sunshine.entity.Category;
import by.sunshine.entity.Product;
import by.sunshine.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private static final int PRODUCTS_ON_PAGE = 5;
    private final ProductRepository productRepository;

    public Set<Product> findAllById(Iterable<Long> ids) {
        return new HashSet<>(productRepository.findAllById(ids));
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Page<Product> paginateProduct(Category category, Integer activePage) {
        Pageable pageable = PageRequest.of(activePage, PRODUCTS_ON_PAGE);
        return productRepository.findAllByCategory(pageable, category);
    }
}
