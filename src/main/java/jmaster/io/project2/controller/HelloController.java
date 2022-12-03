package jmaster.io.project2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @Autowired
    MessageSource messageSource;
    @GetMapping("/hello")


    public String hello() {

        return "hello.html";
    }
}

