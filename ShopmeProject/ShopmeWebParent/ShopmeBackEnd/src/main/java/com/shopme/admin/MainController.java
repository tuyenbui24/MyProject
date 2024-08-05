package com.shopme.admin;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
public class MainController {

    @GetMapping("/")
    public String viewHomePage() {
        return "index";
    }
}
