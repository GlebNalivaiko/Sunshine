package by.sunshine.service;

import by.sunshine.constant.Status;
import by.sunshine.entity.Order;
import by.sunshine.entity.Product;
import by.sunshine.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static by.sunshine.constant.Category.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public void delete(Order order) {
        orderRepository.delete(order);
    }

    public Order createOrder(Product product, Integer quantity) {
        Order order = new Order();
        order.setTitle(product.getTitle());
        order.setPrice(product.getPrice() * (1 - product.getDiscount()));
        order.setProductId(product.getId());
        order.setOrderDate(LocalDateTime.now().plusDays(0));
        order.setQuantity(quantity);
        order.setArrivalDate(getArrivalDate(product, quantity));
        order.setImage(product.getImages().get(0));
        return order;
    }

    private LocalDateTime getArrivalDate(Product product, Integer quantity) {
        LocalDateTime orderDate = LocalDateTime.now();
        int days = quantity;
        switch (product.getCategory().getName()) {
            case ELECTRONIC -> days += 20;
            case COSMETIC -> days += 15;
            case SPORT -> days += 23;
            case BOARD_GAMES -> days += 18;
            case MEN_CLOTHING -> days += 29;
            case WOMEN_CLOTHING -> days += 21;
            case HOUSEHOLD_ITEMS -> days += 9;
            case TOURISM -> days += 13;
            case PROPER_NUTRITION -> days += 5;
        }
        return orderDate.plusDays(days);
    }

    public void updatePartOfWay(Order order) {
        LocalDateTime orderDate = order.getOrderDate();
        LocalDateTime arrivalDate = order.getArrivalDate();
        int partOfWay;
        if (arrivalDate.isBefore(LocalDateTime.now())) partOfWay = Status.ARRIVED;
        else if (LocalDateTime.now().isBefore(orderDate.plusHours(5))) partOfWay = Status.PACKING;
        else if (LocalDateTime.now().isBefore(orderDate.plusDays(3).plusHours(5))) partOfWay = Status.SHIPPED;
        else partOfWay = Status.IN_RECIPIENT_COUNTRY;
        order.setPartOfWay(partOfWay);
    }
}
