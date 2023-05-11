package by.sunshine.repository;

import by.sunshine.entity.Category;
import by.sunshine.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCategory(Pageable pageable, Category category);

    Integer countAllByCategory(Category category);//в чем отличие от countByCategory

    Page<Product> findAllByCategoryAndPriceBetween(Pageable pageable, Category category, int first, int second);

    Page<Product> findAllByCategoryAndAverageRatingBetween(Pageable pageable, Category category, Double first, Double second);

    Integer countAllByCategoryAndAverageRatingBetween(Category category, double first, double second);

    List<Product> findAllById(Iterable<Long> ids);
}
