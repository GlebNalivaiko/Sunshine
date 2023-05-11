package by.sunshine.service;

import by.sunshine.entity.Cart;
import by.sunshine.entity.Product;
import by.sunshine.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    public Cart findById(Long id) {
        return cartRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Cart getCart(Long cartId, Set<Long> productIds) {
        Cart cart;
        if (cartId != null) cart = findById(cartId);
        else {
            cart = new Cart();
            cart.setProducts(productService.findAllById(productIds));
        }
        return cart;
    }

    @Transactional
    public void saveProductToCart(Long cartId, Long id) {
        if (cartId != null) {
            Product product = productService.findById(id);
            Cart cart = findById(cartId);
            cart.getProducts().add(product);
        }
    }


    @Transactional
    public void deleteProductFromCart(Long cartId, Long productId) {
        if (cartId != null) {
            Cart cart = findById(cartId);
            cart.getProducts().remove(productService.findById(productId));
        }
    }

    @Transactional
    public void deleteProductFromCart(Long cartId, Set<Product> products) {
        Cart cart = findById(cartId);
        products.forEach(cart.getProducts()::remove);
    }

    public Cart createCart(Set<Long> productIds) {
        Cart cart = new Cart();
        cart.setProducts(productService.findAllById(productIds));
        return cart;
    }
}
