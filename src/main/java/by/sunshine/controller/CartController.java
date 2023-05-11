package by.sunshine.controller;

import by.sunshine.dto.UserDto;
import by.sunshine.service.CartService;
import by.sunshine.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("cart")
@Slf4j
public class CartController {


    private final CartService cartService;
    private final UserService userService;

    @GetMapping()
    public String getCart(@SessionAttribute("user") UserDto userDto, Model model) {
        model.addAttribute("cart", cartService.getCart(userDto.getCartId(), userDto.getProductIds()));
        return "cart";
    }

    @GetMapping("{product_id}")
    public String save(@PathVariable("product_id") Long productId,
                       @RequestHeader String referer,
                       @SessionAttribute("user") UserDto userDto) {
        userService.saveProductToCart(userDto, productId);
        return "redirect:" + referer;
    }

    @GetMapping("delete/{product_id}")
    public String delete(@PathVariable("product_id") @NonNull Long productId,
                         @RequestHeader String referer,
                         @SessionAttribute("user") UserDto userDto) {
        userService.deleteProductFromCart(userDto, productId);
        return "redirect:" + referer;
    }
}