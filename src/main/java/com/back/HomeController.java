package com.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    private final PersonService personService = new PersonService();

    @GetMapping("/")
    @ResponseBody
    public String main() {
        long count = personService.count();

        return "main // 사람 수 : %d명".formatted(count);
    }
}