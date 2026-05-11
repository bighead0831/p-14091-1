package com.back;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping("/people")
    @ResponseBody
    public String people() {
        long count = personService.count();

        return "people // 사람 수 : %d명".formatted(count);
    }
}