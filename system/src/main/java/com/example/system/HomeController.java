package com.example.system;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(path = "/")
    @ResponseBody
    public String home() {
        return "Foo Bar too";
    }

    @GetMapping(path = "/home")
    @ResponseBody
    public String homeTwo() {
        return "Foo Bar - the second";
    }

    @GetMapping(path = "/fp")
    @ResponseBody
    public String forgotPassword () {
        return "foo";
    }
}
