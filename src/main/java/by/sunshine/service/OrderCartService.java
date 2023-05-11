package by.sunshine.service;

import by.sunshine.dto.UserDto;
import by.sunshine.entity.Cart;
import by.sunshine.entity.Order;
import by.sunshine.entity.OrderCart;
import by.sunshine.entity.Product;
import by.sunshine.repository.OrderCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderCartService {

    private final OrderCartRepository orderCartRepository;
    private final ProductService productService;
    private final CartService cartService;
    private final OrderService orderService;


    @Transactional
    public OrderCart findById(Long id) {
        OrderCart orderCart = orderCartRepository.findById(id).orElseThrow();
        orderCart.getOrders().forEach(orderService::updatePartOfWay);
        return orderCart;
    }


    @Transactional
    public void makeOrder(Map<Long, Integer> productsAndQuantity, UserDto userDto) {
        Set<Long> ids = productsAndQuantity.keySet();
        Set<Product> products = productService.findAllById(ids);
        List<Order> orders = new ArrayList<>();
        products.forEach(product -> {
                    int quantity = productsAndQuantity.get(product.getId());
                    if (product.getQuantityInStock() < quantity) throw new NoSuchElementException();
                    product.setQuantityInStock(product.getQuantityInStock() - quantity);
                    orders.add(orderService.createOrder(product, quantity));
                }
        );
        OrderCart orderCart = orderCartRepository.findById(userDto.getOrderCartId()).orElseThrow();
        orderCart.getOrders().addAll(orders);
        userDto.getProductIds().removeAll(ids);
        cartService.deleteProductFromCart(userDto.getCartId(), products);
    }

    @Transactional
    public OrderCart confirmOrder(Long orderId, UserDto userDto) {
        OrderCart orderCart = orderCartRepository.findById(userDto.getOrderCartId()).orElseThrow();
        orderCart.getOrders().stream().filter(ord -> ord.getId().equals(orderId)).findAny().ifPresent(order -> {
            orderService.updatePartOfWay(order);
            if (order.getPartOfWay() == 4) {
                orderCart.getOrders().remove(order);
                orderService.delete(order);
            }
        });
        orderCart.getOrders().forEach(orderService::updatePartOfWay);
        return orderCart;
    }

    @Transactional
    public OrderCart cancelOrder(Long orderId, UserDto userDto) {
        OrderCart orderCart = orderCartRepository.findById(userDto.getOrderCartId()).orElseThrow();
        orderCart.getOrders().stream().filter(ord -> ord.getId().equals(orderId)).findAny().ifPresent(order -> {
            orderService.updatePartOfWay(order);
            if (order.getPartOfWay() == 1) {
                Product product = productService.findById(order.getProductId());
                product.setQuantityInStock(product.getQuantityInStock() + order.getQuantity());
                orderCart.getOrders().remove(order);
                userDto.getProductIds().add(order.getProductId());
                Cart cart = cartService.findById(userDto.getCartId());
                cart.getProducts().add(product);
                orderService.delete(order);
            }
        });
        orderCart.getOrders().forEach(orderService::updatePartOfWay);
        return orderCart;
    }
}
