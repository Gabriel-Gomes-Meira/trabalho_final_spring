package br.com.dbc.vemser.trabalhofinal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Test {

    public Test() {
    }

    @GetMapping
    public String hello(){
        return "Hello World";
    }
}
