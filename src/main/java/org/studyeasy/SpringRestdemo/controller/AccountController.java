package org.studyeasy.SpringRestdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
public class AccountController {

    @GetMapping("/")
    public String demo(){
        return "Welcome to Project";
    }

    @GetMapping("/test")
    @Tag(name = "Test", description = "The Test API.")
    @SecurityRequirement(name = "manish-chaudhari")
    public String test(){
        return "Test Api";
    }

    
    
}
