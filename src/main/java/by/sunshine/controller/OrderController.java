package by.sunshine.controller;

import by.sunshine.dto.CommentDto;
import by.sunshine.dto.UserDto;
import by.sunshine.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final CommentService commentService;

    @PostMapping("/{order_id}/comment")
    public String comment(@PathVariable(name = "order_id") Long productId,
                          @RequestHeader String referer,
                          @SessionAttribute("user") UserDto userDto,
                          CommentDto commentDto) {
        commentService.save(commentDto, userDto, productId);
        return "redirect:" + referer;
    }
}
