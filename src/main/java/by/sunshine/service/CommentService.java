package by.sunshine.service;

import by.sunshine.constant.Status;
import by.sunshine.converter.CommentConverter;
import by.sunshine.dto.CommentDto;
import by.sunshine.dto.UserDto;
import by.sunshine.entity.Comment;
import by.sunshine.entity.Order;
import by.sunshine.entity.OrderCart;
import by.sunshine.entity.Product;
import by.sunshine.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    private final OrderService orderService;

    private final ProductService productService;

    private final OrderCartService orderCartService;

    private final CommentConverter commentConverter;

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void save(CommentDto commentDto, UserDto userDto, Long orderId) {
        OrderCart orderCart = orderCartService.findById(userDto.getOrderCartId());
        Order order = orderService.findById(orderId);
        orderService.updatePartOfWay(order);
        if (!orderCart.getOrders().contains(order) | order.isCommented() | order.getPartOfWay() != Status.ARRIVED)
            throw new NoSuchElementException();
        Product product = productService.findById(order.getProductId());
        Comment comment = commentConverter.convert(commentDto, userDto.getName());
        comment.setProduct(product);
        order.setCommented(true);
        commentRepository.save(comment);
    }
}
