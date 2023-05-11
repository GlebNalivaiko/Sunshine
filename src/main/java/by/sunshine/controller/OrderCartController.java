package by.sunshine.controller;

import by.sunshine.dto.CommentDto;
import by.sunshine.dto.UserDto;
import by.sunshine.service.OrderCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("order-cart")
public class OrderCartController {

    private final OrderCartService orderCartService;

    @GetMapping
    public String findById(Model model, @SessionAttribute("user") UserDto userDto) {
        model.addAttribute("orderCart", orderCartService.findById(userDto.getOrderCartId()));
        return "order_cart";
    }

    @PostMapping
    public @ResponseBody Map<Long, Integer> makeOrder(@RequestBody Map<Long, Integer> orders, @SessionAttribute("user") UserDto userDto) {
        orderCartService.makeOrder(orders, userDto);
        return orders;
    }

    @GetMapping("confirm/{id}")
    public String confirm(@PathVariable("id") Long orderId, @SessionAttribute("user") UserDto userDto, Model model) {
        model.addAttribute("orderCart", orderCartService.confirmOrder(orderId, userDto));
        return "order_cart";
    }

    @GetMapping("cancel/{orderId}")
    public String cancel(@PathVariable("orderId") Long orderId, Model model, @SessionAttribute("user") UserDto userDto) {
        model.addAttribute("orderCart", orderCartService.cancelOrder(orderId, userDto));
        return "order_cart";
    }

    @ModelAttribute(name = "comment")
    public CommentDto comment() {
        return new CommentDto();
    }
}
