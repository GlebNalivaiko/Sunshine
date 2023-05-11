package by.sunshine.controller;

import by.sunshine.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("admin")
public class AdminController {

    @GetMapping
    public String admin(Model model) {
        model.addAttribute("product", new Product());
        return "admin";
    }
}
