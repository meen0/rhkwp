package com.ohgiraffers.springdatajpa.main.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @GetMapping(value = {"/","/main"})
    public String main(){
        return "main/main";
    }
}
