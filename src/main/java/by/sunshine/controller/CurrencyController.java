package by.sunshine.controller;

import by.sunshine.dto.UserDto;
import by.sunshine.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("currency")
public class CurrencyController {

    private final CurrencyService currencyService;


    @GetMapping("{currency}")
    public String setCurrency(@PathVariable("currency") String currency,
                              @RequestHeader String referer,
                              @SessionAttribute("user") UserDto userDto) {
        currencyService.getValueOfCurrency(currency).ifPresent(value -> {
            userDto.setValueOfCurrency(value);
            userDto.setCurrency(currency);
        });
        return "redirect:" + referer;
    }
}
