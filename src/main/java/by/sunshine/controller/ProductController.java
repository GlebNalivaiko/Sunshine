package by.sunshine.controller;

import by.sunshine.converter.ProductConverter;
import by.sunshine.dto.ProductDto;
import by.sunshine.entity.Category;
import by.sunshine.entity.Product;
import by.sunshine.service.CategoryService;
import by.sunshine.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;
    private final ProductConverter productConverter;

    @GetMapping("{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "product";
    }

    @GetMapping("/{id}/{activePage}")
    public String findProductsByCategory(@PathVariable(name = "id") Long id,
                                         @PathVariable(name = "activePage") Integer activePage,
                                         Model model) {
        Category category = categoryService.findById(id);
        Page<Product> page = productService.paginateProduct(category, activePage);
        model.addAttribute("page", page);
        model.addAttribute("category", category);
        return "products_of_category";
    }

    @PostMapping("admin")
    public @ResponseBody ProductDto product(@RequestBody ProductDto productDto) {
        productService.save(productConverter.convert(productDto));
        return productDto;
    }
}